package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.ReplyVo;

public class ReplyDao {
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
	
	public void insert(ReplyVo vo) {
		Connection connection = null;		
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();

			String sql = "insert into reply values(null, ?, now(), ?, ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getContents());
			pstmt.setLong(2, vo.getUserNo());
			pstmt.setLong(3, vo.getBoardNo());
			
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
	public List<ReplyVo> getList(Long no) {
		List<ReplyVo> list = new ArrayList<ReplyVo>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			String sql = "select a.no, a.name, b.contents, date_format(b.reg_date, '%Y-%m-%d %H:%i:%s'), b.board_no, b.no"								
					+ " from user a, reply b"
					+ " where b.user_no = a.no and board_no=? order by reg_date desc";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long userNo = rs.getLong(1);
				String userName = rs.getString(2);
				String contents = rs.getString(3);				
				String regDate = rs.getString(4);
				Long boardNo = rs.getLong(5);
				Long replyNo = rs.getLong(6);
				ReplyVo vo = new ReplyVo();
				
				vo.setUserNo(userNo);
				vo.setUserName(userName);
				vo.setContents(contents);
				vo.setRegDate(regDate);
				vo.setBoardNo(boardNo);
				vo.setNo(replyNo);
												
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

	public void delete(Long no) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();

			String sql = "delete from reply where no=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);
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

}
