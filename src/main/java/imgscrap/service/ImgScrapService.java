package imgscrap.service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import imgscrap.repository.ImgScrapRepository;
import imgscrap.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import imgscrap.dao.ImgScrapDao;
import imgscrap.exception.BizException;
import imgscrap.util.Code;
import imgscrap.util.ImageUtil;

@Slf4j
@Transactional(rollbackFor = {Exception.class, BizException.class})
@Service
@RequiredArgsConstructor
public class ImgScrapService {
	
	
	@Autowired
	private ImgScrapDao dao;
	
	@Value("${img.save.path}")
	private String imgSavePath;
	
	// 원본이미지 저장경로
	@Value("${img.save.path.org}")
    private String imgSavePathOrg;
	
	// 썸네일이미지 저장경로
	@Value("${img.save.path.thumbnail}")
    private String imgSavePathThumbnail;
	
	@Value("${img.save.thumnail.prefix}")
    private String imgSaveThumnailPrefix;
	
	@Value("${img.service.url}")
    private String imgServiceUrl;

	private final ImgScrapRepository imgScrapRepository;
	
	
	
	/**
	 * 이미지 생성 및 데이터 저장처리
	 * @param request
	 * @throws Exception
	 */
	public void create(ImgScrapCreateRequestVo request) throws Exception {
		String orgImgUrl = request.getImg_url();
		String orgImgFileName = ImageUtil.getFileName(orgImgUrl);
        String imgFileExt = ImageUtil.getFileExt(orgImgUrl);
        
		ImgScrapVo vo = new ImgScrapVo();
		vo.setTitle(request.getTitle());
		vo.setCont(request.getCont());
		vo.setOrgImgUrl(request.getImg_url());
		vo.setImgSavePath(imgSavePathOrg);
		vo.setOrgImgFileName(orgImgFileName);
		vo.setImgSaveFileName(UUID.randomUUID().toString());
		vo.setThumbnailSavePath(imgSavePathThumbnail);
		vo.setImgFileExt(imgFileExt);
		vo.setImgServiceUrl(imgServiceUrl);
		vo.setInsertUserNo(request.getUser_no());
		vo.setUpdateUserNo(request.getUser_no());

		// 원격 이미지 로컬 저장
		saveImg(vo);
        
        // 썸네일 생성
        saveThumbnailImg(vo);
	
        // 이미지스크랩 데이터 등록
        insertImgScrap(vo);	
	}
	
	/**
	 * 원격 이미지 로컬 파일 저장
	 * @param vo
	 * @throws Exception
	 */
	public void saveImg(ImgScrapVo vo) throws Exception{
		URL url = null;
        InputStream in = null;
        OutputStream out = null;
        //String imgFileName = vo.getImgOrgFileName();
        String imgFileExt = vo.getImgFileExt();
        String imgSavePathFull = null;
        try {
        	
        	// 원본 이미지 저장 폴더 생성
    		ImageUtil.mkdir(imgSavePath + imgSavePathOrg);
    		
        	url = new URL(vo.getOrgImgUrl());
            in = url.openStream();
            
            
            imgSavePathFull = imgSavePath + imgSavePathOrg + File.separator + vo.getImgSaveFileName() + "." + imgFileExt;
            
            log.debug("imgSavePathFull : "+imgSavePathFull);
            out = new FileOutputStream(imgSavePathFull); //저장경로
            
            log.debug(url.getFile());
 
            while(true){
                //이미지 read
                int data = in.read();
                if(data == -1){
                    break;
                }
                //이미지 write
                out.write(data);
            }
 
            in.close();
            out.close();
            
            log.debug("이미지 생성 완료");
 
        } catch (Exception e) {
        	log.debug("이미지 생성 오류");
            e.printStackTrace();
        	throw new BizException("이미지 생성 오류");
 
        }finally{
        	try {
        		if(in != null){
        			in.close();
        		}
        		if(out != null){
        			out.close();
        		}
        	} catch(Exception e) {
        		//e.printStackTrace();
        		throw new BizException("이미지 생성 오류");
        	}
        }
	}
	
