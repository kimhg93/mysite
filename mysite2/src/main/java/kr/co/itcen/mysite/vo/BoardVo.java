package kr.co.itcen.mysite.vo;

public class BoardVo {
	private Long no;
	private Long userNo;
	private String userName;
	private String title;
	private String contents;
	private int hit;
	private String regDate;
	private int groupNo;
	private int orderNo;
	private int depth;
	private boolean removed;
	
	
	public boolean getRemoved() {
		return removed;
	}
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public Long getUserNo() {
		return userNo;
	}
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", userNo=" + userNo + ", userName=" + userName + ", title=" + title
				+ ", contents=" + contents + ", hit=" + hit + ", regDate=" + regDate + ", groupNo=" + groupNo
				+ ", orderNo=" + orderNo + ", depth=" + depth + "]";
	}	
	
}
