package me.joohyuk.comment.adapter.`in`.web.dto

import me.joohyuk.comment.domain.Comment
import java.time.LocalDateTime

/**
 * 댓글 응답 DTO
 */
data class CommentResponse(
    val id: Long,
    val content: String,
    val parentCommentId: Long,
    val articleId: Long,
    val writerId: Long,
    val deleted: Boolean,
    val isRoot: Boolean,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                id = comment.id.value,
                content = comment.content,
                parentCommentId = comment.parentCommentId.value,
                articleId = comment.articleId,
                writerId = comment.writerId,
                deleted = comment.deleted,
                isRoot = comment.isRoot(),
                createdAt = comment.createdAt
            )
        }
    }
}

/**
 * 댓글 목록 응답 DTO
 */
data class CommentListResponse(
    val comments: List<CommentResponse>,
    val totalCount: Long
) {
    companion object {
        fun from(
            comments: List<Comment>,
            totalCount: Long
        ): CommentListResponse {
            return CommentListResponse(
                comments = comments.map { CommentResponse.from(it) },
                totalCount = totalCount
            )
        }
    }
}