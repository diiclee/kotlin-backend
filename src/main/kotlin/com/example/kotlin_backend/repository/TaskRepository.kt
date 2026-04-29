package com.example.kotlin_backend.repository

import com.example.kotlin_backend.entity.Task
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository : JpaRepository<Task, Long> {
    fun findByTitleContainingIgnoreCase(keyword: String): List<Task>
}