	/**
	 * 썸네일 이미지 저장
	 * @param vo
	 * @return
	 */
	public void saveThumbnailImg(ImgScrapVo vo) throws Exception{
		// 썸네일 이미지 저장 폴더 생성
		ImageUtil.mkdir(imgSavePath + imgSavePathThumbnail);
		log.debug("ext : "+vo.getImgFileExt());
		String imgSavePathFull = imgSavePath + imgSavePathOrg + File.separator + vo.getImgSaveFileName() + "." + vo.getImgFileExt();
		
		File file = new File(imgSavePathFull);
		
		String thumbnailFileName = imgSaveThumnailPrefix + "_" + vo.getImgSaveFileName();
		
		String thumbnailPath = imgSavePath + imgSavePathThumbnail + File.separator + thumbnailFileName + "." + vo.getImgFileExt(); // 썸네일저장 경로
		File thumbnailFile = new File(thumbnailPath);
		
		double ratio = 2; // 이미지 축소 비율
		
		try {
			BufferedImage img = ImageIO.read(file); // 원본이미지
			int tWidth = (int) (img.getWidth() / ratio); // 생성할 썸네일이미지의 너비
			int tHeight = (int) (img.getHeight() / ratio); // 생성할 썸네일이미지의 높이
			
			BufferedImage tImage = new BufferedImage(tWidth, tHeight, BufferedImage.TYPE_3BYTE_BGR); // 썸네일이미지
			Graphics2D graphic = tImage.createGraphics();
			Image image = img.getScaledInstance(tWidth, tHeight, Image.SCALE_SMOOTH);
			graphic.drawImage(image, 0, 0, tWidth, tHeight, null);
			graphic.dispose(); // 리소스를 모두 해제
			
			ImageIO.write(tImage, vo.getImgFileExt(), thumbnailFile);
			
			vo.setThumbnailFileName(thumbnailFileName);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("썸네일이미지 생성 오류");
		}
	}
	
	/**
	 * 이미지스크랩 데이터 등록
	 * @param vo
	 * @throws Exception
	 */
	public void insertImgScrap(ImgScrapVo vo) throws Exception{
		// mybatis
		//dao.insertImgScrap(vo);
		
		// jpa
		TbImgScrapInfoVo saveVo = new TbImgScrapInfoVo();
		BeanUtils.copyProperties(vo, saveVo);

		saveVo.setDelYn("N");
		saveVo.setInsertDt(new Date());
		saveVo.setUpdateDt(new Date());
		imgScrapRepository.save(saveVo);
		
	}

	/**
	 * 이미지스크랩 목록 조회 총건수
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int selectImgScrapListTotCnt(Map<String, Object> paramMap) throws Exception {
		return dao.selectImgScrapListTotCnt(paramMap);
	}
	
	/**
	 * 이미지스크랩 목록 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public List<ImgScrapInfoListVo> selectImgScrapList(Map<String, Object> paramMap) throws Exception {
		return dao.selectImgScrapList(paramMap);
	}

	/**
	 * 이미지스크랩 상세조회
	 * @param scrapNo
	 * @return
	 * @throws Exception
	 */
	@Cacheable(value=Code.CACHE_IMG_SCRAP, key="#scrapNo", unless="#result == null")
	public ImgScrapInfoDetailVo selectImgScrapDetail(long scrapNo) throws Exception {
		return dao.selectImgScrapDetail(scrapNo);
	}
	
	/**
	 * 이미지스크랩 데이터 수정
	 * @param vo
	 * @throws Exception
	 */
	@CacheEvict(value=Code.CACHE_IMG_SCRAP, key="#vo.scrapNo")
	public void modifyImgScrap(ImgScrapVo vo) throws Exception{
		// mybatis
		//dao.modifyImgScrap(vo);

		// jpa
		TbImgScrapInfoVo saveVo = new TbImgScrapInfoVo();
		BeanUtils.copyProperties(vo, saveVo);

		saveVo.setDelYn("N");
		saveVo.setUpdateDt(new Date());
		imgScrapRepository.save(saveVo);
	}
	
	/**
	 * 이미지스크랩 조회수 증가
	 * @param vo
	 * @throws Exception
	 */
	public void modifyImgScrapHits(ImgScrapVo vo) throws Exception{
		dao.modifyImgScrapHits(vo);
	}

	/**
	 * 이미지스크랩 데이터 삭제
	 * @param vo
	 * @throws Exception
	 */
	@CacheEvict(value=Code.CACHE_IMG_SCRAP, key="#vo.scrapNo")
	public void removeImgScrap(ImgScrapVo vo) throws Exception{
		dao.removeImgScrap(vo);
	}
}
