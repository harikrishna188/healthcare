package com.healthcare.request;

public class SearchUserRequest {
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String contactNumber;
	private int limit;
	private int page;
	private String orderBy;
	private String orderDirection;
	private static final String DEFAULT_SORT = "Desc";
	private static final int DEFAULT_LIMIT = 10;
	private static final int DEFAULT_PAGE = 1;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public int getLimit() {
		if(limit==0) {
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
			orderBy="user_id";
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
		return "SearchUserRequest [firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName
				+ ", email=" + email + ", contactNumber=" + contactNumber + ", limit=" + limit + ", page=" + page
				+ ", orderBy=" + orderBy + ", orderDirection=" + orderDirection + "]";
	}

}
