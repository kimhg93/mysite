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

	public void insert(GuestBookVo vo) {
		guestbookDao.insert(vo);		
	}

	public void delete(GuestBookVo vo) {
		guestbookDao.delete(vo);		
	}
}
