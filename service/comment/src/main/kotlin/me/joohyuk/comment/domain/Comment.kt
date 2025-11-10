package me.joohyuk.comment.domain

import java.time.LocalDateTime

/**
 * 댓글 도메인 모델
 */
data class Comment(
    val id: CommentId,
    val content: String,
    val parentCommentId: CommentId,
    val articleId: Long,
    val writerId: Long,
    val deleted: Boolean,
    val createdAt: LocalDateTime
) {

    /**
     * 루트 댓글인지 확인
     * parentCommentId가 자기 자신의 ID와 같으면 루트 댓글
     */
    fun isRoot(): Boolean {
        return parentCommentId.value == id.value
    }

    /**
     * 댓글 삭제
     */
    fun delete(): Comment {
        return copy(deleted = true)
    }

    /**
     * 작성자 확인
     */
    fun isWrittenBy(userId: Long): Boolean {
        return this.writerId == userId
    }

    companion object {
        private const val MIN_CONTENT_LENGTH = 1
        private const val MAX_CONTENT_LENGTH = 1000

        /**
         * 댓글 생성
         */
        fun create(
            id: CommentId,
            content: String,
            parentCommentId: CommentId?,
            articleId: Long,
            writerId: Long
        ): Comment {
            validateContent(content)

            val now = LocalDateTime.now()
            return Comment(
                id = id,
                content = content,
                parentCommentId = parentCommentId ?: id,
                articleId = articleId,
                writerId = writerId,
                deleted = false,
                createdAt = now
            )
        }

        private fun validateContent(content: String) {
            require(content.length in MIN_CONTENT_LENGTH..MAX_CONTENT_LENGTH) {
                "댓글 내용은 ${MIN_CONTENT_LENGTH}~${MAX_CONTENT_LENGTH}자 사이여야 합니다."
            }
        }
    }
}

/**
 * 댓글 ID Value Object
 */
@JvmInline
value class CommentId(val value: Long) {
    init {
        require(value > 0) { "Comment ID는 양수여야 합니다." }
    }
}