package com.example.kotlin_backend.service

import com.example.kotlin_backend.dto.request.AddCommentRequest
import com.example.kotlin_backend.dto.response.CommentResponse
import com.example.kotlin_backend.mapper.CommentMapper
import com.example.kotlin_backend.repository.CommentRepository
import com.example.kotlin_backend.repository.TaskRepository
import com.example.kotlin_backend.repository.UserRepository
import com.example.kotlin_backend.util.findByIdOrThrow
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository,
    private val commentMapper: CommentMapper
) {

    fun addComment(taskId: Long, request: AddCommentRequest): CommentResponse {
        val task = taskRepository.findByIdOrThrow(taskId, "Task")
        val author = userRepository.findByIdOrThrow(request.authorId, "User")
        val comment = commentMapper.toEntity(request, task, author)
        val savedComment = commentRepository.save(comment)
        return commentMapper.toResponse(savedComment)
    }

    fun getComments(taskId: Long): List<CommentResponse> {
        taskRepository.findByIdOrThrow(taskId, "Task")
        return commentRepository.findByTaskId(taskId)
            .map { commentMapper.toResponse(it) }
    }
}