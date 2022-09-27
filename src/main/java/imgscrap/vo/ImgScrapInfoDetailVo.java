package imgscrap.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("img_scrap_info_detail")
public class ImgScrapInfoDetailVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long scrapNo;				// 스크랩순번
	private String title;				// 제목
	private String cont;				// 내용(설명)
	private String imgUrl;				// 이미지 URL
	private int hits;					// 조회수
	private String insertUserNo;		// 등록사용자번호
	
	public String getInsertUserNo() {
		return insertUserNo;
	}
	public void setInsertUserNo(String insertUserNo) {
		this.insertUserNo = insertUserNo;
	}
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
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	
	
}
