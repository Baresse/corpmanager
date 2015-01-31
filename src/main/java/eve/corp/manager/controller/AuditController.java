package eve.corp.manager.controller;

import eve.corp.manager.common.model.AuditedAPI;
import eve.corp.manager.service.IApiAudit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditController {

    private static final Logger logger = LoggerFactory.getLogger(AuditController.class);

    @Autowired
    private IApiAudit apiAuditService;

    @RequestMapping(
            value = {"/audit/{keyId}/{vCode}"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    public AuditedAPI auditAPI(@PathVariable int keyId, @PathVariable String vCode) {
        logger.info("auditAPI for : keyId=" + keyId + " and vCode=" + vCode);
        AuditedAPI result = this.apiAuditService.performAudit(keyId, vCode);
        logger.info("Done");
        logger.info("Found " + result.getConnectedCharacters().size() + " connected characters.");
        logger.info("Found " + result.getConnectedCorporations().size() + " connected corporations.");
        logger.info("Found " + result.getConnectedAlliances().size() + " connected alliances.");
        logger.info("Found " + result.getConnectedBlackDuckCharacters().size() + " connected BlackDuck characters.");
        logger.info("Found " + result.getConnectedBlackDuckCorporations().size() + " connected BlackDuck corporations.");
        logger.info("Found " + result.getConnectedBlackDuckAlliances().size() + " connected BlackDuck alliances.");
        return result;
    }
}
