package com.ssg.echodairy.mapper;


import com.ssg.echodairy.domain.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClientMapper {

    // 로그인 조회
    Client findByLoginId(@Param("loginId") String loginId);

    Client findByUserId(@Param("userid") Long userid);

    // 아이디 중복 체크
    boolean existsByLoginId(@Param("loginId") String loginId);

    // 닉네임 중복 체크
    boolean existsByNickname(@Param("nickname") String nickname);

    // 회원가입
    void insertClient(
            @Param("loginId") String loginId,
            @Param("password") String password,
            @Param("nickname") String nickname,
            @Param("email") String email
    );

    int existsByEmail(@Param("email") String email);


}
