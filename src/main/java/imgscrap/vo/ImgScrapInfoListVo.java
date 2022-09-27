package imgscrap.vo;

import org.apache.ibatis.type.Alias;

@Alias("img_scrap_info_list")
public class ImgScrapInfoListVo {
	
	private long scrapNo;				// 스크랩순번
	private String title;				// 제목
	private String cont;				// 내용(설명)
	private String thumbnailImgUrl;		// 썸네일 이미지 URL
	private int hits;					// 조회수
	
	public long getScrapNo() {
		return scrapNo;
	}
	public void setScrapNo(long scrapNo) {
		this.scrapNo = scrapNo;
	}
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
	public String getThumbnailImgUrl() {
		return thumbnailImgUrl;
	}
	public void setThumbnailImgUrl(String thumbnailImgUrl) {
		this.thumbnailImgUrl = thumbnailImgUrl;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	
	
}
