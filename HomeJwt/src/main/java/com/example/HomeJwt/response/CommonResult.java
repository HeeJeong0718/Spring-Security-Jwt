package com.example.HomeJwt.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {
   //api결과물을 담아낼 결과모델생성
	private boolean success;
	private int code;
	private String msg;
}
