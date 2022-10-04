package imgscrap.vo;

import lombok.Data;

@Data
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
	private String insertUserNo;				// 등록사용자번호
	private String updateUserNo;				// 수정사용자번호
	private String imgServiceUrl;		// 이미지서비스URL
	private int hits;					// 조회수
	private String delYn;				// 삭제여부
	

}
