package com.project.myapi.service;

import com.project.myapi.domain.Member;
import com.project.myapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> findAll() {

        return memberRepository.findAll();
    }
}
