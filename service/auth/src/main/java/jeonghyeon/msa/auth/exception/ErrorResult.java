package jeonghyeon.msa.auth.exception;

import lombok.Data;

@Data
public class ErrorResult {
    String message;

    public ErrorResult(String message) {
        this.message = message;
    }
}
