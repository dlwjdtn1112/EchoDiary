package com.ssg.echodairy.service;

import com.ssg.echodairy.dto.AdminUserDto;
import com.ssg.echodairy.mapper.AdminUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserMapper adminUserMapper;

    public List<AdminUserDto> getAllUsers() {
        return adminUserMapper.findAllUsers();
    }
}
