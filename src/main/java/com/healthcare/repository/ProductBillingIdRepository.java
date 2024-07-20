package com.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.healthcare.domain.ProductBillingId;

@Repository
public interface ProductBillingIdRepository extends JpaRepository<ProductBillingId, Long> {
	
	@Query (value="SELECT sum(total) from billid where date_time=current_date()",
			nativeQuery = true)
	Long showTodaysSale();

	@Query (value="SELECT sum(total) from billid where date_time<=SUBDATE(CURDATE(),0)"
			+ " and date_time>=SUBDATE(CURDATE(),6);",nativeQuery = true)
	Long showWeekSale();

	@Query (value="SELECT sum(total) from billid where date_time<=SUBDATE(CURDATE(),0)"
			+ " and date_time>=SUBDATE(CURDATE(),29);",nativeQuery = true)
	Long showMonthSale();


}
