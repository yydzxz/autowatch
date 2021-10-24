package com.yyd;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * 抖音操作
 */
public class DouYinOperate {
    static Random random = new Random();

    public static void 评论区操作() throws InterruptedException, IOException {
        CommonOperate.单击(978, 1190, 5, 15, 5000 + random.nextInt(3000), "打开评论区");
        //返回键，之所以按两下是因为，有些没有评论的视频，如果打开评论，会自动弹出输入法，只返回一次不能够返回到视频页
        CommonOperate.返回(1);
        CommonOperate.返回(3);
    }

    public static void 单击点赞(double 点赞x, double 点赞y) throws IOException, InterruptedException {
        CommonOperate.单击(988, 1035, 2, 2, 2000, "点赞");
    }

    public static void 返回桌面(int 返回桌面后停留时间) throws IOException, InterruptedException {
        Runtime.getRuntime().exec("adb shell input keyevent 3");
        TimeUnit.SECONDS.sleep(返回桌面后停留时间);
    }

    public static void 点击视频页最底下中间那个按钮打开任务页() throws InterruptedException, IOException {
        CommonOperate.单击(506.1, 1842, 3, 2, 5000, "点击视频页最底下中间那个按钮");
    }


    public static void 上划(int 划动后最少停止时间, int offset) throws IOException, InterruptedException {
        CommonOperate.上划或者下划(划动后最少停止时间, offset, true);
    }

    public static void 下划(int 划动后最少停止时间, int offset) throws IOException, InterruptedException {
        CommonOperate.上划或者下划(划动后最少停止时间, offset, false);
    }

    public static void 左划或者右划(int 划动后最少停止时间, int offset, boolean 左划) throws IOException, InterruptedException {
        double[] start = new double[2];
        double[] end = new double[2];
        start[0] = 700 + random.nextDouble() * 100;
        start[1] = 700 + random.nextDouble() * 850;
        double x偏移 = 400 + random.nextDouble() * 150;
        double y偏移 = 30 + random.nextDouble() * 30;

        end[0] = start[0] - x偏移;
        end[1] = start[1] + y偏移;
        String commandStr;
        if(左划){
            commandStr = "adb shell input swipe " + start[0] + " " + start[1] + " " + end[0] + " " + end[1];
            System.out.printf("左划, 从 %s, %s 滑动到 %s, %s%n",start[0], start[1], end[0], end[1]);
        }else {
            commandStr = "adb shell input swipe " + end[0] + " " + end[1] + " " + start[0] + " " + start[1];
            System.out.printf("右划, 从 %s, %s 滑动到 %s, %s%n",end[0], end[1], start[0], start[1]);
        }
        Runtime.getRuntime().exec(commandStr);
        TimeUnit.SECONDS.sleep(划动后最少停止时间 + random.nextInt(offset));
    }

    public static void 左划(int 划动后最少停止时间, int offset) throws IOException, InterruptedException {
        左划或者右划(划动后最少停止时间, offset, true);
    }

    public static void 右划(int 划动后最少停止时间, int offset) throws IOException, InterruptedException {
        左划或者右划(划动后最少停止时间, offset, false);
    }

    public static void 赚钱任务页面划动到最顶部() throws IOException, InterruptedException {
        for(int i = 0; i < 8; i++){
            下划(1, 1);
        }
    }
    public static void 赚钱任务页面划动到最底部() throws IOException, InterruptedException {
//        for(int i = 0; i < 8; i++){
//            上划(1, 1);
//        }
        CommonOperate.上划或者下划(6000, 8, 800, 800, 200, true);

    }

    public static void 打开抖音极速版(int userId) throws IOException, InterruptedException {
        Runtime.getRuntime().exec("adb shell am start --user " + userId +  " com.ss.android.ugc.aweme.lite/com.ss.android.ugc.aweme.splash.SplashActivity");
        TimeUnit.SECONDS.sleep(12);
    }

    public static void 退出抖音极速版(int userId) {
        try {
            Runtime.getRuntime().exec("adb shell am force-stop --user " + userId + " com.ss.android.ugc.aweme.lite");
            System.out.println("退出抖音");
            TimeUnit.MILLISECONDS.sleep(2000);
        }catch (Exception e){
            try {
                Runtime.getRuntime().exec("adb shell am force-stop --user " + userId + " com.ss.android.ugc.aweme.lite");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
