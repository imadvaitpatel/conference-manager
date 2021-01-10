package UseCase;

import Entity.Talk;

public class TalkBuilder extends EventBuilder {

    // The speaker giving the talk
    private String speaker;

    /**
     * Constructs a new UseCase.TalkBuilder instance with default values.
     *
     * Defaults:
     *  - Entity.Speaker: ""
     */
    public TalkBuilder() {
        super();

        speaker = "";
    }

    /**
     * Sets the speaker of the builder to the given speaker
     * @param speaker The name of the speaker giving the talk
     */
    public void buildSpeaker(String speaker) {
        this.speaker = speaker;
    }

    @Override
    public Talk getInstance() {
        Talk talk = new Talk(name, dateAndTime, roomCode, speaker, capacity);
        talk.setVipEvent(isVip);

        return talk;
    }
}
