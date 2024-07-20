package com.healthcare.serviceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.healthcare.constant.ServiceConstants;
import com.healthcare.domain.User;
import com.healthcare.repository.UserRepository;
import com.healthcare.request.SearchUserRequest;
import com.healthcare.request.UpdatePassWordRequest;
import com.healthcare.request.UserLoginRequest;
import com.healthcare.request.UserRequest;
import com.healthcare.request.UserSignupRequest;
import com.healthcare.response.PasswordResponse;
import com.healthcare.response.UserLoginResponse;
import com.healthcare.response.UserSearchDto;
import com.healthcare.response.UserSearchResponse;
import com.healthcare.response.UserSignupResponse;
import com.healthcare.service.UserService;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {

	private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;
	
	@Autowired(required=true)
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> user = userRepository.findByUserName(username);

		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.get().getUserName(),
				user.get().getPassword(), new ArrayList<>());
	}

	@Override
	public UserLoginResponse userLogin(UserLoginRequest userRequest) {

		LOGGER.info("Entry :: UserServiceImpl :: userLogin():" + userRequest);
		UserLoginResponse loginResponse = new UserLoginResponse();
		Optional<User> optional = userRepository.findByUserName(userRequest.getUserName());

		if (optional.isPresent()) {
			User foundUser = optional.get();
			boolean passwordEqualityCheck=bCryptPasswordEncoder.matches(userRequest.getPassword(), foundUser.getPassword());
			if (passwordEqualityCheck && foundUser.isApproved()) {
				loginResponse.setMode(foundUser.getMode());
				loginResponse.setStatus(ServiceConstants.LOGIN_SUCCESSFUL);

			} else {
				
				if (!passwordEqualityCheck) {
					loginResponse.setStatus(ServiceConstants.INVALID_PASSWORD);
				}
				if (!foundUser.isApproved()) {
					loginResponse.setStatus(ServiceConstants.WAIT_APPROVAL);
				}

			}
		} else {
			loginResponse.setStatus(ServiceConstants.INVALID_USERNAME);
		}
		LOGGER.info("Exit :: UserServiceImpl :: userLogin():" + loginResponse);
		return loginResponse;

	}

	@Override
	public UserSignupResponse userSignup(UserSignupRequest userRequest) {

		UserSignupResponse response = new UserSignupResponse();
		Optional<User> optional = userRepository.findByUserName(userRequest.getUserName());
		if (!optional.isPresent()) {
			User user = new User();
			BeanUtils.copyProperties(userRequest, user);
			user.setPassword(bcryptEncoder.encode(userRequest.getPassword()));
			user.setFullName(userRequest.getFirstName() + " " + userRequest.getLastName());
			user.setApproved(false);
			user.setIsActive("Y");
			user.setCreatedBy("SELF");
			user.setMode("USER");
			userRepository.save(user);
			response.setStatus(true);
			response.setStatusText("Signup Successful"
					  + "Please wait for approval");
		} else {
			response.setStatus(false);
			response.setStatusText("Signup Failed\nUser Already Exists");
		}
		return response;
	}

	@Override
	public PasswordResponse forgotUserPassword(String email) {
		Optional<User> optional = userRepository.findByEmail(email.toLowerCase());
		PasswordResponse response=new PasswordResponse();
		if(optional.isPresent()) {
			response.setResponse(ServiceConstants.USER_FOUND);
			response.setUser(optional.get());			
		}
		else {
			response.setResponse(ServiceConstants.USER_NOTFOUND);;
		}
		return response;
	}

	@Override
	public String userPasswordChangeSave(UpdatePassWordRequest updatePassWordRequest) {
		Optional<User> optional = userRepository.findByUserName(updatePassWordRequest.getUserName());
		String response=null;
		if(optional.isPresent()) {
			User user=new User();
			BeanUtils.copyProperties(optional.get(), user);
			user.setPassword(bcryptEncoder.encode(updatePassWordRequest.getNewPassword()));
			userRepository.save(user);
			response=ServiceConstants.USER_PASSWORD_MODIFIED;
		}else {
			response=ServiceConstants.USER_PASSWORD_NOT_MODIFIED;
		}
		return response;
	}
	
	
	String getDetailQuery="select * from txn_user tu ";
	String getCount="select count(*) from txn_user tu";

	@Override
	public UserSearchResponse searchAllUsers(SearchUserRequest searchUserRequest) {
		
		LOGGER.info("Entry :: StockServiceimpl :: getAllProductStock():" +searchUserRequest);
		String queryResponse="";
		String countResponse="";
		String offSetQuery="";
		StringBuilder stringBuilder=new StringBuilder();
		UserSearchResponse UserSearchResponse=new UserSearchResponse();
		
		if(searchUserRequest.getFirstName()!=null && !searchUserRequest.getFirstName().isEmpty())
		{
			stringBuilder.append(" tu.full_name like ");
			stringBuilder.append("'%" + searchUserRequest.getFirstName() +"%'");
			stringBuilder.append(" And");
		
		}
		if(searchUserRequest.getLastName()!=null && !searchUserRequest.getLastName().isEmpty())
		{
			stringBuilder.append(" tu.full_name like ");
			stringBuilder.append("'%" + searchUserRequest.getLastName() +"%'");
			stringBuilder.append(" And");
		}
		
		if(searchUserRequest.getUserName()!=null && !searchUserRequest.getUserName().isEmpty())
		{
			stringBuilder.append(" tu.user_name like ");
			stringBuilder.append("'%" + searchUserRequest.getUserName() +"%'");
			stringBuilder.append(" And");			
		}
		if(searchUserRequest.getEmail()!=null && !searchUserRequest.getEmail().isEmpty())
		{
			stringBuilder.append(" tu.email like ");
			stringBuilder.append("'%" + searchUserRequest.getEmail() +"%'");
			stringBuilder.append(" And");
		}
		if(searchUserRequest.getContactNumber()!=null && !searchUserRequest.getContactNumber().isEmpty())
		{
			stringBuilder.append(" tu.contact_number like ");
			stringBuilder.append("'%" + searchUserRequest.getContactNumber() +"%'");
			stringBuilder.append(" And");
		}
		
		int pagecount = searchUserRequest.getPage() - 1;
		int offset = pagecount * searchUserRequest.getLimit();
		
		if (!searchUserRequest.getOrderBy().isEmpty() && 
			 !searchUserRequest.getOrderDirection().isEmpty())
		{
			if (searchUserRequest.getOrderBy().equalsIgnoreCase("firstName"))
			{
				offSetQuery = " ORDER BY tu.full_name " + searchUserRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + searchUserRequest.getLimit() ;
			} 
			else if (searchUserRequest.getOrderBy().equalsIgnoreCase("lastName"))
			{
				offSetQuery = " ORDER BY tu.full_name " + searchUserRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + searchUserRequest.getLimit() ;
			} 
			else if (searchUserRequest.getOrderBy().equalsIgnoreCase("user_name"))
			{
				offSetQuery = " ORDER BY tu.user_name " + searchUserRequest.getOrderDirection() + " LIMIT " + offset
						+ " , " + searchUserRequest.getLimit() ;
			} 
			
			else if (searchUserRequest.getOrderBy().equalsIgnoreCase("email")) 
			{
				offSetQuery = " ORDER BY tu.email " + searchUserRequest.getOrderDirection() + " LIMIT " + offset
						+ " , " + searchUserRequest.getLimit() ;
			} 
			else if (searchUserRequest.getOrderBy().equalsIgnoreCase("contactNumber"))
			{
				offSetQuery = " ORDER BY tu.contact_number " + searchUserRequest.getOrderDirection() + " LIMIT "
						+ offset + " , " + searchUserRequest.getLimit() ;
			}
			else {
				offSetQuery = " order by tu.user_id Desc LIMIT " + offset + " , " + searchUserRequest.getLimit();
			}
			
		} 
		if (!stringBuilder.isEmpty()) {
			stringBuilder.replace(stringBuilder.length() - 3, stringBuilder.length(), "");
			queryResponse = getDetailQuery + " where " + stringBuilder + " AND is_active = 'Y' " + " " + offSetQuery;
		} else {
			queryResponse = getDetailQuery + " where is_active = 'Y' " + " " + offSetQuery;
		}

		if (!stringBuilder.isEmpty()) {
			countResponse = getCount + " where " + stringBuilder + " AND is_active = 'Y' ";
		} else {
			countResponse = getCount + " where is_active = 'Y' ";
		}
		
		List<UserSearchDto> UserSearchResponses = jdbcTemplate.query(queryResponse,
				(rs, rowNum) -> userMapFields(rs));
		int totalCount = jdbcTemplate.queryForObject(countResponse.toString(), Integer.class);
		UserSearchResponse.setCount(Long.valueOf(totalCount));
		UserSearchResponse.setLimit(searchUserRequest.getLimit());
		UserSearchResponse.setPage(searchUserRequest.getPage());		
		UserSearchResponse.setResultObject(UserSearchResponses);
		LOGGER.info("Exit :: StockServiceimpl :: getAllProductStock():" + UserSearchResponse);
		return UserSearchResponse;
	}
	
	private UserSearchDto userMapFields(ResultSet rs) throws SQLException {
		UserSearchDto user=new UserSearchDto();
		user.setUserId(rs.getLong("user_id"));
		user.setFullName(rs.getString("full_name"));
		user.setUserName(rs.getString("user_name"));
		user.setContactNumber(rs.getString("contact_number"));
		user.setEmail(rs.getString("email"));
		user.setApproved(rs.getBoolean("approved"));
		user.setMode(rs.getString("mode"));
			return user;
		}

		@Override
		public User addUserDetails(UserRequest userRequest) {
			LOGGER.info("Entry :: UserServiceImpl :: addUserDetails():" + userRequest);
			Optional<User> optional = userRepository.findByUserName(userRequest.getUserName());
			User user = new User();
			if (optional.isPresent()) {
				user = optional.get();
			}

			user.setFullName(userRequest.getFirstName() + " " + userRequest.getLastName());
			user.setUserName(userRequest.getUserName());
			if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
				user.setPassword(bcryptEncoder.encode(userRequest.getPassword()));
			}
			user.setContactNumber(userRequest.getContactNumber());
			user.setEmail(userRequest.getEmail());
			user.setMode(userRequest.getMode().toUpperCase());

			user.setApproved(true);
			user.setIsActive("Y");
			user.setCreatedBy("");
			user.setUpdatedBy("");
			userRepository.save(user);
			return user;
		}

	@Override
	public Boolean deleteUserByUserId(Long userId) {
		LOGGER.info("Entry :: UserServiceImpl :: deleteUserByUserId():" + userId);
		Optional<User> optional = userRepository.findByUserId(userId);
		Boolean response=false;
		if (optional.isPresent()) {
			userRepository.deleteById(userId);
			response=true;
		}
		return response;
	}
	
	@Override
	public String getByEmail(String email) {
		LOGGER.info("Entry :: UserServiceImpl :: getByEmail():" + email);
		Optional<User> optional = userRepository.findByEmail(email.toLowerCase());
		String message =null;
		if(optional.isPresent()) {
			message=ServiceConstants.USER_ALREADY_EXIST;			
		}
		LOGGER.info("Exit :: UserServiceImpl :: getByEmail():" + email);
		return message;
	}

}
