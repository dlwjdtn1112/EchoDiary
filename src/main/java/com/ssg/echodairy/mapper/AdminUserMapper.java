package com.ssg.echodairy.mapper;


import com.ssg.echodairy.dto.AdminUserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminUserMapper {

    List<AdminUserDto> findAllUsers();


}
