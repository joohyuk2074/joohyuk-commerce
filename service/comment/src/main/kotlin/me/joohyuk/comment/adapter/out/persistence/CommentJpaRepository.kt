package me.joohyuk.comment.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * Comment JPA Repository
 */
@Repository
interface CommentJpaRepository : JpaRepository<CommentJpaEntity, Long> {

    /**
     * 게시글 ID로 댓글 목록 조회 (계층형 구조를 위해 정렬)
     * - 루트 댓글은 parent_comment_id로 그룹핑
     * - 생성 시간 오름차순 정렬
     */
    @Query(
        value = "SELECT c.comment_id, c.content, c.parent_comment_id, c.article_id, " +
                "c.writer_id, c.deleted, c.created_at " +
                "FROM comment c " +
                "WHERE c.article_id = :articleId " +
                "ORDER BY c.parent_comment_id ASC, c.created_at ASC",
        nativeQuery = true
    )
    fun findAllByArticleId(@Param("articleId") articleId: Long): List<CommentJpaEntity>

    /**
     * 게시글 ID로 댓글 개수 조회 (삭제되지 않은 댓글만)
     */
    @Query(
        value = "SELECT COUNT(*) FROM comment WHERE article_id = :articleId AND deleted = false",
        nativeQuery = true
    )
    fun countByArticleId(@Param("articleId") articleId: Long): Long
}