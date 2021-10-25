package com.yyd;

import static org.junit.Assert.assertTrue;

import com.yyd.util.MyDateUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws InterruptedException {

        LocalDateTime begin = LocalDateTime.now();
        TimeUnit.SECONDS.sleep(2);
        System.out.println(LocalDateTime.now().minusSeconds(5).compareTo(begin));

    }
}
