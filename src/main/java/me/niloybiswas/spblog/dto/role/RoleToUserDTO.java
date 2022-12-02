package me.niloybiswas.spblog.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleToUserDTO {
    private String roleName;
    private String userName;
}
