package com.yyd.douyin;

import com.yyd.CommonOperate;
import com.yyd.DouYinOperate;
import com.yyd.DouYinTask;
import com.yyd.时间段;

import java.util.ArrayList;
import java.util.List;

public class 领睡觉金币任务 extends ITask{
    @Override
    public String getCron() {
        return null;
    }

    @Override
    public List<时间段> 任务只在这几个时间段执行() {
        List<时间段> list = new ArrayList<>();
        list.add(new 时间段(5,0,0, 9,0 ,0));
        list.add(new 时间段(11,0,0, 14, 0, 0));
        list.add(new 时间段(17, 0, 0, 20, 0, 0));
        list.add(new 时间段(21, 0, 0, 23, 59, 59));
        return list;
    }

    @Override
    public boolean getShouldStop() {
        return false;
    }

    @Override
    public void setShouldStop(boolean shouldStop) {

    }

    @Override
    public void run() {
        try {
            CommonOperate.退出所有App();
            DouYinOperate.打开抖音极速版(DouYinTask.当前正在运行的userId);
            DouYinOperate.点击视频页最底下中间那个按钮打开任务页();
            DouYinOperate.赚钱任务页面划动到最底部();
            CommonOperate.单击(835, 650, 20, 5, 2500,"点击睡觉赚金币");
            CommonOperate.单击(400, 1760, 20, 10, 2500,"点击我睡觉了");
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            DouYinOperate.退出抖音极速版(DouYinTask.当前正在运行的userId);
        }
    }
}
