package wojtowicz.tomi.booklibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wojtowicz.tomi.booklibrary.domain.User;
import wojtowicz.tomi.booklibrary.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tommy on 7/9/2017.
 */
@Service
@Profile("springdatajpa")
public class UserServiceJPA implements UserService {

    @Autowired
    private UserRepository userRepository;

    //TODO encryption service

    @Override
    public List<?> listAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public User SaveOrUpdate(User domainObject) {
        //TODO encryption
        return userRepository.save(domainObject);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
