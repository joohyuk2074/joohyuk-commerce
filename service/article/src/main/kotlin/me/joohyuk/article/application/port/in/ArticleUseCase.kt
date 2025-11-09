package me.joohyuk.article.application.port.`in`

import me.joohyuk.article.domain.Article
import me.joohyuk.article.domain.ArticleId

interface ArticleUseCase {

    fun createArticle(command: CreateArticleCommand): Article

    /**
     * 게시글 조회
     */
    fun getArticle(id: ArticleId): Article?

    /**
     * 게시글 목록 조회
     */
    fun getArticles(boardId: Long, page: Int, size: Int): List<Article>

    /**
     * 게시글 수정
     */
    fun updateArticle(command: UpdateArticleCommand): Article

    /**
     * 게시글 삭제
     */
    fun deleteArticle(id: ArticleId, requesterId: Long)
}

/**
 * 게시글 생성 커맨드
 */
data class CreateArticleCommand(
    val boardId: Long,
    val writerId: Long,
    val title: String,
    val content: String
)

/**
 * 게시글 수정 커맨드
 */
data class UpdateArticleCommand(
    val id: ArticleId,
    val requesterId: Long,
    val title: String,
    val content: String
)