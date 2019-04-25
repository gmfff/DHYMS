package org.sang.mapper;

import org.apache.ibatis.annotations.Param;
import org.sang.bean.*;


import java.util.List;

public interface EquipmentMapper {
    List<Equipment> getEquipmentByPage(@Param("start") Integer start, @Param("size") Integer size,@Param("keywords") String keywords);
    Long getCountByKeywords(@Param("keywords") String keywords);
    List<Position> getAllTypes();
    int updateEquipment(@Param("equipment") Equipment equipment);
    int updateDy(@Param("yt") Yt yt);
    int addDy(@Param("yts") Yt yt);
    int addEquipment(@Param("equipments") Equipment equipment);
    int deleteEquipmentById(@Param("ids") String[] ids);
    int deleteDyById(@Param("ids") String[] ids);
    List<Yt> getYtByPage(@Param("start") Integer start, @Param("size") Integer size,@Param("keywords") String keywords);
    Long getCount1ByKeywords(@Param("keywords") String keywords);
    boolean updateDynums(@Param("yt") Yt yt);
    Long[] getTypecount(@Param("Typecount") Position position);
    List<Wxrcord> getrecorddata();
    List<Czrecord> getrecorddataSSS();


}
