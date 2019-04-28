package org.sang.service;

import org.sang.bean.Employee;
import org.sang.bean.Nation;
import org.sang.bean.PoliticsStatus;
import org.sang.bean.WorkList;
import org.sang.mapper.EmpMapper;
import org.sang.mapper.WorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

/**
 * Created by sang on 2018/1/12.
 */
@Service
@Transactional
public class WorService {
    @Autowired
    EmpMapper empMapper;
    @Autowired
    WorMapper worMapper;
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");
    String time=null;

    public List<Nation> getAllNations() {
        return empMapper.getAllNations();
    }

    public List<PoliticsStatus> getAllPolitics() {
        return empMapper.getAllPolitics();
    }

    public int addWor(WorkList worklist) {
        return worMapper.addWor(worklist);
    }

    public int getMaxWorkID() {
        int maxWorkID = worMapper.getMaxWorkID();
        return maxWorkID;
    }

    public List<WorkList> getWorklistByPage(Integer page, Integer size, String keywords,String zydbh, String rybh, String beginDateScope) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int start = (page - 1) * size;
        String startBeginDate = null;
        String endBeginDate = null;
        System.out.println("*****************************************************2"+beginDateScope+"%%");
        if (beginDateScope != "" && beginDateScope.contains(",")) {
            try {
                String[] split = beginDateScope.split(",");
                split[0]=split[0].substring(0,10);
                split[1]=split[1].substring(0,10);
                startBeginDate =split[0];
                endBeginDate = split[1];
                System.out.println("*****************************************************8"+birthdayFormat.parse("2012-01-01")+"%%");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("________________________________________-");
            }
        }else if(beginDateScope==null){
            return worMapper.getWorklistByPage(start, size, keywords, zydbh, rybh,null, null);
        }else if(beginDateScope==""){
            return worMapper.getWorklistByPage(start, size, keywords, zydbh, rybh,null, null);
        }
        else {
            return worMapper.getWorklistByPage(start, size, keywords, zydbh, rybh,time, time);
        }
        System.out.println("*****************************************************3"+start+" "+size+" "+keywords+" "+zydbh+" "+rybh+" "+startBeginDate+" "+ endBeginDate);
        return worMapper.getWorklistByPage(start, size, keywords, zydbh, rybh, startBeginDate, endBeginDate);
    }

    public Long getCountByKeywords(String keywords, String zydbh, String rybh,String beginDateScope) {
        String startBeginDate = null;
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String endBeginDate = null;
        if (beginDateScope != null && beginDateScope.contains(",")) {
            try {
                String[] split = beginDateScope.split(",");
                split[0]=split[0].substring(0,10);
                split[1]=split[1].substring(0,10);
                startBeginDate =split[0];
                endBeginDate = split[1];
            } catch (Exception e) {
            }
        }else if(beginDateScope==""){
            System.out.println("*****************************************************3@");
            return worMapper.getCountByKeywords( keywords, zydbh, rybh,time, time);
        }else if (beginDateScope==null){
            System.out.println("*****************************************************3@@");
            return worMapper.getCountByKeywords( keywords, zydbh, rybh,time, time);
        }else {
            System.out.println("*****************************************************3@@@");
            return worMapper.getCountByKeywords( keywords, zydbh, rybh,time, time);
        }
        System.out.println("*****************************************************3*"+keywords+" "+zydbh+" "+rybh+" "+startBeginDate+" "+ endBeginDate);
        return worMapper.getCountByKeywords(keywords, zydbh, rybh,startBeginDate, endBeginDate);
    }

    public int updateWor(WorkList worklist) {
        System.out.println("666666666666666"+worklist.getId());
        return worMapper.updateWor(worklist);
    }

    public boolean deleteWorById(String ids) {
        String[] split = ids.split(",");
        return worMapper.deleteWorById(split) == split.length;
    }

    public List<Employee> getAllEmployees() {
        return empMapper.getEmployeeByPage(null, null, "", null, null, null, null, null, null, null, null);
    }

    public int addEmps(List<Employee> emps) {
        return empMapper.addEmps(emps);
    }

    public List<Employee> getEmployeeByPageShort(Integer page, Integer size) {
        int start = (page - 1) * size;
        return empMapper.getEmployeeByPageShort(start,size);
    }
}
