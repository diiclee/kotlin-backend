package com.example.kotlin_backend.service

import com.example.kotlin_backend.dto.request.AssignTaskRequest
import com.example.kotlin_backend.dto.request.ChangeTaskStatusRequest
import com.example.kotlin_backend.dto.request.CreateTaskRequest
import com.example.kotlin_backend.dto.response.TaskResponse
import com.example.kotlin_backend.dto.request.UpdateTaskRequest
import com.example.kotlin_backend.exception.BadRequestException
import com.example.kotlin_backend.mapper.TaskMapper
import com.example.kotlin_backend.repository.ProjectRepository
import com.example.kotlin_backend.repository.TaskRepository
import com.example.kotlin_backend.repository.UserRepository
import com.example.kotlin_backend.util.findByIdOrThrow
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val projectRepository: ProjectRepository,
    private val userRepository: UserRepository,
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

    fun assignTask(id: Long, request: AssignTaskRequest): TaskResponse {
        val task = taskRepository.findByIdOrThrow(id, "Task")
        val user = userRepository.findByIdOrThrow(request.userId, "User")
        task.assignedUser = user
        val savedTask = taskRepository.save(task)
        return taskMapper.toResponse(savedTask)
    }

    fun unassignTask(id: Long): TaskResponse {
        val task = taskRepository.findByIdOrThrow(id, "Task")
        task.assignedUser = null
        val savedTask = taskRepository.save(task)
        return taskMapper.toResponse(savedTask)
    }

    fun changeTaskStatus(id: Long, request: ChangeTaskStatusRequest): TaskResponse {
        val task = taskRepository.findByIdOrThrow(id, "Task")
        if (!task.status.canTransitionTo(request.status)){
            throw BadRequestException("Status updated from ${task.status} to ${request.status} is not allowed")
        }
        task.status = request.status
        val savedTask = taskRepository.save(task)
        return taskMapper.toResponse(savedTask)
    }

    fun searchTasks(keyword: String): List<TaskResponse> {
        return taskRepository.findByTitleContainingIgnoreCase(keyword)
            .map { taskMapper.toResponse(it) }
    }
}