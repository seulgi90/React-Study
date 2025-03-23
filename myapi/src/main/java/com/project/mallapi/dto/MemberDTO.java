package com.project.mallapi.dto;


import com.project.mallapi.domain.Member;
import com.project.mallapi.util.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.*;
import java.util.stream.Collectors;

public class MemberDTO extends User {

    private String email;
    private String name;
    private List<String> roleNames;

    public MemberDTO(String email, String password, String name, List<String> roleNames) {
        super(email, password
                , roleNames.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())); // 시큐리티 권한 설정, User 클래스 제공_ id(username), 비밀번호, 권한
        this.email = email;
        this.name = name;
        this.roleNames = roleNames;
    }

    // JWT에서 클레임(Claims)을 추출할 때 사용
    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("email",email);
        dataMap.put("name", name);
        dataMap.put("roleNames", roleNames);
        
        return dataMap;
    }

}

