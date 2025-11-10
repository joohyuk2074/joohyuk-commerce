package me.joohyuk.comment.adapter.`in`.web.dto

import me.joohyuk.comment.application.port.`in`.CreateCommentCommand

/**
 * 댓글 생성 요청 DTO
 */
data class CreateCommentRequest(
    val content: String,
    val parentCommentId: Long?,
    val articleId: Long,
    val writerId: Long
) {
    fun toCommand(): CreateCommentCommand {
        return CreateCommentCommand(
            content = content,
            parentCommentId = parentCommentId,
            articleId = articleId,
            writerId = writerId
        )
    }
}