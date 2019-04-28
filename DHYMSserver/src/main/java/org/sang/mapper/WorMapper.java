package org.sang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sang.bean.Employee;
import org.sang.bean.Nation;
import org.sang.bean.PoliticsStatus;
import org.sang.bean.WorkList;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by sang on 2018/1/12.
 */
@Mapper
public interface WorMapper {
    List<Nation> getAllNations();

    List<PoliticsStatus> getAllPolitics();

    int addWor(WorkList workList);

    int getMaxWorkID();

    List<WorkList> getWorklistByPage(@Param("start") Integer start, @Param("size") Integer size, @Param("keywords") String keywords, @Param("zydbh") String zydbh, @Param("zyrybh") String rybh, @Param("startBeginDate") String startBeginDate, @Param("endBeginDate") String endBeginDate);

    Long getCountByKeywords(@Param("keywords") String keywords, @Param("zydbh") String zydbh, @Param("zyrybh") String rybh, @Param("startBeginDate") String startBeginDate, @Param("endBeginDate") String endBeginDate);

    int updateWor(@Param("emp") WorkList worklist);

    int deleteWorById(@Param("ids") String[] ids);

    int addEmps(@Param("emps") List<Employee> emps);

    List<Employee> getEmployeeByPageShort(@Param("start") int start, @Param("size") Integer size);
}
