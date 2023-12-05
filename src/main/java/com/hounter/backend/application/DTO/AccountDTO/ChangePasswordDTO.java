package com.hounter.backend.application.DTO.AccountDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordDTO{
    @NotBlank(message = "Nhập mật khẩu cũ!")
    private String old_password;
    @NotBlank(message = "Nhập mật khẩu mới!")
    private String new_password;
    @NotBlank(message = "Required field!")
    private String confirmPassword;
}
