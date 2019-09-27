package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.dao.ReplyDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao boardDao = new BoardDao();
		Long no = Long.parseLong(request.getParameter("no"));
		BoardVo viewVo = boardDao.getView(no);
		boardDao.updateHit(no, viewVo.getHit()+1);
		
		viewVo.setNo(no);
		
		request.setAttribute("viewVo", viewVo);
		request.setAttribute("reply", new ReplyDao().getList(no));
		WebUtils.forward(request, response, "/WEB-INF/views/board/view.jsp");
	}

}
