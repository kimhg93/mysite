package kr.co.itcen.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	//@Autowired
	//private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;

	public int insert(GuestBookVo vo) {
		return sqlSession.insert("guestbook.insert", vo);
	}

	public int delete(GuestBookVo vo) {
		return sqlSession.delete("guestbook.delete", vo);
	}	
	
	public int delete(Long no, String password) {
		GuestBookVo vo = new GuestBookVo();
		vo.setNo(no);
		vo.setPassword(password);		
		return this.delete(vo);
	}

	public List<GuestBookVo> getList() {
		List<GuestBookVo> result = sqlSession.selectList("guestbook.getList");
		return result;
	}
	
	public List<GuestBookVo> getList(Long startNo) {
		List<GuestBookVo> result = sqlSession.selectList("guestbook.getList2", startNo);
		return result;
	}
}
