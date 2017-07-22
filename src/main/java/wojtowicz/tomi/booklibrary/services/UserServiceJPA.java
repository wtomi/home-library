package wojtowicz.tomi.booklibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wojtowicz.tomi.booklibrary.converters.UserDtoToUser;
import wojtowicz.tomi.booklibrary.domain.User;
import wojtowicz.tomi.booklibrary.domain.VerificationToken;
import wojtowicz.tomi.booklibrary.dto.UserDto;
import wojtowicz.tomi.booklibrary.repositories.UserRepository;
import wojtowicz.tomi.booklibrary.repositories.VerificationTokenRepository;
import wojtowicz.tomi.booklibrary.services.security.EncryptionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tommy on 7/9/2017.
 */
@Service
@Profile("springdatajpa")
public class UserServiceJPA implements UserService {

    private UserRepository userRepository;

    private EncryptionService encryptionService;

    private UserDtoToUser userDtoToUserConverter;

    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Autowired
    public void setUserDtoToUserConverter(UserDtoToUser userDtoToUser) {
        this.userDtoToUserConverter = userDtoToUser;
    }

    @Autowired
    public void setVerificationTokenRepository(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

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
    public User saveOrUpdate(User user) {
        if (user.getPassword() != null) {
            user.setEncryptedPassword(encryptionService.encryptString(user.getPassword()));
        }
        return userRepository.save(user);
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

    @Override
    public User registerNewUser(final UserDto userDto) {
        User user = userDtoToUserConverter.convert(userDto);
        if (user != null)
            return this.saveOrUpdate(user);
        else
            return null;
        //TODO exception
    }

    @Override
    public void createVerificationTokenForUser(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }
}
