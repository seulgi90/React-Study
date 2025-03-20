package com.project.mallapi.domain;

import com.project.mallapi.util.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "roleList") // toString()에서 제외
public class Member {

    @Id
    private String email;

    private String pw;

    private  String name;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private List<Role> roleList = new ArrayList<>(); // 여러개의 권한을 가질 경우 고려하여 추가

    public void addRole(Role role) {
        roleList.add(role);
    }

    public void removeRole() {
        roleList.clear();
    }

    public void changePw(String pw) {
        this.pw = pw;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
