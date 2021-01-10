package UseCase;

import Entity.Party;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class PartyManager implements Serializable {
    // list of parties
    private Set<Party> parties;

    /**
     * Creates a new UseCase.PartyManager with no parties
     */
    public PartyManager() {
        parties = new HashSet<>();
    }

    /**
     * Fetches all of the parties of this UseCase.PartyManager
     * @return The list of parties
     */
    public Set<Party> getParties() {
        return parties;
    }

    /**
     * Fetches the names of the parties managed by the UseCase.PartyManager
     * @return A list of the names of the parties
     */
    public Set<String> getPartyNames() {
        Set<String> names = new HashSet<>();

        for (Party party : getParties()) {
            names.add(party.getName());
        }

        return names;
    }

    /**
     * Retrieves a Entity.Party object with the given name
     * @param partyName The name of the party
     * @return The Entity.Party object with the given name
     */
    public Party getPartyWithName(String partyName) {
        for (Party party : getParties()) {
            if (party.getName().equals(partyName)) {
                return party;
            }
        }

        return null;
    }

    /**
     * Checks whether or not a party with the given name exists
     * @param partyName The name of the party
     * @return Returns true if and only if there is a party with the given name
     */
    public boolean hasParty(String partyName) {
        return getPartyNames().contains(partyName);
    }

    /**
     * Removes the party with the given name
     * @param partyName The name of the party
     */
    public void removeParty(String partyName) {
        Party party = getPartyWithName(partyName);

        if (party != null) {
            parties.remove(party);
        }
    }

    /**
     * Creates a party from the given UseCase.PartyBuilder
     * @param partyBuilder The UseCase.PartyBuilder to be used
     */
    public void createParty(PartyBuilder partyBuilder) {
        parties.add(partyBuilder.getInstance());
    }
}
