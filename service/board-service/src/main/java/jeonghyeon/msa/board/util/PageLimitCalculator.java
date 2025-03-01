package jeonghyeon.msa.board.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageLimitCalculator {

    public static Long calculatePageLimit(Long page, Long pageSize, Long movablePageCount) {
        return (((page) / movablePageCount) + 1) * pageSize * movablePageCount + 1;
    }

}
