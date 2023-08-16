package com.aug.spring.member.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aug.spring.member.domain.Member;
import com.aug.spring.member.service.MemberService;
import com.aug.spring.member.store.MemberStore;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSession session;
	
	@Autowired
	private MemberStore mStore;
	
	@Override
	public int insertMember(Member member) {
		int result = mStore.insertMember(session, member);
		return result;
	}

	@Override
	public int checkMemberLogin(Member member) {
		int result = mStore.checkMemberLogin(session, member);
		return result;
	}

}
