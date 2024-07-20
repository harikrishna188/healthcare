package com.healthcare.request;

import java.util.List;

import com.healthcare.domain.ProductBillingId;
import com.healthcare.domain.ProductStock;

public class BillingDataRequest {

	ProductBillingId userData;
	List<ProductStock> itemData;

	public ProductBillingId getUserData() {
		return userData;
	}

	public void setUserData(ProductBillingId userData) {
		this.userData = userData;
	}

	public List<ProductStock> getItemData() {
		return itemData;
	}

	public void setItemData(List<ProductStock> itemData) {
		this.itemData = itemData;
	}

	@Override
	public String toString() {
		return "BillingDataRequest [userData=" + userData + ", itemData=" + itemData + "]";
	}

}
