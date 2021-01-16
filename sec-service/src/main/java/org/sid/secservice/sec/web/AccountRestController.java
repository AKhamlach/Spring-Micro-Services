package org.sid.secservice.sec.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sid.secservice.sec.JWTUtil;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.sid.secservice.sec.entities.AppRole;
import org.sid.secservice.sec.entities.AppUser;
import org.sid.secservice.sec.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AccountRestController {
    private final AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = JWTUtil.USERS_URI)
    @PreAuthorize("hasAuthority('USER')")
    public List<AppUser> appUsers() {
        return accountService.listUsers();
    }

    @PostMapping(path = JWTUtil.USERS_URI)
    @PreAuthorize("hasAuthority('ADMIN')")
    public AppUser saveUser(@RequestBody AppUser appUser) {
        return accountService.addNewUser(appUser);
    }

    @PostMapping(path = JWTUtil.ROLES_URI)
    @PreAuthorize("hasAuthority('ADMIN')")
    public AppRole saveRole(@RequestBody AppRole appRole) {
        return accountService.addNewRole(appRole);
    }

    @PostMapping(path = JWTUtil.ADD_ROLE_TO_USER_URI)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm) {
        accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRoleName());
    }

    @GetMapping(path = JWTUtil.REFRESH_TOKEN_URI)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authToken = request.getHeader(JWTUtil.AUTH_HEADER);
        if (authToken != null && authToken.startsWith(JWTUtil.PREFIX)) {
            String jwt = authToken.substring(JWTUtil.PREFIX.length());
            Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
            String username=decodedJWT.getSubject();
            AppUser appUser=accountService.loadUserByUsername(username);
            String jwtAccessToken= JWT.create().withSubject(appUser.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis()+JWTUtil.EXPIRE_ACCESS_TOKEN))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles",appUser.getAppRoles().stream().map(AppRole::getRoleName).collect(Collectors.toList()))
                    .sign(algorithm);
            Map<String,String> idToken= new HashMap<>();
            idToken.put("access-token", jwtAccessToken);
            idToken.put("refresh-token", jwt);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(),idToken);

        }
        else{
            throw new RuntimeException("Refresh Token Required!!!");
        }
    }
    @GetMapping(path = JWTUtil.PROFILE_URI)
    public AppUser profile(Principal principal){
        return accountService.loadUserByUsername(principal.getName());
    }
}