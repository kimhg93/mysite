package kr.co.itcen.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	public List<BoardVo> getList(int page, int showCont, String keyWord){
		return boardDao.getList(page, showCont, keyWord);	
	}
	public BoardVo view(Long no) {
		return boardDao.getView(no);
	}
	public void updateHit(Long no, int hit) {
		boardDao.updateHit(no, hit);
	}
	public int countAll() {
		return boardDao.countAll();	}
	public int countAll(String keyWord) {
		return boardDao.countAll(keyWord);
	}
	
}
