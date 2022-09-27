package imgscrap.vo;

import javax.validation.constraints.NotEmpty;

public class ImgScrapGetRequestVo {
	
	@NotEmpty(message="사용자번호를 입력하세요.")
	private String user_no;				// 사용자번호
	
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	
}
