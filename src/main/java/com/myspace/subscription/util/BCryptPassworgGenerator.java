package com.myspace.subscription.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPassworgGenerator {

	public static void main(String[] args) {

		int i = 0;
		while (i < 5) {
			String password = "password";
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);
			System.out.println(hashedPassword);
			i++;
		}

	}

}
