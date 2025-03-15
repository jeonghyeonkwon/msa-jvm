package jeonghyeon.msa.board.util;

import org.junit.jupiter.api.Test;


class PageLimitCalculatorTest {

    @Test
    void test() {
        Long aLong = PageLimitCalculator.calculatePageLimit(10L, 10L, 10L);
        System.out.println(aLong);
    }
}