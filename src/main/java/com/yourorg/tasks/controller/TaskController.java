package com.kaiburr.tasks.controller;

import com.kaiburr.tasks.model.Task;
import com.kaiburr.tasks.model.TaskExecution;
import com.kaiburr.tasks.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) { this.service = service; }

    @GetMapping
    public List<Task> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable String id) {
        Task t = service.findById(id);
        return t != null ? ResponseEntity.ok(t) : ResponseEntity.notFound().build();
    }

    @PutMapping
    public Task createOrUpdate(@RequestBody Task task) {
        return service.save(task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteById(id);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Task>> findByName(@RequestParam String name) {
        List<Task> list = service.findByNameContains(name);
        return list.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(list);
    }

    @PutMapping("/{id}/execute")
    public ResponseEntity<TaskExecution> execute(@PathVariable String id) throws Exception {
        Task t = service.findById(id);
        if(t == null) return ResponseEntity.notFound().build();
        TaskExecution exec = service.runCommandAndRecord(t);
        return ResponseEntity.ok(exec);
    }
}
