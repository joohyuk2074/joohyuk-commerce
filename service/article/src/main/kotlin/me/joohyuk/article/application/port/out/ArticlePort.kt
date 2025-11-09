package me.joohyuk.article.application.port.out

import me.joohyuk.article.domain.Article
import me.joohyuk.article.domain.ArticleId

/**
 * Article 아웃바운드 포트 (영속성 포트)
 * 도메인이 외부 저장소와 통신하기 위한 인터페이스
 */
interface ArticlePort {
    /**
     * 게시글 저장
     */
    fun save(article: Article): Article

    /**
     * ID로 게시글 조회
     */
    fun findById(id: ArticleId): Article?

    /**
     * Board ID로 게시글 목록 조회
     */
    fun findByBoardId(boardId: Long, page: Int, size: Int): List<Article>

    /**
     * 게시글 삭제
     */
    fun deleteById(id: ArticleId)

    /**
     * 게시글 존재 여부 확인
     */
    fun existsById(id: ArticleId): Boolean
}