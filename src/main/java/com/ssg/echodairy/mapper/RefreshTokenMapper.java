package com.ssg.echodairy.mapper;


import com.ssg.echodairy.domain.RefreshToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface RefreshTokenMapper {

    void save(
            @Param("userId") Long userId,
            @Param("refreshToken") String refreshToken,
            @Param("expiresAt") LocalDateTime expiresAt
    );

    RefreshToken findByToken(@Param("refreshToken") String refreshToken);

    void deleteByToken(@Param("refreshToken") String refreshToken);

    void deleteByUserId(@Param("userId") Long userId);

}
