package com.healthcare.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.constant.ServiceConstants;
import com.healthcare.domain.ProductStock;
import com.healthcare.request.ProductSearchRequest;
import com.healthcare.request.ProductStockRequest;
import com.healthcare.response.ProductSearchResponse;
import com.healthcare.service.StockService;
import com.healthcare.utility.CommonUtil;

@RestController
@CrossOrigin
@RequestMapping("api/v1/stock")
public class StockController {

	private Logger LOGGER = LoggerFactory.getLogger(StockController.class);

	@Autowired
	StockService stockService;

	@PostMapping("all")
	public ProductSearchResponse getAllProductStock(@RequestBody ProductSearchRequest productSearchRequest) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/stock/all")));
		
		ProductSearchResponse response = null;
		try {
			response = stockService.getAllProductStock(productSearchRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

	// add stock to existing stock
	@PostMapping("addstock")
	public String addProductStock(@RequestBody ProductStockRequest productStockRequest) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/stock/addstock")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(productStockRequest)));
		String response = null;
		try {
			response = stockService.addProductStock(productStockRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}

	// edit in stock
	@PutMapping("editstock")
	public String editProductStock(@RequestBody ProductStockRequest productStockRequest) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/stock/editstock")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(productStockRequest)));
		String response = null;
		try {
			response = stockService.editProductStock(productStockRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

	// delete product from stock
	@DeleteMapping("deletestock{productId}")
	public String deleteProductStock(@PathVariable Long productId) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/stock/deletestock")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(productId)));
		String response = null;
		try {
			response = stockService.deleteProductStock(productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

}
