package com.youge.yogee.modules.cdoptionpass.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ab on 2018/4/2.
 */
public class Caipiao {
    private List<Integer> fangshi =new ArrayList<Integer>();
    private int[] beishu = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private List<List<Integer>> saishiList = new ArrayList<List<Integer>>();

    public List<Integer> getFangshi() {
        return fangshi;
    }

    public void setFangshi(List<Integer> fangshi) {
        this.fangshi = fangshi;
    }

    public List<List<Integer>> getSaishiList() {
        return saishiList;
    }

    public void setSaishiList(List<List<Integer>> saishiList) {
        this.saishiList = saishiList;
    }

    public int[] getBeishu() {
        return beishu;
    }

    public void setBeishu(int[] beishu) {
        this.beishu = beishu;
    }

}
