package imgscrap.vo;

import java.util.ArrayList;
import java.util.List;

public class ImgScrapListResponseVo extends BaseResponseVo {
	
	private int tot_cnt = 0;
	private int page = 1;
	private int total_page = 0;
	
	private List<ImgScrapInfoListVo> data_list = new ArrayList<ImgScrapInfoListVo>();
	
	public int getTot_cnt() {
		return tot_cnt;
	}
	public void setTot_cnt(int tot_cnt) {
		this.tot_cnt = tot_cnt;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal_page() {
		return total_page;
	}
	public void setTotal_page(int total_page) {
		this.total_page = total_page;
	}
	public List<ImgScrapInfoListVo> getData_list() {
		return data_list;
	}
	public void setData_list(List<ImgScrapInfoListVo> data_list) {
		this.data_list = data_list;
	}
	
}
