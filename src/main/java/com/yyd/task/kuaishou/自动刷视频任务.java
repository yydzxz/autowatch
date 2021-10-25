package com.yyd.task.kuaishou;

import com.yyd.App;
import com.yyd.CommonOperate;
import com.yyd.KuaiShouOperate;
import com.yyd.annotations.TaskAnnotation;
import com.yyd.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.yyd.KuaiShouOperate.单击点赞;

@TaskAnnotation(cron = "", 优先级 = App.低优先级, 所属app = App.KUAI_SHOU)
public class 自动刷视频任务 extends ITask {
    static Logger log = LoggerFactory.getLogger(自动刷视频任务.class);

    volatile int 打开评论的概率;
    volatile int 点赞的概率;

    public 自动刷视频任务(int 当前正在运行的userId) {
        super(当前正在运行的userId);
        this.打开评论的概率 = 25;
        if(当前正在运行的userId == App.主用户id){
            this.点赞的概率 = 5;
        }else {
            this.点赞的概率 = 0;
        }
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
            int count = 1;
            CommonOperate.退出所有App();
            KuaiShouOperate.打开快手极速版(当前正在运行的userId);
            while(true){
                CommonOperate.上划或者下划(15, 5, true);
                boolean 是否点赞 = random.nextInt(100) < 点赞的概率;
                if(是否点赞){
                    单击点赞(962.1, 1118.3);
                }
                System.out.println("已经观看了" + count++ + "个视频");
                if(这个任务这次是否已经执行了足够时间()){
                    log.info("已经执行了足够时间:" + this.这个任务每次执行的最长时间);
                    break;
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }finally {
            CommonOperate.退出所有App();
        }
    }
}
