package com.yyd.task.kuaishou;

import com.yyd.App;
import com.yyd.CommonOperate;
import com.yyd.KuaiShouOperate;
import com.yyd.annotations.TaskAnnotation;
import com.yyd.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@TaskAnnotation(cron = "", 优先级 = App.高优先级, 所属app = App.KUAI_SHOU)
public class 任务中心最高5000金币悬赏 extends ITask {
    static Logger log = LoggerFactory.getLogger(任务中心最高5000金币悬赏.class);

    public 任务中心最高5000金币悬赏(int 当前正在运行的userId) {
        super(当前正在运行的userId);
    }

    @Override
    public boolean 任务满足开始条件() {
        return true;
    }

    @Override
    public long 这个任务每次执行的最长时间() {
        return -1;
    }

    @Override
    public void 初始化时间段() {
        添加时间段(0, 0,0,23,59 ,59, -1);
    }

    @Override
    public void run() {
        try {
            CommonOperate.退出所有App();
            KuaiShouOperate.打开快手极速版(当前正在运行的userId);
            KuaiShouOperate.进入任务中心();

            CommonOperate.上划或者下划(5000, 8, 800, 1900, 300, true );
            CommonOperate.上划或者下划(800, 3, 800, 1000, 300, false );

            for(int i = 0; i < 12; i++){
                CommonOperate.单击(300, 300, 100, 50, 1500, "最高5000金币悬赏");
                TimeUnit.SECONDS.sleep(42);
                Runtime.getRuntime().exec("adb shell input keyevent 4");
                TimeUnit.SECONDS.sleep(3);
                System.out.println("最高5000金币悬赏完成第" + i + "次");
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }finally {
            CommonOperate.退出所有App();

        }

    }
}
