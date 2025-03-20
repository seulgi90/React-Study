package com.project.mallapi.dto;


import com.project.mallapi.domain.Member;
import com.project.mallapi.util.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.*;
import java.util.stream.Collectors;

public class MemberDTO extends User {

    private String email;
    private String pw;
    private String name;
    private List<String> roleNames;

    public MemberDTO(Member member) {
        super(member.getEmail(), member.getPw()
                , member.getRoleList().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())); // 시큐리티 권한 설정, User 클래스 제공_ id(username), 비밀번호, 권한
        this.email = member.getEmail();
//        this.pw = member.getPw();
        this.name = member.getName();
        this.roleNames = member.getRoleList().stream()
                .map(Role::name)
                .collect(Collectors.toList());
    }

    // JWT에서 클레임(Claims)을 추출할 때 사용
    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("email",email);
//        dataMap.put("pw", pw); // todo 추후 삭제 고려
        dataMap.put("name", name);
        dataMap.put("roleNames", roleNames);
        
        return dataMap;
    }

}

