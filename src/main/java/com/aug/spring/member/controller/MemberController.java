package com.aug.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.aug.spring.member.domain.Member;
import com.aug.spring.member.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@RequestMapping(value="/member/register.kh", method = RequestMethod.GET)
	public String showRegisterForm() {
		return "member/register";
	}
	
	@RequestMapping(value="/member/register.kh", method = RequestMethod.POST)
	public String registerMember(
//			 @RequestParam("memberId") String memberId
			@ModelAttribute Member member
			// ㄴ> 
			, Model model) {
		try {
			int result = service.insertMember(member);
			if(result > 0) {
				// 성공하면 로그인 페이지
				// home.jsp가 로그인할 수 있는 페이지가 되면 됨
				return "redirect:/index.jsp";
			}else {
				// 실패하면 에러 페이지
				model.addAttribute("msg", "회원가입이 완료되지 않았습니다.");
				model.addAttribute("error", "회원가입 실패");
				model.addAttribute("url", "/member/register.kh");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/register.kh");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/modify.kh", method = RequestMethod.GET)
	public String showModifyMember(
			@ModelAttribute Member member
			, Model model
			) {
		Member mOne = service.selectOneById(member.getMemberId());
		if(mOne != null) {
			model.addAttribute("member", mOne);
			return "member/modify";
		}else {
			model.addAttribute("error", "데이터를 조회하지 못했습니다.");
			model.addAttribute("msg", "회원 정보 수정 페이지 조회 실패");
			model.addAttribute("url", "/index.jsp");
			return "common/erroePage";
		}
	}

	@RequestMapping(value="/member/modify.kh", method = RequestMethod.POST)
	public String modifyMember(
			@ModelAttribute Member member
			, Model model
			) {
		int result = service.updateMember(member);
		if(result > 0) {
			return "redirect:/index.jsp";
		}else {
			model.addAttribute("error", "회원 정보 수정을 성공하지 못했습니다.");
			model.addAttribute("msg", "회원 정보 수정 실패");
			model.addAttribute("url", "/member/myPage.kh?memberId="+member.getMemberId());
			return "common/erroePage";
		}
	}
	
	@RequestMapping(value="/member/delete.kh", method = RequestMethod.GET)
	public String outServiceMember(
			@RequestParam("memberId") String memberId
			, Model model
			) {
		try {
			int result = service.deleteMember(memberId);
			if(result > 0) {
				return "redirect:/member/logout.kh";
			}else {
				model.addAttribute("error", "회원 탈퇴가 완료되지 않았습니다.");
				model.addAttribute("msg", "회원 탈퇴 실패");
				model.addAttribute("url", "/index.jsp");
				return "common/erroePage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/register.kh");
			return "common/errorPage";
		}
	}

	@RequestMapping(value="/member/login.kh", method = RequestMethod.GET)
	public String showLoginForm() {
		return "member/login";
	}

	@RequestMapping(value="/member/login.kh", method = RequestMethod.POST)
	public String memberLoginCheck(
			@ModelAttribute Member member
			, HttpSession session
			, Model model
			) {
		// SELECT * FROM MEMBER_TBL WHERE MEMBER_ID=? AND MEMBER_PW=?
		// 이번엔 카운트로 해보기!
		try {
			int result = service.checkMemberLogin(member);
			if(result > 0) {
				session.setAttribute("memberId", member.getMemberId());
//				session.setAttribute("memberName", member.getMemberName());
				return "redirect:/index.jsp";
			}else {
				model.addAttribute("msg", "로그인이 완료되지 않았습니다.");
				model.addAttribute("error", "로그인 실패");
				model.addAttribute("url", "/member/login.kh");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/register.kh");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/logout.kh", method = RequestMethod.GET)
	public String memberLogout(HttpSession session, Model model) {
		if(session != null) {
			session.invalidate();
			return "redirect:/index.jsp";
		}else{
			model.addAttribute("error", "로그아웃을 완료하지 못했습니다.");
			model.addAttribute("msg", "로그아웃 실패");
			model.addAttribute("url", "/index.jsp");
			return "common/erroePage";
		}
	}
	
	@RequestMapping(value="/member/myPage.kh", method = RequestMethod.POST)
	public String memberMyPage(
			HttpSession session
			, Model model
			) {
		String memberId = (String)session.getAttribute("memberId");
		if(memberId != "" && memberId != null) {
			Member mOne = service.selectOneById(memberId);
			if(mOne != null) {
				model.addAttribute("member", mOne);
				return "member/myPage";
			}else {
				model.addAttribute("error", "데이터를 조회하지 못했습니다.");
				model.addAttribute("msg", "마이페이지 조회 실패");
				model.addAttribute("url", "/index.jsp");
				return "common/erroePage";
			}
		}else {
			model.addAttribute("error", "데이터를 조회하지 못했습니다.");
			model.addAttribute("msg", "마이페이지 조회 실패");
			model.addAttribute("url", "/index.jsp");
			return "common/erroePage";
		}
	}
}
