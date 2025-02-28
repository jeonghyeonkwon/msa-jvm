package jeonghyeon.msa.board.dto.response;


import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class PageResponse<T> {
    private boolean isFirst;
    private boolean isLast;
    private Long currentPage;
    private Long startBlockPage;
    private Long endBlockPage;
    private Long totalPage;
    private List<T> list;

    // 10 page -> 요청은 11 page
    // 0...9, 10 ... 19, 20 ... 29
    // 1...10, 11... 20, 21 ... 30

    public PageResponse(Long currentPage, Long pageSize, List<T> list, Long count, Long pageBlock) {
        this.currentPage = currentPage;
        this.totalPage = count / pageSize + 1;
        this.startBlockPage = (currentPage / pageBlock) * pageBlock + 1;
        this.endBlockPage = Math.min(this.startBlockPage + pageBlock - 1, this.totalPage);
        this.isFirst = this.startBlockPage / pageBlock != 0 ? true : false;
        this.isLast = endBlockPage * pageBlock < count ? true : false;
        this.list = list;
    }
}
