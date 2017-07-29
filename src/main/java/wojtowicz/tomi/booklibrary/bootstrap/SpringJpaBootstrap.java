package wojtowicz.tomi.booklibrary.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import wojtowicz.tomi.booklibrary.domain.Library;
import wojtowicz.tomi.booklibrary.domain.Role;
import wojtowicz.tomi.booklibrary.domain.User;
import wojtowicz.tomi.booklibrary.services.LibraryService;
import wojtowicz.tomi.booklibrary.services.RoleService;
import wojtowicz.tomi.booklibrary.services.UserService;

@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent>{

    private UserService userService;

    private LibraryService libraryService;

    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void  setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
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
            roleService.saveOrUpdate(role);
        }
    }

    private void createAndSaveUsers() {
        String userNames[] = {"user", "admin"};
        for(String username: userNames) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(username);
            user.setEmail(username + "@" + username + "com");
            user.setEnabled(true);
            user.addRole(roleService.getByRole("USER"));

            Library library = new Library();
            libraryService.saveOrUpdate(library);

            user.setLibrary(library);
            userService.saveOrUpdate(user);
        }
    }

    private void linkAdminRole() {
        User user = userService.getByUsername("admin");
        Role role = roleService.getByRole("ADMIN");
        user.addRole(role);
        userService.saveOrUpdate(user);
    }
}
