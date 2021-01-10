package UseCase;

import Entity.Message;

public class MessageBuilder {
    // The content of the message
    private String messageContent;
    // The username of the user sending the message
    private String senderUsername;
    // The username of the user receiving the message
    private String receiverUsername;

    /**
     * Constructs a new UseCase.MessageBuilder with default values
     *
     * Defaults:
     * - messageContent: ""
     * - senderUsername: ""
     * - receiverUsername: ""
     */
    public MessageBuilder() {
        messageContent = "";
        senderUsername = "";
        receiverUsername = "";
    }

    /**
     * Sets the message content of the builder
     * @param messageContent The content of the message
     */
    public void buildMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * Sets the sender username of the builder
     * @param senderUsername The username of the sender
     */
    public void buildSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    /**
     * Sets the receiver username of the builder
     * @param receiverUsername The username of the receiver
     */
    public void buildReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    /**
     * Creates a new Entity.Message object with the values of the UseCase.MessageBuilder
     * @return The newly created message
     */
    public Message getMessage() {
        Message message = new Message(messageContent, senderUsername, receiverUsername);

        return message;
    }
}
