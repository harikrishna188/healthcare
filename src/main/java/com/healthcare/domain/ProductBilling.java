package com.healthcare.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_billing")
public class ProductBilling extends AuditEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_billing_id")
	private Long productBillingId;

	@Column(name = "product_bill_id")
	private Long productBillId;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "quantity")
	private Long quantity;

	@Column(name = "price")
	private Double price;

	public Long getProductBillingId() {
		return productBillingId;
	}

	public void setProductBillingId(Long productBillingId) {
		this.productBillingId = productBillingId;
	}

	public Long getProductBillId() {
		return productBillId;
	}

	public void setProductBillId(Long productBillId) {
		this.productBillId = productBillId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductBilling [productBillingId=" + productBillingId + ", productBillId=" + productBillId
				+ ", productName=" + productName + ", quantity=" + quantity + ", price=" + price + "]";
	}

}
