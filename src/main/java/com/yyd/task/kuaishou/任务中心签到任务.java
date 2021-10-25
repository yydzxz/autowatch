package com.yyd.task.kuaishou;

import com.yyd.App;
import com.yyd.CommonOperate;
import com.yyd.KuaiShouOperate;
import com.yyd.annotations.TaskAnnotation;
import com.yyd.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TaskAnnotation(cron = "", 优先级 = App.高优先级, 所属app = App.KUAI_SHOU)
public class 任务中心签到任务 extends ITask {
    static Logger log = LoggerFactory.getLogger(任务中心签到任务.class);

    public 任务中心签到任务(int 当前正在运行的userId) {
        super(当前正在运行的userId);
        set顺序(-1);
        今天这个任务是否执行 = false;
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
            CommonOperate.单击(861, 965, 30, 15, 1500, "签到领取奖励");
            CommonOperate.单击(520, 1369, 100, 10, 3000, "签到后看广告");
            CommonOperate.返回(2);
            //再看一个广告
            CommonOperate.单击(421, 1235, 200, 30, 35000, "再看一个广告");
            CommonOperate.返回(3);
            CommonOperate.单击(421, 1235, 200, 30, 35000, "再看一个广告");
            CommonOperate.返回(3);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }finally {
            CommonOperate.退出所有App();
        }
    }
}
