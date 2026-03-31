package com.example.kotlin_backend.service

import com.example.kotlin_backend.dto.request.CreateProjectRequest
import com.example.kotlin_backend.dto.response.ProjectResponse
import com.example.kotlin_backend.mapper.ProjectMapper
import com.example.kotlin_backend.repository.ProjectRepository
import com.example.kotlin_backend.repository.UserRepository
import com.example.kotlin_backend.util.findByIdOrThrow
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val userRepository: UserRepository,
    private val projectMapper: ProjectMapper
) {

    fun createProject(request: CreateProjectRequest): ProjectResponse {
        val owner = userRepository.findByIdOrThrow(request.ownerId, "User")
        val project = projectMapper.toEntity(request, owner)
        val savedProject = projectRepository.save(project)
        return projectMapper.toResponse(savedProject)
    }

    fun getProject(id: Long): ProjectResponse {
        val project = projectRepository.findByIdOrThrow(id, "Project")
        return projectMapper.toResponse(project)
    }

    fun listProjects(): List<ProjectResponse> {
        return projectRepository.findAll()
            .map { projectMapper.toResponse(it) }
    }

    fun listProjectsByUser(ownerId: Long): List<ProjectResponse> {
        userRepository.findByIdOrThrow(ownerId, "User")
        return projectRepository.findByOwnerId(ownerId)
            .map { projectMapper.toResponse(it) }
    }
}