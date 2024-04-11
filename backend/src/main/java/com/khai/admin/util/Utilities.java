package com.khai.admin.util;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khai.admin.entity.User;
import jakarta.servlet.ServletRequest;
import net.htmlparser.jericho.*;
import org.springframework.web.multipart.MultipartFile;

public class Utilities {
	
	// Lấy dữ liệu kiểu Byte từ Para
	public static byte getByteParam(ServletRequest request, String name) {
		byte value = -1;
		
		String str_value = request.getParameter(name);
		
		if (str_value!=null && !str_value.equalsIgnoreCase("")) {
			value = Byte.parseByte(str_value);
		}
		return value;
	}
	
	public static short getShortParam(ServletRequest request, String name) {
		short value = -1;
		
		String str_value = request.getParameter(name);
		
		if (str_value!=null && !str_value.equalsIgnoreCase("")) {
			value = Short.parseShort(str_value);
		}
		return value;
	}
	
	public static int getIntParam(ServletRequest request, String name) {
		int value = -1;
		
		String str_value = request.getParameter(name);
		
		if (str_value!=null && !str_value.equalsIgnoreCase("")) {
			value = Integer.parseInt(str_value);
		}
		return value;
	}
	
	public static HashMap<Integer, Integer> getIntIntMapParam(ServletRequest request, String name) {
		
		String str_value = request.getParameter(name);		
		HashMap<Integer, Integer> map = new HashMap<>();
		
		if (str_value!=null && !str_value.equalsIgnoreCase("")) {
	        String[] pairs = str_value.split(";");
	        for (String pair : pairs) {
	            String[] keyValue = pair.split(",");        
	            int key = Integer.parseInt(keyValue[0]);
	            int value = Integer.parseInt(keyValue[1]);
	            map.put(key, value);
	        }	
		}

		return map;
	}
	
	// Lấy dữ liệu kiểu Boolean từ Para
	public static boolean getBooleanParam(ServletRequest request, String name) {
		boolean value = false;
		
		String str_value = request.getParameter(name);
		
		if (str_value!=null && !str_value.equalsIgnoreCase("")) {
			value = Boolean.parseBoolean(str_value);
		}
		return value;
	}
	
	public static String encode(String str_unicode) {
		return CharacterReference.encode(str_unicode);
	}
	
	public static String decode(String str_html) {
		return CharacterReference.decode(str_html);
	}

	public static <T> List<T> convertJSONToEntity(MultipartFile file) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<T> entities = objectMapper.readValue(file.getInputStream(), new TypeReference<List<T>>() {});
//		List<User> entities = objectMapper.readValue(file.getInputStream(), new TypeReference<List<User>>() {});
		return entities;
	}
}
