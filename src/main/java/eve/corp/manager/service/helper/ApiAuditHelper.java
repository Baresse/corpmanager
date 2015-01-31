package eve.corp.manager.service.helper;

import com.beimin.eveapi.account.accountstatus.AccountStatusParser;
import com.beimin.eveapi.account.accountstatus.AccountStatusResponse;
import com.beimin.eveapi.account.accountstatus.EveAccountStatus;
import com.beimin.eveapi.account.apikeyinfo.ApiKeyInfoParser;
import com.beimin.eveapi.account.apikeyinfo.ApiKeyInfoResponse;
import com.beimin.eveapi.character.contact.list.ContactListParser;
import com.beimin.eveapi.character.contact.list.ContactListResponse;
import com.beimin.eveapi.character.contract.ContractsParser;
import com.beimin.eveapi.character.mail.messages.ApiMailMessage;
import com.beimin.eveapi.character.mail.messages.MailMessagesParser;
import com.beimin.eveapi.character.mail.messages.MailMessagesResponse;
import com.beimin.eveapi.character.wallet.journal.WalletJournalParser;
import com.beimin.eveapi.core.ApiAuthorization;
import com.beimin.eveapi.exception.ApiException;
import com.beimin.eveapi.shared.contacts.ContactList;
import com.beimin.eveapi.shared.contract.ContractsResponse;
import com.beimin.eveapi.shared.contract.EveContract;
import com.beimin.eveapi.shared.wallet.journal.ApiJournalEntry;
import com.beimin.eveapi.shared.wallet.journal.WalletJournalResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class ApiAuditHelper {

    private static final Logger logger = LoggerFactory.getLogger(ApiAuditHelper.class);

    protected ApiAuthorization auth;

    protected int keyId;

    protected String vCode;

    public ApiAuditHelper(int keyId, String vCode) {
        this.keyId = keyId;
        this.vCode = vCode;
        this.initializeAuth();
    }

    public EveAccountStatus getEveAccountStatus() throws ApiException {
        logger.info("Parse Account Status...");
        AccountStatusParser parser = AccountStatusParser.getInstance();
        AccountStatusResponse response = parser.getResponse(this.auth);
        EveAccountStatus accountStatus = response.get();
        logger.info("Done.");
        return accountStatus;
    }

    public ApiKeyInfoResponse getApiKeyInfo() throws ApiException {
        logger.info("Parse API Key Info...");
        ApiKeyInfoParser parser = ApiKeyInfoParser.getInstance();
        ApiKeyInfoResponse apiKeyInfo = parser.getResponse(this.auth);
        logger.info("Done.");
        return apiKeyInfo;
    }

    public Set<ApiMailMessage> getMailMessageHeaders(long characterId) throws ApiException {
        logger.info("Retrieve all Mail Message Headers...");
        ApiAuthorization authCharacter = this.createApiAuthorization(characterId);
        MailMessagesParser parser = MailMessagesParser.getInstance();
        MailMessagesResponse response = parser.getResponse(authCharacter);
        Set<ApiMailMessage> messages = response.getAll();
        logger.info("Done.");
        return messages;
    }

    public ContactList getContactList(long characterId) throws ApiException {
        logger.info("Retrieve the character contact list...");
        ApiAuthorization authCharacter = this.createApiAuthorization(characterId);
        ContactListParser parser = ContactListParser.getInstance();
        ContactListResponse response = parser.getResponse(authCharacter);
        ContactList contactList = response.getContactList();
        logger.info("Done.");
        return contactList;
    }

    public Set<EveContract> getContracts(long characterId) throws ApiException {
        logger.info("Retrieve the character contracts...");
        ApiAuthorization authCharacter = this.createApiAuthorization(characterId);
        ContractsParser parser = ContractsParser.getInstance();
        ContractsResponse response = parser.getResponse(authCharacter);
        Set<EveContract>  contracts = response.getAll();
        logger.info("Done.");
        return contracts;
    }

    public Set<ApiJournalEntry> getCharacterWalletjournal(long characterId) throws ApiException {
        logger.info("Retrieve the character Wallet journal...");
        ApiAuthorization authCharacter = this.createApiAuthorization(characterId);
        WalletJournalParser parser = WalletJournalParser.getInstance();
        WalletJournalResponse response = parser.getWalletJournalResponse(authCharacter);
        HashSet<ApiJournalEntry> allEntries = new HashSet<>();
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
            response = parser.getWalletJournalResponse(this.auth, minRefID);
        } while (entries.size() > 0);

        logger.info("Done.");
        return allEntries;
    }

    private void initializeAuth() { this.auth = new ApiAuthorization(this.keyId, this.vCode); }

    private ApiAuthorization createApiAuthorization(long characterId) {
        ApiAuthorization authCharacter = new ApiAuthorization(this.keyId, this.vCode);
        authCharacter.setCharacterID(characterId);
        return authCharacter;
    }
}
