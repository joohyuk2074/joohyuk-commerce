package me.joohyuk.article.adapter.out.persistence

import jakarta.persistence.*
import me.joohyuk.article.domain.Article
import me.joohyuk.article.domain.ArticleId
import java.time.LocalDateTime

/**
 * Article JPA 엔티티 (영속성 모델)
 * 데이터베이스 테이블과 매핑되는 엔티티
 */
@Entity
@Table(name = "articles")
class ArticleJpaEntity(
    @Id
    @Column(name = "article_id")
    val articleId: Long,

    @Column(name = "board_id", nullable = false)
    val boardId: Long,

    @Column(name = "writer_id", nullable = false)
    val writerId: Long,

    @Column(name = "title", nullable = false, length = 200)
    val title: String,

    @Column(name = "content", nullable = false, length = 10000)
    val content: String,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime,

    @Column(name = "modified_at", nullable = false)
    val modifiedAt: LocalDateTime
) {
    /**
     * 도메인 모델로 변환
     */
    fun toDomain(): Article {
        return Article(
            id = ArticleId(articleId),
            boardId = boardId,
            writerId = writerId,
            title = title,
            content = content,
            createdAt = createdAt,
            modifiedAt = modifiedAt
        )
    }

    companion object {
        /**
         * 도메인 모델로부터 JPA 엔티티 생성
         */
        fun from(article: Article): ArticleJpaEntity {
            return ArticleJpaEntity(
                articleId = article.id.value,
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