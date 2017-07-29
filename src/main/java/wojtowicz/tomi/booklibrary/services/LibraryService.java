package wojtowicz.tomi.booklibrary.services;

import wojtowicz.tomi.booklibrary.domain.Library;

public interface LibraryService extends CRUDService<Library> {
    Library getByOwnerUsername(String username);
}
