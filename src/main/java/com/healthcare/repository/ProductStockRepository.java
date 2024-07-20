package com.healthcare.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.healthcare.domain.ProductStock;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
	
	@Transactional
	@Modifying
	@Query(value = "update medicare_database.txn_product_stock set quantity=(quantity-?1) where product_name =?2",nativeQuery = true)
	void minusItemsFromStocks(Long quantity,String name);
	
	@Query(value = "select * from txn_product_stock where product_name=?1",nativeQuery = true)
	Optional<ProductStock> getExistingStock(String productName );

	@Query(value = "select * from txn_product_stock order by product_name",nativeQuery = true)
	List<ProductStock> getAllProductStocks();

	@Query(value = "select price from txn_product_stock where product_name=?1",nativeQuery = true)
	Double getprice(String productName);

	Optional<ProductStock> findByProductName(String productName);

	Optional<ProductStock> findByProductId(Long productId);

}
