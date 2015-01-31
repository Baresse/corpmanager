package eve.corp.manager.common.dto;

public class EveCorporation {

    private long corporationID;

    private String corporationName;

    public EveCorporation() {
    }

    public EveCorporation(long corporationId, String name) {
        this.corporationID = corporationId;
        this.corporationName = name;
    }

    public long getCorporationID() {
        return this.corporationID;
    }

    public void setCorporationID(long corporationID) {
        this.corporationID = corporationID;
    }

    public String getCorporationName() {
        return this.corporationName;
    }

    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName;
    }

    public String toString() {
        return "EveCorporation=[corporationID=" + this.corporationID + ", corporationName=" + this.corporationName + "]";
    }
}
