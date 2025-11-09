package me.joohyuk.article.adapter.`in`.web

import me.joohyuk.article.application.service.ArticlePermissionDeniedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

/**
 * 전역 예외 핸들러
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ArticleNotFoundException::class)
    fun handleArticleNotFoundException(e: ArticleNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    status = HttpStatus.NOT_FOUND.value(),
                    error = "Not Found",
                    message = e.message ?: "게시글을 찾을 수 없습니다",
                    timestamp = LocalDateTime.now()
                )
            )
    }

    @ExceptionHandler(me.joohyuk.article.application.service.ArticleNotFoundException::class)
    fun handleDomainArticleNotFoundException(
        e: me.joohyuk.article.application.service.ArticleNotFoundException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    status = HttpStatus.NOT_FOUND.value(),
                    error = "Not Found",
                    message = e.message ?: "게시글을 찾을 수 없습니다",
                    timestamp = LocalDateTime.now()
                )
            )
    }

    @ExceptionHandler(ArticlePermissionDeniedException::class)
    fun handleArticlePermissionDeniedException(e: ArticlePermissionDeniedException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(
                ErrorResponse(
                    status = HttpStatus.FORBIDDEN.value(),
                    error = "Forbidden",
                    message = e.message ?: "권한이 없습니다",
                    timestamp = LocalDateTime.now()
                )
            )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    status = HttpStatus.BAD_REQUEST.value(),
                    error = "Bad Request",
                    message = e.message ?: "잘못된 요청입니다",
                    timestamp = LocalDateTime.now()
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    error = "Internal Server Error",
                    message = "서버 오류가 발생했습니다",
                    timestamp = LocalDateTime.now()
                )
            )
    }
}

/**
 * 에러 응답 DTO
 */
data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String,
    val timestamp: LocalDateTime
)