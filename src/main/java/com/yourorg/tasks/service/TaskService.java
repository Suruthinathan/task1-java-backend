package com.kaiburr.tasks.service;

import com.kaiburr.tasks.model.Task;
import com.kaiburr.tasks.model.TaskExecution;
import com.kaiburr.tasks.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.*;

@Service
public class TaskService {
    private final TaskRepository repo;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public TaskService(TaskRepository repo) { this.repo = repo; }

    public List<Task> findAll() { return repo.findAll(); }
    public Task findById(String id) { return repo.findById(id).orElse(null); }
    public Task save(Task t) { return repo.save(t); }
    public void deleteById(String id) { repo.deleteById(id); }
    public List<Task> findByNameContains(String s) { return repo.findByNameContainingIgnoreCase(s); }

    private void validateCommand(String cmd) {
        String lower = cmd.toLowerCase();
        String[] forbidden = {"rm ","sudo","shutdown","reboot",">", "|","&&",";","`","curl ","wget ","nc ","mkfs","dd"};
        for(String f : forbidden) {
            if(lower.contains(f)) throw new IllegalArgumentException("Forbidden token in command: "+f);
        }
    }

    public TaskExecution runCommandAndRecord(Task task) throws Exception {
        validateCommand(task.getCommand());
        Instant start = Instant.now();

        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder pb;
        if(os.contains("win")) {
            pb = new ProcessBuilder("cmd.exe", "/c", task.getCommand());
        } else {
            pb = new ProcessBuilder("sh", "-c", task.getCommand());
        }

        pb.redirectErrorStream(true);
        Process p = pb.start();

        Callable<String> readOutput = () -> {
            try (java.io.InputStream is = p.getInputStream();
                 java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A")) {
                return s.hasNext() ? s.next() : "";
            }
        };

        Future<String> outFuture = executor.submit(readOutput);
        boolean finished = p.waitFor(30, TimeUnit.SECONDS);
        if(!finished) p.destroyForcibly();

        String output = "";
        try { output = outFuture.get(1, TimeUnit.SECONDS); } catch(Exception e){ output="Error reading output"; }

        Instant end = Instant.now();
        TaskExecution exec = new TaskExecution();
        exec.setStartTime(start);
        exec.setEndTime(end);
        exec.setOutput(output);

        task.getTaskExecutions().add(exec);
        repo.save(task);
        return exec;
    }
}
