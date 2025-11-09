package me.joohyuk.pagination

/**
 * 페이징 처리 시 다음 페이지 화살표 버튼 노출 여부를 판단하기 위해
 * 조회해야 할 데이터의 limit 값을 계산하는 유틸리티
 */
object PageLimitCalculator {

    /**
     * 페이지 limit 계산
     *
     * @param page 현재 페이지 번호 (1부터 시작)
     * @param pageSize 페이지당 항목 수
     * @param movablePageCount 한 번에 이동 가능한 페이지 수
     * @return 조회해야 할 데이터의 limit 값
     */
    fun calculatePageLimit(page: Int, pageSize: Int, movablePageCount: Long): Long {
        return (((page - 1) / movablePageCount) + 1) * pageSize * movablePageCount + 1
    }
}