package imgscrap.util;

public class PageInfo {

	private int page = 1;
	private int pageSize = 10;
	private int totalPageNo = 1;
	private int totalCnt = 0;
	private int offset = 0;
	
	// 생성자
	private PageInfo() {}
	// 생성자
	public PageInfo(int totalCnt, int page, int pageSize) {
		this.page = page;
		this.totalCnt = totalCnt;
		this.pageSize = pageSize;
		int totalPage = 1;
		
		totalPage = totalCnt / this.pageSize;
		// 나누어 떨어지지 않는 경우 1를 더해서 꽉 차지 않은 페이지도 보여야함
		int extraPages = totalCnt % pageSize;
		if (extraPages > 0) {
			totalPage += 1;
		}
		// 3.전체 페이지가 0인 경우 1로 하여 빈 페이지라도 표출
		if (totalCnt == 0) {
			totalPage = 1;
		}
		totalPageNo = totalPage;
		
		offset = (page -1) * pageSize;
		
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPageNo() {
		return totalPageNo;
	}

	public void setTotalPageNo(int totalPageNo) {
		this.totalPageNo = totalPageNo;
	}

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
}