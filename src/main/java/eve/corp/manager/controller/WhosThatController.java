package eve.corp.manager.controller;

import com.beimin.eveapi.eve.character.*;
import com.beimin.eveapi.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WhosThatController {

    private static final Logger logger = LoggerFactory.getLogger(WhosThatController.class);

    @RequestMapping(
            value = {"/whosthat/{name}"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    public CharacterInfoResponse whosThatGuy(@PathVariable String name) {

        CharacterInfoResponse result = null;

        try {
            logger.info("Find Id for " + name + "...");
            CharacterLookupParser parser = CharacterLookupParser.getName2IdInstance();
            CharacterLookupResponse response = parser.getResponse(name);
            ApiCharacterLookup character = response.getAll().iterator().next();
            CharacterInfoParser parser2 = CharacterInfoParser.getInstance();
            result = parser2.getResponse(character.getCharacterID());
            logger.info("Done.");
        } catch (ApiException e) {
            logger.error(e.getMessage(), e);
        }

        return result;
    }
}
