package org.sang.bean;

import java.util.Date;

/**
 * @Author: gmfff
 * @Date: 2019/4/23 14:34
 * @Version 1.0
 */

public class Yt {
    private int id;
    private String Repository;
    private int Allnums;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRepository() {
        return Repository;
    }

    public void setRepository(String repository) {
        Repository = repository;
    }

    public int getAllnums() {
        return Allnums;
    }

    public void setAllnums(int allnums) {
        Allnums = allnums;
    }

    public int getRemainnums() {
        return Remainnums;
    }

    public void setRemainnums(int remainnums) {
        Remainnums = remainnums;
    }



    public Date getInDate() {
        return InDate;
    }

    public void setInDate(Date inDate) {
        InDate = inDate;
    }

    public Date getOutDate() {
        return OutDate;
    }

    public void setOutDate(Date outDate) {
        OutDate = outDate;
    }

    private int Remainnums;

    public String getZyd_BH() {
        return zyd_BH;
    }

    public void setZyd_BH(String zyd_BH) {
        this.zyd_BH = zyd_BH;
    }

    public String getDw_BH() {
        return dw_BH;
    }

    public void setDw_BH(String dw_BH) {
        this.dw_BH = dw_BH;
    }

    private String zyd_BH;
    private String dw_BH;
    private Date InDate;
    private Date OutDate;

}
