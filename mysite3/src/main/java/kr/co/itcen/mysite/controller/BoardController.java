package kr.co.itcen.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.ReplyVo;
import kr.co.itcen.mysite.vo.UserVo;
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
		if(vo.getKeyWord()!=null&&vo.getKeyWord().length()<1) {
			keyWord=null;
		}
		
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
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("countAll", countAll - (currentPage - 1) * SHOW_CNT);
		model.addAttribute("pageAll", pageAll);
		model.addAttribute("startPage", startPage);
		model.addAttribute("lastPage", lastPage);
		
		List<BoardVo> list  = boardService.getList(currentPage, SHOW_CNT, keyWord);		
		model.addAttribute("list", list);			
		return "board/list";
	}
	
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String view(BoardVo vo, Model model) {			
		BoardVo viewVo = boardService.view(vo.getNo());
		boardService.updateHit(vo.getNo(), viewVo.getHit()+1);
		
		viewVo.setNo(vo.getNo());
		
		model.addAttribute("viewVo", viewVo);
		model.addAttribute("reply", boardService.getReplyList(vo.getNo()));
		
		return "board/view";
	}
	
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(BoardVo vo, Model model) {
		if(vo.getNo()!=null){			
			model.addAttribute("flag", 1);
			model.addAttribute("groupVo", boardService.getGroup(vo.getNo()));
		} else {	
			model.addAttribute("flag", 0);
		}
		return "board/write";
	}	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String insetBoard(BoardVo vo, HttpSession session) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		vo.setUserNo(authUser.getNo());				
		if(vo.getFlag()==1) {
			int gNo = vo.getgNo();
			int oNo = vo.getoNo()+1;
			int depth = vo.getDepth()+1;
			
			vo.setgNo(gNo);
			vo.setoNo(oNo);
			vo.setDepth(depth);
			
			boardService.updateOno(gNo, oNo);
		} else {
			int groupNo = boardService.getGno();
			vo.setgNo(groupNo+1);
			vo.setoNo(0);
			vo.setDepth(0);
		}
		boardService.insert(vo);	
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="delete", method=RequestMethod.GET)
	public String delete(BoardVo vo) {
		boardService.delete(vo.getNo(), vo.getUserNo());	
		return "";
	}
	
	@RequestMapping(value="/reply", method=RequestMethod.POST)
	public String insertReply(ReplyVo vo) {	
		boardService.insertReply(vo);	
		return "redirect:/board/view?no="+vo.getBoardNo()+"&page="+vo.getPage()+"&keyWord="+vo.getKeyWord();
	}
	
	@RequestMapping(value="/removeReply", method=RequestMethod.POST)
	public String removeReply(ReplyVo vo, HttpSession session) {	
		UserVo authUser = (UserVo)session.getAttribute("authUser");
				
		if(authUser.getNo().equals(vo.getUserNo())) {
			boardService.deleteReply(vo.getNo());
		}			
		return "redirect:/board/view?no="+vo.getBoardNo()+"&page="+vo.getPage()+"&keyWord="+vo.getKeyWord();
	}
		
}
