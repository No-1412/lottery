package com.youge.yogee.interfaces.util;

import java.io.File;
import java.util.List;

/**
 * Created by RenHaipeng on 2016/7/30 0030.
 */
public class MoiveCaptureUtil {
    public static final String FFMPEG_PATH = "/alidata/server/apache-tomcat-7.0.47-zhaoshi-8089/webapps/ROOT/ffmpegShared/bin/ffmpeg.exe";//服务器
//    public static final String FFMPEG_PATH = "F:/test/ffmpegShared/bin/ffmpeg.exe";//测试
    public static boolean processImg(String veido_path,String veidoCapture_path) {
//        System.out.println("video==============="+veido_path);
//        System.out.println("capture==============="+veidoCapture_path);
        File file = new File(veido_path);
        if (!file.exists()) {
            System.err.println("路径[" + veido_path + "]对应的视频文件不存在!");
            return false;
        }
        List<String> commands = new java.util.ArrayList<String>();
//        commands.add(FFMPEG_PATH);//Windows
        commands.add("ffmpeg");//Linux
        commands.add("-i");
        commands.add(veido_path);
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        commands.add("-ss");
        commands.add("00:00:00");//这个参数是设置截取视频多少秒时的画面
        commands.add("-vframes");
        commands.add("1");
//        commands.add("-t");
//        commands.add("0.001");
//        commands.add("-s");
//        commands.add("1280x720");
        commands.add(veidoCapture_path.substring(0, veidoCapture_path.lastIndexOf(".")).replaceFirst("vedio", "file") + ".jpg");
//        System.out.println("real capture==============="+veidoCapture_path.substring(0, veidoCapture_path.lastIndexOf(".")).replaceFirst("vedio", "file"));
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
            System.out.println("截取成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
