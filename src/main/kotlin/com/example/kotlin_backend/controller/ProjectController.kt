package com.example.kotlin_backend.controller

import com.example.kotlin_backend.dto.request.CreateProjectRequest
import com.example.kotlin_backend.dto.response.ProjectResponse
import com.example.kotlin_backend.service.ProjectService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/projects")
class ProjectController(
    private val projectService: ProjectService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProject(@Valid @RequestBody request: CreateProjectRequest): ProjectResponse {
        return projectService.createProject(request)
    }

    @GetMapping("/{id}")
    fun getProject(@PathVariable id: Long): ProjectResponse {
        return projectService.getProject(id)
    }

    @GetMapping
    fun listProjects(): List<ProjectResponse> {
        return projectService.listProjects()
    }

    @GetMapping("/user/{ownerId}")
    fun listProjectsByUser(@PathVariable ownerId: Long): List<ProjectResponse> {
        return projectService.listProjectsByUser(ownerId)
    }
}