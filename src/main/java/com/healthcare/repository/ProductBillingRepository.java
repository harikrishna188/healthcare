package com.healthcare.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.healthcare.domain.ProductBilling;

@Repository
public interface ProductBillingRepository extends JpaRepository<ProductBilling, Long> {
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO ProductBilling (productName, quantity,price,productBillId) VALUES "
			+ "( :productName, :quantity, :price,:productBillId);",nativeQuery = true)
	void insertBillingItem(@Param ("productBillId") Long billid,
			@Param("productName") String name,@Param("quantity") Long quantity,@Param("price") Double price);


}
