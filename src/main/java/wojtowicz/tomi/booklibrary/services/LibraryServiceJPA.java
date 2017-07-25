package wojtowicz.tomi.booklibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import wojtowicz.tomi.booklibrary.domain.Library;
import wojtowicz.tomi.booklibrary.repositories.LibraryRepository;

import java.util.ArrayList;
import java.util.List;

public class LibraryServiceJPA implements LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

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
}
