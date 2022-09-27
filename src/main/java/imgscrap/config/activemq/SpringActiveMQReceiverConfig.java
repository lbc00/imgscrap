package imgscrap.config.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import imgscrap.service.ImgScrapService;
import imgscrap.util.Code;
import imgscrap.vo.ImgScrapVo;

@Component
public class SpringActiveMQReceiverConfig {
	final static Logger logger = LoggerFactory.getLogger(SpringActiveMQReceiverConfig.class);
	
	@Autowired
	private ImgScrapService imgSerivce;
	
	// 조회수 증가 메시지 처리
	@JmsListener(destination = Code.ACTIVEMQ_HITS)
	public void hitsmessage(String message) {
		try {
			logger.debug("Message received from activemq queue hits : " + message);
			
			ObjectMapper mapper = new ObjectMapper();
			ImgScrapVo vo = mapper.readValue(message, new TypeReference<ImgScrapVo>(){});
			imgSerivce.modifyImgScrapHits(vo);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
