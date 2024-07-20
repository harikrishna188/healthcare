package com.healthcare.service;

import org.springframework.stereotype.Service;

import com.healthcare.request.ProductSearchRequest;
import com.healthcare.request.ProductStockRequest;
import com.healthcare.response.ProductSearchResponse;

@Service
public interface StockService {

	ProductSearchResponse getAllProductStock(ProductSearchRequest productSearchRequest);

	String addProductStock(ProductStockRequest productStockRequest);

	String editProductStock(ProductStockRequest productStockRequest);

	String deleteProductStock(Long productId);

}
