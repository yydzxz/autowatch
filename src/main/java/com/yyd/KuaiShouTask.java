package com.yyd;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.yyd.KuaiShouOperate.单击点赞;

public class KuaiShouTask {
    public static int 当前正在运行的userId = 999;
    static Random random = new Random();

    /**
     * 百分比
     */
    static volatile int 打开评论的概率;
    static volatile int 点赞的概率;

    static Long 上一次领取最底下中间宝藏的时间;

    //启动程序时，发现最底下中间那个宝藏已经有金币可以领取的话，这里就设置为true. 默认应该为false
    static volatile boolean 强制领取宝藏金币 = false;

    static {
        每天初始化();
    }
    public static void 每天初始化(){
        打开评论的概率 = 25;
        点赞的概率 = 5;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        任务中心签到();
//        任务中心最高5000金币悬赏();
//        任务中心逛街领1000金币();
        自动刷视频();
    }

    public static void 任务中心签到() throws IOException, InterruptedException {
        KuaiShouOperate.退出快手极速版(当前正在运行的userId);
        KuaiShouOperate.打开快手极速版(当前正在运行的userId);
        KuaiShouOperate.进入任务中心();
        CommonOperate.单击(861, 965, 30, 15, 1500, "签到领取奖励");
        CommonOperate.单击(520, 1369, 100, 10, 3500, "签到后看广告");
        CommonOperate.返回(2);
        //再看一个广告
        CommonOperate.单击(421, 1235, 200, 30, 3500, "签到后看广告");
        CommonOperate.返回(3);
        KuaiShouOperate.退出快手极速版(当前正在运行的userId);
    }


    public static void 任务中心最高5000金币悬赏() throws IOException, InterruptedException {
        KuaiShouOperate.退出快手极速版(当前正在运行的userId);
        KuaiShouOperate.打开快手极速版(当前正在运行的userId);
        KuaiShouOperate.进入任务中心();

        CommonOperate.上划或者下划(5000, 8, 800, 1900, 300, true );
        CommonOperate.上划或者下划(755, 2, 800, 1900, 300, false );

        for(int i = 0; i < 12; i++){
            CommonOperate.单击(100, 260, 300, 20, 1500, "最高5000金币悬赏");
            TimeUnit.SECONDS.sleep(42);
            Runtime.getRuntime().exec("adb shell input keyevent 4");
            TimeUnit.SECONDS.sleep(3);
            System.out.println("最高5000金币悬赏完成第" + i + "次");
        }
    }

    public static void 任务中心逛街领1000金币() throws IOException, InterruptedException {
        KuaiShouOperate.退出快手极速版(当前正在运行的userId);
        KuaiShouOperate.打开快手极速版(当前正在运行的userId);
        KuaiShouOperate.进入任务中心();
        CommonOperate.上划或者下划(6000, 8, 800, 1900, 300, true );

        CommonOperate.单击(200, 1365, 400, 30, 1500, "逛街领1000金币");
        TimeUnit.SECONDS.sleep(2);

        for(int i = 0; i < 30; i++){
            CommonOperate.上划或者下划(6000, 40, 800, 1900, 300, true );
        }
        KuaiShouOperate.退出快手极速版(当前正在运行的userId);
    }

    public static void 自动刷视频() throws IOException, InterruptedException {
        int count = 1;
        // 目前宝箱刷新时间好像是20分钟
        long 宝箱刷新时间 = 22 * 60 * 1000;

        long 每一轮刷视频的开始时间 = System.currentTimeMillis();
        CommonOperate.退出所有App();
        KuaiShouOperate.打开快手极速版(当前正在运行的userId);
        while(true){
            CommonOperate.上划或者下划(15, 5, true);
//            CommonOperate.上划或者下划(800 + random.nextInt(170), 1, 700, 10000, 15000, true);
            boolean 是否点赞 = random.nextInt(100) < 点赞的概率;
            if(是否点赞){
                单击点赞(962.1, 1118.3);
            }
            if(上一次领取最底下中间宝藏的时间 == null){
                if(System.currentTimeMillis() - 每一轮刷视频的开始时间 > 宝箱刷新时间 || 强制领取宝藏金币){
                    KuaiShouOperate.领取任务页宝箱金币();
                    上一次领取最底下中间宝藏的时间 = System.currentTimeMillis();
                    强制领取宝藏金币 = false;
                }
            }else {
                if(System.currentTimeMillis() - 上一次领取最底下中间宝藏的时间 > 宝箱刷新时间 || 强制领取宝藏金币){
                    KuaiShouOperate.领取任务页宝箱金币();
                    上一次领取最底下中间宝藏的时间 = System.currentTimeMillis();
                    强制领取宝藏金币 = false;
                }
            }
            if(count % 60 == 0){
                KuaiShouOperate.退出快手极速版(当前正在运行的userId);
                TimeUnit.SECONDS.sleep(60);
                KuaiShouOperate.打开快手极速版(当前正在运行的userId);
            }
            System.out.println("已经观看了" + count++ + "个视频");
        }
    }
}
