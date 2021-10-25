package com.yyd.task.kuaishou;

import com.yyd.App;
import com.yyd.CommonOperate;
import com.yyd.KuaiShouOperate;
import com.yyd.annotations.TaskAnnotation;
import com.yyd.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TaskAnnotation(cron = "", 优先级 = App.高优先级, 所属app = App.KUAI_SHOU)
public class 领取每20分钟一次的宝箱 extends ITask {

    static Logger log = LoggerFactory.getLogger(领取每20分钟一次的宝箱.class);

    public 领取每20分钟一次的宝箱(int 当前正在运行的userId) {
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
            KuaiShouOperate.进入任务中心();
            CommonOperate.单击(881, 1750, 10, 10, 5000, "点击宝箱");
            CommonOperate.单击(470, 1193, 200, 10, 35000, "看精彩视频赚更多");
            CommonOperate.返回(2);
            CommonOperate.返回(2);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }finally {
            CommonOperate.退出所有App();
        }
    }
}
