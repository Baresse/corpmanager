package eve.corp.manager.common.model;

import com.beimin.eveapi.account.accountstatus.EveAccountStatus;
import com.beimin.eveapi.account.apikeyinfo.ApiKeyInfoResponse;
import com.beimin.eveapi.account.characters.EveCharacter;
import eve.corp.manager.common.dto.Connection;
import eve.corp.manager.common.dto.EveAlliance;
import eve.corp.manager.common.dto.EveCorporation;
import eve.corp.manager.common.enums.ConnectionOrigin;

import java.io.Serializable;
import java.util.*;

public class AuditedAPI implements Serializable {

    private static final long serialVersionUID = 8916961402623443030L;

    private AuditedApiStatus apiKeyInfo = new AuditedApiStatus();

    private AuditedAccountStatus accountStatus = new AuditedAccountStatus();

    private List<AuditedCharacter> accountCharacters = new ArrayList<>();

    private List<AuditedConnectedAlliance> connectedAlliances = new ArrayList<>();
    private List<AuditedConnectedCorporation> connectedCorporations = new ArrayList<>();
    private List<AuditedConnectedCharacter> connectedCharacters = new ArrayList<>();

    private List<AuditedConnectedAlliance> connectedBlackDuckAlliances = new ArrayList<>();
    private List<AuditedConnectedCorporation> connectedBlackDuckCorporations = new ArrayList<>();
    private List<AuditedConnectedCharacter> connectedBlackDuckCharacters = new ArrayList<>();

    public AuditedAPI() {
    }

    public void setApiStatus(ApiKeyInfoResponse info) {
        if (info != null) {
            this.apiKeyInfo.setAccessMask(info.getAccessMask());
            this.apiKeyInfo.setExpires(info.getExpires());
            this.apiKeyInfo.setType(info.getType().toString());
        }
    }

    public void setAccountStatus(EveAccountStatus eas) {
        this.accountStatus.setCreateDate(eas.getCreateDate());
        this.accountStatus.setPaidUntil(eas.getPaidUntil());
        this.accountStatus.setLogonCount(eas.getLogonCount());
        int logonJours = eas.getLogonMinutes() / 1440;
        int logonHeures = (eas.getLogonMinutes() - logonJours * 24 * 60) / 60;
        int logonMinutes = eas.getLogonMinutes() - logonJours * 24 * 60 - logonHeures * 60;
        this.accountStatus.setLogonTime(logonJours + "j " + logonHeures + "h " + logonMinutes + "min");
    }

    public void setAccountCharacters(ApiKeyInfoResponse info, Map<Long, AuditedCharacter> charactersDirectory) {
        for (EveCharacter character : info.getEveCharacters()) {
            this.accountCharacters.add(charactersDirectory.get(character.getCharacterID()));
        }
    }

    public void setConnectedAlliances(Connection connections, Map<Long, EveAlliance> alliancesDirectory) {
        Map<Long, Set<ConnectionOrigin>> allianceConnections = connections.getAllianceConnections();

        for (Long allianceId : allianceConnections.keySet()) {

            AuditedConnectedAlliance newConnexion = new AuditedConnectedAlliance();
            newConnexion.setAllianceID(allianceId);
            if (alliancesDirectory.get(allianceId) != null) {
                newConnexion.setAllianceName(alliancesDirectory.get(allianceId).getAllianceName());
            }

            newConnexion.setOrigins(allianceConnections.get(allianceId));
            this.connectedAlliances.add(newConnexion);
        }
    }

    public void setConnectedCorporations(Connection connections, Map<Long, EveCorporation> corporationsDirectory) {
        Map<Long, Set<ConnectionOrigin>> corporationConnections = connections.getCorporationConnections();

        for (Long corporationId : corporationConnections.keySet()) {
            AuditedConnectedCorporation newConnexion = new AuditedConnectedCorporation();
            newConnexion.setCorporationID(corporationId);
            if (corporationsDirectory.get(corporationId) != null) {
                newConnexion.setCorporationName(corporationsDirectory.get(corporationId).getCorporationName());
            }

            newConnexion.setOrigins(corporationConnections.get(corporationId));
            this.connectedCorporations.add(newConnexion);
        }
    }

    public void setConnectedCharacters(Connection connections, Map<Long, AuditedCharacter> charactersDirectory) {
        Map<Long, Set<ConnectionOrigin>> charactersConnections = connections.getCharacterConnections();

        for (Long characterId : charactersConnections.keySet()) {
            AuditedConnectedCharacter newConnexion = new AuditedConnectedCharacter();
            newConnexion.setCharacterID(characterId);
            if (charactersDirectory.get(characterId) != null) {
                AuditedCharacter character = charactersDirectory.get(characterId);
                newConnexion.setCharacterName(character.getCharacterName());
                newConnexion.setCorporationID(character.getCorporationID());
                newConnexion.setCorporationName(character.getCorporationName());
                newConnexion.setAllianceID(character.getAllianceID());
                newConnexion.setAllianceName(character.getAllianceName());
                newConnexion.setSecurityStatus(character.getSecurityStatus());
            }

            newConnexion.setOrigins(charactersConnections.get(characterId));
            this.connectedCharacters.add(newConnexion);
        }
    }

