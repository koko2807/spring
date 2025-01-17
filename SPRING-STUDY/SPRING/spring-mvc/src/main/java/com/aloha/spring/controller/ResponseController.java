package com.aloha.spring.controller;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aloha.spring.dto.Board;

/**
 * 컨트롤러 응답
 *
 */
@Controller
@RequestMapping("/response")
public class ResponseController {

	private static final Logger logger = LoggerFactory.getLogger(ResponseController.class);
	
	/**
	 * 요청 경로 : /response/index
	 * 응답 	   : /response/index.jsp
	 * @param model
	 */
	@RequestMapping("/index")
	public void response(Model model) {
		logger.info("void 타입 - /response/index");
		logger.info("/response/index.jsp 뷰를 응답");
		model.addAttribute("message", "Hello");
	}
	
	/**
	 * String
	 * 요청 경로	: /response/view
	 * 응답		: /response/index.jsp
	 * @return
	 */
	@RequestMapping("/view")
	public String responseView() {
		logger.info("String 타입 - /response/index");
		logger.info("/response/index.jsp 뷰를 응답");
		logger.info("view 이름을 반환값으로 지정");
		return "response/index";
	}
	
	/**
	 * ModelAndView
	 * 요청 경로		: /response/model/view
	 * 응답			: /response/index.jsp
	 * @param mv
	 * @return
	 */
	@RequestMapping("/model/view")
	public ModelAndView responseModelAndView(ModelAndView mv, Board board) {
		// ModelAndView
		// : 뷰와 모델 데이터를 지정하여 함께 반환 처리할 수 있는 스프링프레임워크 클래스
		logger.info("ModelAndView 타입 - /response/model/view");
		logger.info("/response/index.jsp 뷰를 응답");
		logger.info("모델과 뷰를 MidelAndView 객체에 지정하여 응답");
		
		// 뷰이름 지정
		mv.setViewName("/response/index");		// view : /response/index.jsp
		
		// 모델 등록
		board.setTitle("제목");
		board.setWriter("작성자");
		board.setContent("내용");
		mv.addObject("board", board);
		mv.addObject("message", "ModelAndVie 컨트롤러 응답...");
		// model
		return mv;
	}
	
	/**
	 * 클래스
	 * 요청 경로	: /response/data/board
	 * 응답 		: board (XML/JSON)
	 * @return
	 */
	@ResponseBody		// 응답하는 데이터를 응답 메세지의 body(본문)에 지정하는 어노테이션
	@RequestMapping("/data/board")
	public Board responseBoard() {
		Board board = new Board("제목", "작성자", "내용");
		return board;
	}
	
	/**
	 * 컬렉션
	 * - 클라이언트의 Accept 요청 헤더의 우선순위에 맞는 데이터 형식으로 응답
	 * - Accept
	 * * text/html
	 * * application/xhtml+xml
	 * * application/xml
	 * @return
	 * @throws Exception
	 * 
	 * * produces = "application/json"
	 * 	 : 서버에서 응답 컨텐츠의 타입을 json 으로 지정한다.
	 */
	@ResponseBody
	@RequestMapping(value = "/data/board/list", produces = "application/json")
//	@RequestMapping("/data/board/list")
	public List<Board> responseBoardList() throws Exception {
		List<Board> boardList = new ArrayList<Board>(); 
		boardList.add(new Board("제목1", "작성자1", "내용1"));
		boardList.add(new Board("제목2", "작성자2", "내용2"));
		boardList.add(new Board("제목3", "작성자3", "내용3"));
		
		return boardList;
	}

