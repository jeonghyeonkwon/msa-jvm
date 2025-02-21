package jeonghyeon.msa.auth.dto.response;

import lombok.Data;

@Data
public class ErrorMessage {
    String message;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
