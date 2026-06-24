package com.vcb.vtuber_card_battle.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
// @JsonInclude(JsonInclude.Include.NON_NULL) 代表 null 欄位不輸出到 JSON
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;        // 是否成功
    private int code;               // 業務狀態碼（與 HTTP 狀態碼分開）
    private String message;         // 人類可讀訊息
    private T data;                 // 回應資料（泛型）
    private String error;           // 錯誤詳情（失敗時才有）

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")  // ISO 8601 格式
    private LocalDateTime timestamp;

    // 私有建構子，強制使用工廠方法
    private ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    // ─── 成功工廠方法 ───
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = true;
        response.code = 200;
        response.message = "操作成功";
        response.data = data;
        return response;
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        ApiResponse<T> response = success(data);
        response.message = message;
        return response;
    }

    public static <T> ApiResponse<T> created(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = true;
        response.code = 201;
        response.message = "建立成功";
        response.data = data;
        return response;
    }

    // ─── 失敗工廠方法 ───
    public static <T> ApiResponse<T> error(int code, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = false;
        response.code = code;
        response.message = message;
        return response;
    }

    public static <T> ApiResponse<T> error(int code, String message, String error) {
        ApiResponse<T> response = ApiResponse.error(code, message);
        response.error = error;
        return response;
    }

    // Getter 省略（使用 Lombok @Getter 或自行生成）
}