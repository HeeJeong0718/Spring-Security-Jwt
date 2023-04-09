package com.example.HomeJwt.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> extends CommonResult {
   //제네릭만들기
	private T data;
}
