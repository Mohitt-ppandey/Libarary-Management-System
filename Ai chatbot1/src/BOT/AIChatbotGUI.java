package BOT;

import java.util.*;
import java.util.List;
import java.util.regex.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class AIChatbotGUI extends JFrame {
    private ChatbotEngine engine;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JButton clearButton;
    private JButton historyButton;

    public AIChatbotGUI() {
        engine = new ChatbotEngine("JavaBot");
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle(engine.getBotName() + " - AI Chatbot");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                Color c1 = new Color(135, 206, 250);
                Color c2 = new Color(70, 130, 180);
                GradientPaint gp = new GradientPaint(0, 0, c1, 0, h, c2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        JLabel titleLabel = new JLabel("🤖 " + engine.getBotName() + " AI Assistant");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setMargin(new Insets(10, 10, 10, 10));
        chatArea.setBackground(new Color(248, 249, 250));

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
        inputPanel.setOpaque(false);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sendButton.setBackground(new Color(25, 135, 84));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setBorderPainted(false);
        sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sendButton.setPreferredSize(new Dimension(80, 40));

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));

        clearButton = new JButton("Clear Chat");
        clearButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        clearButton.setBackground(new Color(220, 53, 69));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorderPainted(false);
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        historyButton = new JButton("Show History");
        historyButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        historyButton.setBackground(new Color(13, 110, 253));
        historyButton.setForeground(Color.WHITE);
        historyButton.setFocusPainted(false);
        historyButton.setBorderPainted(false);
        historyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(clearButton);
        buttonPanel.add(historyButton);

        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(inputPanel, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Welcome message
        appendMessage("Bot", "Hello! I'm " + engine.getBotName() + ". How can I help you today?");

        // Event listeners
        sendButton.addActionListener(e -> sendMessage());

        inputField.addActionListener(e -> sendMessage());

        clearButton.addActionListener(e -> {
            chatArea.setText("");
            appendMessage("Bot", "Chat cleared! How can I help you?");
        });

        historyButton.addActionListener(e -> showHistory());

        // Button hover effects
        addHoverEffect(sendButton, new Color(25, 135, 84), new Color(20, 108, 67));
        addHoverEffect(clearButton, new Color(220, 53, 69), new Color(176, 42, 55));
        addHoverEffect(historyButton, new Color(13, 110, 253), new Color(10, 88, 202));
    }

    private void addHoverEffect(JButton button, Color normal, Color hover) {
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hover);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(normal);
            }
        });
    }

    private void sendMessage() {
        String userInput = inputField.getText().trim();

        if (userInput.isEmpty()) {
            return;
        }

        appendMessage("You", userInput);
        inputField.setText("");

        // Simulate thinking delay
        Timer timer = new Timer(500, e -> {
            String response = engine.getResponse(userInput);
            appendMessage("Bot", response);
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void appendMessage(String sender, String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

        if (sender.equals("You")) {
            chatArea.append("[" + timestamp + "] You: " + message + "\n");
        } else {
            chatArea.append("[" + timestamp + "] " + engine.getBotName() + ": " + message + "\n");
        }

        chatArea.append("\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private void showHistory() {
        List<String> history = engine.getConversationHistory();

        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No conversation history yet!",
                    "History",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String entry : history) {
            sb.append(entry).append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this,
                scrollPane,
                "Conversation History",
                JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            AIChatbotGUI gui = new AIChatbotGUI();
            gui.setVisible(true);
        });
    }
}