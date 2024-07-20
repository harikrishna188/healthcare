package com.healthcare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.constant.ServiceConstants;
import com.healthcare.request.BillingDataRequest;
import com.healthcare.service.BillingService;
import com.healthcare.utility.CommonUtil;

@RestController
@CrossOrigin
@RequestMapping("api/v1/bill")
public class BillingController {

	private Logger LOGGER = LoggerFactory.getLogger(BillingController.class);

	@Autowired
	BillingService billingService;

	// sale
	@GetMapping("showsale{value}")
	public Long getProductSales(@PathVariable Long value) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/bill/showsale")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(value)));
		Long response = null;
		try {
			response = billingService.getProductSale(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

	// submit bill which is saved to DB
	@GetMapping("submitbill")
	public Long submitbill(@RequestBody BillingDataRequest billingDataRequest) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/bill/submitbill")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(billingDataRequest)));
		Long response = null;
		try {
			response = billingService.submitBillingDetails(billingDataRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

	@GetMapping("getprice{productName}")
	public Double getProductPrice(@PathVariable String productName) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/bill/getprice")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(productName)));
		Double response = null;
		try {
			response = billingService.getProductPrice(productName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

}
