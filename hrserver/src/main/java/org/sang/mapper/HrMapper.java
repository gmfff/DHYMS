package org.sang.mapper;

import org.apache.ibatis.annotations.Param;
import org.sang.bean.Hr;
import org.sang.bean.Role;

import java.util.List;

/**
 * Created by sang on 2017/12/28.
 */
public interface HrMapper {
    Hr loadUserByUsername(String username);

    List<Role> getRolesByHrId(Long id);

    int hrReg(@Param("username") String username, @Param("password") String password, @Param("phone") String phone,@Param("name") String name,@Param("address") String address,@Param("telephone") String telephone,@Param("userface") String userface);

    List<Hr> getHrsByKeywords(@Param("keywords") String keywords);

    int updateHr(Hr hr);

    int deleteRoleByHrId(Long hrId);

    int addRolesForHr(@Param("hrId") Long hrId, @Param("rids") Long[] rids);

    Hr getHrById(Long hrId);

    int deleteHr(Long hrId);

    List<Hr> getAllHr(@Param("currentId") Long currentId);
}
