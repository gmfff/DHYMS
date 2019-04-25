package org.sang.bean;

import java.util.Date;

/**
 * @Author: gmfff
 * @Date: 2019/4/25 20:42
 * @Version 1.0
 */
public class Czrecord {
    private int id;
    private String sb_BH;
    private String operator;
    private String telephone;
    private Date opera_time;
    private String information;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSb_BH() {
        return sb_BH;
    }

    public void setSb_BH(String sb_BH) {
        this.sb_BH = sb_BH;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getOpera_time() {
        return opera_time;
    }

    public void setOpera_time(Date opera_time) {
        this.opera_time = opera_time;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
