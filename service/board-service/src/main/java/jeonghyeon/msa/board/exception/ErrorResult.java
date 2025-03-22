package jeonghyeon.msa.board.exception;

import lombok.Data;

@Data
public class ErrorResult {
    String message;

    public ErrorResult(String message) {
        this.message = message;
    }
}
