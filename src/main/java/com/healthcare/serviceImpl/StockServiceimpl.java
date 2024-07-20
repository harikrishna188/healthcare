package com.healthcare.serviceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.healthcare.constant.ServiceConstants;
import com.healthcare.domain.ProductStock;
import com.healthcare.repository.ProductStockRepository;
import com.healthcare.request.ProductSearchRequest;
import com.healthcare.request.ProductStockRequest;
import com.healthcare.response.ProductSearchDto;
import com.healthcare.response.ProductSearchResponse;
import com.healthcare.service.StockService;

@Service
public class StockServiceimpl implements StockService {

	private Logger LOGGER = LoggerFactory.getLogger(StockServiceimpl.class);

	@Autowired
	ProductStockRepository productStockRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	String getDetailQuery="select * from txn_product_stock tps ";
	String getCount="select count(*) from txn_product_stock tps";

		@Override
		public ProductSearchResponse getAllProductStock(ProductSearchRequest productSearchRequest) {
			LOGGER.info("Entry :: StockServiceimpl :: getAllProductStock():" +productSearchRequest);
			String queryResponse="";
			String countResponse="";
			String offSetQuery="";
			StringBuilder stringBuilder=new StringBuilder();
			ProductSearchResponse productSearchResponse=new ProductSearchResponse();
			
			if(productSearchRequest.getProductName()!=null && !productSearchRequest.getProductName().isEmpty())
			{
				stringBuilder.append(" tps.product_name like ");
				stringBuilder.append("'%" + productSearchRequest.getProductName() +"%'");
				stringBuilder.append(" And");
			
			}
			if(productSearchRequest.getCompanyName()!=null && !productSearchRequest.getCompanyName().isEmpty())
			{
				stringBuilder.append(" tps.company_name like ");
				stringBuilder.append("'%" + productSearchRequest.getCompanyName() +"%'");
				stringBuilder.append(" And");
			}
			
			if(productSearchRequest.getCreatedBy()!=null && !productSearchRequest.getCreatedBy().isEmpty())
			{
				stringBuilder.append(" tps.created_by like ");
				stringBuilder.append("'%" + productSearchRequest.getCreatedBy() +"%'");
				stringBuilder.append(" And");			
			}
			if(productSearchRequest.getCreatedDate()!=null && !productSearchRequest.getCreatedDate().isEmpty())
			{
				stringBuilder.append(" to_char(tps.created_date,'yyyy-mm-dd') like ");
				stringBuilder.append("'%" + productSearchRequest.getCreatedDate() +"%'");
				stringBuilder.append(" And");
			}
			
			int pagecount = productSearchRequest.getPage() - 1;
			int offset = pagecount * productSearchRequest.getLimit();
			
			if (!productSearchRequest.getOrderBy().isEmpty() && 
				 !productSearchRequest.getOrderDirection().isEmpty())
			{
				if (productSearchRequest.getOrderBy().equalsIgnoreCase("productName"))
				{
					offSetQuery = " ORDER BY tps.product_name " + productSearchRequest.getOrderDirection() + " LIMIT "
							+ offset + " , " + productSearchRequest.getLimit() ;
				} 
				else if (productSearchRequest.getOrderBy().equalsIgnoreCase("companyName"))
				{
					offSetQuery = " ORDER BY tps.company_name " + productSearchRequest.getOrderDirection() + " LIMIT " + offset
							+ " , " + productSearchRequest.getLimit() ;
				} 
				
				else if (productSearchRequest.getOrderBy().equalsIgnoreCase("createdBy")) 
				{
					offSetQuery = " ORDER BY tps.created_by " + productSearchRequest.getOrderDirection() + " LIMIT " + offset
							+ " , " + productSearchRequest.getLimit() ;
				} 
				else if (productSearchRequest.getOrderBy().equalsIgnoreCase("createdDate"))
				{
					offSetQuery = " ORDER BY tps.created_date " + productSearchRequest.getOrderDirection() + " LIMIT "
							+ offset + " , " + productSearchRequest.getLimit() ;
				}
				else {
					offSetQuery = " order by tps.product_id asc LIMIT " + offset + " , " + productSearchRequest.getLimit();
				}
				
			} 
			if (!stringBuilder.isEmpty()) {
				stringBuilder.replace(stringBuilder.length() - 3, stringBuilder.length(), "");
				queryResponse = getDetailQuery + " where " + stringBuilder + " AND is_active = 'Y' " + " " + offSetQuery;
			} else {
				queryResponse = getDetailQuery + " where is_active = 'Y' " + " " + offSetQuery;
			}

			if (!stringBuilder.isEmpty()) {
				countResponse = getCount + " where " + stringBuilder + " AND is_active = 'Y' ";
			} else {
				countResponse = getCount + " where is_active = 'Y' ";
			}
			
			List<ProductSearchDto> ProductSearchResponses = jdbcTemplate.query(queryResponse,
					(rs, rowNum) -> productMapFields(rs));
			int totalCount = jdbcTemplate.queryForObject(countResponse.toString(), Integer.class);
			productSearchResponse.setCount(Long.valueOf(totalCount));
			productSearchResponse.setLimit(productSearchRequest.getLimit());
			productSearchResponse.setPage(productSearchRequest.getPage());		
			productSearchResponse.setResultObject(ProductSearchResponses);
			LOGGER.info("Exit :: StockServiceimpl :: getAllProductStock():" + productSearchResponse);
			return productSearchResponse;
		}

