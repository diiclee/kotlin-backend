package com.example.kotlin_backend.mapper

import com.example.kotlin_backend.dto.request.CreateProjectRequest
import com.example.kotlin_backend.dto.response.ProjectResponse
import com.example.kotlin_backend.entity.Project
import com.example.kotlin_backend.entity.User
import org.springframework.stereotype.Component

@Component
class ProjectMapper {

    fun toEntity(request: CreateProjectRequest, owner: User): Project {
        return Project(
            title = request.title,
            description = request.description,
            owner = owner
        )
    }

    fun toResponse(project: Project): ProjectResponse {
        return ProjectResponse(
            id = project.id ?: throw IllegalStateException("Project id was null after save"),
            title = project.title,
            description = project.description,
            ownerId = project.owner.id ?: throw IllegalStateException("Owner id was null"),
            ownerName = project.owner.name
        )
    }
}