package imgscrap.vo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name="TB_IMG_SCRAP_INFO")
public class TbImgScrapInfoVo {
    @Id
    @GeneratedValue
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
    private String imgServiceUrl;		// 이미지서비스URL
    private int hits;					// 조회수
    private String delYn;				// 삭제여부
    private String insertUserNo;		// 등록사용자번호
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDt;		    // 등록일시
    private String updateUserNo;		// 수정사용자번호
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDt;		    // 수정일시
}
