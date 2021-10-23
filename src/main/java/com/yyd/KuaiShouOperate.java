package com.yyd;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class KuaiShouOperate {

    static Random random = new Random();

    public static void 打开快手极速版(int userId) throws IOException, InterruptedException {
        Runtime.getRuntime().exec("adb shell am start --user " + userId +  " com.kuaishou.nebula/com.yxcorp.gifshow.HomeActivity");
        TimeUnit.SECONDS.sleep(12);
        TimeUnit.SECONDS.sleep(20);
        //第一个视频可能是广告，而不是正常的视频页，所以没有进入任务中心之类的按钮，所以先返回一下
        Runtime.getRuntime().exec("adb shell input keyevent 4");
        TimeUnit.SECONDS.sleep(3);
    }

    public static void 退出快手极速版(int userId) {
        try {
            Runtime.getRuntime().exec("adb shell am force-stop --user " + userId + " com.kuaishou.nebula");
            System.out.println("退出快手");
            TimeUnit.MILLISECONDS.sleep(2000);
        }catch (Exception e){
            try {
                Runtime.getRuntime().exec("adb shell am force-stop --user " + userId + " com.kuaishou.nebula");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static void 进入任务中心() throws IOException, InterruptedException {
        System.out.println("进入任务中心");
        double[] 任务中心入口坐标 = new double[2];
        任务中心入口坐标[0] = 953 + random.nextDouble() * 10;
        任务中心入口坐标[1] = 423 + random.nextDouble() * 6;
        Runtime.getRuntime().exec("adb shell input tap " + 任务中心入口坐标[0] + " " + 任务中心入口坐标[1]);
        TimeUnit.SECONDS.sleep(25);
    }

    public static void 单击点赞(double 点赞x, double 点赞y) throws IOException, InterruptedException {
        CommonOperate.单击(962.1, 1118.3);
        //点赞后跟一个返回操作，防止点赞操作误入直播间
        Runtime.getRuntime().exec("adb shell input keyevent 4");
        TimeUnit.MILLISECONDS.sleep(2000);
    }

}
