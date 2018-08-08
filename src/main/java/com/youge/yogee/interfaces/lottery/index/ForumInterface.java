package com.youge.yogee.interfaces.lottery.index;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.FileUtil;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cforum.entity.CdForum;
import com.youge.yogee.modules.cforum.entity.CdForumReplay;
import com.youge.yogee.modules.cforum.entity.CdForumSupport;
import com.youge.yogee.modules.cforum.service.CdForumReplayService;
import com.youge.yogee.modules.cforum.service.CdForumService;
import com.youge.yogee.modules.cforum.service.CdForumSupportService;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.publicutils.ImgUploudUtlis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wjc on 2017-12-14 0014.
 */
@Controller
@RequestMapping("${frontPath}")
public class ForumInterface {
    private static final Logger logger = LoggerFactory.getLogger(ForumInterface.class);

    @Autowired
    private CdForumService cdForumService;
    @Autowired
    private CdLotteryUserService cdLotteryUserService;
    @Autowired
    private CdForumSupportService cdForumSupportService;
    @Autowired
    private CdForumReplayService cdForumReplayService;

    /***
     * weijinchao，2017-12-28，添加论坛信息
     */
    @RequestMapping(value = "addForum", method = RequestMethod.POST)
    @ResponseBody
    public String addForum(HttpServletRequest request, MultipartFile file) {
        logger.info("添加论坛信息addForum---------- Start--------");

        String forumType = request.getParameter("forumType");
        String userId = request.getParameter("uid");
        String content = request.getParameter("content");
        logger.info(userId);
        String name = " ";//没用上
        if (StringUtils.isEmpty(content)) {
            return HttpResultUtil.errorJson("请填写内容!");
        }
        Map mapData = new HashMap();
        CdLotteryUser clu = cdLotteryUserService.get(userId);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在!");
        }
        String fileNames = "";
        boolean flag = FileUtil.isMultipatr(request);
        if (flag) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            fileNames = FileUtil.fileUploadUnused(multiRequest, "/userfiles/1/images/bbs/");
        } else {
            return HttpResultUtil.errorJson("图片上传异常");
        }
        //String imgList = Global.getConfig("domain.name") + fileNames.replace("|", "");
        CdForum cdForum = new CdForum();
        cdForum.setName(name); //标题
        cdForum.setContent(content); //内容
        cdForum.setUserId(userId);
        cdForum.setUserName(clu.getName());
        cdForum.setImgList(fileNames); //图片
        cdForum.setIsPosts("1"); //是否是帖子(1帖子  0回复内容)
        cdForum.setDianzanCount("0");
        cdForum.setIsUse("1");
        cdForum.setParentsId("");
        cdForum.setParentsUser("");
        cdForum.setForumType(forumType);
        cdForumService.save(cdForum);
        return HttpResultUtil.successJson(mapData);
