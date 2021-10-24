package com.yyd.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtil {

    public static void log(String info){
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ": " + info);
    }
}
