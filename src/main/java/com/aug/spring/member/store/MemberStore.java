package com.aug.spring.member.store;

import org.apache.ibatis.session.SqlSession;

import com.aug.spring.member.domain.Member;

public interface MemberStore {

	int insertMember(SqlSession session, Member member);

	int checkMemberLogin(SqlSession session, Member member);

}
