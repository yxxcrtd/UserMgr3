package cn.edustar.usermgr.util;

/**
 * 分页
 * 
 * @author Yang XinXin
 * @version 2.0.0, 2010-09-02 10:09:22
 */
public class Pager {
	private int totalRows;
	private int pageSize = 20;
	private int currentPage=1;
	private int startRow=0;
	private String itemName = "用户";
	private String itemUnit = "个";
	private String url_pattern = "?page={page}";
	private String firstPageUrl;
	private String prevPageUrl;
	private String nextPageUrl;
	private String lastPageUrl;

	public Pager() {
	}

	public Pager(int _totalRows) {
		totalRows = _totalRows;
		currentPage = 1;
		startRow = 0;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getTotalPages() {
		if (this.totalRows <= 0)
			return 0;
		if (this.pageSize <= 0)
			return 0;
		int i = this.totalRows / this.pageSize;
		if ((this.totalRows % this.pageSize) != 0)
			++i;
		return i;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void first() {
		currentPage = 1;
		startRow = 0;
	}

	public void previous() {
		if (currentPage == 1) {
			return;
		}
		currentPage--;
		startRow = (currentPage - 1) * pageSize;
	}

	public void next() {
		if (currentPage < getTotalPages()) {
			currentPage++;
		}
		startRow = (currentPage - 1) * pageSize;
	}

	public void last() {
		currentPage = getTotalPages();
		startRow = (currentPage - 1) * pageSize;
	}

	public void refresh(int _currentPage) {
		currentPage = _currentPage;
		if (currentPage > getTotalPages()) {
			last();
		}
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setItemNameAndUnit(String itemName, String itemUnit) {
		this.itemName = itemName;
		this.itemUnit = itemUnit;
	}

	public String getUrlPattern() {
		return url_pattern;
	}

	public void setUrlPattern(String url) {
		this.url_pattern = url;
	}

	public String getFirstPageUrl() {
		if (null == this.firstPageUrl || this.firstPageUrl.length() == 0)
			return this.internalGetPageUrl(1);
		return firstPageUrl;
	}

	public void setFirstPageUrl(String firstPageUrl) {
		this.firstPageUrl = firstPageUrl;
	}

	public String getLastPageUrl() {
		if (null == this.lastPageUrl || this.lastPageUrl.length() == 0)
			return this.internalGetPageUrl(getTotalPages());
		return lastPageUrl;
	}

	public void setLastPageUrl(String lastPageUrl) {
		this.lastPageUrl = lastPageUrl;
	}

	public String getNextPageUrl() {
		if (null == this.nextPageUrl || this.nextPageUrl.length() == 0)
			return this.internalGetPageUrl(this.currentPage + 1);
		return nextPageUrl;
	}

	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}

	public String getPrevPageUrl() {
		if (null == this.prevPageUrl || this.prevPageUrl.length() == 0)
			return this.internalGetPageUrl(this.currentPage - 1);
		return prevPageUrl;
	}

	public void setPrevPageUrl(String prevPageUrl) {
		this.prevPageUrl = prevPageUrl;
	}

	private String internalGetPageUrl(int page) {
		if (page <= 1)
			page = 1;

		if (page >= getTotalPages())
			page = getTotalPages();

		if (null == this.url_pattern)
			return "";
		return this.url_pattern.replace("{page}", String.valueOf(page));
	}

}
