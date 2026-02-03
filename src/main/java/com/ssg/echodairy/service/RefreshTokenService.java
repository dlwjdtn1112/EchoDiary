package com.ssg.echodairy.service;


import com.ssg.echodairy.mapper.RefreshTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenMapper refreshTokenMapper;

    private static final long REFRESH_DAYS = 14;

    public String createAndSave(Long userId) {
        String token = UUID.randomUUID().toString();

        refreshTokenMapper.save(
                userId,
                token,
                LocalDateTime.now().plusDays(REFRESH_DAYS)
        );

        return token;
    }

    public boolean isValid(String token) {
        var rt = refreshTokenMapper.findByToken(token);
        return rt != null && rt.getExpiresAt().isAfter(LocalDateTime.now());
    }

    public Long getUserId(String token) {
        return refreshTokenMapper.findByToken(token).getUserId();
    }

    public void delete(String token) {
        refreshTokenMapper.deleteByToken(token);
    }

    public void deleteByUserId(Long userId) {
        refreshTokenMapper.deleteByUserId(userId);
    }



}
