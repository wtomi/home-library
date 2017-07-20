package wojtowicz.tomi.booklibrary.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import wojtowicz.tomi.booklibrary.domain.Role;
import wojtowicz.tomi.booklibrary.domain.User;
import wojtowicz.tomi.booklibrary.services.RoleService;
import wojtowicz.tomi.booklibrary.services.UserService;

/**
 * Created by tommy on 7/9/2017.
 */
@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent>{

    private UserService userService;

    @Autowired
    public void setUserRepository(UserService userService) {
        this.userService = userService;
    }

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        createAndSaveRoles();
        createAndSaveUsers();
        linkAdminRole();
    }

    private void createAndSaveRoles() {
        String rolesNames[] = {"USER", "ADMIN"};
        for(String roleName: rolesNames) {
            Role role = new Role();
            role.setRole(roleName);
            roleService.SaveOrUpdate(role);
        }
    }

    private void createAndSaveUsers() {
        String userNames[] = {"user", "admin"};
        for(String username: userNames) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(username);
            user.setEnabled(true);
            user.addRole(roleService.getByRole("USER"));
            userService.SaveOrUpdate(user);
        }
    }

    private void linkAdminRole() {
        User user = userService.getByUsername("admin");
        Role role = roleService.getByRole("ADMIN");
        user.addRole(role);
        userService.SaveOrUpdate(user);
    }
}
