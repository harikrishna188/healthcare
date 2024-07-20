package com.healthcare.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.constant.ServiceConstants;
import com.healthcare.domain.User;
import com.healthcare.repository.UserRepository;
import com.healthcare.request.PendingRequest;
import com.healthcare.service.PendingRequestService;
@Service
public class PendingReuestServiceImpl implements PendingRequestService {

	private Logger LOGGER = LoggerFactory.getLogger(PendingReuestServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Override
	public List<PendingRequest> getAllPendingRequests() {

		LOGGER.info("Entry :: PendingReuestServiceImpl :: getAllPendingRequests():");
		List<PendingRequest> allPendingRequests = userRepository.getAllPendingRequests();
		LOGGER.info("Exit :: PendingReuestServiceImpl :: getAllPendingRequests():" + allPendingRequests);

		return allPendingRequests;
	}

	@Override
	public String giveUserApproval(Long userId) {
		LOGGER.info("Entry :: PendingReuestServiceImpl :: getAllPendingRequests():" + userId);
		Optional<User> optional = userRepository.findByUserId(userId);
		String response=null;
		if (optional.isPresent()) {
			User user=optional.get();
			user.setApproved(true);
			user.setUpdatedBy("Pending work");
			userRepository.save(user);
			response=ServiceConstants.USER_APPROVED;
		}
		LOGGER.info("Exit :: PendingReuestServiceImpl :: getAllPendingRequests():" + response);
		return response;
	}

	@Override
	public String rejectUserApproval(Long userId) {
		LOGGER.info("Entry :: PendingReuestServiceImpl :: getAllPendingRequests():" + userId);
		Optional<User> optional = userRepository.findByUserId(userId);
		String response=null;
		if (optional.isPresent()) {
			userRepository.deleteById(userId);
			response=ServiceConstants.USER_REJECTED;
		}
		return response;
	}

}
