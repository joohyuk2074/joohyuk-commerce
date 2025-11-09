package me.joohyuk.article.application.service

import me.joohyuk.article.adapter.`in`.web.dto.ArticleListResponse
import me.joohyuk.article.application.port.`in`.ArticleUseCase
import me.joohyuk.article.application.port.`in`.CreateArticleCommand
import me.joohyuk.article.application.port.`in`.UpdateArticleCommand
import me.joohyuk.article.application.port.out.ArticlePort
import me.joohyuk.article.application.port.out.IdGeneratorPort
import me.joohyuk.article.domain.Article
import me.joohyuk.article.domain.ArticleId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ArticleService(
    private val articlePort: ArticlePort,
    private val idGeneratorPort: IdGeneratorPort
) : ArticleUseCase {

    override fun createArticle(command: CreateArticleCommand): Article {
        val articleId = ArticleId(idGeneratorPort.generate())

        val article = Article.create(
            id = articleId,
            boardId = command.boardId,
            writerId = command.writerId,
            title = command.title,
            content = command.content
        )

        return articlePort.save(article)
    }

    @Transactional(readOnly = true)
    override fun getArticle(id: ArticleId): Article? {
        return articlePort.findById(id)
    }

    @Transactional(readOnly = true)
    override fun getArticles(boardId: Long, page: Int, size: Int): ArticleListResponse {    // TODO: 계층 침범
        val articles = articlePort.findByBoardId(boardId, page, size)
        val totalCount = articlePort.countByBoardId(boardId, page, size)

        return ArticleListResponse.from(articles, page, size, totalCount)
    }

    override fun updateArticle(command: UpdateArticleCommand): Article {
        val article = articlePort.findById(command.id)
            ?: throw ArticleNotFoundException("게시글을 찾을 수 없습니다: ${command.id.value}")

        if (!article.isWrittenBy(command.requesterId)) {
            throw ArticlePermissionDeniedException("게시글 수정 권한이 없습니다")
        }

        val updatedArticle = article.update(
            title = command.title,
            content = command.content
        )

        return articlePort.save(updatedArticle)
    }

    override fun deleteArticle(id: ArticleId, requesterId: Long) {
        val article = articlePort.findById(id)
            ?: throw ArticleNotFoundException("게시글을 찾을 수 없습니다: ${id.value}")

        // 권한 확인
        if (!article.isWrittenBy(requesterId)) {
            throw ArticlePermissionDeniedException("게시글 삭제 권한이 없습니다")
        }

        articlePort.deleteById(id)
    }
}

/**
 * 게시글을 찾을 수 없을 때 발생하는 예외
 */
class ArticleNotFoundException(message: String) : RuntimeException(message)

/**
 * 게시글 권한이 없을 때 발생하는 예외
 */
class ArticlePermissionDeniedException(message: String) : RuntimeException(message)