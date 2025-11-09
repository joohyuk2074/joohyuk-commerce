package me.joohyuk.article.application.port.`in`

import me.joohyuk.article.adapter.`in`.web.dto.ArticleListResponse
import me.joohyuk.article.adapter.`in`.web.dto.ArticleResponse
import me.joohyuk.article.domain.Article
import me.joohyuk.article.domain.ArticleId

interface ArticleUseCase {

    fun createArticle(command: CreateArticleCommand): Article

    fun getArticle(id: ArticleId): Article?

    fun readAll(boardId: Long, page: Int, size: Int): ArticleListResponse

    fun readInfiniteScroll(boardId: Long, size: Int, lastArticleId: Long?): List<ArticleResponse>

    fun updateArticle(command: UpdateArticleCommand): Article

    fun deleteArticle(id: ArticleId, requesterId: Long)
}

data class CreateArticleCommand(
    val boardId: Long,
    val writerId: Long,
    val title: String,
    val content: String
)

data class UpdateArticleCommand(
    val id: ArticleId,
    val requesterId: Long,
    val title: String,
    val content: String
)