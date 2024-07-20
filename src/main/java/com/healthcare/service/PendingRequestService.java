package com.healthcare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.healthcare.request.PendingRequest;
@Service
public interface PendingRequestService {

	List<PendingRequest> getAllPendingRequests();

	String giveUserApproval(Long userId);

	String rejectUserApproval(Long userId);

}
