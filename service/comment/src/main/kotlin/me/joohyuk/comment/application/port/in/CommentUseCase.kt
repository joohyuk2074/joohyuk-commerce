package me.joohyuk.comment.application.port.`in`

import me.joohyuk.comment.domain.Comment
import me.joohyuk.comment.domain.CommentId

/**
 * Comment Use Case
 * 댓글 관련 비즈니스 로직 인터페이스
 */
interface CommentUseCase {

    /**
     * 댓글 생성
     */
    fun createComment(command: CreateCommentCommand): Comment

    /**
     * 댓글 단건 조회
     */
    fun getComment(id: CommentId): Comment?

    /**
     * 게시글의 댓글 목록 조회
     */
    fun getCommentsByArticleId(articleId: Long): List<Comment>

    /**
     * 게시글의 댓글 개수 조회
     */
    fun getCommentCount(articleId: Long): Long

    /**
     * 댓글 삭제 (Soft Delete)
     */
    fun deleteComment(id: CommentId, requesterId: Long)
}

/**
 * 댓글 생성 커맨드
 */
data class CreateCommentCommand(
    val content: String,
    val parentCommentId: Long?,
    val articleId: Long,
    val writerId: Long
)