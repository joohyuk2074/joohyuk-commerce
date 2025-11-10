package me.joohyuk.comment.adapter.out.persistence

import jakarta.persistence.*
import me.joohyuk.comment.domain.Comment
import me.joohyuk.comment.domain.CommentId
import java.time.LocalDateTime

/**
 * Comment JPA Entity
 * kotlin-jpa 플러그인이 자동으로 no-arg constructor 생성
 */
@Entity
@Table(name = "comment")
class CommentJpaEntity(
    @Id
    @Column(name = "comment_id")
    val commentId: Long,

    @Column(name = "content", nullable = false, length = 1000)
    val content: String,

    @Column(name = "parent_comment_id", nullable = false)
    val parentCommentId: Long,

    @Column(name = "article_id", nullable = false)
    val articleId: Long,

    @Column(name = "writer_id", nullable = false)
    val writerId: Long,

    @Column(name = "deleted", nullable = false)
    val deleted: Boolean,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime
) {

    /**
     * 도메인 모델로 변환
     */
    fun toDomain(): Comment {
        return Comment(
            id = CommentId(commentId),
            content = content,
            parentCommentId = CommentId(parentCommentId),
            articleId = articleId,
            writerId = writerId,
            deleted = deleted,
            createdAt = createdAt
        )
    }

    companion object {
        /**
         * 도메인 모델로부터 JPA 엔티티 생성
         */
        fun from(comment: Comment): CommentJpaEntity {
            return CommentJpaEntity(
                commentId = comment.id.value,
                content = comment.content,
                parentCommentId = comment.parentCommentId.value,
                articleId = comment.articleId,
                writerId = comment.writerId,
                deleted = comment.deleted,
                createdAt = comment.createdAt
            )
        }
    }
}