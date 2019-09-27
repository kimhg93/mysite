package kr.co.itcen.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.repository.ReplyDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.ReplyVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private ReplyDao replyDao;
	
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
		return boardDao.countAll();	
	}
	public int countAll(String keyWord) {
		return boardDao.countAll(keyWord);
	}
	public void insert(BoardVo vo) {
		boardDao.insert(vo);		
	}
	public List<ReplyVo> getReplyList(Long no) {
		return replyDao.getList(no);
	}
	public void insertReply(ReplyVo vo) {
		replyDao.insert(vo);
	}
	public void delete(Long no, Long userNo) {
		boardDao.delete(no, userNo);
	}
	public void deleteReply(Long no) {
		replyDao.delete(no);
	}
	public void updateOno(int gNo, int oNo) {
		boardDao.updateOno(gNo, oNo);
	}
	public int getGno() {
		return boardDao.getGno();
	}
	public BoardVo getGroup(Long no) {		
		return boardDao.getGroup(no);		
	}
	
}
