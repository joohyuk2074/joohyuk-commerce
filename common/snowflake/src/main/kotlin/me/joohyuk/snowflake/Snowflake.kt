package me.joohyuk.snowflake

import kotlin.random.Random

class Snowflake(
    /**
     * 노드 ID는 분산 환경에서 인스턴스(또는 샤드)를 구분하기 위해 사용됩니다.
     * 기본값은 0~1023 범위에서 랜덤 생성 (원본 Java와 동일한 의도 유지).
     * 운영에서는 고정 값(환경변수/설정) 주입을 권장합니다.
     */
    private val nodeId: Long = Random.nextLong(MAX_NODE_ID + 1)
) {

    companion object {
        private const val UNUSED_BITS = 1
        private const val EPOCH_BITS = 41
        private const val NODE_ID_BITS = 10
        private const val SEQUENCE_BITS = 12

        private const val MAX_NODE_ID: Long = (1L shl NODE_ID_BITS) - 1
        private const val MAX_SEQUENCE: Long = (1L shl SEQUENCE_BITS) - 1

        // UTC = 2024-01-01T00:00:00Z
        private const val START_TIME_MILLIS: Long = 1_704_067_200_000L

        // 쉬프트 폭 미리 계산
        private const val NODE_SHIFT = SEQUENCE_BITS
        private const val TIME_SHIFT = NODE_ID_BITS + SEQUENCE_BITS
    }

    // 동시성 가시성 보장용
    @Volatile
    private var lastTimeMillis: Long = START_TIME_MILLIS

    @Volatile
    private var sequence: Long = 0L

    /**
     * 동기화로 임계영역 보호 (원본 Java: synchronized 메서드와 동일한 효과)
     */
    @Synchronized
    fun nextId(): Long {
        var currentTimeMillis = System.currentTimeMillis()

        // 1) 시계가 뒤로 간 경우 보호
        if (currentTimeMillis < lastTimeMillis) {
            throw IllegalStateException("Invalid Time: system clock moved backwards")
        }

        if (currentTimeMillis == lastTimeMillis) {
            // 2) 동일 ms 안에서 시퀀스 증가 (4096개까지)
            sequence = (sequence + 1) and MAX_SEQUENCE
            if (sequence == 0L) {
                // 3) 오버플로우 시 다음 밀리초까지 대기
                currentTimeMillis = waitNextMillis(currentTimeMillis)
            }
        } else {
            // 4) 새로운 밀리초로 넘어가면 시퀀스 리셋
            sequence = 0L
        }

        lastTimeMillis = currentTimeMillis

        // 5) 비트 조립: [부호/미사용 1][시간 41][노드 10][시퀀스 12]
        val elapsed = currentTimeMillis - START_TIME_MILLIS
        return (elapsed shl TIME_SHIFT) or
                (nodeId shl NODE_SHIFT) or
                sequence
    }

    private fun waitNextMillis(current: Long): Long {
        var ts = current
        while (ts <= lastTimeMillis) {
            ts = System.currentTimeMillis()
        }
        return ts
    }
}