	/**
	 * 컬렉션 (Map)
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
//	 @RequestMapping(value = "/data/map")
	@RequestMapping(value = "/data/map", produces = "application/json")
	public Map<String, Board> responseMap() throws Exception {
		Map<String, Board> map = new HashMap<String, Board>();
		map.put("board1", new Board("제목1","작성자1","내용1"));
		map.put("board2", new Board("제목2","작성자2","내용2"));
		map.put("board3", new Board("제목3","작성자3","내용3"));
		
		return map;
	}
	
	/**
	 * ResponseEntity<Void>
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/data/entity/void")
	public ResponseEntity<Void> responseEntityVoid() {
		// ResponseEntity
		// : 스프링 프레임워크에서 응답 헤더,본문,상태코드 등을 캡슐화하는 객체
		// ResponseEntity<Void>
		// : 헤더정보, 상태코드를 지정하여 사용할 수 있다.
		// HttpStatus 열거타입
		// : 상태코드를 가지고 있는 스프링프레임웤의 열거타입
		// - OK 						: 200
		// - NOT_FOUND 					: 404
		// - INTERNAL_SERVER_ERROR		: 500
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/**
	 * ResponseEntity<String>
	 * - 응답 메시지를 String 타입으로 지정하여 응답
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/data/entity/string")
	public ResponseEntity<String> responseEntityString() {
		
		// new ResponseEntity<반환타입>(응답메시지, 상태코드);
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
	
	/**
	 * ResponseEntity<Board>
	 * : 응답 메시지를 Board 타입으로 반환
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/data/entity/board")
	public ResponseEntity<Board> responseEntityBoard() {
		Board board = new Board("제목","작성자","내용");
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}
	
	/**
	 * ResponseEntity<List<Board>>
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/data/entity/board/list")
	public ResponseEntity<List<Board>> responseEntityBoardList() throws Exception {
		List<Board> boardList = new ArrayList<Board>(); 
		boardList.add(new Board("제목1", "작성자1", "내용1"));
		boardList.add(new Board("제목2", "작성자2", "내용2"));
		boardList.add(new Board("제목3", "작성자3", "내용3"));
		// return new ResponseEntity<>(boardList, HttpStatus.OK);		// <> 생략가능
		return new ResponseEntity<List<Board>>(boardList, HttpStatus.OK);
	}
	
	/**
	 * ResponseEntity<Map<String, Board>>
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/data/entity/map")
	public ResponseEntity<Map<String, Board>> responseEntityBoardMap() throws Exception {
		
		List<Board> boardList = new ArrayList<Board>(); 
		boardList.add(new Board("제목1", "작성자1", "내용1"));
		boardList.add(new Board("제목2", "작성자2", "내용2"));
		boardList.add(new Board("제목3", "작성자3", "내용3"));
		Map<String, Board> map = new HashMap<String, Board>();
		
		int i = 1;
		for (Board board : boardList) {
			map.put("board" + i++, board);
		}
		// json 형식으로 컨텐츠 타입 지정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
//		new ResponseEntity<Void>(응답상태);
//		new ResponseEntity<>(메시지, 응답상태);
//		new ResponseEntity<>(메시지, 헤더, 응답상태);
		return new ResponseEntity<>(map, headers, HttpStatus.OK);
		
	}
	
	/**
	 * 파일 다운로드
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/data/file")
	public ResponseEntity<byte[]> responseFile(HttpServletRequest request) throws Exception {
		
		String path = request.getServletContext() + "/WEB-INF/upload/test.png";
		logger.info("path : " + path);
		
		// 파일 경로
		// String filePath = "E:\\TJE\\UPLOAD\\test.jpg";
		String filePath = request.getServletContext() + "/WEB-INF/upload/test.png";
		String fileName = "test.png";
		// 헤더정보
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType( MediaType.IMAGE_PNG );						// 이미지로 응답
		// headers.setContentType( MediaType.APPLICATION_OCTET_STREAM);		// 일반 프로그램 응답
		
		// headers.add("헤더명", "값")
		// Content-Disposition
		// - inline			: 웹페이지에서 출력(기본값)
		// - attachment		: 첨부파일 (다운로드)
		headers.add("Content-Disposition", "attachment; filename=" + fileName);	// 다운로드 여부, 파일명 지정
		
		byte[] fileData = null;
		try {
			FileInputStream fis = new FileInputStream(filePath);
			// commons-io 라이브러리
			// toByteArray() : 파일을 바이트코드로 변환
			fileData = IOUtils.toByteArray(fis);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		// new ResponseEntity<반환타입>(응답메시지, 헤더,상태코드);
		return new ResponseEntity<byte[]>(fileData, headers, HttpStatus.OK);
	}
}



















