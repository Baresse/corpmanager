package eve.corp.manager.common.model;

import java.io.Serializable;
import java.util.Date;

public class AuditedApiStatus implements Serializable {

    private static final long serialVersionUID = 1401729595734683132L;

    private long accessMask;

    private String type;

    private Date expires;

    public AuditedApiStatus() {
    }

    public long getAccessMask() {
        return this.accessMask;
    }

    public void setAccessMask(long accessMask) {
        this.accessMask = accessMask;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getExpires() {
        return this.expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
}
