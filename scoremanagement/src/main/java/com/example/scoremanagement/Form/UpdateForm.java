package com.example.scoremanagement.Form;

import lombok.Data;

@Data
public class UpdateForm {
    private Integer grade;
    private Integer Score;
    private String subject;
    private String type;
    private Integer deviation;
}
