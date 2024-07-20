package com.healthcare.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.domain.ProductBillingId;
import com.healthcare.domain.ProductStock;
import com.healthcare.repository.ProductBillingIdRepository;
import com.healthcare.repository.ProductBillingRepository;
import com.healthcare.repository.ProductStockRepository;
import com.healthcare.request.BillingDataRequest;
import com.healthcare.service.BillingService;

@Service
public class BillingServiceImpl implements BillingService {

	private Logger LOGGER = LoggerFactory.getLogger(BillingServiceImpl.class);

	@Autowired
	ProductBillingIdRepository productBillingIdRepository;

	@Autowired
	ProductBillingRepository productBillingRepository;

	@Autowired
	ProductStockRepository productStockRepository;

	@Override
	public Long getProductSale(Long value) {
		LOGGER.info("Entry :: BillingServiceImpl :: getProductSale():" + value);
		Long totalSale = 0L;

		if (value == 1) {
			totalSale = productBillingIdRepository.showTodaysSale();
		} else if (value == 7) {
			totalSale = productBillingIdRepository.showWeekSale();
		} else if (value == 30) {
			totalSale = productBillingIdRepository.showMonthSale();
		}
		LOGGER.info("Exit :: BillingServiceImpl :: getProductSale():" + totalSale);
		return totalSale;
	}

	@Override
	public Long submitBillingDetails(BillingDataRequest billingDataRequest) {
		LOGGER.info("Entry :: BillingServiceImpl :: submitBillingDetails():" + billingDataRequest);
		Long response = 0L;
		try {
			ProductBillingId productBillingId = new ProductBillingId();
			BeanUtils.copyProperties(billingDataRequest.getUserData(), productBillingId);
			productBillingId = productBillingIdRepository.save(productBillingId);
			Long productBillId = productBillingId.getProductBillId();
			List<ProductStock> itemList = billingDataRequest.getItemData();
			for (ProductStock item : itemList) {
				productBillingRepository.insertBillingItem(productBillId, item.getProductName(), item.getQuantity(),
						item.getPrice());
				productStockRepository.minusItemsFromStocks(item.getQuantity(), item.getProductName());
			}
			response = 1L;
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit :: BillingServiceImpl :: submitBillingDetails():" + response);
		return response;
	}

	@Override
	public Double getProductPrice(String productName) {
		LOGGER.info("Entry :: BillingServiceImpl :: getProductPrice():" + productName);
		Double response = 0d;
		Optional<ProductStock> optional = productStockRepository.findByProductName(productName);
		if (optional.isPresent()) {
			response = optional.get().getPrice();
		}
		LOGGER.info("Exit :: BillingServiceImpl :: getProductPrice():" + response);
		return response;
	}

}
