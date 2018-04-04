package com.youge.yogee.interfaces.lottery.index;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.FileUtil;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cforum.entity.CdForum;
import com.youge.yogee.modules.cforum.service.CdForumService;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.publicutils.AddUtils;
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
        logger.info("论坛回复addReply---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String userId = (String) jsonData.get("uid");//当前登录人id
        // String userId = "111";
        String sid = (String) jsonData.get("sid");//主人A的帖子或者评论内容的id
        String content = (String) jsonData.get("content");

        if (StringUtils.isEmpty(content)) {
            return HttpResultUtil.errorJson("请填写内容!");
        }

        Map mapData = new HashMap();
        //获取论坛帖子
        CdForum cd = cdForumService.get(sid);
        //把回复内容的id,保存到帖子表的replyId字段
        String parentsId = "";
        if (cd.getParentsId() != null && !"".equals(cd.getParentsId()) && !" ".equals(cd.getParentsId())) {
            if (cd.getParentsId().contains(",")) {
                parentsId = cd.getParentsId() + sid; //如果已经有父id 则在后边继续拼上sid
            }
        } else {
            parentsId = sid + ",";//存放该评论的父id即帖子id
        }
        //用户名字
        String parentsUser = "";
        if (cd.getParentsUser() != null && !"".equals(cd.getParentsUser()) && !" ".equals(cd.getParentsUser())) {
            if (cd.getParentsUser().contains(",")) {
                parentsUser = cd.getParentsUser() + cd.getUserName(); //如果已经有父name 则在后边继续拼上name
            }
        } else {
            parentsUser = cd.getUserName() + ",";//存放该评论的父name即帖子name
        }

        CdLotteryUser user = cdLotteryUserService.get(userId);
        String id = AddUtils.createBigSmallLetterStrOrNumberRadom(20);
        //无图片评论
        AddUtils.addForum(cdForumService, " ", id, userId, user.getName(), " ", content, "0", parentsId, parentsUser, " "); //回复内容的id

        logger.info("论坛回复addReply---------- End--------");
        return HttpResultUtil.successJson(mapData);
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
        Map mapData = new HashMap();
        List listMs = new ArrayList();
        List<CdForum> list = cdForumService.getForum(forumType); //查询所有帖子.
        for (CdForum s : list) {
            Map map = new HashMap();
            map.put("id", s.getId()); //主键
            map.put("createDate", s.getCreateDate());//发布时间
            //map.put("name", s.getName());//标题
            map.put("content", s.getContent());//内容
            map.put("userName", s.getUserName());//发布人
            CdLotteryUser clu = cdLotteryUserService.get(s.getUserId());
            map.put("uImg", clu.getImg());
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
            List listfor = cdForumService.getForumPing(s.getId());
            map.put("commentCount", listfor.size()); //帖子的评论数量
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
        String id = (String) jsonData.get("id");
        Map mapData = new HashMap();
        List listMs = new ArrayList();
        List listPing = new ArrayList();
        CdForum forumDetail = cdForumService.get(id); //查询帖子的详细内容.
        Map map = new HashMap();
        map.put("id", forumDetail.getId());
        map.put("createDate", forumDetail.getCreateDate());//发布时间
        //map.put("name", forumDetail.getName());//标题
        map.put("content", forumDetail.getContent());//内容
        map.put("userName", forumDetail.getUserName());//发布人
        if (StringUtils.isNotEmpty(forumDetail.getUserId())) {
            CdLotteryUser cdLotteryUser = cdLotteryUserService.get(forumDetail.getUserId());
            map.put("uImg", cdLotteryUser.getImg());//用户头像
        }
        map.put("dianZanCount", forumDetail.getDianzanCount());//点赞数量
        listMs.add(map);
        //评论列表
        List listfor = cdForumService.getForumPing(id);
        for (int i = 0; i < listfor.size(); i++) {
            Map mapForum = new HashMap();
            Object[] forum = (Object[]) listfor.get(i);
            if (!forum[1].toString().contains(",")) {
                mapForum.put("userName", forum[0]); //用户名
            } else {
                //拆分parentsUser
                String[] arr = forum[1].toString().split(",");
                if (forum[0].equals(arr[arr.length - 1])) {
                    mapForum.put("userName", arr[arr.length - 1]);
                } else {
                    mapForum.put("userName", forum[0] + ">" + arr[arr.length - 1]);
                }
            }
            mapForum.put("content", forum[2]); //内容
            mapForum.put("id", forum[3]); //主键
            mapForum.put("dianZanCount", forum[4]); //点赞数量
            if (StringUtils.isNotEmpty(forum[5].toString())) {
                CdLotteryUser cdLotteryUser = cdLotteryUserService.get(forum[5].toString());
                mapForum.put("uImg", Global.getConfig("domain.name") + cdLotteryUser.getImg());//用户头像
            }
            listPing.add(mapForum);
        }
        mapData.put("pingCount", listfor.size()); //评论数
        mapData.put("listMs", listMs); //帖子的所有信息
        mapData.put("listPing", listPing); //所有评论和自评论的信息
        logger.info("查看单个帖子的详细内容以及回复selectForumDetail---------- End--------");
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
    @RequestMapping(value = "dianZan", method = RequestMethod.POST)
    @ResponseBody
    public String dianZan(HttpServletRequest request) {
        logger.info("点赞方法dianZan---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String id = (String) jsonData.get("id"); //获得帖子id
        CdForum forum = cdForumService.get(id);
        forum.setDianzanCount(String.valueOf(Integer.valueOf(forum.getDianzanCount()) + 1)); //点赞数量加一
        cdForumService.save(forum);
        logger.info("点赞方法dianZan---------- End--------");
        Map mapData = new HashMap();
        return HttpResultUtil.successJson(mapData);
    }

}
