package imgscrap.vo;

import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class ImgScrapListRequestVo {
	
	@Positive
	private int page;				// 페이지번호
	@Positive
	private int page_size;			// 페이지당 사이즈
}
