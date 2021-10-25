package com.yyd.task.douyin;

import com.yyd.App;
import com.yyd.CommonOperate;
import com.yyd.DouYinOperate;
import com.yyd.annotations.TaskAnnotation;
import com.yyd.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.yyd.util.MyDateUtil.毫秒转LocalDateTime;

@TaskAnnotation(cron = "", 优先级 = App.高优先级, 所属app = App.DOU_YIN)
public class 领取每20分钟一次的宝箱 extends ITask {
    static Logger log = LoggerFactory.getLogger(领取每20分钟一次的宝箱.class);

    private String 任务宝箱位置 = "右";

    public 领取每20分钟一次的宝箱(int 当前正在运行的userId) {
        super(当前正在运行的userId);
    }

    @Override
    public boolean 任务满足开始条件() {
        boolean 是否满足 = App.上个任务 != null && System.currentTimeMillis() - App.上个任务.get这个任务的开始时间() > 20 * 60 * 1000;
        if(!是否满足){
            if(App.上个任务 == null){
                log.info("领取每20分钟一次的宝箱的开始条件不满足");
            }else {
                log.info("执行队列中上一个任务的开始时间: {}", 毫秒转LocalDateTime(App.上个任务.get这个任务的开始时间()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }
        }
        return 是否满足;
    }

    @Override
    public long 这个任务每次执行的最长时间() {
        return -1;
    }

    @Override
    public void 初始化时间段() {
        添加时间段(0,0,0, 23,59 ,59, -1);
    }

    @Override
    public void run() {
        try {
            DouYinOperate.退出抖音极速版(当前正在运行的userId);
            DouYinOperate.打开抖音极速版(当前正在运行的userId);
            DouYinOperate.点击视频页最底下中间那个按钮打开任务页();
            double[] 领取金币坐标 = {594.2f, 1737.2f};
            if("中".equals(任务宝箱位置)){
                领取金币坐标[0] = 594.2 + random.nextDouble() * 2;
                领取金币坐标[1] = 1737.2 + random.nextDouble() * 2;
            }else if("右".equals(任务宝箱位置)){
                领取金币坐标[0] = 900 + random.nextDouble() * 20;
                领取金币坐标[1] = 1700 + random.nextDouble() * 20;
            }
            CommonOperate.单击(领取金币坐标[0], 领取金币坐标[1], 2, 2,5000, "领取金币");
            //领取宝箱金币后，弹出的页面还有一个看广告按钮
            CommonOperate.单击(556, 1188, 2, 2, 35000, "领取宝箱金币后，弹出的页面还有一个看广告按钮");
            CommonOperate.返回(2);
            //返回后会有一个弹窗， 可能会有(也可能没有)一个 再去看看的选项，尝试点击一下，点不到也无所谓
            CommonOperate.单击(476, 1128, 2, 20, 33000, "返回后会有一个弹窗， 可能会有(也可能没有)一个 再去看看的选项，尝试点击一下，点不到也无所谓");
            CommonOperate.返回(2);
            CommonOperate.返回(4);
            CommonOperate.返回(2);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            DouYinOperate.退出抖音极速版(当前正在运行的userId);
        }
    }
}
