package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.ReplyDao;
import kr.co.itcen.mysite.vo.ReplyVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ReplyInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long boardNo = Long.parseLong(request.getParameter("boardNo"));
		Long userNo = Long.parseLong(request.getParameter("userNo"));
		String userName = request.getParameter("userName");
		String contents = request.getParameter("contents");
		String keyWord = request.getParameter("keyWord");
		String page = request.getParameter("page");
		
		ReplyVo vo = new ReplyVo();	
		vo.setBoardNo(boardNo);
		vo.setUserNo(userNo);
		vo.setUserName(userName);		
		vo.setContents(contents);
		
		new ReplyDao().insert(vo);
		
		WebUtils.redirect(request, response, request.getContextPath()+"/board?a=view&no="+boardNo+"&page="+page+"&kwd="+keyWord);
	}

}
