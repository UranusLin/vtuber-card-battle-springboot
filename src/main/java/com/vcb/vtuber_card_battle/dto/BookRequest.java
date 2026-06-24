package com.vcb.vtuber_card_battle.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookRequest {
    @NotNull(message="Title不可為空")
    @NotBlank(message = "Title不可為空白")
    private String title;
    @NotNull(message="Author不可為空")
    @NotBlank(message = "Author不可為空白")
    private String author;
    @NotNull(message="ISBN不可為空")
    @NotBlank(message = "ISBN不可為空白")
    @Pattern(regexp = "^97[89]-\\d{1,5}-\\d{1,7}-\\d{1,6}-\\d$",
            message = "ISBN 格式不正確")
    private String isbn;
    @NotNull(message="publishYear不可為空")
    @Min(value = 1990)
    @Max(value = 2026)
    private Integer publishYear;
    @NotNull(message="totalCopies不可為空")
    @Min(value = 1, message = "數量最少為1本")
    private int totalCopies;
}
