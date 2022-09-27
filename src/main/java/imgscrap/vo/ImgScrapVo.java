package imgscrap.vo;

public class ImgScrapVo {
	
	private long scrapNo;				// 스크랩번호
	private String title;				// 제목
	private String cont;				// 내용(설명)
	private String orgImgUrl;			// 원본이미지URL
	private String orgImgFileName;		// 원본이미지파일명
	private String imgSavePath;			// 원본이미지저장경로
	private String imgSaveFileName;		// 저장된 파일명
	private String thumbnailSavePath;	// 원본이미지저장경로
	private String thumbnailFileName;	// 썸네일파일명
	private String imgFileExt;			// 이미지파일확장자
	private String userNo;				// 사용자번호
	private String imgServiceUrl;		// 이미지서비스URL
	private int hits;					// 조회수
	private String delYn;				// 삭제여부
	
	
	public long getScrapNo() {
		return scrapNo;
	}
	public void setScrapNo(long scrapNo) {
		this.scrapNo = scrapNo;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getImgServiceUrl() {
		return imgServiceUrl;
	}
	public void setImgServiceUrl(String imgServiceUrl) {
		this.imgServiceUrl = imgServiceUrl;
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
	public String getOrgImgUrl() {
		return orgImgUrl;
	}
	public void setOrgImgUrl(String orgImgUrl) {
		this.orgImgUrl = orgImgUrl;
	}
	public String getOrgImgFileName() {
		return orgImgFileName;
	}
	public void setOrgImgFileName(String orgImgFileName) {
		this.orgImgFileName = orgImgFileName;
	}
	public String getImgSavePath() {
		return imgSavePath;
	}
	public void setImgSavePath(String imgSavePath) {
		this.imgSavePath = imgSavePath;
	}
	public String getImgSaveFileName() {
		return imgSaveFileName;
	}
	public void setImgSaveFileName(String imgSaveFileName) {
		this.imgSaveFileName = imgSaveFileName;
	}
	public String getThumbnailSavePath() {
		return thumbnailSavePath;
	}
	public void setThumbnailSavePath(String thumbnailSavePath) {
		this.thumbnailSavePath = thumbnailSavePath;
	}
	public String getThumbnailFileName() {
		return thumbnailFileName;
	}
	public void setThumbnailFileName(String thumbnailFileName) {
		this.thumbnailFileName = thumbnailFileName;
	}
	public String getImgFileExt() {
		return imgFileExt;
	}
	public void setImgFileExt(String imgFileExt) {
		this.imgFileExt = imgFileExt;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
}
