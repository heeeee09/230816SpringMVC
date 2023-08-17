package com.aug.spring.notice.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aug.spring.notice.domain.Notice;
import com.aug.spring.notice.service.NoticeService;

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService service;

	@RequestMapping(value="/notice/insert.kh", method = RequestMethod.GET)
	public String showInsertForm() {
		return "notice/insert";
	}

	@RequestMapping(value="/notice/insert.kh", method = RequestMethod.POST)
	public String insertNotice(
			@ModelAttribute Notice notice
			, @RequestParam(value="uploadFile", required = false) MultipartFile uploadFile
			, HttpServletRequest request
			, Model model) {
		try {
			if(!uploadFile.getOriginalFilename().equals("")) {
				// ====================== 파일 이름 ==========================
				String fileName = uploadFile.getOriginalFilename();
				// 파일 경로 -> 프로젝트의 경로를 가져와야 함(리퀘스트에 있는 서블릿 경로를 가져와야)
				// (내가 저장한 후 그 경로를 DB에 저장하도록 하는 것)
				// root라는 변수에 resources의 경로를 저장함
				String root = request.getSession().getServletContext().getRealPath("resources");
				/* getServletContext -> 서블릿에 관련된 모든 정보를 가지고 있음
				 * getRealPath("resources") -> resources에 대한 경로를 가지고 옴
				 */
				String saveFolder = root + "\\nuploadFiles";
				File folder = new File(saveFolder);
				// 파일 객체 만들기
				//folder가 없으면 만들어줘(내가 업로드한 파일을 저장할 폴더)
				if(!folder.exists()) {
					folder.mkdir();
				}
				
				// ====================== 파일 경로 ==========================
				String savePath = saveFolder + "\\" + fileName;
				File file = new File(savePath);
				// ********************** 파일 저장 **************************
				// 진짜로 파일을 저장하는 메소드
				uploadFile.transferTo(file);
				
				// ====================== 파일 크기 ==========================
				long fileLength = uploadFile.getSize();
				
				// DB에 저장하기 위해 notice에 데이터를 set하는 부분
				notice.setNoticeFileName(fileName);
				notice.setNoticeFilepath(savePath);
				notice.setNoticeFileLength(fileLength);
			}
			int result = service.insertNotice(notice);
			if(result > 0) {
				return "redirect:/notice/list.kh";
			}else {
				model.addAttribute("error", "공지사항 등록이 완료되지 않았습니다.");
				model.addAttribute("msg", "공지사항 등록 실패");
				model.addAttribute("url", "/index.jsp");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("msg", "관리자에게 문의해주세요 실패");
			model.addAttribute("url", "/index.jsp");
			return "common/erroePage";
		}
	}
	
	@RequestMapping(value="/notice/list.kh", method = RequestMethod.GET)
	public String showNoticeList(Model model) {
		try {
			List<Notice> nList = service.selectNoticeList();
			System.out.println(nList.size());
			/*
			 * List 데이터의 유효성 체크 방법 2가지
			 * 1. isEmpty()
			 * 2. size()
			 */
			if(nList.size() > 0) {
				model.addAttribute("nList", nList);
				return "notice/list";
			}else {
				model.addAttribute("error", "데이터 조회가 완료되지 않았습니다.");
				model.addAttribute("msg", "공지사항 목록 조회 실패");
				model.addAttribute("url", "/member/register.kh");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("error", "관리자에게 문의해주세요");
			model.addAttribute("url", "/index.jsp");
			return "common/errorPage";
		}
	}

}
