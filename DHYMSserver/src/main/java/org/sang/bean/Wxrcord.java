package org.sang.bean;

import java.util.Date;

/**
 * @Author: gmfff
 * @Date: 2019/4/25 17:55
 * @Version 1.0
 */
public class Wxrcord {
    private int id;
    private String sb_BH;
    private String wx_RY;
    private String Telephone;
    private Date wx_DATE;
    private String Information;

    public String getSb_BH() {
        return sb_BH;
    }

    public void setSb_BH(String sb_BH) {
        this.sb_BH = sb_BH;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getWx_RY() {
        return wx_RY;
    }

    public void setWx_RY(String wx_RY) {
        this.wx_RY = wx_RY;
    }



    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public Date getWx_DATE() {
        return wx_DATE;
    }

    public void setWx_DATE(Date wx_DATE) {
        this.wx_DATE = wx_DATE;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }
}
