package eve.corp.manager.common.enums;

/**
 * Account Key to used to query Wallet API.
 */
public enum AccountKey {
    DIVISION_1(1000),  // Corporation master wallet
    DIVISION_2(1001),
    DIVISION_3(1002),
    DIVISION_4(1003),
    DIVISION_5(1004),
    DIVISION_6(1005),
    DIVISION_7(1006);

    private int key;

    private AccountKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}
