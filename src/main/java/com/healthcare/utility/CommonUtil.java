package com.healthcare.utility;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	public CommonUtil() 
	{
	
    }
	
	
	public static String getString(Object object) {
		String value = "";
		try {
			value = mapper.writeValueAsString(object);
		} catch (Exception e) {

		}
		return value;
	}

}
