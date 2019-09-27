package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ListAction implements Action {
	private static final int SHOW_PAGE = 5;
	private static final int SHOW_CNT = 5;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao boardDao = new BoardDao();
		int currentPage = 1;

		if ("".equals(request.getParameter("page")) || request.getParameter("page") == null) {
			currentPage = 1;
		} else {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}

		String keyWord = request.getParameter("kwd");

		int countAll = boardDao.countAll();
		if (keyWord != null) {
			countAll = boardDao.countAll(keyWord);
		}

		/////////////////
		String pageMove = request.getParameter("move");

		int pageAll = countAll % SHOW_CNT == 0 ? countAll / SHOW_CNT : countAll / SHOW_CNT + 1;
		int startPage = currentPage % SHOW_PAGE == 0 ? (currentPage / SHOW_PAGE - 1) * SHOW_PAGE + 1
				: (currentPage / SHOW_PAGE) * SHOW_PAGE + 1;
		int lastPage = startPage + SHOW_PAGE - 1;

		if ("next".equals(pageMove)) {
			startPage = currentPage;
			lastPage = startPage + (SHOW_PAGE - 1);
		} else if ("prev".equals(pageMove)) {
			startPage = currentPage - (SHOW_PAGE - 1);
			lastPage = currentPage;
		}

		if (pageAll < lastPage) {
			lastPage = pageAll;
		}

		request.setAttribute("countAll", countAll - (currentPage - 1) * SHOW_CNT);
		request.setAttribute("pageAll", pageAll);
		request.setAttribute("startPage", startPage);
		request.setAttribute("lastPage", lastPage);
		//////////////////

		List<BoardVo> list = boardDao.getList(currentPage, SHOW_CNT, keyWord);
		request.setAttribute("list", list);

		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");

	}
}
