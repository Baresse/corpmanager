package eve.corp.manager.service.impl;

import com.beimin.eveapi.account.accountstatus.EveAccountStatus;
import com.beimin.eveapi.account.apikeyinfo.ApiKeyInfoResponse;
import com.beimin.eveapi.account.characters.EveCharacter;
import com.beimin.eveapi.character.mail.messages.ApiMailMessage;
import com.beimin.eveapi.exception.ApiException;
import com.beimin.eveapi.shared.contacts.ContactList;
import com.beimin.eveapi.shared.contacts.EveContact;
import com.beimin.eveapi.shared.contract.EveContract;
import com.beimin.eveapi.shared.wallet.RefType;
import com.beimin.eveapi.shared.wallet.journal.ApiJournalEntry;
import eve.corp.manager.common.dto.Connection;
import eve.corp.manager.common.enums.ConnectionOrigin;
import eve.corp.manager.common.model.AuditedAPI;
import eve.corp.manager.service.IApiAudit;
import eve.corp.manager.service.helper.ApiAuditHelper;
import eve.corp.manager.service.helper.ConnectionDirectory;
import eve.corp.manager.service.helper.StandingDirectory;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApiAuditService implements IApiAudit {

    private static final Logger logger = LoggerFactory.getLogger(ApiAuditService.class);

    @Autowired
    private StandingDirectory standingDirectory;

    /**
     * @see eve.corp.manager.service.IApiAudit
     */
    public AuditedAPI performAudit(int keyId, String vCode) {

        ConnectionDirectory connectionDirectory = new ConnectionDirectory();
        Connection connections = new Connection();
        AuditedAPI api = new AuditedAPI();

        try {
            // Build an ApiAuditHelper
            ApiAuditHelper helper = new ApiAuditHelper(keyId, vCode);

            // Retrieve EvE account status
            EveAccountStatus accountStatus = helper.getEveAccountStatus();


            ApiKeyInfoResponse apiKeyInfo = helper.getApiKeyInfo();

            for (EveCharacter blackDucksCorporationsConnected : apiKeyInfo.getEveCharacters()) {

                long blackDucksAlliancesConnected = blackDucksCorporationsConnected.getCharacterID();
                logger.info("Add connections for " + blackDucksCorporationsConnected.getName() + " ...");
                connections.addConnection(connectionDirectory.getCharacterById(blackDucksAlliancesConnected), ConnectionOrigin.ACCOUNT);
                logger.info("Audit mails for " + blackDucksCorporationsConnected.getName() + " ...");
                this.auditMailMessageHeader(connections, connectionDirectory, helper, blackDucksAlliancesConnected);
                logger.info("Done.");
                logger.info("Audit blue contacts for " + blackDucksCorporationsConnected.getName() + " ...");
                this.auditBlueContacts(connections, connectionDirectory, helper, blackDucksAlliancesConnected);
                logger.info("Done.");
                logger.info("Audit contracts for " + blackDucksCorporationsConnected.getName() + " ...");
                this.auditContracts(connections, connectionDirectory, helper, blackDucksAlliancesConnected);
                logger.info("Done.");
                logger.info("Audit player donation for " + blackDucksCorporationsConnected.getName() + " ...");
                this.auditPlayerDonation(connections, connectionDirectory, helper, blackDucksAlliancesConnected);
                logger.info("Done.");
            }

            HashSet blackDucksCharatersConnected1 = new HashSet();
            HashSet blackDucksCorporationsConnected1 = new HashSet();
            HashSet blackDucksAlliancesConnected1 = new HashSet();
            this.findBlackConnections(connections, blackDucksCharatersConnected1, blackDucksCorporationsConnected1, blackDucksAlliancesConnected1);
            Map charactersDirectory = connectionDirectory.getCharacters();
            Map corporationsDirectory = connectionDirectory.getCorporations();
            Map alliancesDirectory = connectionDirectory.getAlliances();
            api.setAccountStatus(accountStatus);
            api.setApiStatus(apiKeyInfo);
            api.setAccountCharacters(apiKeyInfo, charactersDirectory);
            api.setConnectedAlliances(connections, alliancesDirectory);
            api.setConnectedCorporations(connections, corporationsDirectory);
            api.setConnectedCharacters(connections, charactersDirectory);
            api.setConnectedBlackDuckAlliances(blackDucksAlliancesConnected1, connections, alliancesDirectory);
            api.setConnectedBlackDuckCorporations(blackDucksCorporationsConnected1, connections, corporationsDirectory);
            api.setConnectedBlackDuckCharacters(blackDucksCharatersConnected1, connections, charactersDirectory);
        } catch (ApiException var15) {
            logger.error(var15.getMessage(), var15);
        }

        return api;
    }

    private void auditMailMessageHeader(Connection connections, ConnectionDirectory connectionDirectory, ApiAuditHelper helper, long characterId) throws ApiException {
        Set messages = helper.getMailMessageHeaders(characterId);
        if (messages != null) {
            Iterator i$ = messages.iterator();

            while (i$.hasNext()) {
                ApiMailMessage message = (ApiMailMessage) i$.next();
                connections.addConnection(connectionDirectory.getCharacterById(message.getSenderID()), ConnectionOrigin.MAIL);
            }
        }

    }

    private void auditBlueContacts(Connection connections, ConnectionDirectory connectionDirectory, ApiAuditHelper helper, long characterId) throws ApiException {
        ContactList contactList = helper.getContactList(characterId);
        if (contactList != null) {
            Iterator i$ = contactList.iterator();

            while (i$.hasNext()) {
                EveContact eveContact = (EveContact) i$.next();
                if (eveContact.getStanding() > 0.0D) {
                    connections.addConnection(connectionDirectory.getCharacterById((long) eveContact.getContactID()), ConnectionOrigin.BLUE_CONTACT);
                }
            }
        }

    }

    private void auditContracts(Connection connections, ConnectionDirectory connectionDirectory, ApiAuditHelper helper, long characterId) throws ApiException {
        Set contracts = helper.getContracts(characterId);
        if (contracts != null) {
            Iterator i$ = contracts.iterator();

            while (i$.hasNext()) {
                EveContract contract = (EveContract) i$.next();
                connections.addConnection(connectionDirectory.getCharacterById(contract.getIssuerID()), ConnectionOrigin.CONTRACT);
                connections.addConnection(connectionDirectory.getCharacterById(contract.getAssigneeID()), ConnectionOrigin.CONTRACT);
                connections.addConnection(connectionDirectory.getCharacterById(contract.getAcceptorID()), ConnectionOrigin.CONTRACT);
            }
        }

    }

    private void auditPlayerDonation(Connection connections, ConnectionDirectory connectionDirectory, ApiAuditHelper helper, long characterId) throws ApiException {
        Set entries = helper.getCharacterWalletjournal(characterId);
        if (entries != null) {
            Iterator i$ = entries.iterator();

            while (i$.hasNext()) {
                ApiJournalEntry apiJournalEntry = (ApiJournalEntry) i$.next();
                if (apiJournalEntry.getRefType() == RefType.PLAYER_DONATION) {
                    logger.info(ToStringBuilder.reflectionToString(apiJournalEntry));
                    connections.addConnection(connectionDirectory.getCharacterById(apiJournalEntry.getOwnerID1()), ConnectionOrigin.WALLET);
                    connections.addConnection(connectionDirectory.getCharacterById(apiJournalEntry.getOwnerID2()), ConnectionOrigin.WALLET);
                }
            }
        }

    }

    private void findBlackConnections(Connection connections, Set<Long> blackDucksCharatersConnected, Set<Long> blackDucksCorporationsConnected, Set<Long> blackDucksAlliancesConnected) {
        List blackDucks = this.standingDirectory.getBlacks();
        Map characterConnections = connections.getCharacterConnections();
        Map corporationConnections = connections.getCorporationConnections();
        Map allianceConnections = connections.getAllianceConnections();
        Iterator i$ = blackDucks.iterator();

        while (i$.hasNext()) {
            EveContact eveContact = (EveContact) i$.next();
            int contactID = eveContact.getContactID();
            if (characterConnections.containsKey(Long.valueOf((long) contactID))) {
                blackDucksCharatersConnected.add(Long.valueOf((long) contactID));
            } else if (corporationConnections.containsKey(Long.valueOf((long) contactID))) {
                blackDucksCorporationsConnected.add(Long.valueOf((long) contactID));
            } else if (allianceConnections.containsKey(Long.valueOf((long) contactID))) {
                blackDucksAlliancesConnected.add(Long.valueOf((long) contactID));
            }
        }

    }
}
