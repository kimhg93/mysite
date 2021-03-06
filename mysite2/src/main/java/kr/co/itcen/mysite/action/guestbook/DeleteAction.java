package kr.co.itcen.mysite.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.GuestBookDao;
import kr.co.itcen.mysite.vo.GuestBookVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String paramno = request.getParameter("no");
		String password = request.getParameter("password");
		Long no = Long.parseLong(paramno);

		GuestBookVo vo = new GuestBookVo();
		vo.setNo(no);
		vo.setPassword(password);

		new GuestBookDao().delete(vo);
		
		WebUtils.redirect(request, response, request.getContextPath() + "/guestbook");
	}

}
