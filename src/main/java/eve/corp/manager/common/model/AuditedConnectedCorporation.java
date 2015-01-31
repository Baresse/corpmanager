package eve.corp.manager.common.model;

import java.io.Serializable;

public class AuditedConnectedCorporation extends Connected implements Serializable {

    private static final long serialVersionUID = 4593260061975981784L;

    private String corporationName;

    private long corporationID;

    private Long allianceID;

    private String allianceName;

    public AuditedConnectedCorporation() {
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
}
