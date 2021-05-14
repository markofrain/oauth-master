package com.cgq.oauth.server;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 */
@Data
public class MyUser implements Serializable {

    private static final long serialVersionUID = -7614063286560993848L;
    private String userName;
    private String password;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked= true;
    private boolean credentialsNonExpired= true;
    private boolean enabled= true;
}
