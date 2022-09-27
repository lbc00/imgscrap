package imgscrap.vo;

import javax.validation.constraints.NotEmpty;

public class ImgScrapModifyRequestVo {
	
	@NotEmpty(message="제목을 입력하세요.")
	private String title;				// 제목
	@NotEmpty(message="설명을 입력하세요.")
	private String cont;				// 내용(설명)
	@NotEmpty(message="사용자번호를 입력하세요.")
	private String user_no;				// 사용자번호
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	
	
}
