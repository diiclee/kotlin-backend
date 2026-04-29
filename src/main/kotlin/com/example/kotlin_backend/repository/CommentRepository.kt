package com.example.kotlin_backend.repository

import com.example.kotlin_backend.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByTaskId(taskId: Long): List<Comment>
}