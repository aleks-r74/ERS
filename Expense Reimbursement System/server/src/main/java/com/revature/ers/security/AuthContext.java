package com.revature.ers.security;

import com.revature.ers.entities.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;


@Component @SessionScope
@NoArgsConstructor
@Getter @Setter
public class AuthContext {
    private String username;
    private UserRole role = UserRole.EMPLOYEE;
    private int userId;

    public void reset(){
        username = null;
        role = UserRole.EMPLOYEE;
        userId = 0;
    }
}
