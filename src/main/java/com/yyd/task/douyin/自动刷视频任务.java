package com.yyd.task.douyin;

import com.yyd.App;
import com.yyd.CommonOperate;
import com.yyd.DouYinOperate;
import com.yyd.annotations.TaskAnnotation;
import com.yyd.task.ITask;
import com.yyd.util.LogUtil;

import java.io.IOException;
import java.util.Random;

import static com.yyd.DouYinOperate.单击点赞;

@TaskAnnotation(cron = "", 优先级 = App.低优先级)
public class 自动刷视频任务 extends ITask {

    volatile boolean 自动刷视频任务是否应该停止;

    volatile int 打开评论的概率;
    volatile int 点赞的概率;

    Long 上一次领取最底下中间宝藏的时间;

    //启动程序时，发现最底下中间那个宝藏已经有金币可以领取的话，这里就设置为true. 默认应该为false
    volatile boolean 强制领取宝藏金币;
    //领取几次后，这个宝箱的位置会从中间变成右边
    volatile String 任务宝箱位置;

    Random random = new Random();

    public 自动刷视频任务(int 当前正在运行的userId) {
        super(当前正在运行的userId);
        this.当前正在运行的userId = 当前正在运行的userId;
        this.自动刷视频任务是否应该停止 = false;
        this.强制领取宝藏金币 = false;
        this.任务宝箱位置 = "右";
        this.打开评论的概率 = 25;
        this.点赞的概率 = 0;
    }

    @Override
    public boolean 任务满足开始条件() {
        return true;
    }

    @Override
    public long 这个任务每次执行的最长时间() {
        return 10 * 60 * 1000;
    }

    @Override
    public void 初始化时间段() {
        添加时间段(0, 0,0,23,59 ,59, -1);
    }

    @Override
    public void run() {
        try {
            CommonOperate.退出所有App();
            DouYinOperate.打开抖音极速版(当前正在运行的userId);
            int count = 0;
            while(true){
                int 划动后最少停止时间 = 5;
                int 划动后最少停止时间的基础上随机延长时间 = 20;
                boolean 是否打开评论 = random.nextInt(100) < 打开评论的概率;
                boolean 是否点赞 = random.nextInt(100) < 点赞的概率;
                if(是否打开评论){
                    //打开评论就会耽搁一会儿，所以缩短随机延长时间
                    划动后最少停止时间的基础上随机延长时间 = 5;
                }
                DouYinOperate.上划(划动后最少停止时间, 划动后最少停止时间的基础上随机延长时间);
                if(是否打开评论){
                    DouYinOperate.评论区操作();
                }
                if(是否点赞){
                    单击点赞(988, 1035);
                }
                System.out.println("已经观看了" + ++count + "个视频");
                if(这个任务这次是否已经执行了足够时间()){
                    LogUtil.log("已经执行了足够时间:" + this.这个任务每次执行的最长时间);
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            CommonOperate.退出所有App();
        }
    }
}
