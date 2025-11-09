package me.joohyuk.article.adapter.`in`.web.dto

import me.joohyuk.article.application.port.`in`.CreateArticleCommand
import me.joohyuk.article.application.port.`in`.UpdateArticleCommand
import me.joohyuk.article.domain.ArticleId

/**
 * 게시글 생성 요청 DTO
 */
data class CreateArticleRequest(
    val boardId: Long,
    val writerId: Long,
    val title: String,
    val content: String
) {
    fun toCommand(): CreateArticleCommand {
        return CreateArticleCommand(
            boardId = boardId,
            writerId = writerId,
            title = title,
            content = content
        )
    }
}

/**
 * 게시글 수정 요청 DTO
 */
data class UpdateArticleRequest(
    val requesterId: Long,
    val title: String,
    val content: String
) {
    fun toCommand(articleId: Long): UpdateArticleCommand {
        return UpdateArticleCommand(
            id = ArticleId(articleId),
            requesterId = requesterId,
            title = title,
            content = content
        )
    }
}