package imgscrap.exception;

public class BizException extends Exception {
	static final long serialVersionUID = -1287516993124229948L;
	
	private String result = null;
	private String message = null;
	
	public BizException(String message) {
		super(message);
		this.message = message;
	}
	
	public BizException(String result, String message) {
		super(message);
		this.result = result;
		this.message = message;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}
