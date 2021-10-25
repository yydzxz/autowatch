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
public class 任务中心逛街领1000金币 extends ITask {
    static Logger log = LoggerFactory.getLogger(任务中心逛街领1000金币.class);

    public 任务中心逛街领1000金币(int 当前正在运行的userId) {
        super(当前正在运行的userId);
    }

    @Override
    public boolean 任务满足开始条件() {
        return true;
    }

    @Override
    public long 这个任务每次执行的最长时间() {
        return 0;
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
            CommonOperate.上划或者下划(6000, 8, 800, 1900, 300, true );

            CommonOperate.单击(200, 1365, 400, 30, 1500, "逛街领1000金币");
            TimeUnit.SECONDS.sleep(2);
            for(int i = 0; i < 60; i++){
                CommonOperate.上划或者下划(6000, 15, 800, 1900, 300, true );
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }finally {
            CommonOperate.退出所有App();
        }
    }
}
