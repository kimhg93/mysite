package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		Long userNo = Long.parseLong(request.getParameter("uno"));
		Long contNo = Long.parseLong(request.getParameter("no"));
		
		if(authUser.getNo()==userNo) {
			new BoardDao().delete(contNo, userNo);			
		}
		WebUtils.redirect(request, response, request.getContextPath()+"/board");
	}

}
