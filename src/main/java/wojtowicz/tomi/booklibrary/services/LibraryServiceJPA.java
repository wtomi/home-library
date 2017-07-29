package wojtowicz.tomi.booklibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wojtowicz.tomi.booklibrary.domain.Library;
import wojtowicz.tomi.booklibrary.repositories.LibraryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryServiceJPA implements LibraryService {

    private LibraryRepository libraryRepository;

    @Autowired
    public void setLibraryRepository(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    @Override
    public List<?> listAll() {
        List<Library> libraries = new ArrayList<>();
        libraryRepository.findAll().forEach(libraries::add);
        return  libraries;
    }

    @Override
    public Library getById(Integer id) {
        return libraryRepository.findOne(id);
    }

    @Override
    public Library saveOrUpdate(Library domainObject) {
        return libraryRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        libraryRepository.delete(id);
    }

    @Override
    public Library getByOwnerUsername(String username) {
        return libraryRepository.findByOwnerUsername(username);
    }
}
