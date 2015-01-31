package eve.corp.manager.service.impl;

import com.beimin.eveapi.shared.wallet.journal.ApiJournalEntry;
import eve.corp.manager.common.data.JournalEntry;
import eve.corp.manager.common.data.JournalEntryRepository;
import eve.corp.manager.common.enums.AccountKey;
import eve.corp.manager.service.IWallet;
import eve.corp.manager.service.helper.WalletHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class WalletService implements IWallet {

    @Autowired
    private JournalEntryRepository repository;

    @Autowired
    private WalletHelper helper;

    /**
     * @see eve.corp.manager.service.IWallet
     */
    public void fetchCorporationWalletJournal(AccountKey accountKey) {
        Set<ApiJournalEntry> apiJournalEntries = this.helper.fetchCorpWalletJournal(accountKey.getKey());
        for (ApiJournalEntry journalEntry : apiJournalEntries) {
            this.repository.save(new JournalEntry(journalEntry));
        }
    }

    /**
     * @see eve.corp.manager.service.IWallet
     */
    public List<JournalEntry> getJournalEntriesByMonth(String monthStr) {
        int yearValue = Integer.parseInt(monthStr.substring(0, 4));
        int monthValue = Integer.parseInt(monthStr.substring(5));
        return this.repository.findByYearMonth(yearValue, monthValue);
    }

    /**
     * @see eve.corp.manager.service.IWallet
     */
    public List<JournalEntry> getAllJournalEntries() {
        return this.repository.findAll();
    }
}
