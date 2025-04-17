package com.project.myapi.controller;

import com.project.myapi.domain.Member;
import com.project.myapi.repository.MemberRepository;
import com.project.myapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoleController {

    private final MemberService memberService;

    @PostMapping("/roleAll")
    public String test() {
        log.info("----- RoleController ----");
        return "권한 없는 요청 url test";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/hasRole")
    public List<Member> testHasRole() {
        return memberService.findAll();
    }
}
