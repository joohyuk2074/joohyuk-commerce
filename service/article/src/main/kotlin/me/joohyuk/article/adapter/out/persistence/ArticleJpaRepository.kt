package me.joohyuk.article.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * Article JPA Repository
 * Spring Data JPA를 활용한 데이터 접근 계층
 */
@Repository
interface ArticleJpaRepository : JpaRepository<ArticleJpaEntity, Long> {

    @Query(
        value = "SELECT article.article_id, article.title, article.content, article.board_id, " +
                "article.writer_id, article.created_at, article.modified_at " +
                "FROM (" +
                "   SELECT article_id FROM article " +
                "   WHERE board_id = :boardId " +
                "   ORDER BY article_id DESC LIMIT :limit OFFSET :offset " +
                ") t LEFT JOIN article on t.article_id = article.article_id",
        nativeQuery = true
    )
    fun findAll(
        @Param("boardId") boardId: Long,
        @Param("offset") offset: Int,
        @Param("limit") limit: Int
    ): List<ArticleJpaEntity>

    @Query(
        value = "SELECT count(*) FROM (" +
                "   SELECT article.article_id FROM article " +
                "   WHERE article.board_id = :boardId LIMIT :limit" +
                ") t",
        nativeQuery = true
    )
    fun count(
        @Param("boardId") boardId: Long,
        @Param("limit") limit: Long
    ): Long

    @Query(
        value = "SELECT article.article_id, article.title, article.content, article.board_id, " +
                "article.writer_id, article.created_at, article.modified_at " +
                "FROM article " +
                "WHERE board_id = :boardId " +
                "ORDER BY article_id DESC LIMIT :limit",
        nativeQuery = true,
    )
    fun findAllInfiniteScroll(
        @Param("boardId") boardId: Long,
        @Param("limit") limit: Int
    ): List<ArticleJpaEntity>

    @Query(
        value = "SELECT article.article_id, article.title, article.content, article.board_id, " +
                "article.writer_id, article.created_at, article.modified_at " +
                "FROM article " +
                "WHERE board_id = :boardId AND article.article_id < :lastArticleId " +
                "ORDER BY article_id DESC LIMIT :limit",
        nativeQuery = true,
    )
    fun findAllInfiniteScroll(
        @Param("boardId") boardId: Long,
        @Param("limit") limit: Int,
        @Param("lastArticleId") lastArticleId: Long
    ): List<ArticleJpaEntity>
}