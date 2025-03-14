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

    // 프론트에서 page - 1 해서 백엔드 보내야됨

    // page = 10

    // 프론트에서 1...10, 11... 20, 21 ... 30
    // 백엔드 0...9, 10 ... 19, 20 ... 29

    // count = 101
    // totalPage = 101 / 10 + 1 = 11
    // startBlockPage = 0 / 10 * 10 = 0
    // endBlockPage = Math.min(9, 11)

    // count = 5
    // totalPage = 5 / 10 + 1 = 1
    // startBlockPage = 0 / 10 * 10 = 0
    // endBlockPage = Math.min(9, 0)


    public PageResponse(Long currentPage, Long pageSize, List<T> list, Long count, Long pageBlock) {
        this.currentPage = currentPage;
        this.totalPage = count % pageSize == 0 ? count / pageSize - 1 : count / pageSize;
        this.startBlockPage = (currentPage / pageBlock) * pageBlock;
        this.endBlockPage = Math.min(this.startBlockPage + pageBlock - 1, this.totalPage);
        this.isFirst = this.startBlockPage / pageBlock != 0 ? true : false;
        this.isLast = this.endBlockPage < this.totalPage ? true : false;


        this.list = list;
    }
}
