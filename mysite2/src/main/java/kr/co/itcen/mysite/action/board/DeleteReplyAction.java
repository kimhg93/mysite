package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.ReplyDao;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class DeleteReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		HttpSession session = request.getSession();
		UserVo user = (UserVo)session.getAttribute("authUser");
		
		Long userNo = Long.parseLong(request.getParameter("userNo"));
		Long boardNo = Long.parseLong(request.getParameter("boardNo"));
		String keyWord = request.getParameter("keyWord");
		String page = request.getParameter("page");
		Long no = Long.parseLong(request.getParameter("replyNo"));
		
		if(user.getNo().equals(userNo)) {
			new ReplyDao().delete(no);
		}
		
		
		WebUtils.redirect(request, response, request.getContextPath()+"/board?a=view&no="+boardNo+"&page="+page+"&kwd="+keyWord);
	}

}
