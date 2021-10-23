package com.yyd.douyin;

import com.yyd.DouYinTask;
import com.yyd.时间段;

import java.io.IOException;
import java.util.List;

public class 自动刷视频任务 extends ITask{
    @Override
    public String getCron() {
        return null;
    }

    @Override
    public List<时间段> 任务只在这几个时间段执行() {
        return null;
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
            DouYinTask.自动刷视频();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
