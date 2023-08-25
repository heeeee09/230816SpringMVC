package com.aug.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.aug.spring.board.domain.Board;
import com.aug.spring.board.domain.PageInfo;
import com.aug.spring.board.domain.Reply;
import com.aug.spring.board.service.BoardService;
import com.aug.spring.board.service.ReplyService;


@Controller
public class BoardController {
	
	@Autowired
	private BoardService bService;
	@Autowired
	private ReplyService rService;
	
	@RequestMapping(value="/board/list.kh", method = RequestMethod.GET)
	public ModelAndView showBoardList(
			@RequestParam(value="page", required = false, defaultValue = "1") Integer currentPage
			, ModelAndView mv) {
		Integer totalCount = bService.getListCount();
		PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
		// pInfo 만든 후 bList 만듦
		List<Board> bList = bService.selectBoardList(pInfo);
		// 메소드 체이닝
		if(!bList.isEmpty()) {
			mv.addObject("bList", bList).addObject("pInfo", pInfo).setViewName("board/list");
		}
		return mv;
	}
	
	@RequestMapping(value="/board/detail.kh", method = RequestMethod.GET)
	public ModelAndView showDetailBoard(
			ModelAndView mv
			, @ModelAttribute Board board) {
		Board bPost = bService.selectOneBoardByNo(board.getBoardNo());
		if(bPost != null) {
			List<Reply> replyList = rService.selectReplyList(board.getBoardNo());
			if(replyList.size() > 0) {
				mv.addObject("rList", replyList);
			}
			mv.addObject("board", bPost).setViewName("board/detail");
		} else {
			mv.addObject("msg", "게시글 조회가 완료되지 않았습니다.");
			mv.addObject("error", "게시글 조회 실패");
			mv.addObject("url", "/board/write.kh");
			mv.setViewName("common/errorPage");
		}
		return mv;
	}

	//!!!!!!!!!!! 페이지 네비게이션 !!!!!!!!!!!!!!
	public PageInfo getPageInfo(Integer currentPage, Integer totalCount) {
		int recordCountPerPage = 10;
		int naviCountPerPage = 10;
		int naviTotalCount;
		//					Math.ceil = 올림
		naviTotalCount = (int)Math.ceil((double)totalCount / recordCountPerPage);
		int startNavi = ((int)((double)currentPage / naviCountPerPage+0.9)-1)*naviCountPerPage+1;
		int endNavi = startNavi + naviCountPerPage - 1;
		if(endNavi > naviTotalCount) {
			endNavi = naviTotalCount;
		}
		PageInfo pInfo = new PageInfo(currentPage, totalCount, naviTotalCount, recordCountPerPage, naviCountPerPage, startNavi, endNavi);
		return pInfo;
	}

	@RequestMapping(value="/board/write.kh", method = RequestMethod.GET)
	public ModelAndView showWriteForm(ModelAndView mv) {
		/*
		 * ModelAndView : 데이터 보내고, 페이지 이동도 하는 기능을 가짐
		 * return으로 경로를 적어주는 것이 아니라 setViewName에 넣어준다.
		 * 왜 쓰지?? -> mv는 다양한 기능을 가지고 있기 때문에 
		 * 사용하려면? -> 매개변수로 넣어줘야 함
		 * aJax에서는 ModelAndView는 사용할 수 없기 때문에 String도 알고 있어야 함
		 * 기본적으론 ModelAndView를 사용해 개발하기~!
		 */
		mv.setViewName("board/write");
		return mv;
	}
	
	@RequestMapping(value="/board/write.kh", method = RequestMethod.POST)
	public ModelAndView boardRegister(
			ModelAndView mv
			, @RequestParam(value="uploadFile", required = false) MultipartFile uploadFile
			, @ModelAttribute Board board
			, HttpServletRequest request
			) {
		try {
			if(uploadFile != null && !uploadFile.getOriginalFilename().equals("")) {
				// 파일 정보(이름, 리네임, 경로, 크기) 및 파일 저장
				Map<String, Object> bMap = this.saveFile(request, uploadFile);
				board.setBoardFileName((String)bMap.get("fileName"));
				board.setBoardFileRename((String)bMap.get("fileRename"));
				board.setBoardFilePath((String)bMap.get("filePath"));
				board.setBoardFileLength((long)bMap.get("fileLength"));
			}
			// 완료했으면 비즈니스 로직 태우기
			int result = bService.insertBoard(board);
			if(result > 0) {
				mv.setViewName("redirect:/board/list.kh");
			}
		} catch (Exception e) {
			mv.addObject("msg", "게시글 등록이 완료되지 않았습니다.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url", "/board/write.kh");
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
	
	// !!!!!!!!!!!!!!파일 저장 메소드!!!!!!!!!!!!
	private Map<String, Object> saveFile(HttpServletRequest request, MultipartFile uploadFile) throws Exception, IOException {
		Map<String, Object> fileMap = new HashMap<String, Object>();
		// resources 경로 구하기
		String root = request.getSession().getServletContext().getRealPath("resources");
		// 파일 저장 경로 구하기
		String savePath = root + "\\buploadFiles";
		// 파일 이름 구하기
		String fileName = uploadFile.getOriginalFilename();
		// 파일 확장자 구하기
		String extension = fileName.substring(fileName.lastIndexOf(".")+1);
		// 파일 시간으로 리네임하기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileRename = sdf.format(new Date(System.currentTimeMillis()))+"."+extension;
		// 파일 저장 전 폴더 만들기
		File saveFolder = new File(savePath);
		if(!saveFolder.exists()) {
			saveFolder.mkdir();
		}
		// 파일 저장
		// 경로 + \\ + 파일리네임
		File saveFile = new File(savePath+"\\"+fileRename);
		uploadFile.transferTo(saveFile);
		long fileLength = uploadFile.getSize();
		// 파일 정보 리턴
		fileMap.put("fileName", fileName);
		fileMap.put("fileRename", fileRename);
		fileMap.put("filePath", "../resources/buploadFiles/"+fileRename);
		fileMap.put("fileLength", fileLength);
		return fileMap;
	}
	
}
