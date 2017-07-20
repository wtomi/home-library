package wojtowicz.tomi.booklibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import wojtowicz.tomi.booklibrary.domain.VerificationToken;
import wojtowicz.tomi.booklibrary.repositories.VerificationTokenRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tommy on 7/18/2017.
 */
@Service
@Profile("springdatajpa")
public class VerificationTokenServiceJPA implements VerificationTokenService {

    private VerificationTokenRepository repository;

    @Autowired
    public void setRepository(VerificationTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<?> listAll() {
        List<VerificationToken> tokens = new ArrayList<>();
        repository.findAll().forEach(tokens::add);
        return tokens;
    }

    @Override
    public VerificationToken getById(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public VerificationToken SaveOrUpdate(VerificationToken domainObject) {
        return repository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public VerificationToken getByToken(String token) {
        return repository.findByToken(token);
    }
}
