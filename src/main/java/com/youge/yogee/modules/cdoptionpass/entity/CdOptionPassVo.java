package com.youge.yogee.modules.cdoptionpass.entity;

import com.youge.yogee.common.persistence.BaseEntity;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by ab on 2018/4/4.
 */
@Entity
@Table(name = "cd_option_pass")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdOptionPassVo extends BaseEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String number;
    private String option;
    private String pass;
    private String del_flag;

    public CdOptionPassVo(){
        super();
    }
    public CdOptionPassVo(int id){
        this();
        this.id =id;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }


}
