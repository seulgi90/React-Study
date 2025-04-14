package com.project.myapi.service;

import com.project.myapi.domain.Member;
import com.project.myapi.dto.MemberDto;
import com.project.myapi.repository.MemberRepository;
import com.project.myapi.util.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("-------------- CustomUserDetailsService >  loadUserByUsername");

        Member member = memberRepository.getWithRoles(username);

        if (member == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        List<String> roleNames = member.getRoleList()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return new MemberDto(member.getEmail(), member.getPassword(), member.getName(), roleNames);
    }

}
