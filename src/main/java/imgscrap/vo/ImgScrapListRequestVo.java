package imgscrap.vo;

import javax.validation.constraints.Positive;

public class ImgScrapListRequestVo {
	
	@Positive
	private int page;				// 페이지번호
	@Positive
	private int page_size;			// 페이지당 사이즈
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPage_size() {
		return page_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
}
