package me.joohyuk.article.adapter.out.persistence

import me.joohyuk.article.application.port.out.IdGeneratorPort
import me.joohyuk.snowflake.Snowflake
import org.springframework.stereotype.Component

@Component
class SnowflakeIdGeneratorAdapter(
    private val snowflake: Snowflake
) : IdGeneratorPort {

    override fun generate(): Long {
        return snowflake.nextId()
    }
}