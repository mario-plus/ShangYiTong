package com.gree.commonUtil.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class HttpResponseResult<T> {

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public HttpResponseResult(){}

    protected static <T> HttpResponseResult<T> build(T data) {
        HttpResponseResult<T> httpResponseResult = new HttpResponseResult<T>();
        if (data != null)
            httpResponseResult.setData(data);
        return httpResponseResult;
    }

    public static <T> HttpResponseResult<T> build(T body, ResultCodeEnum resultCodeEnum) {
        HttpResponseResult<T> httpResponseResult = build(body);
        httpResponseResult.setCode(resultCodeEnum.getCode());
        httpResponseResult.setMessage(resultCodeEnum.getMessage());
        return httpResponseResult;
    }

    public static <T> HttpResponseResult<T> build(Integer code, String message) {
        HttpResponseResult<T> httpResponseResult = build(null);
        httpResponseResult.setCode(code);
        httpResponseResult.setMessage(message);
        return httpResponseResult;
    }

    public static<T> HttpResponseResult<T> ok(){
        return HttpResponseResult.ok(null);
    }

    /**
     * 操作成功
     * @param data
     * @param <T>
     * @return
     */
    public static<T> HttpResponseResult<T> ok(T data){
        HttpResponseResult<T> httpResponseResult = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }

    public static<T> HttpResponseResult<T> fail(){
        return HttpResponseResult.fail(null);
    }

    /**
     * 操作失败
     * @param data
     * @param <T>
     * @return
     */
    public static<T> HttpResponseResult<T> fail(T data){
        HttpResponseResult<T> httpResponseResult = build(data);
        return build(data, ResultCodeEnum.FAIL);
    }

    public HttpResponseResult<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public HttpResponseResult<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public boolean isOk() {
        if(this.getCode().intValue() == ResultCodeEnum.SUCCESS.getCode().intValue()) {
            return true;
        }
        return false;
    }
}
