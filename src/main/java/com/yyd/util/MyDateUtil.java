package com.yyd.util;

import java.time.*;
import java.util.Random;

public class MyDateUtil {
    static Random random = new Random();

//    public static int 现在距离晚上12点还剩几个小时(){
//        // 因为当前时间一般都是14：23分，如果永24-14=8小时，但其实只剩7小时37分
//        // 所以用23来减
//        return 23 - LocalDateTime.now().getHour();
//    }

    public static long 现在距离晚上12点还剩多少毫秒(){
        LocalDateTime 晚上24点 = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59));
        Duration duration = Duration.between(LocalDateTime.now(), 晚上24点);
        return duration.getSeconds() * 1000;
    }

    public static long 第二天的某个时间点(int startHours, int offset){
        return LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0, 0)).toEpochSecond(ZoneOffset.of("+8")) * 1000 + (startHours + random.nextInt(offset + 1)) * 60 * 60 * 1000;
    }

    public static LocalDateTime 毫秒转LocalDateTime(long millSeconds){
        return LocalDateTime.ofEpochSecond(millSeconds/1000, 0, ZoneOffset.ofHours(8));
    }

    public static String transform(final long seconds) {
        long hh = seconds / 3600;
        long mm = (seconds % 3600) / 60;
        long ss = (seconds % 3600) % 60;
        return (hh < 10 ? ("0" + hh) : hh) + ":" + (mm < 10 ? ("0" + mm) : mm) + ":" + (ss < 10 ? ("0" + ss) : ss);
    }
}
