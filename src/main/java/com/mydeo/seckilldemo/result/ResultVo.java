package com.mydeo.seckilldemo.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * description 请求结果信息类
 *
 * @author Chris he
 * @date 2020/9/29
 */
@ApiModel(value = "请求结果信息类")
@Data
public class ResultVo<T> {

  @ApiModelProperty(value = "请求结果代码")
  private Integer code;
  @ApiModelProperty(value = "提示信息")
  private String msg;
  @ApiModelProperty(value = "返回的数据")
  private T data;

  public ResultVo(ErrorCode codeEnum, String msg) {
    this.code = codeEnum.getCode();
    this.msg = msg;
  }

  public ResultVo(ErrorCode codeEnum) {
    this.code = codeEnum.getCode();
    this.msg = codeEnum.getMsg();
  }

  public ResultVo(ErrorCode codeEnum, T data) {
    this.code = codeEnum.getCode();
    this.msg = codeEnum.getMsg();
    this.data = data;
  }

  public ResultVo(ErrorCode codeEnum, String msg, T data) {
    this.code = codeEnum.getCode();
    this.msg = msg;
    this.data = data;
  }

  public ResultVo(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public static ResultVo success() {
    return new ResultVo(ErrorCode.SUCCESS);
  }

  public static <T> ResultVo<T> success(T t) {
    return new ResultVo<>(ErrorCode.SUCCESS, t);
  }

  public static <T> ResultVo<T> success(String msg, T t) {
    return new ResultVo<>(ErrorCode.SUCCESS, msg, t);
  }

  public static <T> ResultVo<T> success(ErrorCode codeEnum, T t) {
    return new ResultVo<>(codeEnum, t);
  }

  public static <T> ResultVo<T> error(String msg) {
    return new ResultVo<>(ErrorCode.ERROR, msg);
  }

  public static <T> ResultVo<T> error(ErrorCode codeEnum, String msg) {
    return new ResultVo<>(codeEnum, msg);
  }

  public static <T> ResultVo<T> error(ErrorCode codeEnum) {
    return new ResultVo<>(codeEnum);
  }

  public static <T> ResultVo<T> error(String msg, T t) {
    return new ResultVo<>(ErrorCode.ERROR, msg, t);
  }

  public static <T> ResultVo<T> error(Integer code, String msg) {
    return new ResultVo<>(code, msg);
  }

}