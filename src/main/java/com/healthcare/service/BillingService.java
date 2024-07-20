package com.healthcare.service;

import org.springframework.stereotype.Service;

import com.healthcare.request.BillingDataRequest;

@Service
public interface BillingService {

	Long getProductSale(Long value);

	Long submitBillingDetails(BillingDataRequest billingDataRequest);

	Double getProductPrice(String productName);

}
