package eve.corp.manager.service;

import eve.corp.manager.common.data.JournalEntry;
import eve.corp.manager.common.enums.AccountKey;

import java.util.List;

/**
 * This Service interface will deal with corporation wallets
 */
public interface IWallet {

    /**
     * Fetch Corporation Wallet defined by the accountKey into the database.
     * This is the only service which interacts with the EvE API.
     * <p/>
     * Note Master wallet is mapped to AccountKey.DIVISION_1.
     *
     * @param accountKey wallet division to fetch into the database
     */
    void fetchCorporationWalletJournal(AccountKey accountKey);

    /**
     * @return Returns all the preivously fetched journal entries from corporation wallets
     */
    List<JournalEntry> getAllJournalEntries();

    /**
     * @param monthStr Define the month requested with the following format "YYYY-MM"
     * @return Returns all the preivously fetched journal entries from corporation wallets
     */
    List<JournalEntry> getJournalEntriesByMonth(String monthStr);
}
