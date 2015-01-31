package eve.corp.manager.common.data;

import com.beimin.eveapi.shared.wallet.journal.ApiJournalEntry;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.util.Date;

@Entity
@NamedQuery(
        name = "JournalEntry.findByYearMonth",
        query = "SELECT j FROM JournalEntry j WHERE YEAR(j.dateEntry) = ?1 AND MONTH(j.dateEntry) = ?2"
)
public class JournalEntry {
    @Id
    private Long refID;
    private Date dateEntry;
    private Integer refTypeID;
    private String refType;
    private String ownerName1;
    private Long ownerID1;
    private String ownerName2;
    private Long ownerID2;
    private String argName1;
    private Long argID1;
    private Double amount;
    private Double balance;
    private String reason;
    private Long taxReceiverID;
    private Double taxAmount;

    public JournalEntry() {
    }

    public JournalEntry(ApiJournalEntry entry) {
        this.dateEntry = entry.getDate();
        this.refID = entry.getRefID();
        this.refTypeID = entry.getRefTypeID();
        this.refType = entry.getRefType().toString();
        this.ownerName1 = entry.getOwnerName1();
        this.ownerID1 = entry.getOwnerID1();
        this.ownerName2 = entry.getOwnerName2();
        this.ownerID2 = entry.getOwnerID2();
        this.argName1 = entry.getArgName1();
        this.argID1 = entry.getArgID1();
        this.amount = entry.getAmount();
        this.balance = entry.getBalance();
        this.reason = entry.getReason();
        this.taxReceiverID = entry.getTaxReceiverID();
        this.taxAmount = entry.getTaxAmount();
    }

    public Long getRefID() {
        return this.refID;
    }

    public void setRefID(Long refID) {
        this.refID = refID;
    }

    public Date getDateEntry() {
        return this.dateEntry;
    }

    public void setDateEntry(Date date) {
        this.dateEntry = date;
    }

    public Integer getRefTypeID() {
        return this.refTypeID;
    }

    public void setRefTypeID(Integer refTypeID) {
        this.refTypeID = refTypeID;
    }

    public String getRefType() {
        return this.refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getOwnerName1() {
        return this.ownerName1;
    }

    public void setOwnerName1(String ownerName1) {
        this.ownerName1 = ownerName1;
    }

    public Long getOwnerID1() {
        return this.ownerID1;
    }

    public void setOwnerID1(Long ownerID1) {
        this.ownerID1 = ownerID1;
    }

    public String getOwnerName2() {
        return this.ownerName2;
    }

    public void setOwnerName2(String ownerName2) {
        this.ownerName2 = ownerName2;
    }

    public Long getOwnerID2() {
        return this.ownerID2;
    }

    public void setOwnerID2(Long ownerID2) {
        this.ownerID2 = ownerID2;
    }

    public String getArgName1() {
        return this.argName1;
    }

    public void setArgName1(String argName1) {
        this.argName1 = argName1;
    }

    public Long getArgID1() {
        return this.argID1;
    }

    public void setArgID1(Long argID1) {
        this.argID1 = argID1;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBalance() {
        return this.balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getTaxReceiverID() {
        return this.taxReceiverID;
    }

    public void setTaxReceiverID(Long taxReceiverID) {
        this.taxReceiverID = taxReceiverID;
    }

    public Double getTaxAmount() {
        return this.taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }
}
