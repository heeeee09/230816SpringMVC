package com.aug.spring.notice.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

public class sample {
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHmmss"); // 나중에 SS랑 비교
		String result = sdf.format(new Date(System.currentTimeMillis()));
		System.out.println(result);
	}
}
