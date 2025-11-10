package me.joohyuk.comment.application.port.out

import me.joohyuk.comment.domain.Comment
import me.joohyuk.comment.domain.CommentId

/**
 * Comment 출력 포트
 * 영속성 계층과의 인터페이스
 */
interface CommentPort {

    /**
     * 댓글 저장
     */
    fun save(comment: Comment): Comment

    /**
     * ID로 댓글 조회
     */
    fun findById(id: CommentId): Comment?

    /**
     * 게시글 ID로 댓글 목록 조회
     */
    fun findAllByArticleId(articleId: Long): List<Comment>

    /**
     * 게시글 ID로 댓글 개수 조회
     */
    fun countByArticleId(articleId: Long): Long

    /**
     * 댓글 삭제
     */
    fun deleteById(id: CommentId)

    /**
     * 댓글 존재 여부 확인
     */
    fun existsById(id: CommentId): Boolean
}