package imgscrap.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import imgscrap.config.mybatis.MybatisMapper;
import imgscrap.vo.ImgScrapInfoDetailVo;
import imgscrap.vo.ImgScrapInfoListVo;
import imgscrap.vo.ImgScrapVo;

@MybatisMapper
public interface ImgScrapDao {
	/**
	 * 이미지스크랩 데이터 등록
	 * @param vo
	 * @throws Exception
	 */
	public void insertImgScrap(ImgScrapVo vo) throws Exception;
	
	/**
	 * 이미지스크랩 목록 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public List<ImgScrapInfoListVo> selectImgScrapList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 이미지스크랩 목록 조회 총건수
	 * @param paramMap
	 * @throws Exception
	 */
	public int selectImgScrapListTotCnt(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 이미지스크랩 상세 조회
	 * @param scrapNo
	 * @throws Exception
	 */
	public ImgScrapInfoDetailVo selectImgScrapDetail(@Param("scrapNo") long scrapNo) throws Exception;
	
	/**
	 * 이미지스크랩 데이터 수정
	 * @param paramMap
	 * @throws Exception
	 */
	public void modifyImgScrap(ImgScrapVo vo) throws Exception;
	
	/**
	 * 이미지스크랩 조회수 증가
	 * @param paramMap
	 * @throws Exception
	 */
	public void modifyImgScrapHits(ImgScrapVo vo) throws Exception;
	
	/**
	 * 이미지스크랩 데이터 삭제
	 * @param paramMap
	 * @throws Exception
	 */
	public void removeImgScrap(ImgScrapVo vo) throws Exception;
}