	private ProductSearchDto productMapFields(ResultSet rs) throws SQLException {
		ProductSearchDto product=new ProductSearchDto();
		product.setProductId(rs.getLong("product_id"));
		product.setProductName(rs.getString("product_name"));
		product.setCompanyName(rs.getString("company_name"));
		product.setQuantity(rs.getLong("quantity"));
		product.setPrice(rs.getDouble("price"));
		product.setCreatedBy(rs.getString("created_by"));
		product.setCreatedDate(rs.getDate("created_date"));
		product.setUpdatedBy(rs.getString("updated_by"));
		product.setUpdatedDate(rs.getDate("updated_date"));
			return product;
		}

	@Override
	public String addProductStock(ProductStockRequest productStockRequest) {
		LOGGER.info("Entry :: StockServiceimpl :: addProductStock():" + productStockRequest);
		ProductStock stock = new ProductStock();

		Optional<ProductStock> optional = productStockRepository.getExistingStock(productStockRequest.getProductName());
		if (optional.isPresent()) {
			stock = optional.get();
			stock.setQuantity(stock.getQuantity() + productStockRequest.getQuantity());
			productStockRepository.save(stock);
			LOGGER.info("Exit :: StockServiceimpl :: addProductStock():" + ServiceConstants.STOCK_UPDATED);
			return ServiceConstants.STOCK_UPDATED;
		} else {
			BeanUtils.copyProperties(productStockRequest, stock);
			productStockRepository.save(stock);
			LOGGER.info("Exit :: StockServiceimpl :: addProductStock():" + ServiceConstants.STOCK_ADDED);
			return ServiceConstants.STOCK_ADDED;
		}
	}

	@Override
	public String editProductStock(ProductStockRequest productStockRequest) {
		LOGGER.info("Entry :: StockServiceimpl :: editProductStock():" + productStockRequest);
		Optional<ProductStock> optional = productStockRepository
				.findByProductName(productStockRequest.getProductName());

		ProductStock stock = optional.get();
		BeanUtils.copyProperties(productStockRequest, stock);
		productStockRepository.save(stock);
		LOGGER.info("Exit :: StockServiceimpl :: editProductStock():" + ServiceConstants.STOCK_EDITED);
		return ServiceConstants.STOCK_EDITED;

	}

	@Override
	public String deleteProductStock(Long productId) {
		LOGGER.info("Entry :: StockServiceimpl :: deleteProductStock():" + productId);
		Optional<ProductStock> optional = productStockRepository.findByProductId(productId);
		String response = null;
		if (optional.isPresent()) {
			ProductStock stock = optional.get();
			productStockRepository.deleteById(stock.getProductId());
			response = ServiceConstants.STOCK_DELETED;
		} else {
			response = ServiceConstants.SOME_THING_WENT_WRONG;
		}

		LOGGER.info("Exit :: StockServiceimpl :: deleteProductStock():" + response);
		return response;

	}
}
