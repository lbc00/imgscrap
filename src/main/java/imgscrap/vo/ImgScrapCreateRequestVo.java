package imgscrap.vo;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class ImgScrapCreateRequestVo {
	
	@NotEmpty(message="제목을 입력하세요.")
	private String title;				// 제목
	@NotEmpty(message="설명을 입력하세요.")
	private String cont;				// 내용(설명)
	@NotEmpty(message="이지미URL을 입력하세요.")
	private String img_url;				// 이미지URL
	@NotEmpty(message="사용자번호를 입력하세요.")
	private String user_no;
	
}
