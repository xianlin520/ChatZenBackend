package vip.xianlin.entity;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    
    @Schema(description = "状态码")
    private Integer code;
    @Schema(description = "返回数据")
    private Object data;
    @Schema(description = "返回信息")
    private String msg;
    
    // 成功
    public static Result succ(Object data) {
        return succ(200, "操作成功", data);
    }
    
    // 失败
    public static Result fail(String msg) {
        return fail(400, msg, null);
    }
    
    // 无权限
    public static Result noAuth(String msg) {
        return fail(403, msg, null);
    }
    
    // 登录到期
    public static Result loginExpire(String msg) {
        return fail(401, msg, null);
    }
    
    // 无资源
    public static Result noResource(String msg) {
        return fail(404, msg, null);
    }
    
    public static Result succ(int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    
    public static Result fail(int code, String msg, Object data) {
        Result result = new Result();
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
