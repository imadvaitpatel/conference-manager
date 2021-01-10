package UseCase;

import Entity.Event;
import Entity.Message;

import java.io.Serializable;
import java.util.*;

public class MessageManager implements Serializable {

    private List<Message> allMessages = new ArrayList<>();

    /**
     * Create a new UseCase.MessageManager with no messages.
     */
    public MessageManager(){}

    /**
     * Create a new message and store it.
     * @param messageContent - Content of message being created.
     * @param sender - Entity.User that is sending the message.
     * @param receiver - Entity.User that is receiving the message.
     */
    public void messageUser(String messageContent, String sender, String receiver){
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.buildMessageContent(messageContent);
        messageBuilder.buildSenderUsername(sender);
        messageBuilder.buildReceiverUsername(receiver);

        Message message = messageBuilder.getMessage();

        allMessages.add(message);
    }

    /**
     * Respond to a message that a Entity.User has received with a new message
     * @param newMessageContent - The content of the response message.
     * @param receivedMessageId - The id of the message the Entity.User is responding to.
     */
    public void respondToMessage(String newMessageContent, Integer receivedMessageId){
        Message receivedMessage = allMessages.get(receivedMessageId);
        receivedMessage.readMessage();
        String recipientUsername = receivedMessage.getSender();
        String senderUsername = receivedMessage.getReceiver();

        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.buildMessageContent(newMessageContent);
        messageBuilder.buildSenderUsername(senderUsername);
        messageBuilder.buildReceiverUsername(recipientUsername);

        Message message = messageBuilder.getMessage();
        allMessages.add(message);
    }

    /**
     * Organizer sends a message to all attendees of an event.
     * Precondition: Either confirmed organizer or speaker.
     * @param messageContent - the content of the message sent to all attendees.
     * @param event - the event that the organizer/speaker is messaging the attendees of.
     * @param userName - the username of the organizer/speaker sending the message.
     */
    public void messageEventAttendees(String messageContent, Event event, String userName){
        Set<String> attendees = event.getAttendees();
        for (String attendee : attendees){
            messageUser(messageContent, userName, attendee);
        }
    }

    /**
     * Retrieves a list of "threads" user has with other users.
     * @param user - the user you are retrieving the list for
     * @return list of strings that compose a thread
     */
    public List<String> getExistingThreads(String user) {
        ArrayList<String> threads = new ArrayList<>();

        for (int i = 0; i < allMessages.size(); i++) {
            Message message = allMessages.get(i);
            if (message.getReceiver().equals(user)) {
                threads.remove(message.getSender());
                threads.add(message.getSender());
            } else if (message.getSender().equals(user)) {
                threads.remove(message.getReceiver());
                threads.add(message.getReceiver());
            }
        }

        return threads;
    }

    /**
     * Retrieves a specific conversation thread between two users.
     * @param mainUser - the user accessing this method
     * @param otherUser - the other user in the conversation thread
     * @return list of the messages between these two users
     */
    private List<Message> getThread(String mainUser, String otherUser) {
        ArrayList<Message> thread = new ArrayList<>();

        for (int i = 0; i < allMessages.size(); i++) {
            Message message = allMessages.get(i);

            if (i == allMessages.size() - 1) {
                message.markAsUnread();
            }


            boolean otherToMain = message.getSender().equals(otherUser) && message.getReceiver().equals(mainUser);
            boolean mainToOther = message.getSender().equals(mainUser) && message.getReceiver().equals(otherUser);


            if (otherToMain || mainToOther) {
                thread.add(message);
            }
        }

        return thread;
    }

    /**
     * Retrieves the string version of the messages between two users in a conversation thread.
     * @param mainUser - the user accessing this method
     * @param otherUser - the other user in the conversation thread
     * @return list of messages in string form
     */
    public List<String[]> getThreadContents(String mainUser, String otherUser) {
        ArrayList<String[]> thread = new ArrayList<>();

            for (int i = 0; i < allMessages.size(); i++) {
                Message message = allMessages.get(i);

                boolean otherToMain = message.getSender().equals(otherUser) && message.getReceiver().equals(mainUser);
                boolean mainToOther = message.getSender().equals(mainUser) && message.getReceiver().equals(otherUser);


                if (otherToMain || mainToOther) {
                    thread.add(new String[]{String.valueOf(i), message.getSender(), message.getMessageContent(), String.valueOf(message.getReadStatus()), String.valueOf(message.getArchivedStatus())});
                }
            }

        return thread;
    }

    /**
     * Retrieves the last message between these two users in the thread.
     * @param mainUser - the user accessing this method
     * @param otherUser - the other user in the conversation thread
     * @return last message in thread between two users
     */
    public String[] getLastMessageFromThread(String mainUser, String otherUser) {
        List<String[]> thread = getThreadContents(mainUser, otherUser);
        return thread.get(thread.size()-1);
    }

    /**
     * Sends a response message to a thread.
     * @param mainUser - the user accessing this method
     * @param otherUser - the other user in the conversation thread
     * @param newMessageContent - the message content of the response
     */
    public void respondToThread(String mainUser, String otherUser, String newMessageContent) {
        Message lastMessage = null;

        for (int i = 0; i < allMessages.size(); i++) {
            Message message = allMessages.get(i);

            boolean otherToMain = message.getSender().equals(otherUser) && message.getReceiver().equals(mainUser);
            boolean mainToOther = message.getSender().equals(mainUser) && message.getReceiver().equals(otherUser);

            if (otherToMain || mainToOther) {
                lastMessage = message;
            }
        }

        if (lastMessage != null) {
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.buildMessageContent(newMessageContent);
            messageBuilder.buildSenderUsername(mainUser);
            messageBuilder.buildReceiverUsername(otherUser);

            Message message = messageBuilder.getMessage();
            allMessages.add(message);
        }
    }

    /**
     * Marks a thread as read.
     * @param mainUser - the user accessing this method
     * @param otherUser - the other user in the conversation thread
     */
    public void markThreadAsRead(String mainUser, String otherUser) {
        List<Message> thread = getThread(mainUser, otherUser);

        for (Message message : thread) {
            if (message.getReceiver().equals(mainUser)) {
                message.readMessage();
            }
        }
    }

    /**
     * Marks a message as read.
     * @param messageId - the id of the message that is being marked as read.
     */
    public void markAsRead(int messageId) {
        Message message = allMessages.get(messageId);
        message.readMessage();
    }

    /**
     * Marks a message as unread.
     * @param messageId - the id of the message that is being marked as unread.
     */
    public void markAsUnread(int messageId) {
        Message message = allMessages.get(messageId);
        message.markAsUnread();
    }

    /**
     * Archives a message.
     * @param messageId - the id of the message that is being marked as archived.
     */
    public void archiveMessage(int messageId) {
        Message message = allMessages.get(messageId);
        if (message != null) {
            message.archiveMessage();
        }
    }

    /**
     * Unarchives a message.
     * @param messageId - the id of the message that is being unarchived.
     */
    public void unArchiveMessage(int messageId) {
        Message message = allMessages.get(messageId);
        if (message != null) {
            message.unarchiveMessage();
        }
    }

    /**
     * Deletes a message from the list of all messages.
     * @param messageId - the id of the message that is being deleted.
     */
    public void deleteMessage(int messageId) {
        allMessages.remove(messageId);
    }
}
