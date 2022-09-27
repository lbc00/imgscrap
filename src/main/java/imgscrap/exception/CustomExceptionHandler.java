package imgscrap.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import imgscrap.vo.BaseResponseVo;

public class CustomExceptionHandler {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Object exception(Exception e) {
		BaseResponseVo response = new BaseResponseVo();
		response.setMsg(e.getMessage());
		return response;
    }
}
