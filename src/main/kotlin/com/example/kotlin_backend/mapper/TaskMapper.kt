package com.example.kotlin_backend.mapper

import com.example.kotlin_backend.dto.request.CreateTaskRequest
import com.example.kotlin_backend.dto.response.TaskResponse
import com.example.kotlin_backend.entity.Project
import com.example.kotlin_backend.entity.Task
import org.springframework.stereotype.Component

@Component
class TaskMapper {

    fun toEntity(request: CreateTaskRequest, project: Project): Task {
        return Task(
            title = request.title,
            description = request.description,
            priority = request.priority,
            dueDate = request.dueDate,
            project = project
        )
    }

    fun toResponse(task: Task): TaskResponse {
        return TaskResponse(
            id = task.id ?: throw IllegalStateException("Task id was null after save"),
            title = task.title,
            description = task.description,
            status = task.status,
            priority = task.priority,
            dueDate = task.dueDate,
            projectId = task.project.id ?: throw IllegalStateException("Project id was null"),
            assignedUserId = task.assignedUser?.id,
            assignedUserName = task.assignedUser?.name
        )
    }
}