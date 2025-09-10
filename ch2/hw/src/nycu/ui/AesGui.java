package nycu.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import static nycu.core.AesCore.*;
public class AesGui extends JFrame {

    // all your UI components here
    private JTextField keyFilePathField;
    private JTextField dataDirField;
    private JTextField cipherDirField;
    private JTextArea logArea;
    private JButton encryptButton;
    private JButton decryptButton;
    private JComboBox<String> keyLengthComboBox;
    private JCheckBox useEnvVarsCheckBox;
    private JLabel envStatusLabel;

    public AesGui() {
        initializeGUI();
        checkAndUpdateEnvironmentVariables();
    }

    // ================= GUI related methods =================
    private void initializeGUI() {
        setTitle("NYCU AESer - GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Top config panel
        JPanel configPanel = createConfigPanel();

        // Middle button panel
        JPanel buttonPanel = createButtonPanel();

        // Bottom log panel
        JPanel logPanel = createLogPanel();

        mainPanel.add(configPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(logPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createConfigPanel() {
        JPanel configPanel = new JPanel(new GridBagLayout());
        configPanel.setBorder(BorderFactory.createTitledBorder("Configuration"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // AES Key File Path
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        configPanel.add(new JLabel("AES Key File:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        keyFilePathField = new JTextField();
        configPanel.add(keyFilePathField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton browseKeyButton = new JButton("Browse");
        browseKeyButton.addActionListener(e -> browseForKeyFile());
        configPanel.add(browseKeyButton, gbc);

        // Data Directory
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        configPanel.add(new JLabel("Data Directory:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        dataDirField = new JTextField();
        configPanel.add(dataDirField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton browseDataButton = new JButton("Browse");
        browseDataButton.addActionListener(e -> browseForDirectory(dataDirField, "Select Data Directory"));
        configPanel.add(browseDataButton, gbc);

        // Cipher Directory
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        configPanel.add(new JLabel("Cipher Directory:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cipherDirField = new JTextField();
        configPanel.add(cipherDirField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton browseCipherButton = new JButton("Browse");
        browseCipherButton.addActionListener(e -> browseForDirectory(cipherDirField, "Select Cipher Directory"));
        configPanel.add(browseCipherButton, gbc);

        // Environment Variables Status and Control
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        configPanel.add(new JLabel("Environment Variables:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JPanel envPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        useEnvVarsCheckBox = new JCheckBox("Auto Use Environment Variables");
        useEnvVarsCheckBox.addActionListener(e -> toggleEnvironmentVariables());
        envPanel.add(useEnvVarsCheckBox);

        envStatusLabel = new JLabel();
        envStatusLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        envPanel.add(envStatusLabel);

        configPanel.add(envPanel, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton refreshEnvButton = new JButton("Refresh");
        refreshEnvButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        refreshEnvButton.addActionListener(e -> checkAndUpdateEnvironmentVariables());
        configPanel.add(refreshEnvButton, gbc);

        // Key Length Selection
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        configPanel.add(new JLabel("Key Length:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        keyLengthComboBox = new JComboBox<>(new String[]{"16 chars", "24 chars", "32 chars"});
        keyLengthComboBox.setSelectedIndex(2); // Default to 32 chars
        configPanel.add(keyLengthComboBox, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        configPanel.add(new JLabel(""), gbc); // Empty label for alignment

        return configPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        encryptButton = new JButton("Encrypt Files");
        encryptButton.setPreferredSize(new Dimension(120, 40));
        encryptButton.addActionListener(new EncryptActionListener());

        decryptButton = new JButton("Decrypt Files");
        decryptButton.setPreferredSize(new Dimension(120, 40));
        decryptButton.addActionListener(new DecryptActionListener());

        JButton generateKeyButton = new JButton("Generate Key");
        generateKeyButton.setPreferredSize(new Dimension(120, 40));
        generateKeyButton.addActionListener(e -> generateNewKey());

        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        buttonPanel.add(generateKeyButton);

        return buttonPanel;
    }

    private JPanel createLogPanel() {
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("Log"));

        logArea = new JTextArea(10, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(logArea);
        logPanel.add(scrollPane, BorderLayout.CENTER);

        JButton clearLogButton = new JButton("Clear Log");
        clearLogButton.addActionListener(e -> logArea.setText(""));
        logPanel.add(clearLogButton, BorderLayout.SOUTH);

        return logPanel;
    }

    // Check and update environment variable status
    private void checkAndUpdateEnvironmentVariables() {
        String keyPath = System.getenv(AES_KEY_FILE_PATH_ENV_NAME);
        String dataDir = System.getenv(AES_DATA_DIR_ENV_NAME);
        String cipherDir = System.getenv(AES_CIPHER_DIR_ENV_NAME);

        int envCount = 0;
        if (keyPath != null && !keyPath.trim().isEmpty()) {
            envCount++;
        }
        if (dataDir != null && !dataDir.trim().isEmpty()) {
            envCount++;
        }
        if (cipherDir != null && !cipherDir.trim().isEmpty()) {
            envCount++;
        }

        // Update status label
        if (envCount == 3) {
            envStatusLabel.setText("(3/3✓)");
            envStatusLabel.setForeground(new Color(0, 128, 0)); // Green
            useEnvVarsCheckBox.setSelected(true);
            appendLog("Detected all environment variables set");
        } else if (envCount > 0) {
            envStatusLabel.setText("(" + envCount + "/3)");
            envStatusLabel.setForeground(new Color(255, 140, 0)); // Orange
            useEnvVarsCheckBox.setSelected(false);
            appendLog("Detected partial environment variables set (" + envCount + "/3)");
        } else {
            envStatusLabel.setText("(0/3)");
            envStatusLabel.setForeground(Color.RED);
            useEnvVarsCheckBox.setSelected(false);
            appendLog("No environment variables detected");
        }

        // If checkbox is selected, auto fill from env vars
        if (useEnvVarsCheckBox.isSelected()) {
            applyEnvironmentVariables();
        }
    }

    // Apply environment variables to input fields
    private void applyEnvironmentVariables() {
        String keyPath = System.getenv(AES_KEY_FILE_PATH_ENV_NAME);
        String dataDir = System.getenv(AES_DATA_DIR_ENV_NAME);
        String cipherDir = System.getenv(AES_CIPHER_DIR_ENV_NAME);

        if (keyPath != null && !keyPath.trim().isEmpty()) {
            keyFilePathField.setText(keyPath);
        }
        if (dataDir != null && !dataDir.trim().isEmpty()) {
            dataDirField.setText(dataDir);
        }
        if (cipherDir != null && !cipherDir.trim().isEmpty()) {
            cipherDirField.setText(cipherDir);
        }

        appendLog("Applied environment variable settings to input fields");
    }

    // Toggle environment variable usage
    private void toggleEnvironmentVariables() {
        if (useEnvVarsCheckBox.isSelected()) {
            // User chooses to use env vars
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to overwrite current input fields with environment variables?",
                    "Confirm Use of Environment Variables",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                applyEnvironmentVariables();
            } else {
                useEnvVarsCheckBox.setSelected(false);
            }
        } else {
            // User chooses not to use env vars
            appendLog("Disabled auto-loading of environment variables, will use manual input paths");
        }
    }

    // Get effective key file path (prefer user input, then env var)
    private String getEffectiveKeyPath() {
        String userInput = keyFilePathField.getText().trim();

        // If user input exists and is not from env var, prefer user input
        if (!userInput.isEmpty()) {
            String envPath = System.getenv(AES_KEY_FILE_PATH_ENV_NAME);
            if (envPath == null || !userInput.equals(envPath)) {
                appendLog("Using user-specified key path: " + userInput);
                return userInput;
            }
        }

        // If checkbox is selected and env var exists
        if (useEnvVarsCheckBox.isSelected()) {
            String envPath = System.getenv(AES_KEY_FILE_PATH_ENV_NAME);
            if (envPath != null && !envPath.trim().isEmpty()) {
                appendLog("Using environment variable key path: " + envPath);
                return envPath;
            }
        }

        return userInput; // May be empty
    }

    // Get effective data directory path
    private String getEffectiveDataDir() {
        String userInput = dataDirField.getText().trim();

        if (!userInput.isEmpty()) {
            String envPath = System.getenv(AES_DATA_DIR_ENV_NAME);
            if (envPath == null || !userInput.equals(envPath)) {
                return userInput;
            }
        }

        if (useEnvVarsCheckBox.isSelected()) {
            String envPath = System.getenv(AES_DATA_DIR_ENV_NAME);
            if (envPath != null && !envPath.trim().isEmpty()) {
                return envPath;
            }
        }

        return userInput;
    }

    // Get effective cipher directory path
    private String getEffectiveCipherDir() {
        String userInput = cipherDirField.getText().trim();

        if (!userInput.isEmpty()) {
            String envPath = System.getenv(AES_CIPHER_DIR_ENV_NAME);
            if (envPath == null || !userInput.equals(envPath)) {
                return userInput;
            }
        }

        if (useEnvVarsCheckBox.isSelected()) {
            String envPath = System.getenv(AES_CIPHER_DIR_ENV_NAME);
            if (envPath != null && !envPath.trim().isEmpty()) {
                return envPath;
            }
        }

        return userInput;
    }

    private void browseForKeyFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select AES Key File");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            keyFilePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void browseForDirectory(JTextField textField, String title) {
        JFileChooser directoryChooser = new JFileChooser();
        directoryChooser.setDialogTitle(title);
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (directoryChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            textField.setText(directoryChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void generateNewKey() {
        String keyPath = keyFilePathField.getText().trim();
        if (keyPath.isEmpty()) {
            appendLog("Error: Please specify key file path first");
            return;
        }

        try {
            // Get selected key length
            int selectedIndex = keyLengthComboBox.getSelectedIndex();
            int keyLength;
            switch (selectedIndex) {
                case 0:
                    keyLength = 128;
                    break;
                case 1:
                    keyLength = 192;
                    break;
                case 2:
                    keyLength = 256;
                    break;
                default:
                    keyLength = 256;
                    break;
            }

            File keyFile = new File(keyPath);
            String key = generateRandomAesKey(keyLength);
            Files.write(keyFile.toPath(), key.getBytes());
            appendLog("Generated " + keyLength + "-bit key and saved to: " + keyPath);
            appendLog("Key content: " + key);
        } catch (Exception e) {
            appendLog("Error generating key: " + e.getMessage());
        }
    }

    // GUI version of key retrieval
    private String getAesKeyGUI() {
        String keyPath = getEffectiveKeyPath();
        if (keyPath.isEmpty()) {
            appendLog("Error: Please specify AES key file path");
            return null;
        }

        File file = new File(keyPath);
        if (!file.exists()) {
            appendLog("Error: Key file does not exist: " + keyPath);
            return null;
        }

        try {
            byte[] keyBytes = Files.readAllBytes(file.toPath());
            String key = new String(keyBytes).trim();
            appendLog("Successfully read key from file: " + keyPath);
            return key;
        } catch (Exception e) {
            appendLog("Error reading key file: " + e.getMessage());
            return null;
        }
    }

    private void appendLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append("[" + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    // Encrypt button event handler
    private class EncryptActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(() -> {
                SwingUtilities.invokeLater(() -> encryptButton.setEnabled(false));
                try {
                    performEncryption();
                } finally {
                    SwingUtilities.invokeLater(() -> encryptButton.setEnabled(true));
                }
            }).start();
        }

        private void performEncryption() {
            try {
                String key = getAesKeyGUI();
                File cipherDir = new File(getEffectiveCipherDir());
                File dataDir = new File(getEffectiveDataDir());

                int count = encryptFiles(key, dataDir, cipherDir, AesGui.this::appendLog);
            } catch (Exception ex) {
                appendLog("Error during encryption: " + ex.getMessage());
            }
        }
    }

    // Decrypt button event handler
    private class DecryptActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(() -> {
                SwingUtilities.invokeLater(() -> decryptButton.setEnabled(false));
                try {
                    performDecryption();
                } finally {
                    SwingUtilities.invokeLater(() -> decryptButton.setEnabled(true));
                }
            }).start();
        }

        private void performDecryption() {
            try {
                String key = getAesKeyGUI();
                File cipherDir = new File(getEffectiveCipherDir());
                File dataDir = new File(getEffectiveDataDir());

                int count = decryptFiles(key, dataDir, cipherDir, AesGui.this::appendLog);
            } catch (Exception ex) {
                appendLog("Error during decryption: " + ex.getMessage());
            }
        }
    }
}
