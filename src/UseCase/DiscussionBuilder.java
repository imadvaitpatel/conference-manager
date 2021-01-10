package UseCase;

import Entity.Discussion;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DiscussionBuilder extends EventBuilder {

    // The speakers at this discussion
    private Set<String> speakers;

    /**
     * Constructs a new UseCase.DiscussionBuilder object with default values.
     *
     * Defaults:
     * - speakers: {}
     */
    public DiscussionBuilder() {
        super();

        speakers = new HashSet<>();
    }

    /**
     * Adds a collection of speakers to the builder
     * @param speakers The names of the speakers at the discussion
     */
    public void addSpeakers(Collection<String> speakers) {
        this.speakers.addAll(speakers);
    }

    /**
     * Builds a new Discussion object with the built parameters
     * @return The built discussion
     */
    @Override
    public Discussion getInstance() {
        Discussion discussion = new Discussion(name, dateAndTime, roomCode, speakers, capacity);
        discussion.setVipEvent(isVip);

        return discussion;
    }
}
