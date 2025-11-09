package me.joohyuk.article.adapter.out.persistence

import me.joohyuk.article.application.port.out.ArticlePort
import me.joohyuk.article.domain.Article
import me.joohyuk.article.domain.ArticleId
import me.joohyuk.pagination.PageLimitCalculator
import org.springframework.stereotype.Component

/**
 * Article 영속성 어댑터
 * ArticlePort를 구현하여 도메인과 영속성 계층을 연결
 */
@Component
class ArticlePersistenceAdapter(
    private val articleJpaRepository: ArticleJpaRepository
) : ArticlePort {

    override fun save(article: Article): Article {
        val jpaEntity = ArticleJpaEntity.from(article)
        val savedEntity = articleJpaRepository.save(jpaEntity)
        return savedEntity.toDomain()
    }

    override fun findById(id: ArticleId): Article? {
        return articleJpaRepository.findById(id.value)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findByBoardId(boardId: Long, page: Int, size: Int): List<Article> {
        return articleJpaRepository.findAll(boardId, (page - 1) * size, size)
            .map { it.toDomain() }
    }

    override fun countByBoardId(boardId: Long, page: Int, size: Int): Long {
        return articleJpaRepository.count(
            boardId,
            PageLimitCalculator.calculatePageLimit(page, size, 10L)
        )
    }

    override fun deleteById(id: ArticleId) {
        articleJpaRepository.deleteById(id.value)
    }

    override fun existsById(id: ArticleId): Boolean {
        return articleJpaRepository.existsById(id.value)
    }
}