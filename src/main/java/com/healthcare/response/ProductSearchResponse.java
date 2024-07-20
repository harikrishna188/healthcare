package com.healthcare.response;

import java.util.List;


public class ProductSearchResponse {
	
	private long count;
	private long limit;
	private long page;
	private List<ProductSearchDto> resultObject;
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public long getLimit() {
		return limit;
	}
	public void setLimit(long limit) {
		this.limit = limit;
	}
	public long getPage() {
		return page;
	}
	public void setPage(long page) {
		this.page = page;
	}
	public List<ProductSearchDto> getResultObject() {
		return resultObject;
	}
	public void setResultObject(List<ProductSearchDto> resultObject) {
		this.resultObject = resultObject;
	}
	@Override
	public String toString() {
		return "ProductSearchResponse [count=" + count + ", limit=" + limit + ", page=" + page + ", resultObject="
				+ resultObject + "]";
	}
	
}
