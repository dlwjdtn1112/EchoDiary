package com.ssg.echodairy.sercurity;

import com.ssg.echodairy.domain.Client;
import com.ssg.echodairy.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ClientMapper clientMapper;

    @Override
    public UserDetails loadUserByUsername(String loginId)
            throws UsernameNotFoundException {

        Client member = clientMapper.findByLoginId(loginId);

        if (member == null) {
            throw new UsernameNotFoundException("존재하지 않는 사용자");
        }

        return new CustomUserDetails(member);
    }
}
