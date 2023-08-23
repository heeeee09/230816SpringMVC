package com.aug.spring.notice.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
			/*
			 * 파일 업로드 할 때는 3가지 설정 수정해야 함!!!
			 * 1. pom.xml / 2. root-context.xml (빈 등록) / 
			 * 3. MultupartFile (jsp - form에 enctype 추가, 첨부 파일 input type="file"의 name값 컨트롤러에 추가)
			 *
			 * FileName : 사용자가 입력한 값
			 * FileRename : 실제로 저장된 값
			 */
			if(uploadFile != null && !uploadFile.getOriginalFilename().equals("")) {
				Map<String, Object> nMap = this.saveFile(uploadFile, request);
				String fileName = (String)nMap.get("fileName");
				String fileRename = (String)nMap.get("fileRename");
				String savePath = (String)nMap.get("filePath");
				long fileLength = (long)nMap.get("fileLength");
				
				// DB에 저장하기 위해 notice에 데이터를 set하는 부분
				notice.setNoticeFileName(fileName);
				notice.setNoticeFileRename(fileRename);
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
			model.addAttribute("msg", "관리자에게 문의해주세요");
			model.addAttribute("url", "/index.jsp");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/notice/modify.kh", method = RequestMethod.GET)
	public String showModifyForm(
			@RequestParam("noticeNo") Integer noticeNo
			, Model model) {
		Notice noticeOne = service.selectNoticeByNo(noticeNo);
		model.addAttribute("notice", noticeOne);
		return "notice/modify";
	}
	
	@RequestMapping(value="/notice/modify.kh", method = RequestMethod.POST)
	public String updateNotice(
			@ModelAttribute Notice notice
			, @RequestParam(value="uploadFile", required = false) MultipartFile uploadFile 
			, Model model
			, HttpServletRequest request
			) {
		// 경로 가져오기 위해 HttpServletRequest 사용
		try {
			// !!! noticeFileName임 !!
			String fileName = notice.getNoticeFileRename();
			if(uploadFile != null && !uploadFile.isEmpty()) {
				/*
				 * 수정
				 * 1. 대체, 2. 삭제 후 등록(이거할거)
				 * 먼저 기본에 업로드된 파일을 체크해야 함
				 */
				if(notice.getNoticeFileName() != null) {
					// 있으면 기존 파일 삭제 파일 객체를 이용하면 삭제할 수 있다.
					// 다른 곳에서도 사용하기 때문에 메소드화
					// (삭제할 파일 경로를 구하기 위해 request, fileName을 넘겨줌)
					this.deleteFile(request, fileName);
				}
				// 없으면 새로 업로드 하려는 파일 저장

				// saveFile 메소드 불러오기
				Map<String, Object> infoMap = this.saveFile(uploadFile, request);
				// 변수로 만들어서 해보기(둘 다 가능하다는 얘기!)
				String fileRename = (String)infoMap.get("fileRename");
				String noticeFileName = (String)infoMap.get("fileName");
				long noticeFileLength = (long)infoMap.get("fileLength");
				// 변수 없이 해보기
				notice.setNoticeFileName(noticeFileName);
				notice.setNoticeFileRename(fileRename);
				notice.setNoticeFilepath((String)infoMap.get("filePath"));
				notice.setNoticeFileLength(noticeFileLength);
			}
			int result = service.updateNotice(notice);
			if(result > 0) {
				return "redirect:/notice/detail.kh?noticeNo="+notice.getNoticeNo();
			}else {
				model.addAttribute("error", "정보 수정이 완료되지 않았습니다.");
				model.addAttribute("msg", "공지사항 수정 실패");
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
	
	@RequestMapping(value="/notice/detail.kh", method = RequestMethod.GET)
	public String showNoticeDetail(
			@RequestParam("noticeNo") Integer noticeNo
			, Model model
			) {
		/*
		 * Integet : null체크를 위해
		 * int : 비어있으면 0
		 * 값을 꺼내오는 데에는 아무 문제 없음!
		 */
		Notice noticeOne = service.selectNoticeByNo(noticeNo);
		model.addAttribute("notice", noticeOne);
		return "notice/detail";
	}
	
	// 파일 처리 메소드화!!!!!!!!!!!!!!!!!!!!!!!!
	public Map<String, Object > saveFile(MultipartFile uploadFile, HttpServletRequest request) throws Exception, IOException {
		//* 넘겨야 하는 값이 여러개일 때 사용하는 방법
		/* 1. VO클래스를 사용하는 방법
		 * 2. HashMap을 사용하는 방법(* 이걸로 사용)
		 */
		Map<String, Object> infoMap = new HashMap<String, Object>();
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
		// 파일 리네임 : 이름이 같은 파일을 업로드 시 다른 사람이 올린 파일로 덮어쓰기 방지
		// 파일 네임을 리네임한 다음에 경로에 적기
		
//		// 랜덤함수를 사용해 파일 이름 리네임 해보기
//		Random rand = new Random();
//		String strResult = "N";
//		for(int i = 0; i < 7; i++) {
//			int result = rand.nextInt(20)+1;
//			strResult += result+"";
//		}
		
		// 시간으로 파일 리네임 해보기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHmmss");	// 나중에 SS랑 비교
		String strResult = sdf.format(new Date(System.currentTimeMillis()));

		// 확장자명 구하기
		// 마지막에서부터 .을 빼고 자른다 (+1).
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		String fileRename = "N"+strResult + "."+ext;
		String savePath = saveFolder + "\\" + fileRename;
//		String savePath = saveFolder + "\\" + fileName;
		File file = new File(savePath);
		// ********************** 파일 저장 **************************
		// 진짜로 파일을 저장하는 메소드
		uploadFile.transferTo(file);
		
		// ====================== 파일 크기 ==========================
		long fileLength = uploadFile.getSize();
		/*
		 * 파일 이름, 경로, 크기를 넘겨주기 위해 Map에 정보를 저장한 후 return 함
		 * 왜 return하는가> DB에 저장하기 위한 정보니까!
		 */
		infoMap.put("fileName", fileName);
		infoMap.put("fileRename", fileRename);
		infoMap.put("filePath", savePath);
		infoMap.put("fileLength", fileLength);
		return infoMap;
	}

	private void deleteFile(HttpServletRequest request, String fileName) {
		// 절대경로를 넣어주면 삭제가 됨
		// * resources의 경로는 request 객체가 있으면 경로를 가져올 수 있음!
		String root = request.getSession().getServletContext().getRealPath("resources");
		String delFilePath = root+"\\nuploadFiles\\"+fileName;
		File file = new File(delFilePath);	// 지우고 싶은 파일 경로를 입력하면
		if(file.exists()) {
			// 파일이 존재하니?
			// 있으면 삭제해
			file.delete();				// 파일이 지워진다.
		}
		
	}
	
	
	
	
	
	
	
}
