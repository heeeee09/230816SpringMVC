package com.aug.spring.member.service;

import com.aug.spring.member.domain.Member;

public interface MemberService {

	/**
	 * 회원가입 서비스
	 * @param member
	 * @return
	 */
	int insertMember(Member member);

	/**
	 * 로그인 서비스
	 * @param member
	 * @return
	 */
	int checkMemberLogin(Member member);

}
