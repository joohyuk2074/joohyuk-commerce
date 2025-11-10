package me.joohyuk.comment.application.port.out

/**
 * ID 생성 포트
 */
interface IdGeneratorPort {
    /**
     * 고유 ID 생성
     */
    fun generate(): Long
}