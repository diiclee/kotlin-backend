package com.example.kotlin_backend.repository

import com.example.kotlin_backend.entity.Project
import org.springframework.data.jpa.repository.JpaRepository

interface ProjectRepository : JpaRepository<Project, Long> {
    fun findByOwnerId(ownerId: Long): List<Project>
}