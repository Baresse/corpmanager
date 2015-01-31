package eve.corp.manager.common.enums;

/**
 * Track the origin of a connection
 */
public enum ConnectionOrigin {
    ACCOUNT,        // This character is one of the 3 characters own by the player in the audited API
    BLUE_CONTACT,   // This character is found as blue contact
    MAIL,           // This character is found in the mails either as sender or recipient of at least one mail
    CONTRACT,       // This character is found in the contracts / donations either as seller or buyer
    WALLET;         // This character is found in the wallet transactions either as sender or recipient

    private ConnectionOrigin() {
    }
}
