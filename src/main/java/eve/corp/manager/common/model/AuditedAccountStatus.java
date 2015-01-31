package eve.corp.manager.common.model;

import java.io.Serializable;
import java.util.Date;

public class AuditedAccountStatus implements Serializable {

    private static final long serialVersionUID = -7686351596513851686L;

    private Date paidUntil;

    private Date createDate;

    private int logonCount;

    private String logonTime;

    public AuditedAccountStatus() {
    }

    public Date getPaidUntil() {
        return this.paidUntil;
    }

    public void setPaidUntil(Date paidUntil) {
        this.paidUntil = paidUntil;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getLogonCount() {
        return this.logonCount;
    }

    public void setLogonCount(int logonCount) {
        this.logonCount = logonCount;
    }

    public String getLogonTime() {
        return this.logonTime;
    }

    public void setLogonTime(String logonTime) {
        this.logonTime = logonTime;
    }
}
