package jeonghyeon.msa.board.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String toStringByLocalDateTime(LocalDateTime localDateTime){
        return localDateTime.format(formatter);
    }
}
