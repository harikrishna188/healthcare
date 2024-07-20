package com.healthcare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.healthcare.domain.User;
import com.healthcare.request.PendingRequest;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUserName(String userName);
	
	@Query(value = "select user_id as userId, full_name as fullName,user_name as userName, mode as mode from txn_user where approved=FALSE",nativeQuery = true)
	List<PendingRequest> getAllPendingRequests();

	Optional<User> findByUserId(Long userId);

	Optional<User> findByEmail(String email);
	
//	@Query(value = "select *  from txn_user where email=?1",nativeQuery = true)
//	Optional<User> getByEmail(String email);
}
