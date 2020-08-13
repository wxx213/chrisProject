package com.mydeo.seckilldemo.result;


/**
 * description 错误码
 *
 * @author yang nie
 * @date 2018/10/15
 */
public enum ErrorCode implements ResultCode {
  SUCCESS(0, "请求成功!"),
  ERROR(9999, "请求失败!"),
  UNERROR(9999, "未知错误!");




  private Integer code;
  private String msg;

  private ErrorCode(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  ;

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("ErrorCode{");
    sb.append("code=").append(code);
    sb.append(", msg='").append(msg).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
