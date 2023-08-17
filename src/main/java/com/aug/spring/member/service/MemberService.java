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
	 * 회원 정보 수정
	 * @param member
	 * @return
	 */
	int updateMember(Member member);

	/**
	 * 회원 탈퇴 service, update로 할 예정
	 * @param memberId
	 * @return
	 */
	int deleteMember(String memberId);

	/**
	 * 로그인 서비스
	 * @param member
	 * @return
	 */
	int checkMemberLogin(Member member);

	/**
	 * 아이디로 검색
	 * @param memberId
	 * @return
	 */
	Member selectOneById(String memberId);

}
