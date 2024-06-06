package com.example.scoremanagement.Form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserForm {
    @NotBlank(message = "Usernameは必須入力です")
    private String loginId;
    @NotBlank(message = "Passwordは必須入力です")
    private String password;
}
