package wojtowicz.tomi.booklibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wojtowicz.tomi.booklibrary.converters.UserDtoToUser;
import wojtowicz.tomi.booklibrary.domain.User;
import wojtowicz.tomi.booklibrary.domain.VerificationToken;
import wojtowicz.tomi.booklibrary.dto.UserDto;
import wojtowicz.tomi.booklibrary.exceptions.UserAlreadyExistsException;
import wojtowicz.tomi.booklibrary.repositories.UserRepository;
import wojtowicz.tomi.booklibrary.repositories.VerificationTokenRepository;
import wojtowicz.tomi.booklibrary.services.security.EncryptionService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        if (userAlreadyExists(userDto)) {
            throw new UserAlreadyExistsException("User already exists");
        }
        User user = userDtoToUserConverter.convert(userDto);
        return this.saveOrUpdate(user);
    }

    private boolean userAlreadyExists(UserDto userDto) {
        return userRepository.findByUsername(userDto.getUsername()) != null ||
                userRepository.findByEmail(userDto.getEmail()) != null;
    }

    @Override
    public VerificationToken createVerificationTokenForUser(User user) {
        VerificationToken verificationToken = createUniqueToken(user);
        return verificationTokenRepository.save(verificationToken);
    }

    private VerificationToken createUniqueToken(User user) {
        boolean isTokenUnique = false;
        String token = null;
        while (!isTokenUnique) {
            token = UUID.randomUUID().toString();
            if (verificationTokenRepository.findByToken(token) == null) {
                isTokenUnique = true;
            }
        }
        return new VerificationToken(user, token);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }
}
