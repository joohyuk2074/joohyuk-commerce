package me.joohyuk.article.domain

import java.time.LocalDateTime

data class Article(
    val id: ArticleId,
    val boardId: Long,
    val writerId: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {

    fun update(title: String, content: String): Article {
        validateTitle(title)
        validateContent(content)

        return copy(
            title = title,
            content = content,
            modifiedAt = LocalDateTime.now()
        )
    }

    fun isWrittenBy(userId: Long): Boolean {
        return this.writerId == userId
    }

    companion object {
        private const val MIN_TITLE_LENGTH = 1
        private const val MAX_TITLE_LENGTH = 200
        private const val MIN_CONTENT_LENGTH = 1
        private const val MAX_CONTENT_LENGTH = 10000

        fun create(
            id: ArticleId,
            boardId: Long,
            writerId: Long,
            title: String,
            content: String
        ): Article {
            validateTitle(title)
            validateContent(content)

            val now = LocalDateTime.now()
            return Article(
                id = id,
                boardId = boardId,
                writerId = writerId,
                title = title,
                content = content,
                createdAt = now,
                modifiedAt = now
            )
        }

        private fun validateTitle(title: String) {
            require(title.length in MIN_TITLE_LENGTH..MAX_TITLE_LENGTH) {
                "제목은 ${MIN_TITLE_LENGTH}~${MAX_TITLE_LENGTH}자 사이여야 합니다."
            }
        }

        private fun validateContent(content: String) {
            require(content.length in MIN_CONTENT_LENGTH..MAX_CONTENT_LENGTH) {
                "내용은 ${MIN_CONTENT_LENGTH}~${MAX_CONTENT_LENGTH}자 사이여야 합니다."
            }
        }
    }
}

@JvmInline
value class ArticleId(val value: Long) {
    init {
        require(value > 0) { "Article ID는 양수여야 합니다." }
    }
}