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

    @GetMapping("/{id}")
    fun getArticle(@PathVariable id: Long): ArticleResponse {
        val article = articleUseCase.getArticle(ArticleId(id))
            ?: throw ArticleNotFoundException("게시글을 찾을 수 없습니다: $id")
        return ArticleResponse.from(article)
    }

    @GetMapping
    fun readAll(
        @RequestParam boardId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ArticleListResponse {
        return articleUseCase.readAll(boardId, page, size)
    }

    @GetMapping("/infinite-scroll")
    fun readAllInfiniteScroll(
        @RequestParam boardId: Long,
        @RequestParam(defaultValue = "20") size: Int,
        @RequestParam(required = false) lastArticleId: Long?,
    ): List<ArticleResponse> {
        return articleUseCase.readInfiniteScroll(boardId, size, lastArticleId)
    }

    @PutMapping("/{id}")
    fun updateArticle(
        @PathVariable id: Long,
        @RequestBody request: UpdateArticleRequest
    ): ArticleResponse {
        val article = articleUseCase.updateArticle(request.toCommand(id))
        return ArticleResponse.from(article)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteArticle(
        @PathVariable id: Long,
        @RequestParam requesterId: Long
    ) {
        articleUseCase.deleteArticle(ArticleId(id), requesterId)
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class ArticleNotFoundException(message: String) : RuntimeException(message)