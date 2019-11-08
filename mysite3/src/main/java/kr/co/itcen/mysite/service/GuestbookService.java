package kr.co.itcen.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.GuestBookDao;
import kr.co.itcen.mysite.vo.GuestBookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestBookDao guestbookDao;

	public List<GuestBookVo> getList() {
		return guestbookDao.getList();
	}
	
	public List<GuestBookVo> getList(Long startNo) {
		return guestbookDao.getList(startNo);
	}
	
	public boolean insert(GuestBookVo vo) {
		int cnt = guestbookDao.insert(vo);
		return cnt==1;
	}

	public void delete(GuestBookVo vo) {
		guestbookDao.delete(vo);		
	}
	
	public boolean delete(Long no, String password) {
		return 1 == guestbookDao.delete(no, password);		
	}
}