//        /******************图片上传start**********************/
//        String fileUrl = " ";
//        if(file!=null){
//            Map  map = ImgUploudUtlis.getUploud(request, file);
//            fileUrl = map.get("fileListNames").toString();
//        }
//        /******************图片上传end**********************/
//        String id = AddUtils.createBigSmallLetterStrOrNumberRadom(20);
//        AddUtils.addForum(cdForumService, name, id, userId, user.getName(), fileUrl, content, "1", "", "", forumType);
//        logger.info("添加论坛信息addForum---------- End--------");


        //Map<String, Object> mapData = new HashMap<>();

    }


    /***
     * weijinchao，2017-12-28，论坛回复功能
     */
    @RequestMapping(value = "addReply", method = RequestMethod.POST)
    @ResponseBody
    public String addReply(HttpServletRequest request) {
        try {
            logger.info("论坛回复addReply---------- Start--------");
            Map jsonData = HttpServletRequestUtils.readJsonData(request);
            String uid = (String) jsonData.get("uid");//当前登录人id
            if (StringUtils.isEmpty(uid)) {
                return HttpResultUtil.errorJson("uid为空!");
            }
            String fid = (String) jsonData.get("fid");//主人A的帖子或者评论内容的id
            if (StringUtils.isEmpty(fid)) {
                return HttpResultUtil.errorJson("fid为空!");
            }
            String content = (String) jsonData.get("content");
            if (StringUtils.isEmpty(content)) {
                return HttpResultUtil.errorJson("请填写内容!");
            }

            Map mapData = new HashMap();
            //获取论坛帖子
            CdForum cd = cdForumService.get(fid);
            if (cd == null) {
                return HttpResultUtil.errorJson("帖子不存在!");
            }
            CdLotteryUser clu = cdLotteryUserService.get(uid);
            if (clu == null) {
                return HttpResultUtil.errorJson("用户不存在!");
            }
            CdForumReplay cfr = new CdForumReplay();
            cfr.setText(content);//回复内容
            cfr.setUid(uid);//用户id
            cfr.setFid(fid);//帖子id
            cfr.setUserName(clu.getName());//用户名
            cfr.setUserImg(clu.getImg());//头像
            cfr.setSupportCount("0");//点赞数
            cdForumReplayService.save(cfr);
            logger.info("论坛回复addReply---------- End--------");
            return HttpResultUtil.successJson(mapData);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResultUtil.errorJson("");

        }
//        //把回复内容的id,保存到帖子表的replyId字段
//        String parentsId = "";
//        if (cd.getParentsId() != null && !"".equals(cd.getParentsId()) && !" ".equals(cd.getParentsId())) {
//            if (cd.getParentsId().contains(",")) {
//                parentsId = cd.getParentsId() + sid; //如果已经有父id 则在后边继续拼上sid
//            }
//        } else {
//            parentsId = sid + ",";//存放该评论的父id即帖子id
//        }
//        //用户名字
//        String parentsUser = "";
//        if (cd.getParentsUser() != null && !"".equals(cd.getParentsUser()) && !" ".equals(cd.getParentsUser())) {
//            if (cd.getParentsUser().contains(",")) {
//                parentsUser = cd.getParentsUser() + cd.getUserName(); //如果已经有父name 则在后边继续拼上name
//            }
//        } else {
//            parentsUser = cd.getUserName() + ",";//存放该评论的父name即帖子name
//        }
//
//        CdLotteryUser user = cdLotteryUserService.get(userId);
//        String id = AddUtils.createBigSmallLetterStrOrNumberRadom(20);
//        //无图片评论
//        AddUtils.addForum(cdForumService, " ", id, userId, user.getName(), " ", content, "0", parentsId, parentsUser, ""); //回复内容的id


    }

    /**
     * 查询所有的帖子
     */
    @RequestMapping(value = "selectForumList", method = RequestMethod.POST)
    @ResponseBody
    public String selectForumList(HttpServletRequest request) {
        logger.info("查询所有的帖子selectForumList---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String forumType = (String) jsonData.get("forumType");

        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            uid = "";
        }
        String total = (String) jsonData.get("total");
        if (StringUtils.isEmpty(total)) {
            logger.error("total为空");
            return HttpResultUtil.errorJson("total为空");
        }
        String count = (String) jsonData.get("count");
        if (StringUtils.isEmpty(count)) {
            logger.error("count为空");
            return HttpResultUtil.errorJson("count为空");
        }

        Map mapData = new HashMap();
        List listMs = new ArrayList();
        List<CdForum> list = cdForumService.getForum(forumType, total, count); //查询所有帖子.
        for (CdForum s : list) {
            Map map = new HashMap();
            if (StringUtils.isNotEmpty(uid)) {
                CdForumSupport cfs = cdForumSupportService.findByFidAndUid(s.getId(), uid);
                if (cfs == null) {
                    map.put("supFlag", "0"); //没点赞
                } else {
                    map.put("supFlag", "1"); //点赞
                }
            } else {
                map.put("supFlag", "0"); //没点赞
            }

            map.put("id", s.getId()); //主键
            map.put("createDate", s.getCreateDate());//发布时间
            //map.put("name", s.getName());//标题
            map.put("content", s.getContent());//内容
            map.put("userName", s.getUserName());//发布人
            String sUid = s.getUserId();
            CdLotteryUser clu = cdLotteryUserService.get(sUid);
            String img = "";
            if (clu != null) {
                img = clu.getImg();
            }
            map.put("uImg", img);
            map.put("dianZanCount", s.getDianzanCount());//点赞数量
            //List<String> imgList = new ArrayList<>();
            String imgs = s.getImgList();
            String[] imgsArray = imgs.split("\\|");
            String bannerImg = "";
            for (int i = 1; i < imgsArray.length; i++) {
                //imgList.add(Global.getConfig("domain.name") + imgsArray[i]);
                if (i == imgsArray.length - 1) {
                    bannerImg += Global.getConfig("domain.name") + imgsArray[i];
                    break;
                }
                bannerImg += Global.getConfig("domain.name") + imgsArray[i] + "|";

            }
            map.put("bannerImg", bannerImg);
         /*   List bannerList = new ArrayList();
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(s.getImgList())){
                String str = s.getImgList().substring(1);
                String[] imgList = str.split("\\|");
                for (int i = 0; i < imgList.length; i++) {
                    Map maps = new HashMap();
                    maps.put("bannerImg", Global.getConfig("domain.name") + imgList[i]);
                    bannerList.add(maps);
                }
            }*/
            List rList = cdForumReplayService.findByFid(s.getId());
            String commentCount = String.valueOf(rList.size());
            map.put("commentCount", commentCount); //帖子的评论数量
            listMs.add(map);
        }
        mapData.put("listMs", listMs);
        logger.info("查询所有的帖子selectForumList---------- End--------");
        return HttpResultUtil.successJson(mapData);
    }

    /**
     * 查看单个帖子的详细内容以及回复
     */
    @RequestMapping(value = "selectForumDetail", method = RequestMethod.POST)
    @ResponseBody
    public String selectForumDetail(HttpServletRequest request) {
        logger.info("查看单个帖子的详细内容以及回复selectForumDetail---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String fid = (String) jsonData.get("fid");
        if (StringUtils.isEmpty(fid)) {
            return HttpResultUtil.errorJson("fid为空!");
        }
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return HttpResultUtil.errorJson("uid为空!");
        }
        Map mapData = new HashMap();
        CdForum forumDetail = cdForumService.get(fid); //查询帖子的详细内容.
        CdForumSupport cdForumSupport = cdForumSupportService.findByFidAndUid(forumDetail.getId(), uid);
        if (cdForumSupport == null) {
            mapData.put("supFlag", "0"); //没点赞
        } else {
            mapData.put("supFlag", "1"); //已点赞
        }
        mapData.put("id", forumDetail.getId());
        mapData.put("createDate", forumDetail.getCreateDate());//发布时间
        //map.put("name", forumDetail.getName());//标题
        mapData.put("content", forumDetail.getContent());//内容
        mapData.put("userName", forumDetail.getUserName());//发布人
        if (StringUtils.isNotEmpty(forumDetail.getUserId())) {
            CdLotteryUser cdLotteryUser = cdLotteryUserService.get(forumDetail.getUserId());
            mapData.put("uImg", cdLotteryUser.getImg());//用户头像
        }
        mapData.put("supportCount", forumDetail.getDianzanCount());//点赞数量

        //评论列表
        List<CdForumReplay> rList = cdForumReplayService.findByFid(fid);
        List repList = new ArrayList();
        if (rList.size() > 0) {
            for (CdForumReplay r : rList) {
                Map<String, String> map = new HashMap<>();
                map.put("id", r.getId());//
                map.put("text", r.getText());//评论内容
                map.put("createDate", r.getCreateDate());//时间
                map.put("count", r.getSupportCount());//点赞数
                map.put("name", r.getUserName());//用户名
                map.put("img", r.getUserImg());//头像
                CdForumSupport cfs = cdForumSupportService.findByFidAndUid(r.getId(), uid);
                if (cfs == null) {
                    map.put("supFlag", "0"); //没点赞
                } else {
                    map.put("supFlag", "1"); //已点赞
                }
                repList.add(map);
            }
        }
        mapData.put("pingCount", String.valueOf(repList.size())); //评论数
        mapData.put("listPing", repList); //所有评论和自评论的信息

        return HttpResultUtil.successJson(mapData);
    }

    /**
     * 编辑帖子方法
     */
    @RequestMapping(value = "forumDetailEdit", method = RequestMethod.POST)
    @ResponseBody
    public String forumDetailEdit(HttpServletRequest request, MultipartFile file) {
        logger.info("编辑帖子方法forumDetailEdit---------- Start--------");
        String id = request.getParameter("id"); //获得帖子id
        String uid = request.getParameter("uid");//获得当前登录人id
        String content = request.getParameter("content"); //内容
        CdForum forum = cdForumService.get(id);
        if (!uid.equals(forum.getUserId())) {
            logger.error("不可编辑");
            return HttpResultUtil.errorJson("不可编辑");
        }

        Map map = ImgUploudUtlis.getUploud(request, file);
        forum.setImgList(map.get("fileListNames").toString());
        forum.setContent(content);
        cdForumService.save(forum);
        logger.info("编辑帖子方法forumDetailEdit---------- End--------");
        Map mapData = new HashMap();
        return HttpResultUtil.successJson(mapData);
    }

    /**
     * 点赞方法
     */
    @RequestMapping(value = "forumSupport", method = RequestMethod.POST)
    @ResponseBody
    public String forumSupport(HttpServletRequest request) {
        logger.info("forumSupport---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String id = (String) jsonData.get("id"); //获得帖子或评论的id
        if (StringUtils.isEmpty(id)) {
            return HttpResultUtil.errorJson("id为空!");
        }
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return HttpResultUtil.errorJson("uid为空!");
        }
        String type = (String) jsonData.get("type");
        if (StringUtils.isEmpty(type)) {
            return HttpResultUtil.errorJson("type为空!");
        }
        //添加点赞记录
        CdForumSupport cfs = new CdForumSupport();
        cfs.setFid(id);
        cfs.setUid(uid);
        cdForumSupportService.save(cfs);
        //修改点赞数量
        if ("1".equals(type)) {
            CdForum forum = cdForumService.get(id);
            if (forum != null) {
                forum.setDianzanCount(String.valueOf(Integer.valueOf(forum.getDianzanCount()) + 1)); //点赞数量加一
                cdForumService.save(forum);
            } else {
                return HttpResultUtil.errorJson("信息错误!");
            }
        } else {
            CdForumReplay cfr = cdForumReplayService.get(id);
            if (cfr != null) {
                int newCount = Integer.parseInt(cfr.getSupportCount()) + 1;
                cfr.setSupportCount(String.valueOf(newCount));
                cdForumReplayService.save(cfr);
            } else {
                return HttpResultUtil.errorJson("信息错误!");
            }
        }
        Map mapData = new HashMap();
        return HttpResultUtil.successJson(mapData);
    }

}
