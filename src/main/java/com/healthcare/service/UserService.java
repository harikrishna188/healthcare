package com.healthcare.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

@Service
public interface UserService {

	UserLoginResponse userLogin(UserLoginRequest userRequest);

	UserSignupResponse userSignup(UserSignupRequest userRequest);

	PasswordResponse forgotUserPassword(String email);

	String userPasswordChangeSave(UpdatePassWordRequest updatePassWordRequest);

	UserDetails loadUserByUsername(String username);

	UserSearchResponse searchAllUsers(SearchUserRequest searchUserRequest);

	User addUserDetails(UserRequest userRequest);

	Boolean deleteUserByUserId(Long userId);

	String getByEmail(String email);

}