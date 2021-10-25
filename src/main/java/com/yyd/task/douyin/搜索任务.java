package com.yyd.task.douyin;

import com.yyd.App;
import com.yyd.CommonOperate;
import com.yyd.DouYinOperate;
import com.yyd.annotations.TaskAnnotation;
import com.yyd.task.ITask;
import com.yyd.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

@TaskAnnotation(cron = "", 优先级 = App.高优先级, 所属app = App.DOU_YIN)
public class 搜索任务 extends ITask {

    Logger log = LoggerFactory.getLogger(搜索任务.class);
    Config config;

    public 搜索任务(int 当前正在运行的userId) {
        super(当前正在运行的userId);
        this.config = gson.fromJson(FileUtil.读取resources下的文件("/task/douyin/搜索任务.json"), Config.class);
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
        添加时间段(0, 0,0,23,59 ,59, 1);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < config.get搜索次数(); i++){
                CommonOperate.退出所有App();
                DouYinOperate.打开抖音极速版(当前正在运行的userId);
                CommonOperate.单击(config.get放大镜坐标x(), config.get放大镜坐标y(), 2, 10, 3000, "点击放大镜标志的搜索按钮");

                String 搜索词 = config.get用于搜索的列表().get(random.nextInt(config.get用于搜索的列表().size()));
                Runtime.getRuntime().exec("adb shell input keyboard text " + 搜索词);
                log.info("输入搜索词语:" + 搜索词);
                TimeUnit.SECONDS.sleep(5);

                CommonOperate.单击(config.get右小角搜索确认坐标x(), config.get右小角搜索确认坐标y(), 2, 2, 4, "点击右下角搜索确认");
                CommonOperate.单击(400, 350, 50, 200, 4000, "随机选中一个搜索结果");
                CommonOperate.单击(400, 350, 50, 200, 4000, "随机选中一个搜索结果");
                DouYinOperate.上划(15, 5);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }finally {
            DouYinOperate.退出抖音极速版(当前正在运行的userId);
        }
    }

    private static class Config{
        List<String> 用于搜索的列表;
        private int 搜索次数;
        private double 放大镜坐标x;
        private double 放大镜坐标y;
        private double 右小角搜索确认坐标x;
        private double 右小角搜索确认坐标y;

        public int get搜索次数() {
            return 搜索次数;
        }

        public void set搜索次数(int 搜索次数) {
            this.搜索次数 = 搜索次数;
        }

        public double get放大镜坐标x() {
            return 放大镜坐标x;
        }

        public void set放大镜坐标x(double 放大镜坐标x) {
            this.放大镜坐标x = 放大镜坐标x;
        }

        public double get放大镜坐标y() {
            return 放大镜坐标y;
        }

        public void set放大镜坐标y(double 放大镜坐标y) {
            this.放大镜坐标y = 放大镜坐标y;
        }

        public double get右小角搜索确认坐标x() {
            return 右小角搜索确认坐标x;
        }

        public void set右小角搜索确认坐标x(double 右小角搜索确认坐标x) {
            this.右小角搜索确认坐标x = 右小角搜索确认坐标x;
        }

        public double get右小角搜索确认坐标y() {
            return 右小角搜索确认坐标y;
        }

        public void set右小角搜索确认坐标y(double 右小角搜索确认坐标y) {
            this.右小角搜索确认坐标y = 右小角搜索确认坐标y;
        }

        public List<String> get用于搜索的列表() {
            return 用于搜索的列表;
        }

        public void set用于搜索的列表(List<String> 用于搜索的列表) {
            this.用于搜索的列表 = 用于搜索的列表;
        }
    }
}
