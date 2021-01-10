package Entity;

import java.io.Serializable;

public class Message implements Serializable {

    // The content of this Entity.Message
    private String messageContent;
    // The username of sender of this Entity.Message
    private String senderUsername;
    // The username of recipient of this Entity.Message
    private String receiverUsername;
    // Whether this message is read or not
    private Boolean read;
    // Whether this message is archived or not.
    private Boolean archived;


    /**
     * Constructs a new Entity.Message.
     *
     * @param content - the content of this Entity.Message
     * @param sender - the sender of this Entity.Message
     * @param receiver - the recipient of this Entity.Message
     */
    public Message(String content, String sender, String receiver) {
        messageContent = content;
        senderUsername = sender;
        receiverUsername = receiver;
        read = false;
        archived = false;
    }

    /**
     * Changes the content of this Entity.Message
     * @param newContent - new content of this Entity.Message
     */
    public void setMessageContent(String newContent) {
        messageContent = newContent;
    }

    /**
     * Changes the sender of this Entity.Message
     * @param newSender - new sender of this Entity.Message
     */
    public void setSender(String newSender) {
        senderUsername = newSender;
    }

    /**
     * Changes the recipient of this Entity.Message
     * @param newReceiver - new receiver of this Entity.Message
     */
    public void setReceiver(String newReceiver) {
        receiverUsername = newReceiver;
    }

    /**
     * Fetches the content of the message.
     * @return the content of this Entity.Message.
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * Fetches the username of the sender of the message.
     * @return the sender of this Entity.Message.
     */
    public String getSender() {
        return senderUsername;
    }

    /**
     * Fetches the username of the receiver of the message.
     * @return the receiver of this Entity.Message
     */
    public String getReceiver() {
        return receiverUsername;
    }

    /**
     * Changes the status of this message to read.
     */
    public void readMessage() {read = true;}

    /**
     * Changes the status of this message to unread.
     */

    public void markAsUnread() {read = false;}

    /**
     * Fetches the status of this message.
     * @return the status of this Entity.Message.
     */
    public boolean getReadStatus() {return read;}

    /**
     * Archives this message.
     */
    public void archiveMessage() {
        archived = true;
    }

    /**
     * Unarchives this message.
     */
    public void unarchiveMessage() {archived = false;}

    /**
     * Fetches whether this message is archived or not.
     * @return true iff this message is archived
     */
    public boolean getArchivedStatus() {
        return archived;
    }
}
