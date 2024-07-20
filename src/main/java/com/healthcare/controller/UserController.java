package com.healthcare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.constant.ServiceConstants;
import com.healthcare.domain.User;
import com.healthcare.request.SearchUserRequest;
import com.healthcare.request.UpdatePassWordRequest;
import com.healthcare.request.UserLoginRequest;
import com.healthcare.request.UserRequest;
import com.healthcare.request.UserSignupRequest;
import com.healthcare.response.PasswordResponse;
import com.healthcare.response.UserLoginResponse;
import com.healthcare.response.UserSearchResponse;
import com.healthcare.response.UserSignupResponse;
import com.healthcare.service.UserService;
import com.healthcare.utility.CommonUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/user")
public class UserController {

	private Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	// signup new user
	@RequestMapping("signup")
	public UserSignupResponse signup(@RequestBody UserSignupRequest userRequest) {
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/user/signup")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(userRequest)));
		UserSignupResponse response = null;
		try {
			response = userService.userSignup(userRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

	// login window
	@PostMapping("login")
	public UserLoginResponse login(@RequestBody UserLoginRequest userRequest) {

		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/user/login")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(userRequest)));
		UserLoginResponse response = null;
		try {
			response = userService.userLogin(userRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}

	// forgot password window
	@GetMapping("forgot/password/{email}")
	public PasswordResponse forgotUserPassword(@PathVariable String email) {

		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/user/forgot/password")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(email)));
		PasswordResponse response = null;
		try {
			response = userService.forgotUserPassword(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

	// password changes save window
	@PostMapping("update/password")
	public String userPasswordChangeSave(@RequestBody UpdatePassWordRequest updatePassWordRequest) {

		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/user/update/password")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(updatePassWordRequest)));
		String response = null;
		try {
			response = userService.userPasswordChangeSave(updatePassWordRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

	// search user (add user window)
	@PostMapping("search/all")
	public UserSearchResponse searchAllUsers(@RequestBody SearchUserRequest searchUserRequest) {

		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/user/login")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(searchUserRequest)));
		UserSearchResponse response = null;
		try {
			response = userService.searchAllUsers(searchUserRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}
	
	// add user (add user window)
	@PostMapping("save")
	public String addUserDetails(@RequestBody UserRequest userRequest) {

		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/user/login")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(userRequest)));
		String response = null;
		User user = userService.addUserDetails(userRequest);
		if (user != null) {
			response = ServiceConstants.USER_ADDED_SUCCESSFULLY;
		} else {
			response = ServiceConstants.USER_NOT_ADDED;
		}

		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}
		
	// delete user (add user window)
	@DeleteMapping("delete/user{userId}")
	public String deleteUser(@PathVariable Long userId) {

		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("api/v1/user/login")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(userId)));
		String response = null;
		Boolean user = userService.deleteUserByUserId(userId);
		if (user) {
			response = ServiceConstants.USER_DELETED_SUCCESSFULLY;
		} else {
			response = ServiceConstants.USER_NOT_DELETED;
		}

		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;

	}
	
	@GetMapping("lookup/email/{email}")
	public String getByEmail(@PathVariable String email) {

		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString("lookup/email/{email}")));
		LOGGER.info(String.format(ServiceConstants.REQUEST_URL, CommonUtil.getString(email)));
		String response = null;
		try {
			 response = userService.getByEmail(email);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(String.format(ServiceConstants.RESPONSE, CommonUtil.getString(response)));
		return response;
	}

}
