package wojtowicz.tomi.booklibrary.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tommy on 7/6/2017.
 */
@Getter
@Setter
@Entity
public class User extends AbstractDomainClass{

    @Column(unique = true)
    private String username;

    private String email;

    @Transient
    private String password;

    private String encryptedPassword;

    private Boolean enabled = false;

    private Integer failedLoginAttempts = 0;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private List<Role> roles = new ArrayList<>();

    public void addRole(Role role){
        if(!this.roles.contains(role)){
            this.roles.add(role);
        }
    }

    public void removeRole(Role role){
        this.roles.remove(role);
    }

}


