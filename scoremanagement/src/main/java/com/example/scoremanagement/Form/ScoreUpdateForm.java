package com.example.scoremanagement.Form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
@Data
public class ScoreUpdateForm {

    private int id;
    @NotBlank(message="必須")
    private String grade;
    @NotNull(message = "必須")
    @Range(min=0,max=200,message = "不正の値です")
    private Integer score;
    @NotBlank(message = "必須")
    private String subject;
    @NotBlank(message = "必須")
    private String type;
    @NotNull(message = "必須")
    @Range(min=0,max=100,message = "不正な値です")
    private Integer deviation;
}