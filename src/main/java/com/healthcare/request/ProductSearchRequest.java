package com.healthcare.request;

public class ProductSearchRequest {
	
	private String productName;
	private String companyName;
	private String createdDate;
	private String createdBy;;
	private int limit;
	private int page;
	private String orderBy;
	private String orderDirection;
	private static final String DEFAULT_SORT = "Desc";
	private static final int DEFAULT_LIMIT = 10;
	private static final int DEFAULT_PAGE = 1;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public int getLimit() {
		if(limit==0 ) {
			limit=DEFAULT_LIMIT;
		}
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getPage() {
		if(page==0) {
			page=DEFAULT_PAGE;
		}
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getOrderBy() {
		if(orderBy==null || orderBy.isEmpty()) {
			orderBy="product_id";
		}
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getOrderDirection() {
		if(orderDirection==null || orderDirection.isEmpty()) {
			orderDirection=DEFAULT_SORT;
		}
		return orderDirection;
	}
	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}
	public static String getDefaultSort() {
		return DEFAULT_SORT;
	}
	public static int getDefaultLimit() {
		return DEFAULT_LIMIT;
	}
	public static int getDefaultPage() {
		return DEFAULT_PAGE;
	}
	@Override
	public String toString() {
		return "ProductSearchRequest [productName=" + productName + ", companyName=" + companyName + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy + ", limit=" + limit + ", page=" + page + ", orderBy="
				+ orderBy + ", orderDirection=" + orderDirection + "]";
	}
	
}
