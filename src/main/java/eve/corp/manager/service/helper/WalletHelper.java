package eve.corp.manager.service.helper;

import com.beimin.eveapi.core.ApiAuthorization;
import com.beimin.eveapi.corporation.wallet.journal.WalletJournalParser;
import com.beimin.eveapi.exception.ApiException;
import com.beimin.eveapi.shared.wallet.journal.ApiJournalEntry;
import com.beimin.eveapi.shared.wallet.journal.WalletJournalResponse;
import eve.corp.manager.common.CommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
public class WalletHelper {

    private static final Logger logger = LoggerFactory.getLogger(WalletHelper.class);

    private ApiAuthorization auth;

    @Autowired
    public WalletHelper(@Value("${corpo.characterId}") String corpoCharacterId,
                        @Value("${corpo.keyId}") String corpoKeyId,
                        @Value("${corpo.vCode}") String corpoVcode) {

        logger.info("Get ApiAuthorization : corpoCharacterId=" + corpoCharacterId + " corpoKeyId=" + corpoKeyId + " corpoVcode=" + corpoVcode);
        this.auth = CommonConstants.getCorporationApiAuthorization(Integer.parseInt(corpoCharacterId), Integer.parseInt(corpoKeyId), corpoVcode);
    }

    public Set<ApiJournalEntry> fetchCorpWalletJournal(int accountKey) {
        logger.info("fetchCorpWalletJournal...");

        try {
            WalletJournalParser e = WalletJournalParser.getInstance();
            WalletJournalResponse response = e.getResponse(this.auth, accountKey);
            if (response.hasError()) {
                throw new RuntimeException(response.getError().toString());
            } else {
                Set<ApiJournalEntry> allEntries = new HashSet<>();

                Set<ApiJournalEntry> entries;
                do {
                    entries = response.getAll();
                    long minRefID = Long.MAX_VALUE;

                    for (ApiJournalEntry apiJournalEntry : entries) {
                        allEntries.add(apiJournalEntry);
                        if (apiJournalEntry.getRefID() < minRefID) {
                            minRefID = apiJournalEntry.getRefID();
                        }
                    }

                    logger.info("minRefID:" + minRefID);
                    response = e.getResponse(this.auth, accountKey, minRefID, 2560);
                    if (response.hasError()) {
                        throw new RuntimeException(response.getError().toString());
                    }
                } while (entries.size() > 0);

                return allEntries;
            }
        } catch (ApiException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
