package me.joohyuk.article.application.port.out

/**
 * ID 생성 포트
 * Snowflake ID 생성을 위한 인터페이스
 */
interface IdGeneratorPort {
    fun generate(): Long
}