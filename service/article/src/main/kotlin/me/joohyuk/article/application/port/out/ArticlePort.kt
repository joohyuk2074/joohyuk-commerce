package me.joohyuk.article.application.port.out

import me.joohyuk.article.domain.Article
import me.joohyuk.article.domain.ArticleId

/**
 * Article 아웃바운드 포트 (영속성 포트)
 * 도메인이 외부 저장소와 통신하기 위한 인터페이스
 */
interface ArticlePort {

    fun save(article: Article): Article

    fun findById(id: ArticleId): Article?

    fun findByBoardId(boardId: Long, page: Int, size: Int): List<Article>

    fun countByBoardId(boardId: Long, page: Int, size: Int): Long

    fun deleteById(id: ArticleId)

    fun existsById(id: ArticleId): Boolean
}