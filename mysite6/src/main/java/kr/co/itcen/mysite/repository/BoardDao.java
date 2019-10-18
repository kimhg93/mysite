package kr.co.itcen.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public void insert(BoardVo vo) {
		sqlSession.insert("board.insert", vo);
	}
	
	public int getGno() {		
		return sqlSession.selectOne("board.getGno");
	}
	
	public void modify(BoardVo vo) {
		sqlSession.update("board.modify", vo);		
	}
		
	public List<BoardVo> getList(int page, int showCont, String keyWord) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", (page-1)*showCont);
		map.put("showCont", showCont);
		map.put("keyWord", "%"+keyWord+"%");		
		map.put("keyWord2", keyWord);
		return sqlSession.selectList("board.getList", map);		
	}
	
	public void updateOno(int gNo, int oNo) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("gNo", gNo);
		map.put("oNo", oNo);
		sqlSession.update("board.updateOno", map);
	}	
	
	public BoardVo getView(Long no) {
		return sqlSession.selectOne("board.getView", no);
	}
	
	public BoardVo getGroup(Long no) {
		return sqlSession.selectOne("board.getGroup", no);
	}
		
	public void delete(Long no, Long userNo) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("no", no);
		map.put("userNo", userNo);
		sqlSession.update("board.delete", map);		
	}	
	
	public void updateHit(Long no, int hit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hit", hit);
		map.put("no", no);
		sqlSession.update("board.updateHit", map);	
	}
		
	public int countAll() {
		return sqlSession.selectOne("board.countAll");		
	}
	public int countAll(String keyWord) {
		return sqlSession.selectOne("board.countAll2", "%"+keyWord+"%");	
	}
	
//	private String getWhere() {
//		String where = "removed=false or (removed = true and ((b.o_no = (select max(o_no) o_no"
//					+ " from board where g_no=b.g_no and depth=b.depth) and (select (select count(*)"
//					+ " from board where g_no = b.g_no and o_no > b.o_no and depth > b.depth and removed = false)>0)"
//					+ " or ((select count(*) from board where g_no = b.g_no and o_no > b.o_no"
//					+ " and depth > b.depth and o_no < (select o_no from board where g_no = b.g_no and o_no > b.o_no"
//					+ " and depth = b.depth order by o_no asc limit 0, 1) and removed = false)>0))))";
//		return where;
//	}
	
	
	
}
