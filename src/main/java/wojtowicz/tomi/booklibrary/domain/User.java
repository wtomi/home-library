package wojtowicz.tomi.booklibrary.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User extends AbstractDomainClass {

    @Column(unique = true)
    private String username;

    @OneToOne
    @JoinColumn
    private Library library;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    private List<Library> friendsLibraries;

    @NotEmpty
    @NotNull
    private String email;

    @Transient
    private String password;

    @NotNull
    @NotEmpty
    private String encryptedPassword;

    @NotNull
    private Boolean enabled = false;

    @NotNull
    private Integer failedLoginAttempts = 0;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private List<Role> roles = new ArrayList<>();

    public void addRole(Role role) {
        if (!this.roles.contains(role)) {
            this.roles.add(role);
        }
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

}


