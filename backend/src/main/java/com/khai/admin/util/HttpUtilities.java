package com.khai.admin.util;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khai.admin.constants.HeaderSecurity;
import com.khai.admin.dto.Product.components.Screen;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import net.htmlparser.jericho.*;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class HttpUtilities {
	
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

	public static String getTokenFromRequest(HttpServletRequest request){
		String bearerToken = request.getHeader("Authorization");

		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
			return bearerToken.substring(7);
		}

		return null;
	}

	public static UUID getClientId(HttpServletRequest request) {
		String userId = request.getHeader(HeaderSecurity.CLIENT_ID.getValue());
		if(StringUtils.hasText(userId)) {
			return UUID.fromString(userId);
		}
		return null;
	}

	public static String getRefreshToken(HttpServletRequest request) {
		String refreshToken = request.getParameter(HeaderSecurity.REFRESH_TOKEN.getValue());
		return refreshToken;
	}

	public static UUID getClientId(Map<String, Object> header) {
		String userId = String.valueOf(header.get(HeaderSecurity.CLIENT_ID.getValue()));
		if(StringUtils.hasText(userId)) {
			return UUID.fromString(userId);
		}
		return null;
	}

	public static String getRefreshToken(Map<String, Object> header) {
		String refreshToken = String.valueOf(header.get(HeaderSecurity.REFRESH_TOKEN.getValue()));
		return refreshToken;
	}

	public static Map<String, Object> convertJsonToMap(String jsonData) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonData, Map.class);
	}

	public static Map<String, Map<String, String>> convertJsonToMapJsonObject(String jsonData) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();

		Map<String, Map<String, String>> jsonMap = objectMapper.readValue(jsonData, new TypeReference<Map<String, Map<String, String>>>() {});
		return jsonMap;
	}
}
