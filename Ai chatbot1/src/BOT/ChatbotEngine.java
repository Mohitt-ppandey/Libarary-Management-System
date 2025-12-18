package BOT;

import java.util.*;
import java.util.List;
import java.util.regex.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Chatbot Engine Class
class ChatbotEngine {
    private String botName;
    private Map<String, String> knowledgeBase;
    private List<String> conversationHistory;
    private Random random;

    public ChatbotEngine(String name) {
        this.botName = name;
        this.knowledgeBase = new HashMap<>();
        this.conversationHistory = new List<String>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<String> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(String s) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends String> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends String> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public String get(int index) {
                return "";
            }

            @Override
            public String set(int index, String element) {
                return "";
            }

            @Override
            public void add(int index, String element) {

            }

            @Override
            public String remove(int index) {
                return "";
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<String> listIterator() {
                return null;
            }

            @Override
            public ListIterator<String> listIterator(int index) {
                return null;
            }

            @Override
            public List<String> subList(int fromIndex, int toIndex) {
                return List.of();
            }
        };
        this.random = new Random();
        initializeKnowledgeBase();
    }

    private void initializeKnowledgeBase() {
        knowledgeBase.put("(?i).*\\b(hi|hello|hey|greetings)\\b.*",
                "Hello! How can I help you today?|Hi there! What can I do for you?|Hey! Nice to meet you!");

        knowledgeBase.put("(?i).*how are you.*",
                "I'm doing great, thanks for asking! How about you?|I'm functioning perfectly! How can I assist you?|Excellent! Ready to help you today!");

        knowledgeBase.put("(?i).*what.*(your name|you called).*",
                "My name is " + botName + ". Nice to meet you!|I'm " + botName + ", your AI assistant!");

        knowledgeBase.put("(?i).*(what time|current time|time now).*",
                "TIME_REQUEST");

        knowledgeBase.put("(?i).*(what date|today's date|current date).*",
                "DATE_REQUEST");

        knowledgeBase.put("(?i).*(help|assist|support).*",
                "I can help you with various things! Try asking me about the time, date, weather, or just chat with me!|I'm here to assist! Ask me questions or just have a conversation!");

        knowledgeBase.put("(?i).*(weather|temperature).*",
                "I'm a simple chatbot and can't check real weather, but I hope it's nice where you are!|Weather information requires internet access, but I hope you're having a pleasant day!");

        knowledgeBase.put("(?i).*(thank|thanks|appreciate).*",
                "You're welcome! Happy to help!|No problem at all!|Glad I could help!");

        knowledgeBase.put("(?i).*(bye|goodbye|see you).*",
                "Goodbye! Have a great day!|See you later! Take care!|Bye! Come back anytime!");

        knowledgeBase.put("(?i).*how old are you.*",
                "I'm an AI, so I don't age like humans do! But I'm here to help you.|Age is just a number for an AI like me!");

        knowledgeBase.put("(?i).*what can you do.*",
                "I can chat with you, answer questions, tell you the time and date, and much more! Just ask!|I'm here to have conversations and help answer your questions!");

        knowledgeBase.put("(?i).*(joke|funny|laugh).*",
                "Why did the programmer quit his job? Because he didn't get arrays! 😄|What do you call a programmer from Finland? Nerdic!|Why do Java developers wear glasses? Because they don't C#!");
    }

    public String getResponse(String userInput) {
        conversationHistory.add("User: " + userInput);

        for (Map.Entry<String, String> entry : knowledgeBase.entrySet()) {
            Pattern pattern = Pattern.compile(entry.getKey());
            Matcher matcher = pattern.matcher(userInput);

            if (matcher.matches()) {
                String response = entry.getValue();

                if (response.equals("TIME_REQUEST")) {
                    response = getCurrentTime();
                } else if (response.equals("DATE_REQUEST")) {
                    response = getCurrentDate();
                } else {
                    response = getRandomResponse(entry.getKey());
                }

                conversationHistory.add(botName + ": " + response);
                return response;
            }
        }

        String[] defaultResponses = {
                "That's interesting! Tell me more.",
                "I see. Can you elaborate on that?",
                "Hmm, I'm not sure I understand completely. Could you rephrase?",
                "That's a good point! What else would you like to know?",
                "I'm still learning! Can you ask me something else?"
        };

        String response = defaultResponses[random.nextInt(defaultResponses.length)];
        conversationHistory.add(botName + ": " + response);
        return response;
    }

    private String getRandomResponse(String pattern) {
        String responses = knowledgeBase.get(pattern);
        String[] options = responses.split("\\|");
        return options[random.nextInt(options.length)];
    }

    private String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return "The current time is " + now.format(formatter);
    }

    private String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
        return "Today's date is " + now.format(formatter);
    }

    public List<String> getConversationHistory() {
        return conversationHistory;
    }

    public String getBotName() {
        return botName;
    }
}