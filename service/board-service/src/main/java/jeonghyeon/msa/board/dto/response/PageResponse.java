package jeonghyeon.msa.board.dto.response;


import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    private boolean isFirst;
    private boolean isLast;

    private int currentPage;
    private int totalPage;
    private int startBlockPage;
    private int endBlockPage;

    private List<T> lists;


}
