package me.joohyuk.comment.adapter.`in`.web

import me.joohyuk.comment.adapter.`in`.web.dto.CommentListResponse
import me.joohyuk.comment.adapter.`in`.web.dto.CommentResponse
import me.joohyuk.comment.adapter.`in`.web.dto.CreateCommentRequest
import me.joohyuk.comment.application.port.`in`.CommentUseCase
import me.joohyuk.comment.domain.CommentId
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * Comment REST Controller
 */
@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    private val commentUseCase: CommentUseCase
) {

    /**
     * 댓글 생성
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createComment(@RequestBody request: CreateCommentRequest): CommentResponse {
        val comment = commentUseCase.createComment(request.toCommand())
        return CommentResponse.from(comment)
    }

    /**
     * 댓글 단건 조회
     */
    @GetMapping("/{id}")
    fun getComment(@PathVariable id: Long): CommentResponse {
        val comment = commentUseCase.getComment(CommentId(id))
            ?: throw CommentNotFoundException("댓글을 찾을 수 없습니다: $id")
        return CommentResponse.from(comment)
    }

    /**
     * 게시글의 댓글 목록 조회
     */
    @GetMapping
    fun getCommentsByArticleId(@RequestParam articleId: Long): CommentListResponse {
        val comments = commentUseCase.getCommentsByArticleId(articleId)
        val totalCount = commentUseCase.getCommentCount(articleId)
        return CommentListResponse.from(comments, totalCount)
    }

    /**
     * 댓글 삭제 (Soft Delete)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteComment(
        @PathVariable id: Long,
        @RequestParam requesterId: Long
    ) {
        commentUseCase.deleteComment(CommentId(id), requesterId)
    }
}

/**
 * 댓글을 찾을 수 없을 때 발생하는 예외
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class CommentNotFoundException(message: String) : RuntimeException(message)