package com.todaysfailbe.common.utils;

import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class EncryptUtil {
	private final String salt;

	public EncryptUtil(@Value("${encrypt.salt}") String salt) {
		Assert.notNull(salt, "salt 값은 null이 될 수 없습니다.");
		this.salt = salt;
	}

	public String encrypt(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.reset();
			md.update(salt.getBytes());
			byte[] input = md.digest(password.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < input.length; i++) {
				sb.append(Integer.toString((input[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

