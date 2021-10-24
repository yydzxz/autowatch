package com.yyd;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CommonOperate {

    static Random random = new Random();
    static double 屏幕y轴最小可用坐标 = 400;
    static double 屏幕y轴最大可用坐标 = 1400;
    static double 上下划动的最长距离 = 屏幕y轴最大可用坐标 - 屏幕y轴最小可用坐标;
    static double 上下划动的最短距离 = 100;

    public static void 上划或者下划(int 总共要划动的距离, int 分几次划动完成, int 每次划动耗时单位毫秒, int 划动后最少停止时间毫秒, int offset, boolean 上划) throws IOException, InterruptedException {
        double 平均每次移动距离 = 总共要划动的距离/分几次划动完成;
        if(平均每次移动距离 > 上下划动的最长距离){
            throw new RuntimeException("平均划动距离超过:" + 上下划动的最长距离 + "，可能会失败，所以请多分几次完成划动动作");
        }
        if(平均每次移动距离 < 上下划动的最短距离){
            throw new RuntimeException("划动距离太短，或者分的次数太多，上下划动的最短距离:" + 上下划动的最短距离);
        }
        double 前n减1次划动距离最大为 = 0;
        double 前n减1次划动距离最小为 = 0;
        if(分几次划动完成 > 1){
            //为了保证最后一次划动距离小于 上下划动的最长距离  ，大于 上下划动的最长距离
            前n减1次划动距离最大为 = (总共要划动的距离 - 上下划动的最短距离)/(分几次划动完成 - 1);
            前n减1次划动距离最小为 = (总共要划动的距离 - 上下划动的最长距离)/(分几次划动完成 - 1);
        }
        //每次需要2个坐标点，每个坐标点有x,y两个坐标
        int 总共的坐标点数量 = 分几次划动完成 * 2;
        double[][] 坐标数组 = new double[总共的坐标点数量][2];

        double 剩余可用于随机分配的距离 = 总共要划动的距离;
        for(int i = 0 ; i < 总共的坐标点数量; i++){
            double 随机分配的距离;
            if(i % 2 == 0){
                if(i == 总共的坐标点数量 - 2){
                    随机分配的距离 = 剩余可用于随机分配的距离;
                }else {
                    随机分配的距离 = 前n减1次划动距离最小为 + random.nextDouble() * (前n减1次划动距离最大为 - 前n减1次划动距离最小为);
                }
                坐标数组[i][1] =  屏幕y轴最大可用坐标 - random.nextDouble() * (屏幕y轴最大可用坐标 - 随机分配的距离);
                坐标数组[i + 1][1] =  坐标数组[i][1] - 随机分配的距离;
                剩余可用于随机分配的距离 -= 随机分配的距离;
            }
        }
        //初始化x坐标
        for (int i = 0; i < 总共的坐标点数量; i++){
            坐标数组[i][0] = 150 + random.nextDouble() * 600;
        }
        double 实际总共划动距离 = 0;
        //开始划动
        for (int i = 0; i < 总共的坐标点数量; i++){
            String commandStr;
            //TODO 左右划动也可以用这个方法，坐标点交换一下就行
            if(上划){
                commandStr = "adb shell input swipe " + 坐标数组[i][0] + " " + 坐标数组[i][1] + " " + 坐标数组[i+1][0] + " " + 坐标数组[i+1][1] + " " + 每次划动耗时单位毫秒;
                Runtime.getRuntime().exec(commandStr);
                System.out.println(String.format("上划, 从 %s, %s 滑动到 %s, %s",坐标数组[i][0], 坐标数组[i][1], 坐标数组[i+1][0], 坐标数组[i+1][1]));
            }else {
                commandStr = "adb shell input swipe " + 坐标数组[i+1][0] + " " + 坐标数组[i+1][1] + " " + 坐标数组[i][0] + " " + 坐标数组[i][1] + " " + 每次划动耗时单位毫秒;
                Runtime.getRuntime().exec(commandStr);
                System.out.println(String.format("下划, 从 %s, %s 滑动到 %s, %s",坐标数组[i+1][0], 坐标数组[i+1][1], 坐标数组[i][0], 坐标数组[i][1]));
            }
            double 本次划动距离 = Math.abs(坐标数组[i][1] - 坐标数组[i+1][1]);
            System.out.println("本次划动距离: " + 本次划动距离);
            实际总共划动距离 += 本次划动距离;
            TimeUnit.MILLISECONDS.sleep(划动后最少停止时间毫秒 + random.nextInt(offset));
            i++;
        }
        System.out.println("实际总共划动距离：" + 实际总共划动距离);
    }
    public static void 划动(double x1, double y1, double x2, double y2, long 划动时间单位毫秒) throws IOException {
        String commandStr = "adb shell input swipe " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + 划动时间单位毫秒;
        Runtime.getRuntime().exec(commandStr);
        System.out.println(String.format("下划, 从 %s, %s 滑动到 %s, %s",x1, y1, x2, y2));
    }

    @Deprecated
    public static void 上划或者下划(int 划动后最少停止时间, int offset, boolean 上划) throws IOException, InterruptedException {
        double[] start = new double[2];
        double[] end = new double[2];
        start[0] = 100 + random.nextFloat() * 400;
        start[1] = 700 + random.nextFloat() * 850;
        end[0] = start[0] + random.nextFloat() * 70 - random.nextFloat() * 70 ;
        end[1] = start[1] - 700 - random.nextInt(400);
        String commandStr;
        if(上划){
            commandStr = "adb shell input swipe " + start[0] + " " + start[1] + " " + end[0] + " " + end[1];
            System.out.println(String.format("上划, 从 %s, %s 滑动到 %s, %s",start[0], start[1], end[0], end[1]));

        }else {
            commandStr = "adb shell input swipe " + end[0] + " " + end[1] + " " + start[0] + " " + start[1];
            System.out.println(String.format("下划, 从 %s, %s 滑动到 %s, %s",end[0], end[1], start[0], start[1]));
        }
        Runtime.getRuntime().exec(commandStr);
        TimeUnit.SECONDS.sleep(划动后最少停止时间 + random.nextInt(offset));
    }

    public static void 单击(double x, double y) throws IOException, InterruptedException {
        单击(x, y, 2, 2, 1500, "单击");
    }

    public static void 单击(double x, double y, int x最大偏移, int y最大偏移, long 点击后休眠多久单位毫秒, String 动作名字) throws IOException, InterruptedException {
        System.out.println(动作名字);
        double[] 点赞坐标 = new double[2];
        点赞坐标[0] = x + random.nextDouble() * x最大偏移;
        点赞坐标[1] = y + random.nextDouble() * y最大偏移;
        Runtime.getRuntime().exec("adb shell input tap " + 点赞坐标[0] + " " + 点赞坐标[1]);
        TimeUnit.MILLISECONDS.sleep(点击后休眠多久单位毫秒);
    }

    public static void 返回(int 返回后停留时间单位秒) throws IOException, InterruptedException {
        System.out.println("返回");
        Runtime.getRuntime().exec("adb shell input keyevent 4");
        TimeUnit.SECONDS.sleep(返回后停留时间单位秒);
    }

    public static void 退出所有App(){
        DouYinOperate.退出抖音极速版(0);
        DouYinOperate.退出抖音极速版(999);
        KuaiShouOperate.退出快手极速版(0);
        KuaiShouOperate.退出快手极速版(999);
    }
}
