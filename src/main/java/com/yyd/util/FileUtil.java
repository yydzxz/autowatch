package com.yyd.util;

import com.yyd.task.ITask;

import java.io.*;
import java.util.List;

public class FileUtil {
    /**
     * 比如 /task/douyin/搜索任务.json
     * @param path
     * @return
     * @throws IOException
     */
    public static String 读取resources下的文件(String path) {
        int ch;
        StringBuffer sb = new StringBuffer();
        try(InputStream inputStream = ITask.class.getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));) {
            while ((ch = br.read()) != -1) {
                sb.append((char) ch);
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        System.out.println(sb);
        return sb.toString();
    }

    /** * 查找所有的文件 * * @param dir 路径 * @param fileList 文件集合 */
    public static void listFiles(File dir, List<File> fileList) {
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                listFiles(f, fileList);
            }
        } else {
            if(dir.getName().endsWith(".class")) {
                fileList.add(dir);
            }
        }
    }
}
