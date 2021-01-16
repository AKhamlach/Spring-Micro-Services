package org.sid.secservice.sec.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public
class RoleUserForm{
    private String username;
    private String roleName;
}