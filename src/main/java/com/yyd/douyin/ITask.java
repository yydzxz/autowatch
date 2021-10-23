package com.yyd.douyin;

import com.yyd.时间段;

import java.util.List;

public abstract class ITask implements Runnable{

    //暂时还用不上crom
    public abstract  String getCron();

    /**
     * HH:mm:ss
     * @return
     */
    public abstract List<时间段> 任务只在这几个时间段执行();

    public abstract boolean getShouldStop();

    public abstract void setShouldStop(boolean shouldStop);

    public void stop(){
        setShouldStop(true);
    }
}
