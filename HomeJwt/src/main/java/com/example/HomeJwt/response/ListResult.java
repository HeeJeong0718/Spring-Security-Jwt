package com.example.HomeJwt.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListResult<T> extends CommonResult {
     //복수건 처리 
	private List<T> list;
}
