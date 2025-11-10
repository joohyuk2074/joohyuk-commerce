package me.joohyuk.comment.adapter.out.persistence

import me.joohyuk.comment.application.port.out.CommentPort
import me.joohyuk.comment.domain.Comment
import me.joohyuk.comment.domain.CommentId
import org.springframework.stereotype.Component

/**
 * Comment 영속성 어댑터
 * CommentPort를 구현하여 도메인과 영속성 계층을 연결
 */
@Component
class CommentPersistenceAdapter(
    private val commentJpaRepository: CommentJpaRepository
) : CommentPort {

    override fun save(comment: Comment): Comment {
        val jpaEntity = CommentJpaEntity.from(comment)
        val savedEntity = commentJpaRepository.save(jpaEntity)
        return savedEntity.toDomain()
    }

    override fun findById(id: CommentId): Comment? {
        return commentJpaRepository.findById(id.value)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findAllByArticleId(articleId: Long): List<Comment> {
        return commentJpaRepository.findAllByArticleId(articleId)
            .map { it.toDomain() }
    }

    override fun countByArticleId(articleId: Long): Long {
        return commentJpaRepository.countByArticleId(articleId)
    }

    override fun deleteById(id: CommentId) {
        commentJpaRepository.deleteById(id.value)
    }

    override fun existsById(id: CommentId): Boolean {
        return commentJpaRepository.existsById(id.value)
    }
}