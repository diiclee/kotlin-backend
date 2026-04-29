package com.example.kotlin_backend.controller

import com.example.kotlin_backend.dto.request.AddCommentRequest
import com.example.kotlin_backend.dto.response.CommentResponse
import com.example.kotlin_backend.service.CommentService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks/{taskId}/comments")
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addComment(
        @PathVariable taskId: Long,
        @Valid @RequestBody request: AddCommentRequest
    ): CommentResponse {
        return commentService.addComment(taskId, request)
    }

    @GetMapping
    fun getComments(@PathVariable taskId: Long): List<CommentResponse> {
        return commentService.getComments(taskId)
    }
}