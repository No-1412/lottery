package com.youge.yogee.publicutils;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.FileUtil;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wjc on 2017-12-11 上传图片工具类
 */
public class ImgUploudUtlis {
    public static Map getUploud(HttpServletRequest request,MultipartFile file) {

        Map map = new HashMap();

        if (file != null && file.getSize() > 0) {
            String originalFilename = null;
            //获取文件名称
            originalFilename = file.getOriginalFilename();
            //判断文件名称
            if (originalFilename != null && !originalFilename.equals("")) {
                //获取时间作为最后目录文件夹名称
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                Date date = new Date();
                String date_path = format.format(date);
                //声明文件地址变量
                String fileNames = StringUtils.EMPTY;
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                //获取文件名称
                fileNames = FileUtil.fileUploadUnused(multiRequest, date_path + "/");
                //按|截取文件名称
                String[] str= fileNames.split("\\|");
                StringBuffer strBufffer = new StringBuffer();
                //长度
                int strLength = str.length;
                // 返回格式  "|8.jpg|8.jpg|"  一个图片时长度为2
                for (int i=0;i<strLength;i++){
                    if(i==0){
                        continue;
                    }
                    strBufffer.append("|").append(Global.getConfig("userfiles.imagePath")).append(str[i]);
                }
                //单张图片
                map.put("fileNames",Global.getConfig("userfiles.imagePath")+str[1]);//路径+图片名称
                //图片集
                map.put("fileListNames",strBufffer.toString());//路径+图片名称

                map.put("imgPath", Global.getConfig("userfiles.basedir"));//图片存储路径截止到日期文件夹
            }
        }

        return map;
    }
}
