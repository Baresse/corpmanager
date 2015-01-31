package eve.corp.manager.service;

import eve.corp.manager.common.model.AuditedAPI;

public interface IApiAudit {

    /**
     * Perform the EvE Full API audit in order to find black ducks.
     *
     * @param keyId parameter of the EvE Full API to be audited
     * @param vCode parameter of the EvE Full API to be audited
     * @return audit report
     */
    AuditedAPI performAudit(int keyId, String vCode);
}
