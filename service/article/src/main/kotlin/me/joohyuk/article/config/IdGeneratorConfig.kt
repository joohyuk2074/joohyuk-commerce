package me.joohyuk.article.config

import me.joohyuk.snowflake.Snowflake
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * ID Generator 설정
 */
@Configuration
class IdGeneratorConfig {

    @Bean
    fun snowflake(
        @Value("\${snowflake.node-id:1}") nodeId: Long
    ): Snowflake {
        return Snowflake(nodeId)
    }
}