    public void setConnectedBlackDuckAlliances(Set<Long> ducks, Connection connections, Map<Long, EveAlliance> alliancesDirectory) {

        for (Long allianceId : ducks) {
            AuditedConnectedAlliance newConnexion = new AuditedConnectedAlliance();
            newConnexion.setAllianceID(allianceId);
            if (alliancesDirectory.get(allianceId) != null) {
                newConnexion.setAllianceName(alliancesDirectory.get(allianceId).getAllianceName());
            }

            newConnexion.setOrigins(connections.getAllianceConnections().get(allianceId));
            this.connectedBlackDuckAlliances.add(newConnexion);
        }
    }

    public void setConnectedBlackDuckCorporations(Set<Long> ducks, Connection connections, Map<Long, EveCorporation> corporationsDirectory) {

        for (Long corporationId : ducks) {
            AuditedConnectedCorporation newConnexion = new AuditedConnectedCorporation();
            newConnexion.setCorporationID(corporationId);
            if (corporationsDirectory.get(corporationId) != null) {
                newConnexion.setCorporationName(corporationsDirectory.get(corporationId).getCorporationName());
            }

            newConnexion.setOrigins(connections.getCorporationConnections().get(corporationId));
            this.connectedBlackDuckCorporations.add(newConnexion);
        }
    }

    public void setConnectedBlackDuckCharacters(Set<Long> ducks, Connection connections, Map<Long, AuditedCharacter> charactersDirectory) {

        for (Long characterId : ducks) {
            AuditedConnectedCharacter newConnexion = new AuditedConnectedCharacter();
            newConnexion.setCharacterID(characterId);
            if (charactersDirectory.get(characterId) != null) {
                AuditedCharacter character = charactersDirectory.get(characterId);
                newConnexion.setCharacterName(character.getCharacterName());
                newConnexion.setCorporationID(character.getCorporationID());
                newConnexion.setCorporationName(character.getCorporationName());
                newConnexion.setAllianceID(character.getAllianceID());
                newConnexion.setAllianceName(character.getAllianceName());
                newConnexion.setSecurityStatus(character.getSecurityStatus());
            }

            newConnexion.setOrigins(connections.getCharacterConnections().get(characterId));
            this.connectedBlackDuckCharacters.add(newConnexion);
        }

    }

    public AuditedAccountStatus getAccountStatus() { return this.accountStatus; }

    public void setAccountStatus(AuditedAccountStatus accountStatus) { this.accountStatus = accountStatus; }

    public AuditedApiStatus getApiKeyInfo() { return this.apiKeyInfo; }

    public void setApiKeyInfo(AuditedApiStatus apiKeyInfo) { this.apiKeyInfo = apiKeyInfo; }

    public List<AuditedCharacter> getAccountCharacters() { return this.accountCharacters; }

    public void setAccountCharacters(List<AuditedCharacter> accountCharacters) {
        this.accountCharacters = accountCharacters;
    }

    public List<AuditedConnectedAlliance> getConnectedAlliances() { return this.connectedAlliances; }

    public void setConnectedAlliances(List<AuditedConnectedAlliance> connectedAlliances) {
        this.connectedAlliances = connectedAlliances;
    }

    public List<AuditedConnectedCorporation> getConnectedCorporations() {
        return this.connectedCorporations;
    }

    public void setConnectedCorporations(List<AuditedConnectedCorporation> connectedCorporations) {
        this.connectedCorporations = connectedCorporations;
    }

    public List<AuditedConnectedCharacter> getConnectedCharacters() {
        return this.connectedCharacters;
    }

    public void setConnectedCharacters(List<AuditedConnectedCharacter> connectedCharacters) {
        this.connectedCharacters = connectedCharacters;
    }

    public List<AuditedConnectedAlliance> getConnectedBlackDuckAlliances() {
        return this.connectedBlackDuckAlliances;
    }

    public void setConnectedBlackDuckAlliances(List<AuditedConnectedAlliance> connectedBlackDuckAlliances) {
        this.connectedBlackDuckAlliances = connectedBlackDuckAlliances;
    }

    public List<AuditedConnectedCorporation> getConnectedBlackDuckCorporations() {
        return this.connectedBlackDuckCorporations;
    }

    public void setConnectedBlackDuckCorporations(List<AuditedConnectedCorporation> connectedBlackDuckCorporations) {
        this.connectedBlackDuckCorporations = connectedBlackDuckCorporations;
    }

    public List<AuditedConnectedCharacter> getConnectedBlackDuckCharacters() {
        return this.connectedBlackDuckCharacters;
    }

    public void setConnectedBlackDuckCharacters(List<AuditedConnectedCharacter> connectedBlackDuckCharacters) {
        this.connectedBlackDuckCharacters = connectedBlackDuckCharacters;
    }
}
