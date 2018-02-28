package com.youge.yogee.interfaces.util;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.google.common.collect.Maps;
import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.security.Digests;
import com.youge.yogee.common.utils.FileUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Administrator on 2015/10/23.
 */
public class FileUtil {

    public static String fileUpload(
            MultipartHttpServletRequest multiRequest, String path) {


        String fileNames = StringUtils.EMPTY;
        Iterator<String> ite = multiRequest.getFileNames();
        while (ite.hasNext()) {
            MultipartFile file = multiRequest.getFile(ite.next());

            if (file != null) {
                String fileMD5 = file.getName();
                byte[] filemd5Byte = null;
                try {
                    // 文件流
                    InputStream sourceInputStreamFile = file.getInputStream();
                    filemd5Byte = Digests.md5(sourceInputStreamFile);

                    String result = StringUtils.EMPTY;
                    // 转换成16进制代码
                    for (int i = 0; i < filemd5Byte.length; i++) {
                        result += Integer.toHexString(
                                (0x000000ff & filemd5Byte[i]) | 0xffffff00)
                                .substring(6);
                    }
                    //md5错误
                    if (!result.equals(fileMD5)) {
                        return "1";
                    }

                    String fileName = file.getOriginalFilename();
                    //Long data =  new Date().getTime();
                    String uuid = IdGen.uuid();

                    String suffix = fileName.substring(fileName.lastIndexOf("."));

                    String name = uuid + suffix;

                    // FileUtils.createDirectory(Global.getCkBaseDir() + path);
                    File localFile = new File(Global.getCkBaseDir() + name);
                    file.transferTo(localFile);
                    //HttpServletRequestUtils.getPathSub(multiRequest)
                    if (StringUtils.isBlank(fileNames)) {
                        fileNames = "|" + fileNames + path + name + "|";
                    } else {
                        fileNames = fileNames + path + name + "|";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileNames;
    }


    public static Map fileUploadMap(
            MultipartHttpServletRequest multiRequest, String path) {
        Map map = new HashMap();
        String skill = StringUtils.EMPTY;
        String head = StringUtils.EMPTY;
        Iterator<String> ite = multiRequest.getFileNames();
        while (ite.hasNext()) {
            MultipartFile file = multiRequest.getFile(ite.next());


            String md5 = StringUtils.EMPTY;
            String skillname = StringUtils.EMPTY;
            if (file != null) {
                String fileMD5 = file.getName();

                String type = fileMD5.substring(fileMD5.length() - 1, fileMD5.length());


                //如果type 等于0 就是头像的图片，如果type 等于1 就是证书的图片
                if (type.equals("0")) {
                    md5 = fileMD5.substring(0, fileMD5.length() - 1);
                } else {

                    String md51 = fileMD5.substring(0, fileMD5.length() - 1);
                    String[] md5split = md51.split("-");
                    md5 = md5split[0];
                    skillname = md5split[1];
                }


                byte[] filemd5Byte = null;
                try {
                    // 文件流
                    InputStream sourceInputStreamFile = file.getInputStream();
                    filemd5Byte = Digests.md5(sourceInputStreamFile);

                    String result = StringUtils.EMPTY;
                    // 转换成16进制代码
                    for (int i = 0; i < filemd5Byte.length; i++) {
                        result += Integer.toHexString(
                                (0x000000ff & filemd5Byte[i]) | 0xffffff00)
                                .substring(6);
                    }
                    //md5错误
                    if (!result.equals(md5)) {
                        map.put("skill", "0");
                        return map;
                    }

                    String fileName = file.getOriginalFilename();
                    //Long data =  new Date().getTime();
                    String uuid = IdGen.uuid();

                    String suffix = fileName.substring(fileName.lastIndexOf("."));

                    String name = uuid + suffix;

                    // FileUtils.createDirectory(Global.getCkBaseDir() + path);
                    File localFile = new File(Global.getCkBaseDir() + name);
                    file.transferTo(localFile);
                    //HttpServletRequestUtils.getPathSub(multiRequest)

                    //如果是0 就是头像的图片
                    if (type.equals("0")) {

                        //Integer errnum = FaceRecognition.register(uuid,new File(Global.getCkBaseDir() + name));

                        /*if(errnum <0){
                           // FileUtils.delFile(Global.getCkBaseDir() + name);
                            FaceRecognition.Delete(uuid);
                            map.put("skill","1");
                            return map;
                        }*/

                        if (StringUtils.isBlank(head)) {
                            head = "|" + head + path + name + "|";
                        } else {
                            head = head + path + name + "|";
                        }

                        //如果是1就是就是证书的图片
                    } else if (type.equals("1")) {

                        if (StringUtils.isBlank(skill)) {
                            skill = "|" + skill + skillname + "&" + path + name + "|";
                        } else {
                            skill = skill + skillname + "&" + path + name + "|";
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        map.put("skill", skill);
        map.put("head", head);
        return map;
    }


    public static String AuthenticationfileUpload(
            MultipartHttpServletRequest multiRequest, String path) {


        String fileNames = StringUtils.EMPTY;
        Iterator<String> ite = multiRequest.getFileNames();
        while (ite.hasNext()) {
            MultipartFile file = multiRequest.getFile(ite.next());

            if (file != null) {
                String fileMD5 = file.getName();
                byte[] filemd5Byte = null;
                try {
                    // 文件流
                    InputStream sourceInputStreamFile = file.getInputStream();
                    filemd5Byte = Digests.md5(sourceInputStreamFile);

                    String result = StringUtils.EMPTY;
                    // 转换成16进制代码
                    for (int i = 0; i < filemd5Byte.length; i++) {
                        result += Integer.toHexString(
                                (0x000000ff & filemd5Byte[i]) | 0xffffff00)
                                .substring(6);
                    }
                    //md5错误
                    if (!result.equals(fileMD5)) {
                        return "1";
                    }

                    String fileName = file.getOriginalFilename();
                    //Long data =  new Date().getTime();
                    String uuid = IdGen.uuid();

                    String suffix = fileName.substring(fileName.lastIndexOf("."));

                    String name = uuid + suffix;

                    // FileUtils.createDirectory(Global.getCkBaseDir() + path);
                    File localFile = new File(Global.getCkBaseDir() + name);
                    file.transferTo(localFile);
                    //HttpServletRequestUtils.getPathSub(multiRequest)
                    if (StringUtils.isBlank(fileNames)) {

                        fileNames = "|" + path + name;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileNames;
    }


    public static Map NewAuthenticationfileUpload(
            MultipartHttpServletRequest multiRequest, String path) {

        String md5 = StringUtils.EMPTY;

        Map map = Maps.newHashMap();

        String fileNames = StringUtils.EMPTY;
        Iterator<String> ite = multiRequest.getFileNames();
        while (ite.hasNext()) {
            MultipartFile file = multiRequest.getFile(ite.next());

            if (file != null) {
                String fileMD5 = file.getName();


                String type = fileMD5.substring(fileMD5.length() - 1, fileMD5.length());


                //如果type 等于1 就是头像的图片，如果type 等于1 就是证书的图片
                if (type.equals("1")) {
                    md5 = fileMD5.substring(0, fileMD5.length() - 1);
                } else {
                    md5 = fileMD5.substring(0, fileMD5.length() - 1);
                }

                byte[] filemd5Byte = null;
                try {
                    // 文件流
                    InputStream sourceInputStreamFile = file.getInputStream();
                    filemd5Byte = Digests.md5(sourceInputStreamFile);

                    String result = StringUtils.EMPTY;
                    // 转换成16进制代码
                    for (int i = 0; i < filemd5Byte.length; i++) {
                        result += Integer.toHexString(
                                (0x000000ff & filemd5Byte[i]) | 0xffffff00)
                                .substring(6);
                    }
                    //md5错误
                   /* if (!result.equals(md5)) {

                        map.put("id","1");
                        return map;
                    }*/

                    String fileName = file.getOriginalFilename();
                    //Long data =  new Date().getTime();
                    String uuid = IdGen.uuid();

                    String suffix = fileName.substring(fileName.lastIndexOf("."));

                    String name = uuid + suffix;

                    // FileUtils.createDirectory(Global.getCkBaseDir() + path);
                    File localFile = new File(Global.getCkBaseDir() + name);
                    file.transferTo(localFile);
                    //HttpServletRequestUtils.getPathSub(multiRequest)

                    fileNames = path + name;
                    if (type.equals("2")) {
                        map.put("figure", fileNames);
                    } else {
                        map.put("id", fileNames);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }


    public static String fileUploadUnused(
            MultipartHttpServletRequest multiRequest, String path) {

        String fileNames = StringUtils.EMPTY;
        Iterator<String> ite = multiRequest.getFileNames();
        while (ite.hasNext()) {
            List<MultipartFile> file = multiRequest.getFiles(ite.next());

            for (int i = 0;i < file.size();i++){

                if (file.get(i) != null) {
                    String fileMD5 = file.get(i).getName();
                    System.out.println(fileMD5);
                    byte[] filemd5Byte = null;
                    try {
                        // 文件流
                        InputStream sourceInputStreamFile = file.get(i).getInputStream();
                        filemd5Byte = Digests.md5(sourceInputStreamFile);

                        String result = StringUtils.EMPTY;
                        // 转换成16进制代码
                        for (int j = 0; j < filemd5Byte.length; j++) {
                            result += Integer.toHexString(
                                    (0x000000ff & filemd5Byte[j]) | 0xffffff00)
                                    .substring(6);
                        }

                        String fileName = file.get(i).getOriginalFilename();
                        //Long data =  new Date().getTime();
                        String uuid = IdGen.uuid();

                        String suffix = fileName.substring(fileName.lastIndexOf("."));

                        String name = uuid + suffix;

                        System.out.println(Global.getCkBaseDir() + path + name);
                        FileUtils.createDirectory(Global.getCkBaseDir() + path);
                        File localFile = new File(Global.getCkBaseDir() + path + name);
                        file.get(i).transferTo(localFile);
                        if (StringUtils.isBlank(fileNames)) {
                            fileNames = "|" + fileNames + path + name + "|";
                        } else {
                            fileNames = fileNames + path + name + "|";
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
        return fileNames;
    }


    //视频 视频缩略图 图片方法
    public static Map fileVideoAndVideoCapture(
            MultipartHttpServletRequest multiRequest, String path, String pathVideo, String videoCapture) {

        Map map = Maps.newHashMap();
        String fileNames = StringUtils.EMPTY;
        String video = StringUtils.EMPTY;
        String videoimg = StringUtils.EMPTY;
        Iterator<String> ite = multiRequest.getFileNames();
        while (ite.hasNext()) {
            MultipartFile file = multiRequest.getFile(ite.next());

            if (file != null) {
                String fileMD5 = file.getName();
                //String filesMD5 = fileMD5.substring(0,fileMD5.indexOf("-"));
                String type = fileMD5.substring(fileMD5.length() - 1, fileMD5.length());
                //System.out.println(filesMD5);
                byte[] filemd5Byte = null;
                try {
                    // 文件流
                    InputStream sourceInputStreamFile = file.getInputStream();
                    filemd5Byte = Digests.md5(sourceInputStreamFile);

                    String result = StringUtils.EMPTY;
                    // 转换成16进制代码
                    for (int i = 0; i < filemd5Byte.length; i++) {
                        result += Integer.toHexString(
                                (0x000000ff & filemd5Byte[i]) | 0xffffff00)
                                .substring(6);
                    }
                    //md5错误
                    /*if (!result.equals(filesMD5)) {
                        return "1";
                    }*/

                    String fileName = file.getOriginalFilename();
                    //Long data =  new Date().getTime();
                    String uuid = IdGen.uuid();

                    String suffix = fileName.substring(fileName.lastIndexOf("."));

                    String name = uuid + suffix;

                    if (type.equals("4")) {
                        File localFile = new File(Global.getCkBaseDir() + "/videoCapture/" + name);
                        file.transferTo(localFile);
                        //HttpServletRequestUtils.getPathSub(multiRequest)
                        if (StringUtils.isBlank(videoimg)) {
                            map.put("videoimg", videoimg = videoimg + videoCapture + name);
                        }
                    } else if (name.contains(".mp4")) {
                        File localFile = new File(Global.getCkBaseDir() + "/video/" + name);
                        file.transferTo(localFile);
                        //HttpServletRequestUtils.getPathSub(multiRequest)
                        if (StringUtils.isBlank(video)) {
                            map.put("video", video = video + pathVideo + name);
                        }
                    } else {
                        // FileUtils.createDirectory(Global.getCkBaseDir() + path);
                        File localFile = new File(Global.getCkBaseDir() + "/billingImg/" + name);
                        file.transferTo(localFile);
                        //HttpServletRequestUtils.getPathSub(multiRequest)
                        if (StringUtils.isBlank(fileNames)) {
                            fileNames = "|" + fileNames + path + name + "|";
                        } else {
                            fileNames = fileNames + path + name + "|";
                        }
                        map.put("img", fileNames);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }


    public static String fileUploadUnuseds(
            MultipartHttpServletRequest multiRequest, String path) {


        String fileNames = StringUtils.EMPTY;
        Iterator<String> ite = multiRequest.getFileNames();
        while (ite.hasNext()) {
            MultipartFile file = multiRequest.getFile(ite.next());

            if (file != null) {
                String fileMD5 = file.getName();
                //String filesMD5 = fileMD5.substring(0,fileMD5.indexOf("-"));
                //System.out.println(filesMD5);
                byte[] filemd5Byte = null;
                try {
                    // 文件流
                    InputStream sourceInputStreamFile = file.getInputStream();
                    filemd5Byte = Digests.md5(sourceInputStreamFile);

                    String result = StringUtils.EMPTY;
                    // 转换成16进制代码
                    for (int i = 0; i < filemd5Byte.length; i++) {
                        result += Integer.toHexString(
                                (0x000000ff & filemd5Byte[i]) | 0xffffff00)
                                .substring(6);
                    }
                    //md5错误
                    if (!result.equals(fileMD5)) {
                        return "1";
                    }

                    String fileName = file.getOriginalFilename();
                    //Long data =  new Date().getTime();
                    String uuid = IdGen.uuid();

                    String suffix = fileName.substring(fileName.lastIndexOf("."));

                    String name = uuid + suffix;

                    // FileUtils.createDirectory(Global.getCkBaseDir() + path);
                    File localFile = new File(Global.getCkBaseDir() + "/billingImg/" + name);
                    file.transferTo(localFile);
                    //HttpServletRequestUtils.getPathSub(multiRequest)
                    if (StringUtils.isBlank(fileNames)) {
                        fileNames = "|" + fileNames + path + name + "|";
                    } else {
                        fileNames = fileNames + path + name + "|";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileNames;
    }


    /**
     * 判断是否有文件上传
     *
     * @param request
     * @return
     */
    public static boolean isMultipatr(HttpServletRequest request) {
        boolean flag = false;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            flag = true;
        }
        return flag;
    }


    public static Boolean Orientation(File jpegFile) throws Exception {
        Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);
        if (null == metadata) {
            return false;
        }
        Directory exif = metadata.getDirectory(ExifIFD0Directory.class);
        if (null == exif) {
            return false;
        }
        Collection<Tag> tags = exif.getTags();
        if (null == tags) {
            return false;
        }
        Iterator<Tag> iter = tags.iterator();
        if (null == iter) {
            return false;
        }
        // 逐个遍历每个Tag

        while (iter.hasNext()) {
            Tag tag = (Tag) iter.next();
            if (tag.getTagName().contains("Orientation")) {
                System.out.println("方向" + " = " + tag.getDescription());
                if (tag.getDescription().contains("90") || tag.getDescription().contains("270")) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    public static void main(String[] args) throws ParseException {

        String url = "http://www.dianping.com/search/category/70/80/g195";

//        GoodGather aa = new GoodGather();
//        aa.setDBPubBase(url, "北京");

    }

}
