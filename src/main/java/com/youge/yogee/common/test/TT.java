package com.youge.yogee.common.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by ab on 2018/4/3.
 */
public class TT {
   /* public static void main(String arg[]){

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1); //抓取当前时间前两天的数据
        date = calendar.getTime();
        String str = sdf.format(date);
        int yesterday = Integer.valueOf(str);
        System.out.println(yesterday);

        //押对的彩票
        List<String> winList = new ArrayList<String>();
        //押对的彩票(用于带胆的彩票)
        List<String> danWinList = new ArrayList<String>();
        TT tc = new TT();

        String beat ="0+周二002+广州恒大vs天津权健+1/4.20,|";
       // tc.judgeFootballFollow(beat, "beat", winList, danWinList);

        String let ="0+周二001+全北现代vs布里兰+1/4.20,0/1.73,|0+周二002+广州恒大vs天津权健+1/3.28,0/2.40,|";
        if (StringUtils.isNotEmpty(let)) {
            String[] letBalls = {"-2", "-1"};
            String[] methodArray = let.split("\\|");
            for (int i = 0; i < methodArray.length; i++) {
                String[] aMethodArray = methodArray[i].split("\\+");
                //CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(aMethodArray[1]);
                String finish = "";
                int hs = Integer.valueOf(2);
                int vs = Integer.valueOf(2);
                int letBall = Integer.valueOf(letBalls[i]);
                if (hs + letBall > vs) {
                    finish = "3";
                } else if (hs + letBall == vs) {
                    finish = "1";
                } else {
                    finish = "0";
                }

                String[] odds = methodArray[i].split(finish + "/");
                if (odds.length > 1) {
                    if (odds[1].contains(",")) {
                        winList.add(odds[1].split(",")[0]);
                        danWinList.add(aMethodArray[1] + odds[1].split(",")[0]);
                    } else {
                        winList.add(odds[1]);
                        danWinList.add(aMethodArray[1] + odds[1]);
                    }
                }
            }
        }
    }*/

    public static void main(String ars[]){
        TT tt =new TT();
        tt.drawImage("E:\\2018\\杂项目\\dayinji\\辅助票样板图片\\辅助票样板图片2.png",1);
    }


    public void drawImage(String fileName, int count) {
        try {
            DocFlavor dof = null;
            if (fileName.endsWith(".gif")) {
                dof = DocFlavor.INPUT_STREAM.GIF;
            } else if (fileName.endsWith(".jpg")) {
                dof = DocFlavor.INPUT_STREAM.JPEG;
            } else if (fileName.endsWith(".png")) {
                dof = DocFlavor.INPUT_STREAM.PNG;
            }

            PrintService ps = PrintServiceLookup.lookupDefaultPrintService();

            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            pras.add(OrientationRequested.PORTRAIT);

            pras.add(new Copies(count));
            pras.add(PrintQuality.HIGH);
            DocAttributeSet das = new HashDocAttributeSet();

            // 设置打印纸张的大小（以毫米为单位）
            das.add(new MediaPrintableArea(0, 0, 210, 296, MediaPrintableArea.MM));
            FileInputStream fin = new FileInputStream(fileName);

            Doc doc = new SimpleDoc(fin, dof, das);

            DocPrintJob job = ps.createPrintJob();

            job.print(doc, pras);
            fin.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (PrintException pe) {
            pe.printStackTrace();
        }
    }
    private void judgeFootballFollow(String method, String key, List<String> winList, List<String> danWinList) {
        String[] methodArray = method.split("\\|");
        for (String aMethod : methodArray) {
            String[] aMethodArray = aMethod.split("\\+");
            // CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(aMethodArray[1]);
            String finish = "";
            switch (key) {
                case "beat":
                    finish = "平";
                    break;
                case "let":
                    finish = "让主负";
                    break;
            }
            String[] odds = aMethod.split(finish + "/");
            if (odds.length > 1) {
                if (odds[1].contains(",")) {
                    winList.add(odds[1].split(",")[0]);
                    danWinList.add(aMethodArray[1] + odds[1].split(",")[0]);
                } else {
                    winList.add(odds[1]);
                    danWinList.add(aMethodArray[1] + odds[1]);
                }
            }
        }
    }
    public void test02(){
        String paraDate = "{\n" +
                "\t\"buyWays\": \"1\",\n" +
                "\t\"detail\": [{\n" +
                "\t\t\t\"matchId\": \"周六302\",\n" +
                "\t\t\t\"hostWin\": \"\",\n" +
                "\t\t\t\"hostFail\": \"\",\n" +
                "\t\t\t\"size\": \"\",\n" +
                "\t\t\t\"beat\": \"0/3.40,\",\n" +
                "\t\t\t\"let\": \"\",\n" +
                "\t\t\t\"isMust\": \"0\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"matchId\": \"周六303\",\n" +
                "\t\t\t\"hostWin\": \"1-5/4.30,16-20/7.70,\",\n" +
                "\t\t\t\"hostFail\": \"1-5/5.90,16-20/32.00,\",\n" +
                "\t\t\t\"size\": \"1/1.80,0/1.69,\",\n" +
                "\t\t\t\"beat\": \"0/2.92,1/1.24,\",\n" +
                "\t\t\t\"let\": \"0/1.75,1/1.75,\",\n" +
                "\t\t\t\"isMust\": \"0\"\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"uid\": \"111\",\n" +
                "\t\"followNum\": \"2,\",\n" +
                "\t\"times\": \"1\"\n" +
                "}";

        JSONObject jsonObject = JSON.parseObject(paraDate);

             System.out.println(jsonObject.getString("buyWays")+":"+jsonObject.getString("followNum"));
    }
    public void test01(){
       /* double[] dbjj = {9.22,10.86,12.38,13.32,14.58,16.20,17.88,19.08,23.40};//单倍奖金
        int tzje =90;//投注金额*/
        double[] dbjj={4.45,8.51,9.17,11.05,22.75};
        int tzje =50;
        int zhushu = 5;//注数
        int zuidajishu = tzje/2-zhushu+1;//37

        double[] d1=new double[zuidajishu];
        double[] d2=new double[zuidajishu];
        for (int i = 0; i < zuidajishu; i++) {
           // System.out.println(i);
           d1[i] = dbjj[0]*(i+1);
            d2[i] = dbjj[dbjj.length-1]*(i+1);
        }
        for (int i = 0; i < zuidajishu; i++) {
            System.out.print(d1[i]+",");
        }
        System.out.println();
        for (int i = 0; i < zuidajishu; i++) {
            System.out.print(d2[i]+",");
        }
        System.out.println();
        int ii=1;//记录第一个下标
        int jj=1;//记录第二个下标
        double cc =55.93;//(6.64+7.29+9.22)/3*15;//56.35;
        double bb=d1[0]-cc>=0?d1[0]-cc:cc-d1[0];
        for (int i = 0; i < zuidajishu; i++) {
            double dd = d1[i]-cc>=0?d1[i]-cc:cc-d1[i];
            if(dd<bb){
                bb = dd;
                System.out.println(i+1);
            }
        }
        double bb2=d2[0]-cc>=0?d2[0]-cc:cc-d2[0];
        for (int i = 0; i < zuidajishu; i++) {
            double dd = d2[i]-cc>=0?d2[i]-cc:cc-d2[i];
            if(dd<bb2){
                bb2 = dd;
                System.out.println(i+1);
            }
        }
    }
}
