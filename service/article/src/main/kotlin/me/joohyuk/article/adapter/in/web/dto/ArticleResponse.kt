package me.joohyuk.article.adapter.`in`.web.dto

import me.joohyuk.article.domain.Article
import java.time.LocalDateTime

/**
 * 게시글 응답 DTO
 */
data class ArticleResponse(
    val id: Long,
    val boardId: Long,
    val writerId: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {
    companion object {
        fun from(article: Article): ArticleResponse {
            return ArticleResponse(
                id = article.id.value,
                boardId = article.boardId,
                writerId = article.writerId,
                title = article.title,
                content = article.content,
                createdAt = article.createdAt,
                modifiedAt = article.modifiedAt
            )
        }
    }
}

/**
 * 게시글 목록 응답 DTO
 */
data class ArticleListResponse(
    val articles: List<ArticleResponse>,
    val page: Int,
    val size: Int,
    val totalCount: Int
) {
    companion object {
        fun from(articles: List<Article>, page: Int, size: Int): ArticleListResponse {
            return ArticleListResponse(
                articles = articles.map { ArticleResponse.from(it) },
                page = page,
                size = size,
                totalCount = articles.size
            )
        }
    }
}