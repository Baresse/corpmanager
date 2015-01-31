package eve.corp.manager.common.dto;

public class EveAlliance {

    private Long allianceID;

    private String allianceName;

    public EveAlliance() {
    }

    public EveAlliance(Long allianceID, String allianceName) {
        this.allianceID = allianceID;
        this.allianceName = allianceName;
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

    public String toString() {
        return "EveAlliance=[allianceID=" + this.allianceID + ", allianceName=" + this.allianceName + "]";
    }
}
