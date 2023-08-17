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
	public int updateMember(SqlSession session, Member member) {
		int result = session.update("MemberMapper.updateMember", member);
		return result;
	}

	@Override
	public int deleteMember(SqlSession session, String memberId) {
		int result = session.update("MemberMapper.deleteMember", memberId);
		return result;
	}

	@Override
	public int checkMemberLogin(SqlSession session, Member member) {
		int result = session.selectOne("MemberMapper.checkMemberLogin", member);
		return result;
	}

	@Override
	public Member selectOneById(SqlSession session, String memberId) {
		Member result = session.selectOne("MemberMapper.selectOneById", memberId);
		return result;
	}

}
