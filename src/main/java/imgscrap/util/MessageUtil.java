package imgscrap.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Component
public class MessageUtil {
	final static Logger logger = LoggerFactory.getLogger(MessageUtil.class);
	
	@Autowired
	private MessageSource msg;
	
	public String getErrorMessages(BindingResult result) {
		String returnMsg = null;
		
		try {
			StringBuilder sb = new StringBuilder();
			for(ObjectError error : result.getAllErrors()) {
				if(error instanceof FieldError) {
					FieldError fe = (FieldError)error;
					sb.append(fe.getField());
					sb.append(" : ");
					sb.append(fe.getDefaultMessage());
					sb.append(",");
				}
			}
			//sb.setLength(sb.length()-1);
			sb.deleteCharAt(sb.length()-1);
			returnMsg = sb.toString();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return returnMsg;
	}
	
	public String getMessage(String msgId, String... params) {
		String returnMsg = null;
		try {
			returnMsg = msg.getMessage(msgId, params, LocaleContextHolder.getLocale());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return returnMsg;
	}
	
	
}
