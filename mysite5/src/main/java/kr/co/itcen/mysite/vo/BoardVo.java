package kr.co.itcen.mysite.vo;

public class BoardVo {
	private Long no;
	private Long userNo;
	private String userName;
	private String title;
	private String contents;
	private int hit;
	private String regDate;
	private int gNo;
	private int oNo;
	private int depth;
	private int page;
	private String keyWord;
	private boolean removed;
	private String move;
	private int flag;
	
	
	

	public int getgNo() {
		return gNo;
	}
	public void setgNo(int gNo) {
		this.gNo = gNo;
	}
	public int getoNo() {
		return oNo;
	}
	public void setoNo(int oNo) {
		this.oNo = oNo;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getMove() {
		return move;
	}
	public void setMove(String move) {
		this.move = move;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
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
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", userNo=" + userNo + ", userName=" + userName + ", title=" + title
				+ ", contents=" + contents + ", hit=" + hit + ", regDate=" + regDate + ", gNo=" + gNo + ", oNo=" + oNo
				+ ", depth=" + depth + ", page=" + page + ", keyWord=" + keyWord + ", removed=" + removed + ", move="
				+ move + ", flag=" + flag + "]";
	}
	
}
