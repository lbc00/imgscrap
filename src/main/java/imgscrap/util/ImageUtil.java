package imgscrap.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtil {
	final static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	
	public static void mkdir(String imgSavePath) {
		logger.debug("imgSavePath : "+imgSavePath);
		File folder = new File(imgSavePath);
		
		// 해당 디렉토리가 없을경우 디렉토리를 생성
		if (!folder.exists()) {
			folder.mkdir(); //폴더 생성
			logger.debug("폴더 생성");				
		}
	}
	
	/**
	 * 파일명 추출(확장자제외)
	 * @param orgImgUrl
	 * @return
	 */
	public static String getFileName(String orgImgUrl) {
		logger.debug("orgImgUrl : "+orgImgUrl);
		String fileName = orgImgUrl.substring(orgImgUrl.lastIndexOf('/')+1, orgImgUrl.length() );

		String fileNameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
		logger.debug("fileNameWithoutExt : "+fileNameWithoutExt);
		return fileNameWithoutExt; 
	}
	
	/**
	 * 파일 확장자 추출
	 * @param fileName
	 * @return
	 */
	public static String getFileExt(String orgImgUrl) {
		logger.debug("orgImgUrl : "+orgImgUrl);
		String fileExtn = orgImgUrl.substring(orgImgUrl.lastIndexOf('.')+1);
		logger.debug("fileExtn : "+fileExtn);
		return fileExtn; 
	}
}
