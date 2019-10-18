package kr.co.itcen.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.ReplyVo;

@Repository
public class ReplyDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public void insert(ReplyVo vo) {
		sqlSession.insert("reply.insert", vo);
	}
	public List<ReplyVo> getList(Long no) {		
		return sqlSession.selectList("reply.getList", no);
	}

	public void delete(Long no) {
		sqlSession.delete("reply.delete",no);
	}

}
