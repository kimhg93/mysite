package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		Long userNo = authUser.getNo();
				
		BoardDao boardDao = new BoardDao();
		int flag = Integer.parseInt(request.getParameter("flag"));
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		boolean removed = Boolean.parseBoolean(request.getParameter("removed"));
				
		BoardVo vo = new BoardVo();	
		vo.setUserNo(userNo);
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setRemoved(removed);
		
		if(flag==1) {
			int gNo = Integer.parseInt(request.getParameter("gno"));
			int oNo = Integer.parseInt(request.getParameter("ono"))+1;
			int depth = Integer.parseInt(request.getParameter("depth"))+1;
			
			vo.setGroupNo(gNo);
			vo.setOrderNo(oNo);
			vo.setDepth(depth);
			
			boardDao.updateOderNo(gNo, oNo);
		} else {
			int groupNo = boardDao.getGno();
			vo.setGroupNo(groupNo+1);
			vo.setOrderNo(0);
			vo.setDepth(0);
		}
		
		new BoardDao().insert(vo);
		
		WebUtils.redirect(request, response, request.getContextPath()+"/board");
	}
}
