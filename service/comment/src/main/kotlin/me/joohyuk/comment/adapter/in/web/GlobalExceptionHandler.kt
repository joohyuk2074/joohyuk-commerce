package me.joohyuk.comment.adapter.`in`.web

import me.joohyuk.comment.application.service.CommentDeletedException
import me.joohyuk.comment.application.service.CommentPermissionDeniedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 전역 예외 처리 핸들러
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CommentNotFoundException::class)
    fun handleCommentNotFoundException(e: CommentNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(e.message ?: "댓글을 찾을 수 없습니다"))
    }

    @ExceptionHandler(CommentPermissionDeniedException::class)
    fun handleCommentPermissionDeniedException(e: CommentPermissionDeniedException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(ErrorResponse(e.message ?: "권한이 없습니다"))
    }

    @ExceptionHandler(CommentDeletedException::class)
    fun handleCommentDeletedException(e: CommentDeletedException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(e.message ?: "삭제된 댓글입니다"))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(e.message ?: "잘못된 요청입니다"))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse("서버 오류가 발생했습니다"))
    }
}

data class ErrorResponse(
    val message: String
)