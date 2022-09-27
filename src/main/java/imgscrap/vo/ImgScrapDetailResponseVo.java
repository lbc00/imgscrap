package imgscrap.vo;

public class ImgScrapDetailResponseVo extends BaseResponseVo {
	
	private ImgScrapInfoDetailVo data = new ImgScrapInfoDetailVo();

	public ImgScrapInfoDetailVo getData() {
		return data;
	}

	public void setData(ImgScrapInfoDetailVo data) {
		this.data = data;
	}
}
