package me.joohyuk.article.adapter.`in`.web

import me.joohyuk.article.adapter.`in`.web.dto.ArticleListResponse
import me.joohyuk.article.adapter.`in`.web.dto.ArticleResponse
import me.joohyuk.article.adapter.`in`.web.dto.CreateArticleRequest
import me.joohyuk.article.adapter.`in`.web.dto.UpdateArticleRequest
import me.joohyuk.article.application.port.`in`.ArticleUseCase
import me.joohyuk.article.domain.ArticleId
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/articles")
class ArticleController(
    private val articleUseCase: ArticleUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createArticle(@RequestBody request: CreateArticleRequest): ArticleResponse {
        val article = articleUseCase.createArticle(request.toCommand())
        return ArticleResponse.from(article)
    }

    /**
     * 게시글 단건 조회
     */
    @GetMapping("/{id}")
    fun getArticle(@PathVariable id: Long): ArticleResponse {
        val article = articleUseCase.getArticle(ArticleId(id))
            ?: throw ArticleNotFoundException("게시글을 찾을 수 없습니다: $id")
        return ArticleResponse.from(article)
    }

    /**
     * 게시글 목록 조회 (Board 별)
     */
    @GetMapping
    fun getArticles(
        @RequestParam boardId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ArticleListResponse {
        val articles = articleUseCase.getArticles(boardId, page, size)
        return ArticleListResponse.from(articles, page, size)
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{id}")
    fun updateArticle(
        @PathVariable id: Long,
        @RequestBody request: UpdateArticleRequest
    ): ArticleResponse {
        val article = articleUseCase.updateArticle(request.toCommand(id))
        return ArticleResponse.from(article)
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteArticle(
        @PathVariable id: Long,
        @RequestParam requesterId: Long
    ) {
        articleUseCase.deleteArticle(ArticleId(id), requesterId)
    }
}

/**
 * 게시글을 찾을 수 없을 때 발생하는 예외
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class ArticleNotFoundException(message: String) : RuntimeException(message)