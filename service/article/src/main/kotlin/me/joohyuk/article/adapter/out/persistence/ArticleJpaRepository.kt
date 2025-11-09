package me.joohyuk.article.adapter.out.persistence

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Article JPA Repository
 * Spring Data JPA를 활용한 데이터 접근 계층
 */
@Repository
interface ArticleJpaRepository : JpaRepository<ArticleJpaEntity, Long> {
    /**
     * Board ID로 게시글 목록 조회
     */
    fun findByBoardId(boardId: Long, pageable: Pageable): List<ArticleJpaEntity>
}