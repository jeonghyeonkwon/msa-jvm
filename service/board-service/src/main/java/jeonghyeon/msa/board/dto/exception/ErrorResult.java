package jeonghyeon.msa.board.dto.exception;

import lombok.Data;

@Data
public class ErrorResult {
    String message;

    public ErrorResult(String message) {
        this.message = message;
    }
}
