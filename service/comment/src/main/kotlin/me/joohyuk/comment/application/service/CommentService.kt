package me.joohyuk.comment.application.service

import me.joohyuk.comment.application.port.`in`.CommentUseCase
import me.joohyuk.comment.application.port.`in`.CreateCommentCommand
import me.joohyuk.comment.application.port.out.CommentPort
import me.joohyuk.comment.application.port.out.IdGeneratorPort
import me.joohyuk.comment.domain.Comment
import me.joohyuk.comment.domain.CommentId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Comment Service
 * 댓글 관련 비즈니스 로직 구현
 */
@Service
@Transactional
class CommentService(
    private val commentPort: CommentPort,
    private val idGeneratorPort: IdGeneratorPort
) : CommentUseCase {

    override fun createComment(command: CreateCommentCommand): Comment {
        val commentId = CommentId(idGeneratorPort.generate())

        // parentCommentId가 있는 경우 부모 댓글 존재 여부 확인
        if (command.parentCommentId != null) {
            val parentComment = commentPort.findById(CommentId(command.parentCommentId))
                ?: throw CommentNotFoundException("부모 댓글을 찾을 수 없습니다: ${command.parentCommentId}")

            // 부모 댓글이 삭제된 경우
            if (parentComment.deleted) {
                throw CommentDeletedException("삭제된 댓글에는 답글을 작성할 수 없습니다")
            }
        }

        val comment = Comment.create(
            id = commentId,
            content = command.content,
            parentCommentId = command.parentCommentId?.let { CommentId(it) },
            articleId = command.articleId,
            writerId = command.writerId
        )

        return commentPort.save(comment)
    }

    @Transactional(readOnly = true)
    override fun getComment(id: CommentId): Comment? {
        return commentPort.findById(id)
    }

    @Transactional(readOnly = true)
    override fun getCommentsByArticleId(articleId: Long): List<Comment> {
        return commentPort.findAllByArticleId(articleId)
    }

    @Transactional(readOnly = true)
    override fun getCommentCount(articleId: Long): Long {
        return commentPort.countByArticleId(articleId)
    }

    override fun deleteComment(id: CommentId, requesterId: Long) {
        val comment = commentPort.findById(id)
            ?: throw CommentNotFoundException("댓글을 찾을 수 없습니다: ${id.value}")

        // 권한 확인
        if (!comment.isWrittenBy(requesterId)) {
            throw CommentPermissionDeniedException("댓글 삭제 권한이 없습니다")
        }

        // 이미 삭제된 댓글인 경우
        if (comment.deleted) {
            throw CommentDeletedException("이미 삭제된 댓글입니다")
        }

        // Soft Delete
        val deletedComment = comment.delete()
        commentPort.save(deletedComment)
    }
}

/**
 * 댓글을 찾을 수 없을 때 발생하는 예외
 */
class CommentNotFoundException(message: String) : RuntimeException(message)

/**
 * 댓글 권한이 없을 때 발생하는 예외
 */
class CommentPermissionDeniedException(message: String) : RuntimeException(message)

/**
 * 이미 삭제된 댓글일 때 발생하는 예외
 */
class CommentDeletedException(message: String) : RuntimeException(message)