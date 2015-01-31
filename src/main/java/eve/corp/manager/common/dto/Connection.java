package eve.corp.manager.common.dto;

import eve.corp.manager.common.enums.ConnectionOrigin;
import eve.corp.manager.common.model.AuditedCharacter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The class is used to keep a track of any connection found in the EVE API.
 * <p>
 * There are 3 types of connections : characterConnections, corporationConnections and allianceConnections.
 * Note a Character found as a connection by the audit will in general add a Corporation connection (his corporation)
 * and eventually an alliance connection (should his corp belongs to an alliance).
 */
public class Connection {

    /**
     * Map of character connections with characterId as Key and a ConnectionOrigin as value
     */
    private Map<Long, Set<ConnectionOrigin>> characterConnections = new HashMap<>();

    /**
     * Map of corporation connections with characterId as Key and a ConnectionOrigin as value
     */
    private Map<Long, Set<ConnectionOrigin>> corporationConnections = new HashMap<>();

    /**
     * Map of alliance connections with characterId as Key and a ConnectionOrigin as value
     */
    private Map<Long, Set<ConnectionOrigin>> allianceConnections = new HashMap<>();

    /**
     * Default constructor
     */
    public Connection() {
    }

    //-----------------------  Getters / Setters
    public Map<Long, Set<ConnectionOrigin>> getCorporationConnections() { return this.corporationConnections; }

    public Map<Long, Set<ConnectionOrigin>> getCharacterConnections() { return this.characterConnections; }

    public Map<Long, Set<ConnectionOrigin>> getAllianceConnections() { return this.allianceConnections; }

    /**
     * Add an AuditedCharacted to the connections found during the audit.
     *
     * @param character AuditedCharacted to be added as a (new) connection
     * @param origin Orign of the connection for this AuditedCharacted
     */
    public void addConnection(AuditedCharacter character, ConnectionOrigin origin) {
        if (character != null) {
            long characterId = character.getCharacterID();
            if (characterId != 0L) {
                Set<ConnectionOrigin> corporationIDs;
                if (!this.characterConnections.containsKey(characterId)) {
                    corporationIDs = new HashSet<>();
                    this.characterConnections.put(characterId, corporationIDs);
                } else {
                    corporationIDs = this.characterConnections.get(characterId);
                }

                corporationIDs.add(origin);
            }

            long corporationId = character.getCorporationID();
            if (corporationId != 0L) {
                Set<ConnectionOrigin> allianceIDs;
                if (!this.corporationConnections.containsKey(corporationId)) {
                    allianceIDs = new HashSet<>();
                    this.corporationConnections.put(corporationId, allianceIDs);
                } else {
                    allianceIDs = this.corporationConnections.get(corporationId);
                }

                allianceIDs.add(origin);
            }

            Long allianceId = character.getAllianceID();
            if (allianceId != null) {
                Set<ConnectionOrigin> allianceOrigins;
                if (!this.allianceConnections.containsKey(allianceId)) {
                    allianceOrigins = new HashSet<>();
                    this.allianceConnections.put(allianceId, allianceOrigins);
                } else {
                    allianceOrigins = this.allianceConnections.get(allianceId);
                }

                allianceOrigins.add(origin);
            }
        }
    }
}
