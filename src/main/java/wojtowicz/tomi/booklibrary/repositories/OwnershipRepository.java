package wojtowicz.tomi.booklibrary.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import wojtowicz.tomi.booklibrary.domain.Ownership;

import java.util.List;

public interface OwnershipRepository extends CrudRepository<Ownership, Integer> {

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM Ownership o WHERE o.currentRental is NULL AND o.bookData.library.id = :libraryId AND " +
            "o.bookData.book.id = :bookId")
    List<Ownership> findPossibleToBorrowForUpdate(@Param("libraryId") Integer libraryId, @Param("bookId") Integer bookId);
}

