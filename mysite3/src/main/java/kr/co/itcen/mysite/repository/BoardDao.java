package kr.co.itcen.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	
	private Connection getConnection() throws SQLException {
		Connection connection = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");// 2. 연결하기
			String url = "jdbc:mariadb://192.168.1.61:3306/webdb?characterEncoding=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("Fail to Loading Driver: " + e);
		} 
		return connection;
	}
	
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
		map.put("keyWord", keyWord);		
		return sqlSession.selectList("board.getList", map);		
	}
	
	public void updateOderNo(int gNo, int oNo) {
		Connection connection = null;		
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();

			String sql = "update board set o_no=o_no+1 where g_no = ? and o_no >= ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, gNo);
			pstmt.setInt(2, oNo);
			pstmt.executeQuery();		
						
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public void delete(Long no, Long userNo) {
		Connection connection = null;		
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();

			String sql = "update board set removed = true where no = ? and user_no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);
			pstmt.setLong(2, userNo);
			pstmt.executeQuery();		
						
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public BoardVo getView(Long no) {
		BoardVo result = null;
		Connection connection = null;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();

			String sql = "select title, contents, user_no, hit"								
								+ " from board"
								+ " where no = ?";
			pstmt = connection.prepareStatement(sql);		
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();		
			
			if(rs.next()) {
				result = new BoardVo();				
				result.setTitle(rs.getString(1));
				result.setContents(rs.getString(2));
				result.setUserNo(rs.getLong(3));		
				result.setHit(rs.getInt(4));
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return result;
	}
	
	public void updateHit(Long no, int hit) {
		Connection connection = null;		
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();

			String sql = "update board set hit=? where no=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, hit);
			pstmt.setLong(2, no);
			pstmt.executeUpdate();		
						
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public BoardVo getGroup(Long no) {
		BoardVo result = null;
		Connection connection = null;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();

			String sql = "select g_no, o_no, depth"								
								+ " from board"
								+ " where no = ?";
			pstmt = connection.prepareStatement(sql);		
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();		
			
			if(rs.next()) {
				result = new BoardVo();				
				result.setGroupNo(rs.getInt(1));
				result.setOrderNo(rs.getInt(2));
				result.setDepth(rs.getInt(3));
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return result;
	}
	
	public int countGroup(int gNo) {
		Connection connection = null;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int countGroup=0;
		try {
			connection = getConnection();

			String sql = "select count(*)"								
								+ " from board"
								+ " where g_no = ?";
			pstmt = connection.prepareStatement(sql);		
			pstmt.setInt(1, gNo);
			rs = pstmt.executeQuery();		
			
			if(rs.next()) {
				countGroup = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return countGroup;
	}
	
	public int countAll() {
		Connection connection = null;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count=0;
		try {
			connection = getConnection();

			String sql = "select count(*) from board b where " +  getWhere();
			
			pstmt = connection.prepareStatement(sql);	
			rs = pstmt.executeQuery();		
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return count;
	}
	public int countAll(String keyWord) {
		Connection connection = null;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count=0;
		try {
			connection = getConnection();			
			String sql = "select count(*) from board b where "
					+ " (title like '%"+keyWord+"%' or contents like '%"+keyWord+"%')"
					+ " and " + getWhere();
			
			pstmt = connection.prepareStatement(sql);	
			rs = pstmt.executeQuery();		
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return count;
	}
	
	private String getWhere() {
		String where = "removed=false or (removed = true and ((b.o_no = (select max(o_no) o_no"
					+ " from board where g_no=b.g_no and depth=b.depth) and (select (select count(*)"
					+ " from board where g_no = b.g_no and o_no > b.o_no and depth > b.depth and removed = false)>0)"
					+ " or ((select count(*) from board where g_no = b.g_no and o_no > b.o_no"
					+ " and depth > b.depth and o_no < (select o_no from board where g_no = b.g_no and o_no > b.o_no"
					+ " and depth = b.depth order by o_no asc limit 0, 1) and removed = false)>0))))";
		return where;
	}
}
