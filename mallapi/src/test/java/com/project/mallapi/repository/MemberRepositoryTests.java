package com.project.mallapi.repository;

import com.project.mallapi.domain.Member;
import com.project.mallapi.domain.Role;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsertMember() {
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .pw(passwordEncoder.encode("1111"))
                    .name("USER" + i)
                    .build();
            member.addRole(Role.USER);
            if (i >= 5)
                member.addRole(Role.MANAGER);
            if (i >= 8)
                member.addRole(Role.ADMIN);
            memberRepository.save(member);
        }
    }

    @Test
    public void testRead() {
        String email = "user1@aaa.com";

        Member member = memberRepository.getWithRoles(email);

    }
}
