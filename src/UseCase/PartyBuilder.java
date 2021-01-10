package UseCase;

import Entity.Party;

public class PartyBuilder extends EventBuilder {
    @Override
    public Party getInstance() {
        Party party = new Party(name, dateAndTime, roomCode, capacity);
        party.setVipEvent(isVip);

        return party;
    }
}
