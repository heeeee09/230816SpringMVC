package com.aug.spring.member.store.logic;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.aug.spring.member.domain.Member;
import com.aug.spring.member.store.MemberStore;

@Repository
public class MemberStoreLogic implements MemberStore{

	@Override
	public int insertMember(SqlSession session, Member member) {
		int result = session.insert("MemberMapper.insertMember", member);
		return result;
	}

	@Override
	public int checkMemberLogin(SqlSession session, Member member) {
		int result = session.selectOne("MemberMapper.checkMemberLogin", member);
		return result;
	}

}
