package eve.corp.manager.common.model;

import java.io.Serializable;

public class AuditedCharacter implements Serializable {

    private static final long serialVersionUID = 8439087603523118358L;

    private String characterName;

    private long characterID;

    private String corporationName;

    private long corporationID;

    private Long allianceID;

    private String allianceName;

    private double securityStatus;

    public AuditedCharacter() {
    }

    public String getCharacterName() {
        return this.characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public long getCharacterID() {
        return this.characterID;
    }

    public void setCharacterID(long characterID) {
        this.characterID = characterID;
    }

    public String getCorporationName() {
        return this.corporationName;
    }

    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName;
    }

    public long getCorporationID() {
        return this.corporationID;
    }

    public void setCorporationID(long corporationID) {
        this.corporationID = corporationID;
    }

    public Long getAllianceID() {
        return this.allianceID;
    }

    public void setAllianceID(Long allianceID) {
        this.allianceID = allianceID;
    }

    public String getAllianceName() {
        return this.allianceName;
    }

    public void setAllianceName(String allianceName) {
        this.allianceName = allianceName;
    }

    public double getSecurityStatus() {
        return this.securityStatus;
    }

    public void setSecurityStatus(double securityStatus) {
        this.securityStatus = securityStatus;
    }
}
