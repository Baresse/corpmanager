package eve.corp.manager.common;

import com.beimin.eveapi.core.ApiAuthorization;

public class CommonConstants {

    public static final String APPLICATION_JSON = "application/json";

    /**
     * Build a Corporation API authorization used to find corporation standings and access to corporation wallets API
     *
     * @param corpoCharacterId CharacterId of a corporation director in the provided EvE API in the application.properties
     * @param corpoKeyId keyId parameter of the provided EvE API in the application.properties
     * @param corpoVcode vCode parameter of the provided EvE API in the application.properties
     * @return Corporation API authorization
     */
    public static ApiAuthorization getCorporationApiAuthorization(int corpoCharacterId, int corpoKeyId, String corpoVcode) {
        ApiAuthorization auth = new ApiAuthorization(corpoKeyId, corpoVcode);
        auth.setCharacterID((long) corpoCharacterId);
        return auth;
    }
}
