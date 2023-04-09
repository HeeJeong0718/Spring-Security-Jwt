package com.example.HomeJwt.response;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ResponseService {
    
	  //단일건 결과 처리 메서드
	public <T> SingleResult<T> getSingleResult(T data){
		SingleResult<T> result = new SingleResult<T>();
		result.setData(data);
		setSucessResult(result);
		return result;
	}
	//복수건 결과처리
	public <T> ListResult<T> getListResult(List<T> list){
		ListResult<T> result = new ListResult<T>();
		result.setList(list);
	    setSucessResult(result);
	    return result;
	}
	//성공결과만처리
	public CommonResult getSuccessResult() {
		CommonResult result  = new CommonResult();
		setSucessResult(result);
		return result;
	}
	//실패결과만처리
	public CommonResult getFailResult() {
		CommonResult result = new CommonResult();
		setFailResult(result);
		return result;
	}
	
	//API요청 성공 시 응답모델을 성공 데이터로 셋팅
	public void setSucessResult(CommonResult result) {
		result.setSuccess(true);
		result.setCode(CommonResponse.SUCCESS.getCode());
		result.setMsg(CommonResponse.SUCCESS.getMsg());
	}
	//API요청 성공 시 응답모델을 실패 데이터로 셋팅
	public void setFailResult(CommonResult result) {
		result.setSuccess(false);
		result.setCode(CommonResponse.FAIL.getCode());
		result.setMsg(CommonResponse.FAIL.getMsg());
	}
}
