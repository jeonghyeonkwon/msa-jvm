package jeonghyeon.msa.board.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PageLimitCalculatorTest {


    @Test
    void test(){
        Long aLong = PageLimitCalculator.calculatePageLimit(10L, 10L, 10L);
        System.out.println(aLong);
    }
}