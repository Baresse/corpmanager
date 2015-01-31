package eve.corp.manager.common.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {

    /**
     * @param year year
     * @param month month
     * @return JournalEntries from the database for a given month in a year
     */
    List<JournalEntry> findByYearMonth(int year, int month);
}
