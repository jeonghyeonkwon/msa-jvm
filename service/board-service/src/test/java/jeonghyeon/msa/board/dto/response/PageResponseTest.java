package jeonghyeon.msa.board.dto.response;

import jeonghyeon.msa.board.util.PageLimitCalculator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PageResponseTest {


    @Test
    void page_0번() {
        List<Object> test = new ArrayList<>();
        Long count = PageLimitCalculator.calculatePageLimit(0L, 10L, 10L);
        PageResponse<Object> dto = new PageResponse<Object>(0L, 10L, test, count, 10L);

        assertAll(
                () -> assertThat(dto.getStartBlockPage()).isEqualTo(0),
                () -> assertThat(dto.getEndBlockPage()).isEqualTo(9),
                () -> assertThat(dto.isFirst()).isEqualTo(false),
                () -> assertThat(dto.isLast()).isEqualTo(true)
        );
    }

    @Test
    void page_0번_block_size_5() {
        List<Object> test = new ArrayList<>();
        Long count = PageLimitCalculator.calculatePageLimit(0L, 1L, 5L);
        System.out.println(count);
        PageResponse<Object> dto = new PageResponse<Object>(0L, 1L, test, count, 5L);

        assertAll(
                () -> assertThat(dto.getStartBlockPage()).isEqualTo(0),
                () -> assertThat(dto.getEndBlockPage()).isEqualTo(4),
                () -> assertThat(dto.isFirst()).isEqualTo(false),
                () -> assertThat(dto.isLast()).isEqualTo(true)
        );
    }

    @Test
    void page_9번() {
        List<Object> test = new ArrayList<>();
        Long count = PageLimitCalculator.calculatePageLimit(9L, 10L, 10L);
        PageResponse<Object> dto = new PageResponse<Object>(9L, 10L, test, count, 10L);
        assertAll(
                () -> assertThat(dto.getStartBlockPage()).isEqualTo(0),
                () -> assertThat(dto.getEndBlockPage()).isEqualTo(9),
                () -> assertThat(dto.isFirst()).isEqualTo(false),
                () -> assertThat(dto.isLast()).isEqualTo(true)
        );
    }

    @Test
    void page_10번() {
        List<Object> test = new ArrayList<>();
        Long count = PageLimitCalculator.calculatePageLimit(10L, 10L, 10L);
        PageResponse<Object> dto = new PageResponse<Object>(10L, 10L, test, count, 10L);
        assertAll(
                () -> assertThat(dto.getStartBlockPage()).isEqualTo(10),
                () -> assertThat(dto.getEndBlockPage()).isEqualTo(19),
                () -> assertThat(dto.isFirst()).isEqualTo(true),
                () -> assertThat(dto.isLast()).isEqualTo(true)
        );
    }

    @Test
    void page_14번() {
        // 10,          11,         12,       13         14
        // 101 ~ 110, 111 ~ 120, 121 ~ 130, 131 ~ 140, 141 ~ 150
        List<Object> test = new ArrayList<>();
        Long count = 144L;
        PageResponse<Object> dto = new PageResponse<Object>(14L, 10L, test, count, 10L);
        assertAll(
                () -> assertThat(dto.getStartBlockPage()).isEqualTo(10),
                () -> assertThat(dto.getEndBlockPage()).isEqualTo(14),
                () -> assertThat(dto.isFirst()).isEqualTo(true),
                () -> assertThat(dto.isLast()).isEqualTo(false)
        );
    }

    @Test
    void page_0번_갯수가_10개_아닐_시() {

        List<Object> test = new ArrayList<>();
        Long count = 5L;
        PageResponse<Object> dto = new PageResponse<Object>(0L, 10L, test, count, 10L);
        System.out.println(dto);
        assertAll(
                () -> assertThat(dto.getStartBlockPage()).isEqualTo(0),
                () -> assertThat(dto.getEndBlockPage()).isEqualTo(0),
                () -> assertThat(dto.isFirst()).isEqualTo(false),
                () -> assertThat(dto.isLast()).isEqualTo(false)
        );

    }

    @Test
    void page_리스트_갯수가_10개일_시() {

        List<Object> test = new ArrayList<>();
        Long count = 7L;
        PageResponse<Object> dto = new PageResponse<Object>(0L, 10L, test, count, 5L);
        System.out.println(dto);
        assertAll(
                () -> assertThat(dto.getStartBlockPage()).isEqualTo(0),
                () -> assertThat(dto.getEndBlockPage()).isEqualTo(0),
                () -> assertThat(dto.isFirst()).isEqualTo(false),
                () -> assertThat(dto.isLast()).isEqualTo(false)
        );

    }

}