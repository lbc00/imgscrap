package imgscrap.vo;

import javax.validation.constraints.NotEmpty;

public class ImgScrapCreateRequestVo {
	
	@NotEmpty(message="제목을 입력하세요.")
	private String title;				// 제목
	@NotEmpty(message="설명을 입력하세요.")
	private String cont;				// 내용(설명)
	@NotEmpty(message="이지미URL을 입력하세요.")
	private String img_url;				// 이미지URL
	@NotEmpty(message="사용자번호를 입력하세요.")
	private String user_no;
	
	
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
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	
	
}
