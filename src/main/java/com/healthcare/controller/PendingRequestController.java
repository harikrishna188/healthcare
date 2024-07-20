package com.healthcare.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.constant.ServiceConstants;
import com.healthcare.request.PendingRequest;
import com.healthcare.service.PendingRequestService;
import com.healthcare.utility.CommonUtil;

@RestController
@CrossOrigin
@RequestMapping("api/v1/pending-req")
public class PendingRequestController {

	private Logger LOGGER = LoggerFactory.getLogger(PendingRequestController.class);

	@Autowired
	PendingRequestService pendingReuestService;

	// get pending requests
	@GetMapping("pendingrequests")
	public List<PendingRequest> getAllPendingRequests() {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL,
				CommonUtil.getString("api/v1/pending-req/pendingrequests")));
		List<PendingRequest> response = null;
		try {
			response = pendingReuestService.getAllPendingRequests();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

	// Approved pending requests
	@GetMapping("giveapproval/{userId}")
	public String giveUserApproval(@PathVariable Long userId) {
		LOGGER.info(
				String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/pending-req/giveapproval")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(userId)));
		String response = null;
		try {
			response = pendingReuestService.giveUserApproval(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}

	// Reject pending requests
	@RequestMapping("rejectapproval/{userId}")
	public String rejectUserApproval(@PathVariable Long userId) {
		LOGGER.info(
				String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/pending-req/rejectapproval")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(userId)));
		String response = null;
		try {
			response = pendingReuestService.rejectUserApproval(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

}
