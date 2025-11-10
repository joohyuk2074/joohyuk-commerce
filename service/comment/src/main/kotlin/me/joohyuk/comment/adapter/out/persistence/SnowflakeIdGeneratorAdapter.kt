package me.joohyuk.comment.adapter.out.persistence

import me.joohyuk.comment.application.port.out.IdGeneratorPort
import me.joohyuk.snowflake.Snowflake
import org.springframework.stereotype.Component

/**
 * Snowflake ID Generator Adapter
 * Snowflake 알고리즘을 활용한 ID 생성
 */
@Component
class SnowflakeIdGeneratorAdapter(
    private val snowflake: Snowflake
) : IdGeneratorPort {

    override fun generate(): Long {
        return snowflake.nextId()
    }
}