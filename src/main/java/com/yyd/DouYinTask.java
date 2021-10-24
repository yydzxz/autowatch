//package com.yyd;
//
//import com.yyd.annotations.TaskAnnotation;
//import com.yyd.util.MyDateUtil;
//
//import java.io.IOException;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.Scanner;
//import java.util.concurrent.TimeUnit;
//
//import static com.yyd.DouYinOperate.单击点赞;
//
//public class DouYinTask {
//    /**
//     * 0:机主
//     * 999：分身
//     */
//    public static int 当前正在运行的userId = 0;
//
//    //启动程序时，发现最底下中间那个宝藏已经有金币可以领取的话，这里就设置为true. 默认应该为false
//    static volatile boolean 强制领取宝藏金币;
//    //领取几次后，这个宝箱的位置会从中间变成右边
//    static volatile String 任务宝箱位置;
//
//    static long 每天刷视频默认最长时间 = 12 * 60 * 60 * 1000;
//
//    static Random random = new Random();
//    static List<String> 用于搜索的列表 = new ArrayList<>();
//
//    /**
//     * 百分比
//     */
//    static volatile int 打开评论的概率;
//    static volatile int 点赞的概率;
//
//    static volatile boolean 自动刷视频任务是否应该停止 = false;
//
//    static Long 上一次领取最底下中间宝藏的时间;
//    static long 刷视频的开始时间;
//    static long 每天刷视频最长时间;
//
//    static {
//        用于搜索的列表.add("zhoujielun");
//        用于搜索的列表.add("lol");
//        用于搜索的列表.add("s11");
//        用于搜索的列表.add("hupu");
//        用于搜索的列表.add("swift");
//        用于搜索的列表.add("doinb");
//        用于搜索的列表.add("faker");
//        用于搜索的列表.add("rng");
//        用于搜索的列表.add("edg");
//
//        // 如果现在距离晚上12点不足“每天刷视频默认最长时间”, 那么就只刷到晚上12点
//        // 减去30 * 60 * 1000是因为，经常有睡眠操作，防止意外
//        每天刷视频最长时间 = Long.min(每天刷视频默认最长时间, MyDateUtil.现在距离晚上12点还剩多少毫秒() - 30 * 60 * 1000);
//        System.out.println("今天刷视频预计时间" + MyDateUtil.transform(每天刷视频最长时间/1000));
//        刷视频的开始时间 = System.currentTimeMillis();
//        每天初始化();
//    }
//    public static void 每天初始化(){
//        强制领取宝藏金币 = false;
//        任务宝箱位置 = "右";
//        打开评论的概率 = 25;
//        点赞的概率 = 5;
//    }
//
//    public static void main(String[] args) throws IOException, InterruptedException {
//        Thread thread = new Thread(new 命令输入处理任务());
//        thread.setDaemon(true);
//        thread.start();
////        领睡觉金币();
////        领吃饭金币();
////        搜索任务();
////        看小说任务(25 * 60);
//        自动刷视频();
//    }
//
//    public static class 命令输入处理任务 implements Runnable{
//        @Override
//        public void run() {
//            String command = "";
//            while (true){
//                Scanner scanner = new Scanner(System.in);
//                if(scanner.hasNextLine()){
//                    try {
//                        command = scanner.nextLine().trim();
//                        System.out.println("收到命令:" + command);
//                        if("强制领取右边宝箱金币".equals(command)){
//                            强制领取宝藏金币 = true;
//                            任务宝箱位置 = "右";
//                        }else if("强制领取中间宝箱金币".equals(command)){
//                            强制领取宝藏金币 = true;
//                            任务宝箱位置 = "中";
//                        }else if("不强制领取宝箱金币".equals(command)){
//                            强制领取宝藏金币 = false;
//                        }else if(command.startsWith("宝箱位置")){
//                            任务宝箱位置 = command.split(" ")[1];
//                        }else if(command.startsWith("点赞")){
//                            点赞的概率 = Integer.parseInt(command.split(" ")[1]);
//                        }else if(command.startsWith("评论")){
//                            打开评论的概率 = Integer.parseInt(command.split(" ")[1]);
//                        }
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//
//    public static void 自动刷视频() throws IOException, InterruptedException {
//        CommonOperate.退出所有App();
//        DouYinOperate.打开抖音极速版(当前正在运行的userId);
//        long 今天刷视频的开始时间 = System.currentTimeMillis();
//        long 明天刷视频的开始时间 = MyDateUtil.第二天的某个时间点(7, 3);
//
//        long 每一轮刷视频的开始时间 = System.currentTimeMillis();
//        long 每一轮刷视频多长时间后回到桌面休息 = 60 * 60 * 1000L;
//        // 目前宝箱刷新时间好像是20分钟
//        long 宝箱刷新时间 = 21 * 60 * 1000;
//        int count = 0;
//
//        while(!自动刷视频任务是否应该停止){
//            int 划动后最少停止时间 = 5;
//            int offset = 20;
//            boolean 是否打开评论 = random.nextInt(100) < 打开评论的概率;
//            if(是否打开评论){
//                //打开评论就会耽搁一会儿
//                offset = 5;
//            }
//            DouYinOperate.上划(划动后最少停止时间, offset);
//            if(是否打开评论){
//                DouYinOperate.评论区操作();
//            }
//            if(random.nextInt(100) < 点赞的概率){
//                单击点赞(988, 1035);
//            }
//            if(上一次领取最底下中间宝藏的时间 == null){
//                if(System.currentTimeMillis() - 每一轮刷视频的开始时间 > 宝箱刷新时间 || 强制领取宝藏金币){
//                    领取任务页宝箱金币(任务宝箱位置);
//                    上一次领取最底下中间宝藏的时间 = System.currentTimeMillis();
//                    强制领取宝藏金币 = false;
//                }
//            }else {
//                if(System.currentTimeMillis() - 上一次领取最底下中间宝藏的时间 > 宝箱刷新时间 || 强制领取宝藏金币){
//                    领取任务页宝箱金币(任务宝箱位置);
//                    上一次领取最底下中间宝藏的时间 = System.currentTimeMillis();
//                    强制领取宝藏金币 = false;
//                }
//            }
//            System.out.println("已经观看了" + ++count + "个视频");
//
//            //长时间刷视频后，返回桌面休息一段时间
//            if(System.currentTimeMillis() - 每一轮刷视频的开始时间 > 每一轮刷视频多长时间后回到桌面休息){
//                DouYinOperate.退出抖音极速版(当前正在运行的userId);
//                TimeUnit.SECONDS.sleep(30);
//                //如果今天已经刷够了时间，那么要等到 `下一轮刷视频的开始时间` 才能继续刷
//                if(System.currentTimeMillis() - 今天刷视频的开始时间 > 每天刷视频最长时间){
//                    System.out.println("今天已经刷够了时间: " + ((System.currentTimeMillis() - 今天刷视频的开始时间)/1000) + "秒");
//                    while(System.currentTimeMillis() < 明天刷视频的开始时间){
//                        TimeUnit.MINUTES.sleep(10);
//                    }
//                    System.out.println("新的一天已经来到，精神满满的开始刷视频");
//                    今天刷视频的开始时间 = System.currentTimeMillis();
//                    System.out.println("今天的开始时间是:" + MyDateUtil.毫秒转LocalDateTime(今天刷视频的开始时间).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//                    明天刷视频的开始时间 = MyDateUtil.第二天的某个时间点(7, 3);
//                    System.out.println("明天刷视频的开始时间:" + MyDateUtil.毫秒转LocalDateTime(明天刷视频的开始时间).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//                    每天刷视频最长时间 = 每天刷视频默认最长时间;
//                    System.out.println("今天刷视频预计时间" + MyDateUtil.transform(每天刷视频最长时间/1000));
//                    每天初始化();
//                    搜索任务();
//                    看小说任务(50 * 60);
//                }
//                每一轮刷视频的开始时间 = System.currentTimeMillis();
//                DouYinOperate.打开抖音极速版(当前正在运行的userId);
//            }
//        }
//    }
//    public static void 自动刷视频2() throws IOException, InterruptedException {
//        CommonOperate.退出所有App();
//        DouYinOperate.打开抖音极速版(当前正在运行的userId);
//        long 每一轮刷视频的开始时间 = System.currentTimeMillis();
//        long 每一轮刷视频多长时间后回到桌面休息 = 60 * 60 * 1000L;
//        // 目前宝箱刷新时间好像是20分钟
//        long 宝箱刷新时间 = 22 * 60 * 1000;
//        int count = 0;
//        while(!自动刷视频任务是否应该停止){
//            int 划动后最少停止时间 = 5;
//            int 划动后最少停止时间的基础上随机延长时间 = 20;
//            boolean 是否打开评论 = random.nextInt(100) < 打开评论的概率;
//            boolean 是否点赞 = random.nextInt(100) < 点赞的概率;
//            if(是否打开评论){
//                //打开评论就会耽搁一会儿，所以缩短随机延长时间
//                划动后最少停止时间的基础上随机延长时间 = 5;
//            }
//            DouYinOperate.上划(划动后最少停止时间, 划动后最少停止时间的基础上随机延长时间);
//            if(是否打开评论){
//                DouYinOperate.评论区操作();
//            }
//            if(是否点赞){
//                单击点赞(988, 1035);
//            }
//            if(上一次领取最底下中间宝藏的时间 == null){
//                if(System.currentTimeMillis() - 每一轮刷视频的开始时间 > 宝箱刷新时间 || 强制领取宝藏金币){
//                    领取任务页宝箱金币(任务宝箱位置);
//                    上一次领取最底下中间宝藏的时间 = System.currentTimeMillis();
//                    强制领取宝藏金币 = false;
//                }
//            }else {
//                if(System.currentTimeMillis() - 上一次领取最底下中间宝藏的时间 > 宝箱刷新时间 || 强制领取宝藏金币){
//                    领取任务页宝箱金币(任务宝箱位置);
//                    上一次领取最底下中间宝藏的时间 = System.currentTimeMillis();
//                    强制领取宝藏金币 = false;
//                }
//            }
//            System.out.println("已经观看了" + ++count + "个视频");
//            //长时间刷视频后，返回桌面休息一段时间
//            if(System.currentTimeMillis() - 每一轮刷视频的开始时间 > 每一轮刷视频多长时间后回到桌面休息){
//                DouYinOperate.退出抖音极速版(当前正在运行的userId);
//                TimeUnit.SECONDS.sleep(30);
//                每一轮刷视频的开始时间 = System.currentTimeMillis();
//                DouYinOperate.打开抖音极速版(当前正在运行的userId);
//            }
//        }
//    }
//
//    public static void 看小说任务(long 看小说时间单位秒) throws IOException, InterruptedException {
//        System.out.println(MyDateUtil.毫秒转LocalDateTime(System.currentTimeMillis()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)  +  "看小说任务开始");
//        DouYinOperate.退出抖音极速版(当前正在运行的userId);
//        DouYinOperate.打开抖音极速版(当前正在运行的userId);
//        DouYinOperate.点击视频页最底下中间那个按钮打开任务页();
//        DouYinOperate.赚钱任务页面划动到最底部();
//        CommonOperate.单击(899, 1259, 2, 2, 5000, "点击看小说");
//        CommonOperate.单击(135.2, 700.5, 400, 100, 5000, "点击书架上的小说");
//        long 看小说开始时间 = System.currentTimeMillis();
//        long 看小说结束时间 = 看小说开始时间 + 看小说时间单位秒 * 1000;
//        while (System.currentTimeMillis() < 看小说结束时间){
//            DouYinOperate.左划(6, 6);
//        }
//
//        DouYinOperate.退出抖音极速版(当前正在运行的userId);
//        System.out.println(MyDateUtil.毫秒转LocalDateTime(System.currentTimeMillis()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)  +  "看小说任务结束");
//    }
//
//    public static void 领取任务页宝箱金币(String 任务宝箱位置) throws IOException, InterruptedException {
////        DouYinOperate.退出抖音极速版(当前正在运行的userId);
////        DouYinOperate.打开抖音极速版(当前正在运行的userId);
//        DouYinOperate.点击视频页最底下中间那个按钮打开任务页();
//        double[] 领取金币坐标 = {594.2f, 1737.2f};
//        if("中".equals(任务宝箱位置)){
//            领取金币坐标[0] = 594.2 + random.nextDouble() * 2;
//            领取金币坐标[1] = 1737.2 + random.nextDouble() * 2;
//        }else if("右".equals(任务宝箱位置)){
//            领取金币坐标[0] = 900 + random.nextDouble() * 20;
//            领取金币坐标[1] = 1700 + random.nextDouble() * 20;
//        }
//        CommonOperate.单击(领取金币坐标[0], 领取金币坐标[1], 2, 2,5000, "领取金币");
//
//        //领取宝箱金币后，弹出的页面还有一个看广告按钮
//        CommonOperate.单击(556, 1188, 2, 2, 35000, "领取宝箱金币后，弹出的页面还有一个看广告按钮");
//        CommonOperate.返回(2);
//        //返回后会有一个弹窗， 可能会有(也可能没有)一个 再去看看的选项，尝试点击一下，点不到也无所谓
//        CommonOperate.单击(476, 1128, 2, 20, 33000, "返回后会有一个弹窗， 可能会有(也可能没有)一个 再去看看的选项，尝试点击一下，点不到也无所谓");
//        CommonOperate.返回(2);
//        CommonOperate.返回(4);
//        CommonOperate.返回(2);
//    }
//
//
//    public static void 搜索任务() {
//        try {
//            System.out.println(MyDateUtil.毫秒转LocalDateTime(System.currentTimeMillis()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +  "搜索任务开始");
//            for (int i = 0; i < 3; i++){
//                DouYinOperate.退出抖音极速版(当前正在运行的userId);
//                DouYinOperate.打开抖音极速版(当前正在运行的userId);
//                CommonOperate.单击(1001, 153.2, 2, 10, 3000, "点击放大镜标志的搜索按钮");
//
//                String 搜索词 = 用于搜索的列表.get(random.nextInt(用于搜索的列表.size()));
//                Runtime.getRuntime().exec("adb shell input keyboard text " + 搜索词);
//                System.out.println("输入搜索词语" + 搜索词);
//                TimeUnit.SECONDS.sleep(5);
//
//                CommonOperate.单击(975, 1829, 2, 2, 4, "点击右下角搜索确认");
//                CommonOperate.单击(400, 300, 2, 400, 4000, "随机选中一个搜索结果");
//                DouYinOperate.上划(15, 5);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            DouYinOperate.退出抖音极速版(当前正在运行的userId);
//        }
//        System.out.println(MyDateUtil.毫秒转LocalDateTime(System.currentTimeMillis()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)  + "搜索任务结束");
//    }
//
//    public static void 领睡觉金币() throws IOException, InterruptedException {
//        CommonOperate.退出所有App();
//        DouYinOperate.打开抖音极速版(DouYinTask.当前正在运行的userId);
//        DouYinOperate.点击视频页最底下中间那个按钮打开任务页();
//        DouYinOperate.赚钱任务页面划动到最底部();
//        CommonOperate.单击(400, 435, 300, 20, 3500,"点击睡觉赚金币");
//        CommonOperate.单击(400, 1760, 20, 10, 2500,"点击我睡觉了");
//        CommonOperate.退出所有App();
//    }
//    public static void 领吃饭金币() throws IOException, InterruptedException {
//        CommonOperate.退出所有App();
//        DouYinOperate.打开抖音极速版(DouYinTask.当前正在运行的userId);
//        DouYinOperate.点击视频页最底下中间那个按钮打开任务页();
//        DouYinOperate.赚钱任务页面划动到最底部();
//        CommonOperate.单击(400, 1022, 300, 20, 3500,"点击吃饭补贴");
//        CommonOperate.单击(500, 1670, 200, 20, 3500,"领取夜宵补贴");
//        CommonOperate.单击(443, 1212, 200, 20, 35000,"看视频再领金币");
//        CommonOperate.返回(2);
//        CommonOperate.返回(2);
//        DouYinOperate.退出抖音极速版(DouYinTask.当前正在运行的userId);
//    }
//
//}
