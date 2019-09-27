package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.BoardVo;


public class BoardDao {	
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
		Connection connection = null;		
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();

			String sql = "insert into board values(null, ?, ?, 0, now(), ?, ?, ?, ?, ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getGroupNo());
			pstmt.setInt(4, vo.getOrderNo());
			pstmt.setInt(5, vo.getDepth());
			pstmt.setLong(6, vo.getUserNo());
			pstmt.setBoolean(7, vo.getRemoved());
			
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
	
	public int getGno() {
		Connection connection = null;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int maxGno = 0;
		try {
			connection = getConnection();

			String sql = "select max(g_no) from board";
			pstmt = connection.prepareStatement(sql);						
			rs = pstmt.executeQuery();		
			
			if(rs.next()) {
				maxGno = rs.getInt(1);		
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
		return maxGno;
	}
	
	public void modify(BoardVo vo) {
		Connection connection = null;		
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();

			String sql = "update board set title=?, contents=? where no=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());	
			pstmt.setLong(3, vo.getNo());
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
	public List<BoardVo> getList(int page, int showCont, String keyWord) {
		List<BoardVo> list = new ArrayList<BoardVo>();
		Connection connection = null;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String like = " ";
		try {
			connection = getConnection();
			if(keyWord!=null) {
				like= "and (title like '%"+keyWord+"%' or contents like '%"+keyWord+"%')";
			}
			
			String sql = "select b.title, a.name, b.hit, date_format(b.reg_date, '%Y-%m-%d %H:%i:%s'),"
								+ " b.no, b.user_no, b.depth, b.g_no, b.removed"								
								+ " from user a, board b"
								+ " where a.no = b.user_no "+ like 
								+ " and "+  getWhere() 
								+ " group by b.no"
								+ " order by b.g_no desc, o_no asc"
								+ " limit ?, ?";			
			
			pstmt = connection.prepareStatement(sql);			
				pstmt.setInt(1, (page-1)*showCont);
				pstmt.setInt(2, showCont);
			
			rs = pstmt.executeQuery();		
			while(rs.next()) {
				BoardVo vo = new BoardVo();	
				
				vo.setTitle(rs.getString(1));
				vo.setUserName(rs.getString(2));
				vo.setHit(rs.getInt(3));
				vo.setRegDate(rs.getString(4));
				vo.setNo(rs.getLong(5));
				vo.setUserNo(rs.getLong(6));
				vo.setDepth(rs.getInt(7));
				vo.setGroupNo(rs.getInt(8));
				vo.setRemoved(rs.getBoolean(9));
				
				list.add(vo);			
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
		return list;
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
