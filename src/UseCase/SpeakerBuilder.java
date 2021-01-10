package UseCase;

import Entity.Speaker;

public class SpeakerBuilder extends UserBuilder {

    @Override
    public Speaker getInstance() {
        return new Speaker(username, password, permissionLevel);
    }
}
