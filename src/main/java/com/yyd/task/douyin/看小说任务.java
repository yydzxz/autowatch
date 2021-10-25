package com.yyd.task.douyin;

import com.yyd.App;
import com.yyd.CommonOperate;
import com.yyd.DouYinOperate;
import com.yyd.annotations.TaskAnnotation;
import com.yyd.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TaskAnnotation(cron = "", 优先级 = App.高优先级, 所属app = App.DOU_YIN)
public class 看小说任务 extends ITask {
    static Logger log = LoggerFactory.getLogger(看小说任务.class);

    public 看小说任务(int 当前正在运行的userId) {
        super(当前正在运行的userId);
    }

    @Override
    public boolean 任务满足开始条件() {
        return true;
    }

    @Override
    public long 这个任务每次执行的最长时间() {
        return 30 * 60 * 1000;
    }

    @Override
    public void 初始化时间段() {
        添加时间段(0, 0,0,23,59 ,59, 1);
    }

    @Override
    public void run() {
        try {
            CommonOperate.退出所有App();
            DouYinOperate.打开抖音极速版(当前正在运行的userId);
            DouYinOperate.点击视频页最底下中间那个按钮打开任务页();
            DouYinOperate.赚钱任务页面划动到最底部();
            CommonOperate.单击(899, 1259, 2, 2, 10000, "点击看小说");
            CommonOperate.单击(135.2, 700.5, 400, 100, 5000, "点击书架上的小说");
            while (!这个任务这次是否已经执行了足够时间()){
                DouYinOperate.左划(6, 6);
            }
            DouYinOperate.退出抖音极速版(当前正在运行的userId);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }finally {
            CommonOperate.退出所有App();
        }
    }
}
