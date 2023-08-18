package com.aug.spring.notice.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.aug.spring.notice.domain.PageInfo;
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
	
	// 															null 체크를 위한 integer
	@RequestMapping(value="/notice/list.kh", method = RequestMethod.GET)
	public String showNoticeList(
			@RequestParam(value="page", required = false, defaultValue = "1") Integer currentPage
			, Model model) {
		try {
//			int currentPage = page !=0 ? page : 1;
//			// page값이 0이 아니면 1로 넣어주기 => requestParam의 메소드로
			int totalCount = service.getListCount();
			PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
			List<Notice> nList = service.selectNoticeList(pInfo);
			System.out.println(nList.size());
			/*
			 * List 데이터의 유효성 체크 방법 2가지
			 * 1. isEmpty()
			 * 2. size()
			 */
			if(nList.size() > 0) {
				model.addAttribute("pInfo", pInfo);
				model.addAttribute("nList", nList);
				return "notice/list";
			}else {
				model.addAttribute("error", "데이터 조회가 완료되지 않았습니다.");
				model.addAttribute("msg", "공지사항 목록 조회 실패");
				model.addAttribute("url", "/index.jsp");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("error", "관리자에게 문의해주세요");
			model.addAttribute("url", "/index.jsp");
			return "common/errorPage";
		}
	}
	
	public PageInfo getPageInfo(int currentPage, int totalCount) {
		PageInfo pi = null;
		int recordCountPerPage = 10;	// 한 페이지에 보여줄 게시물(전체 게시물의 값이 필요)
		int naviCountPerPage = 10;	// 한 페이지에 보여줄 페이지의 개수(currentPage의 값이 필요)
		int naviTotalCount;		// 범위의 총 갯수(currentPage의 값이 필요)
		int startNavi;
		int endNavi;
		
		// (int) 강제 형변환, (double) 자동 형변환
		naviTotalCount = (int)((double)totalCount/recordCountPerPage+0.9);
													// +0.9하면 알아서 반올림
		// currentPage값이 1~5일 때 startNavi가 1로 고정되도록 구해주는 식
		startNavi = ((int)((double)currentPage/naviCountPerPage+0.9)-1)*naviCountPerPage + 1;
		endNavi = startNavi + naviCountPerPage -1;
		// endNavi는 startNavi에 무조건 naviCountPerPage값을 더하므로 실제 최대값보다 커질 수 있음
		if(endNavi > naviTotalCount) {
			endNavi = naviTotalCount;
		}
		pi = new PageInfo(currentPage, naviCountPerPage, naviCountPerPage, startNavi, endNavi, totalCount, naviTotalCount); 
		return pi;
	}
	
	@RequestMapping(value="/notice/search.kh", method = RequestMethod.GET)
	public String searchNoticeList(
			@RequestParam("searchCondition") String searchCondition
			, @RequestParam("searchKeyword") String searchKeyword
			, @RequestParam(value="page", required = false, defaultValue = "1") Integer currentPage
			, Model model
			) {
		/*
		 * 두 개의 값을 하나의 변수로 다루는 방법
		 * 1. VO클래스 만드는 방법(해봄)
		 * 2. HashMap 사용(안 해봄. 이걸로 할 것)
		 * 
		 * put() 메소드를 사용해서 key-value 설정을 하는데 
		 * key값(파란색)이 mapper.xml에서 사용됨!
		 */
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("searchCondition", searchCondition);
		paramMap.put("searchKeyword", searchKeyword);
		int totalCount = service.getListCount(paramMap); // 검색 내용에 따른 전체 갯수를 가져와야 함
		PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
		List<Notice> searchList = service.searchNoticesByKeyword(pInfo, paramMap);
//		List<Notice> searchList = new ArrayList<Notice>();
//		switch (searchCondition) {
//		case "all":
//			// SELECT * FROM NOTICE_TBL WHERE NOTICE_SUBJECT = ? OR WHERE NOTICE_CONTENT = ? OR NOTICE_WRITER = ?
//			searchList = service.searchNoticeByAll(searchKeyword);
//			break;
//		case "writer":
//			// SELECT * FROM NOTICE_TBL WHERE NOTICE_WRITER = ?
//			searchList = service.searchNoticeByWriter(searchKeyword);
//			break;
//		case "title":
//			/*
//			 *  = -> 똑같은 것만 검색됨
//			 *  LIKE '%   %' 와일드 카드와 함께 사용해야 함
//			 *  SELECT * FROM NOTICE_TBL WHERE NOTICE_SUBJECT LIKE '%?'
//			 */
//			searchList = service.searchNoticeByTitle(searchKeyword);
//			break;
//		case "content":
//			// SELECT * FROM NOTICE_TBL WHERE NOTICE_CONTENT = ?
//			searchList = service.searchNoticeByContent(searchKeyword);
//			break;
//		}
		if(!searchList.isEmpty()) {
			model.addAttribute("paramMap", paramMap);
			model.addAttribute("pInfo", pInfo);
			model.addAttribute("sList", searchList);
			return "notice/search";
		}else {
			model.addAttribute("error", "데이터 조회가 완료되지 않았습니다.");
			model.addAttribute("msg", "공지사항 검색 실패");
			model.addAttribute("url", "/index.jsp");
			return "common/errorPage";
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
