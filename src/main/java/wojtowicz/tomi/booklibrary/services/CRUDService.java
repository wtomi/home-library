package wojtowicz.tomi.booklibrary.services;

import java.util.List;

/**
 * Created by tommy on 7/9/2017.
 */
public interface CRUDService<T> {
    List<?> listAll();

    T getById(Integer id);

    T SaveOrUpdate(T domainObject);

    void delete(Integer id);
}
