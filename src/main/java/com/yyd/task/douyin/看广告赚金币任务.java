package com.yyd.task.douyin;

import com.yyd.App;
import com.yyd.CommonOperate;
import com.yyd.DouYinOperate;
import com.yyd.annotations.TaskAnnotation;
import com.yyd.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


@TaskAnnotation(cron = "", 优先级 = App.高优先级, 所属app = App.DOU_YIN)
public class 看广告赚金币任务 extends ITask {
    static Logger log = LoggerFactory.getLogger(看广告赚金币任务.class);

    public 看广告赚金币任务(int 当前正在运行的userId) {
        super(当前正在运行的userId);
        this.任务间隔时间毫秒 = 21 * 60 * 1000;
        set顺序(-1);
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
        添加时间段(0, 0,0,23,59 ,59, 12);
    }

    @Override
    public void run() {
        try {
            CommonOperate.退出所有App();
            DouYinOperate.打开抖音极速版(当前正在运行的userId);
            DouYinOperate.点击视频页最底下中间那个按钮打开任务页();
            DouYinOperate.赚钱任务页面划动到最底部();
            CommonOperate.上划或者下划(1267, 3, 800, 800, 200, false);
            TimeUnit.SECONDS.sleep(5);
            CommonOperate.单击(354, 327, 50, 5, 36000, "看广告赚金币");
            CommonOperate.返回(3);
            CommonOperate.单击(430, 1047, 5, 5, 36000, "再看一个");
            CommonOperate.返回(3);
            CommonOperate.返回(3);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }finally {
            CommonOperate.退出所有App();
        }
    }
}
