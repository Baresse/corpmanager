package eve.corp.manager.service.helper;

import com.beimin.eveapi.corporation.sheet.CorpSheetParser;
import com.beimin.eveapi.corporation.sheet.CorpSheetResponse;
import com.beimin.eveapi.eve.alliancelist.AllianceListParser;
import com.beimin.eveapi.eve.alliancelist.AllianceListResponse;
import com.beimin.eveapi.eve.alliancelist.ApiAlliance;
import com.beimin.eveapi.eve.character.CharacterInfoParser;
import com.beimin.eveapi.eve.character.CharacterInfoResponse;
import com.beimin.eveapi.exception.ApiException;
import eve.corp.manager.common.dto.EveAlliance;
import eve.corp.manager.common.dto.EveCorporation;
import eve.corp.manager.common.model.AuditedCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ConnectionDirectory {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionDirectory.class);

    private Map<Long, AuditedCharacter> characters = new HashMap<>();

    private Map<Long, EveCorporation> corporations = new HashMap<>();

    private Map<Long, EveAlliance> alliances = new HashMap<>();

    private Set<ApiAlliance> allianceList = null;

    public ConnectionDirectory() {
        try {
            logger.info("Fetch Alliance List...");
            AllianceListParser parser = AllianceListParser.getInstance();
            AllianceListResponse response = parser.getResponse();
            this.allianceList = response.getAll();
            logger.info("Done.");
        } catch (ApiException e) {
            logger.error(e.getMessage(), e);
        }

    }

    public AuditedCharacter getCharacterById(long id) throws ApiException {

        AuditedCharacter character = this.characters.get(id);

        if (character == null) {
            CharacterInfoResponse charInfoResponse = this.findCharacterInfoById(id);

            if (!charInfoResponse.hasError()) {
                character = new AuditedCharacter();
                character.setCharacterID(charInfoResponse.getCharacterID());
                character.setCharacterName(charInfoResponse.getCharacterName());
                character.setCorporationID(charInfoResponse.getCorporationID());
                character.setCorporationName(charInfoResponse.getCorporation());
                character.setAllianceID(charInfoResponse.getAllianceID());
                character.setAllianceName(charInfoResponse.getAlliance());
                character.setSecurityStatus(charInfoResponse.getSecurityStatus());
                if (!this.characters.containsKey(character.getCharacterID())) {
                    this.characters.put(character.getCharacterID(), character);
                }

                this.addCorporation(new EveCorporation(character.getCorporationID(), character.getCorporationName()));
                Long corpResponse = charInfoResponse.getAllianceID();
                if (corpResponse != null && corpResponse > 0L) {
                    this.alliances.put(corpResponse, new EveAlliance(corpResponse, charInfoResponse.getAlliance()));
                }
            } else {
                CorpSheetResponse corpResponse = this.findCorporationInfoById(id);
                if (!corpResponse.hasError()) {
                    this.addCorporation(new EveCorporation(corpResponse.getCorporationID(), corpResponse.getCorporationName()));
                    Long alliance = corpResponse.getAllianceID();
                    if (alliance != null && alliance > 0L) {
                        this.alliances.put(alliance, new EveAlliance(alliance, corpResponse.getAllianceName()));
                    }
                } else {
                    EveAlliance alliance = this.findAllianceById(id);
                    if (alliance != null) {
                        this.alliances.put(id, alliance);
                    }
                }
            }
        }

        return character;
    }

    private void addCorporation(EveCorporation corp) {
        if (!this.corporations.containsKey(corp.getCorporationID())) {
            this.corporations.put(corp.getCorporationID(), corp);
        }

    }

    private CharacterInfoResponse findCharacterInfoById(long id) throws ApiException {
        CharacterInfoParser parser = CharacterInfoParser.getInstance();
        return parser.getResponse(id);
    }

    private CorpSheetResponse findCorporationInfoById(long id) throws ApiException {
        CorpSheetParser parser = CorpSheetParser.getInstance();
        return parser.getResponse(id);
    }

    private EveAlliance findAllianceById(long id) throws ApiException {
        Iterator allianceIterator = this.allianceList.iterator();

        ApiAlliance alliance;
        do {
            if (!allianceIterator.hasNext()) {
                return null;
            }

            alliance = (ApiAlliance) allianceIterator.next();
        } while (alliance.getAllianceID() != id);

        EveAlliance eveAlliance = new EveAlliance();
        eveAlliance.setAllianceID(id);
        eveAlliance.setAllianceName(alliance.getName());
        return eveAlliance;
    }

    public Map<Long, AuditedCharacter> getCharacters() { return this.characters; }

    public Map<Long, EveCorporation> getCorporations() { return this.corporations; }

    public Map<Long, EveAlliance> getAlliances() { return this.alliances; }
}
