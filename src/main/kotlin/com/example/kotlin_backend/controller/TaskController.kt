package com.example.kotlin_backend.controller

import com.example.kotlin_backend.dto.request.CreateTaskRequest
import com.example.kotlin_backend.dto.response.TaskResponse
import com.example.kotlin_backend.dto.request.UpdateTaskRequest
import com.example.kotlin_backend.service.TaskService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks")
class TaskController(
    private val taskService: TaskService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTask(@Valid @RequestBody request: CreateTaskRequest): TaskResponse {
        return taskService.createTask(request)
    }

    @GetMapping("/{id}")
    fun getTask(@PathVariable id: Long): TaskResponse {
        return taskService.getTask(id)
    }

    @PutMapping("/{id}")
    fun updateTask(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateTaskRequest
    ): TaskResponse {
        return taskService.updateTask(id, request)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTask(@PathVariable id: Long) {
        taskService.deleteTask(id)
    }
}