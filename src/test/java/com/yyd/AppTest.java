package com.yyd;

import static org.junit.Assert.assertTrue;

import com.yyd.util.MyDateUtil;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        for(int i = 0; i< 10; i++){
            long 明天刷视频的开始时间 = MyDateUtil.第二天的某个时间点(7, 3);
            System.out.println("明天刷视频的开始时间:" + MyDateUtil.毫秒转LocalDateTime(明天刷视频的开始时间).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }

    }
}
