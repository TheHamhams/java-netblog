package com.netblog.netblog.dtos;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class UpdateUserDto {
    @Nullable
    private String firstName;
    @Nullable
    private String lastName;
    @Nullable
    private String email;
    @Nullable
    private String currentPassword;
    @Nullable
    private String newPassword;
    @Nullable
    private String newPasswordConfirm;
}
