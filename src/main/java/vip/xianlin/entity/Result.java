package vip.xianlin.entity;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    
    @Schema(description = "状态码")
    private Integer code;
    @Schema(description = "返回数据")
    private T data;
    @Schema(description = "返回信息")
    private String msg;
    
    /**
     * 快速构建成功返回结果
     *
     * @param data 返回数据
     * @return Result
     */
    public static <T> Result<T> succ(T data) {
        return succ(200, "操作成功", data);
    }
    
    /**
     * 400-快速构建失败返回结果
     *
     * @param msg 返回信息
     * @return Result
     */
    public static <T> Result<T> fail(String msg) {
        return fail(400, msg, null);
    }
    
    /**
     * 403-无权限
     *
     * @param msg 返回信息
     * @return Result
     */
    public static <T> Result<T> noAuth(String msg) {
        return fail(403, msg, null);
    }
    
    /**
     * 401-登录过期
     *
     * @param msg 返回信息
     * @return Result
     */
    public static <T> Result<T> loginExpire(String msg) {
        return fail(401, msg, null);
    }
    
    /**
     * 404-资源不存在
     *
     * @param msg 返回信息
     * @return Result
     */
    public static <T> Result<T> noResource(String msg) {
        return fail(404, msg, null);
    }
    
    public static <T> Result<T> succ(int code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    
    public static <T> Result<T> fail(int code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    
    /**
     * 快速将当前实体转换为JSON字符串格式
     *
     * @return JSON字符串
     */
    public String asJsonString() {
        return JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls);
    }
    
}
