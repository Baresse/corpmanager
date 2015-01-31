package eve.corp.manager.common.model;

import java.io.Serializable;

public class AuditedConnectedAlliance extends Connected implements Serializable {

    private static final long serialVersionUID = -4601748774114384572L;

    private Long allianceID;

    private String allianceName;

    public AuditedConnectedAlliance() {
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
}
