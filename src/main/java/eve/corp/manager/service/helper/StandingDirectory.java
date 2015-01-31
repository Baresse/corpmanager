package eve.corp.manager.service.helper;

import com.beimin.eveapi.core.ApiAuthorization;
import com.beimin.eveapi.corporation.contact.list.ContactListParser;
import com.beimin.eveapi.corporation.contact.list.ContactListResponse;
import com.beimin.eveapi.exception.ApiException;
import com.beimin.eveapi.shared.contacts.ContactList;
import com.beimin.eveapi.shared.contacts.EveContact;
import eve.corp.manager.common.CommonConstants;
import eve.corp.manager.service.impl.ApiAuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class StandingDirectory {

    private static final Logger logger = LoggerFactory.getLogger(ApiAuditService.class);

    private List<EveContact> blacks = new ArrayList<>();

    @Autowired
    public StandingDirectory(@Value("${corpo.characterId}") String corpoCharacterId,
                             @Value("${corpo.keyId}") String corpoKeyId,
                             @Value("${corpo.vCode}") String corpoVcode) {
        logger.info("Get ApiAuthorization : corpoCharacterId=" + corpoCharacterId + " corpoKeyId=" + corpoKeyId + " corpoVcode=" + corpoVcode);
        ApiAuthorization auth = CommonConstants.getCorporationApiAuthorization(Integer.parseInt(corpoCharacterId), Integer.parseInt(corpoKeyId), corpoVcode);
        logger.info("Fetch Ghreu Contacts...");
        this.fetchCorpAllyContacts(auth);
        logger.info("Done.");
    }

    private void fetchCorpAllyContacts(ApiAuthorization auth) {
        try {
            ContactListParser contactListParser = ContactListParser.getInstance();
            ContactListResponse response =  contactListParser.getResponse(auth);
            if (response.hasError()) {
                throw new RuntimeException(response.getError().toString());
            }

            ContactList allianceContactList = response.getAllianceContactList();
            ContactList corporationContactList = response.getCorporateContactList();
            Iterator iterator;
            EveContact eveContact;
            if (allianceContactList != null) {
                iterator = allianceContactList.iterator();

                while (iterator.hasNext()) {
                    eveContact = (EveContact) iterator.next();
                    if (eveContact.getStanding() <= 0.0D) {
                        this.blacks.add(eveContact);
                    }
                }
            }

            if (corporationContactList != null) {
                iterator = corporationContactList.iterator();

                while (iterator.hasNext()) {
                    eveContact = (EveContact) iterator.next();
                    if (eveContact.getStanding() <= 0.0D) {
                        this.blacks.add(eveContact);
                    }
                }
            }
        } catch (ApiException e) {
            logger.error(e.getMessage(), e);
        }

    }

    public List<EveContact> getBlacks() {
        return this.blacks;
    }
}
