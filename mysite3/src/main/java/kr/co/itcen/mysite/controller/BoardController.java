package kr.co.itcen.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;
@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value= {"", "/list"}, method=RequestMethod.GET)
	public String list(@ModelAttribute BoardVo vo, Model model) {
		int SHOW_PAGE = 5;
		int SHOW_CNT = 5;		
		int currentPage = 1;

		if (vo.getPage()!=0) {
			currentPage = vo.getPage();
		}

		String keyWord = vo.getKeyWord();

		int countAll = boardService.countAll();
		if (keyWord != null) {
			countAll = boardService.countAll(keyWord);
		}

		int pageAll = countAll % SHOW_CNT == 0 ?
					  countAll / SHOW_CNT 
					: countAll / SHOW_CNT + 1;
		
		int startPage = currentPage % SHOW_PAGE == 0 ?
				       (currentPage / SHOW_PAGE - 1) * SHOW_PAGE + 1
					 : (currentPage / SHOW_PAGE) * SHOW_PAGE + 1;
		
		int lastPage = startPage + SHOW_PAGE - 1;

		if ("next".equals(vo.getMove())) {
			startPage = currentPage;
			lastPage = startPage + (SHOW_PAGE - 1);
		} else if ("prev".equals(vo.getMove())) {
			startPage = currentPage - (SHOW_PAGE - 1);
			lastPage = currentPage;
		}

		if (pageAll < lastPage) {
			lastPage = pageAll;
		}

		model.addAttribute("countAll", countAll - (currentPage - 1) * SHOW_CNT);
		model.addAttribute("pageAll", pageAll);
		model.addAttribute("startPage", startPage);
		model.addAttribute("lastPage", lastPage);
		
		List<BoardVo> list  = boardService.getList(currentPage, SHOW_CNT, keyWord);
		model.addAttribute("list", list);			
		return "board/list";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {		
		return "board/write";
	}
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@ModelAttribute BoardVo vo) {		
		return "board/write";
	}
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String view(BoardVo vo, Model model) {			
		BoardVo viewVo = boardService.view(vo.getNo());
		boardService.updateHit(viewVo.getNo(), viewVo.getHit()+1);
		
		//viewVo.setNo(no);
		
		//request.setAttribute("viewVo", viewVo);
		//request.setAttribute("reply", new ReplyDao().getList(no));
		//WebUtils.forward(request, response, "/WEB-INF/views/board/view.jsp");
		return "board/view";
	}
		
}
