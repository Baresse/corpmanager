package eve.corp.manager.controller;

import eve.corp.manager.common.data.JournalEntry;
import eve.corp.manager.common.enums.AccountKey;
import eve.corp.manager.service.IWallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/wallet"})
public class WalletController {

    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private IWallet walletService;

    @RequestMapping(
            value = {"/journal"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    public List<JournalEntry> getJournal() {
        logger.info("Retrieve all fetched wallet journal");
        return this.walletService.getAllJournalEntries();
    }

    @RequestMapping(
            value = {"/journal/{monthStr}"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    public List<JournalEntry> getJournal(@PathVariable String monthStr) {
        logger.info("Retrieve wallet journal for wallet during the month :" + monthStr);
        return this.walletService.getJournalEntriesByMonth(monthStr);
    }

    @RequestMapping(
            value = {"/journal/fetch/{accountKey}"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    public void fetchWalletAPI(@PathVariable AccountKey accountKey) {
        logger.info("Retrieve wallet journal from Eve API for wallet :" + accountKey);
        this.walletService.fetchCorporationWalletJournal(accountKey);
    }
}
