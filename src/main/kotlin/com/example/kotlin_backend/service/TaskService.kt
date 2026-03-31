package com.example.kotlin_backend.service

import com.example.kotlin_backend.dto.request.CreateTaskRequest
import com.example.kotlin_backend.dto.response.TaskResponse
import com.example.kotlin_backend.dto.request.UpdateTaskRequest
import com.example.kotlin_backend.mapper.TaskMapper
import com.example.kotlin_backend.repository.ProjectRepository
import com.example.kotlin_backend.repository.TaskRepository
import com.example.kotlin_backend.util.findByIdOrThrow
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val projectRepository: ProjectRepository,
    private val taskMapper: TaskMapper
) {

    fun createTask(request: CreateTaskRequest): TaskResponse {
        val project = projectRepository.findByIdOrThrow(request.projectId, "Project")
        val task = taskMapper.toEntity(request, project)
        val savedTask = taskRepository.save(task)
        return taskMapper.toResponse(savedTask)
    }

    fun getTask(id: Long): TaskResponse {
        val task = taskRepository.findByIdOrThrow(id, "Task")
        return taskMapper.toResponse(task)
    }

    fun updateTask(id: Long, request: UpdateTaskRequest): TaskResponse {
        val task = taskRepository.findByIdOrThrow(id, "Task")
        task.title = request.title
        task.description = request.description
        task.priority = request.priority
        task.dueDate = request.dueDate
        val savedTask = taskRepository.save(task)
        return taskMapper.toResponse(savedTask)
    }

    fun deleteTask(id: Long) {
        val task = taskRepository.findByIdOrThrow(id, "Task")
        taskRepository.delete(task)
    }
}