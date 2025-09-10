package nycu.ui;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import static nycu.tools.AesCore.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    // ================= GUI 相關方法 =================
    private void initializeGUI() {
        setTitle("NYCU AESer - GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // 主要面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 頂部配置面板
        JPanel configPanel = createConfigPanel();

        // 中間按鈕面板
        JPanel buttonPanel = createButtonPanel();

        // 底部日誌面板
        JPanel logPanel = createLogPanel();

        mainPanel.add(configPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(logPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createConfigPanel() {
        JPanel configPanel = new JPanel(new GridBagLayout());
        configPanel.setBorder(BorderFactory.createTitledBorder("配置設定"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // AES Key File Path
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        configPanel.add(new JLabel("AES 金鑰檔案:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        keyFilePathField = new JTextField();
        configPanel.add(keyFilePathField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton browseKeyButton = new JButton("瀏覽");
        browseKeyButton.addActionListener(e -> browseForKeyFile());
        configPanel.add(browseKeyButton, gbc);

        // Data Directory
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        configPanel.add(new JLabel("資料目錄:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        dataDirField = new JTextField();
        configPanel.add(dataDirField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton browseDataButton = new JButton("瀏覽");
        browseDataButton.addActionListener(e -> browseForDirectory(dataDirField, "選擇資料目錄"));
        configPanel.add(browseDataButton, gbc);

        // Cipher Directory
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        configPanel.add(new JLabel("加密檔案目錄:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cipherDirField = new JTextField();
        configPanel.add(cipherDirField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton browseCipherButton = new JButton("瀏覽");
        browseCipherButton.addActionListener(e -> browseForDirectory(cipherDirField, "選擇加密檔案目錄"));
        configPanel.add(browseCipherButton, gbc);

        // Environment Variables Status and Control
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        configPanel.add(new JLabel("環境變數:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JPanel envPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        useEnvVarsCheckBox = new JCheckBox("自動使用環境變數");
        useEnvVarsCheckBox.addActionListener(e -> toggleEnvironmentVariables());
        envPanel.add(useEnvVarsCheckBox);

        envStatusLabel = new JLabel();
        envStatusLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        envPanel.add(envStatusLabel);

        configPanel.add(envPanel, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton refreshEnvButton = new JButton("重新檢測");
        refreshEnvButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        refreshEnvButton.addActionListener(e -> checkAndUpdateEnvironmentVariables());
        configPanel.add(refreshEnvButton, gbc);

        // Key Length Selection
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        configPanel.add(new JLabel("金鑰長度:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        keyLengthComboBox = new JComboBox<>(new String[]{"16 字元", "24 字元", "32 字元"});
        keyLengthComboBox.setSelectedIndex(2); // 預設選擇 32 字元
        configPanel.add(keyLengthComboBox, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        configPanel.add(new JLabel(""), gbc); // 空白標籤保持對齊

        return configPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createTitledBorder("操作"));

        encryptButton = new JButton("加密檔案");
        encryptButton.setPreferredSize(new Dimension(120, 40));
        encryptButton.addActionListener(new EncryptActionListener());

        decryptButton = new JButton("解密檔案");
        decryptButton.setPreferredSize(new Dimension(120, 40));
        decryptButton.addActionListener(new DecryptActionListener());

        JButton generateKeyButton = new JButton("產生新金鑰");
        generateKeyButton.setPreferredSize(new Dimension(120, 40));
        generateKeyButton.addActionListener(e -> generateNewKey());

        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        buttonPanel.add(generateKeyButton);

        return buttonPanel;
    }

    private JPanel createLogPanel() {
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("操作日誌"));

        logArea = new JTextArea(10, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(logArea);
        logPanel.add(scrollPane, BorderLayout.CENTER);

        JButton clearLogButton = new JButton("清除日誌");
        clearLogButton.addActionListener(e -> logArea.setText(""));
        logPanel.add(clearLogButton, BorderLayout.SOUTH);

        return logPanel;
    }

    // 檢查並更新環境變數狀態
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

        // 更新狀態標籤
        if (envCount == 3) {
            envStatusLabel.setText("（3/3✓）");
            envStatusLabel.setForeground(new Color(0, 128, 0)); // 綠色
            useEnvVarsCheckBox.setSelected(true);
            appendLog("檢測到完整的環境變數設定");
        } else if (envCount > 0) {
            envStatusLabel.setText("（" + envCount + "/3）");
            envStatusLabel.setForeground(new Color(255, 140, 0)); // 橙色
            useEnvVarsCheckBox.setSelected(false);
            appendLog("檢測到部分環境變數設定 (" + envCount + "/3)");
        } else {
            envStatusLabel.setText("（0/3）");
            envStatusLabel.setForeground(Color.RED);
            useEnvVarsCheckBox.setSelected(false);
            appendLog("未檢測到環境變數設定");
        }

        // 如果勾選框被選中，則自動填入環境變數
        if (useEnvVarsCheckBox.isSelected()) {
            applyEnvironmentVariables();
        }
    }

    // 套用環境變數到輸入欄位
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

        appendLog("已套用環境變數設定到輸入欄位");
    }

    // 切換環境變數使用狀態
    private void toggleEnvironmentVariables() {
        if (useEnvVarsCheckBox.isSelected()) {
            // 使用者選擇使用環境變數
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "是否要用環境變數覆蓋目前的輸入欄位內容？",
                    "確認使用環境變數",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                applyEnvironmentVariables();
            } else {
                useEnvVarsCheckBox.setSelected(false);
            }
        } else {
            // 使用者選擇不使用環境變數
            appendLog("已停用環境變數自動載入，將使用手動輸入的路徑");
        }
    }

    // 獲取有效的金鑰檔案路徑（優先使用使用者輸入，其次環境變數）
    private String getEffectiveKeyPath() {
        String userInput = keyFilePathField.getText().trim();

        // 如果使用者有輸入且不是從環境變數來的，優先使用使用者輸入
        if (!userInput.isEmpty()) {
            String envPath = System.getenv(AES_KEY_FILE_PATH_ENV_NAME);
            if (envPath == null || !userInput.equals(envPath)) {
                appendLog("使用使用者指定的金鑰路徑: " + userInput);
                return userInput;
            }
        }

        // 如果勾選使用環境變數且環境變數存在
        if (useEnvVarsCheckBox.isSelected()) {
            String envPath = System.getenv(AES_KEY_FILE_PATH_ENV_NAME);
            if (envPath != null && !envPath.trim().isEmpty()) {
                appendLog("使用環境變數金鑰路徑: " + envPath);
                return envPath;
            }
        }

        return userInput; // 回傳使用者輸入（可能為空）
    }

    // 獲取有效的資料目錄路徑
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

    // 獲取有效的加密檔案目錄路徑
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
        fileChooser.setDialogTitle("選擇 AES 金鑰檔案");
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
            appendLog("錯誤: 請先指定金鑰檔案路徑");
            return;
        }

        try {
            // 取得選擇的金鑰長度
            int selectedIndex = keyLengthComboBox.getSelectedIndex();
            int keyLength;
            switch (selectedIndex) {
                case 0:
                    keyLength = 16;
                    break;  // 128 位元
                case 1:
                    keyLength = 24;
                    break;  // 192 位元
                case 2:
                    keyLength = 32;
                    break;  // 256 位元
                default:
                    keyLength = 32;
                    break; // 預設 256 位元
            }

            File keyFile = new File(keyPath);
            String key = generateRandomAesKey(keyLength);
            Files.write(keyFile.toPath(), key.getBytes());
            appendLog("已產生 " + keyLength + " 字元金鑰並儲存至: " + keyPath);
            appendLog("金鑰內容: " + key);
        } catch (Exception e) {
            appendLog("產生金鑰時發生錯誤: " + e.getMessage());
        }
    }

    // GUI 版本的金鑰取得方法
    private String getAesKeyGUI() {
        String keyPath = getEffectiveKeyPath();
        if (keyPath.isEmpty()) {
            appendLog("錯誤: 請指定 AES 金鑰檔案路徑");
            return null;
        }

        File file = new File(keyPath);
        if (!file.exists()) {
            appendLog("錯誤: 金鑰檔案不存在: " + keyPath);
            return null;
        }

        try {
            byte[] keyBytes = Files.readAllBytes(file.toPath());
            String key = new String(keyBytes).trim();

            // 檢查金鑰長度
            if (!(key.length() == 16 || key.length() == 24 || key.length() == 32)) {
                appendLog("錯誤: 金鑰長度必須是 16、24 或 32 個字元，目前長度: " + key.length());
                return null;
            }

            appendLog("成功讀取金鑰，長度: " + key.length() + " 字元");
            return key;
        } catch (Exception e) {
            appendLog("讀取金鑰檔案時發生錯誤: " + e.getMessage());
            return null;
        }
    }

    private void appendLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append("[" + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    // 加密按鈕事件處理
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
                if (key == null) {
                    return;
                }

                String dataPath = getEffectiveDataDir();
                String cipherPath = getEffectiveCipherDir();

                if (dataPath.isEmpty() || cipherPath.isEmpty()) {
                    appendLog("錯誤: 請指定資料目錄和加密檔案目錄");
                    return;
                }

                File dataDir = new File(dataPath);
                File cipherDir = new File(cipherPath);

                if (!dataDir.exists() || !dataDir.isDirectory()) {
                    appendLog("錯誤: 資料目錄不存在或不是目錄");
                    return;
                }

                if (!cipherDir.exists()) {
                    cipherDir.mkdirs();
                    appendLog("已建立加密檔案目錄: " + cipherPath);
                }

                SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);

                File[] files = dataDir.listFiles();
                if (files == null) {
                    appendLog("資料目錄中沒有檔案");
                    return;
                }

                int encryptedCount = 0;
                for (File file : files) {
                    if (!file.isFile()) {
                        continue;
                    }

                    byte[] fileBytes = Files.readAllBytes(file.toPath());
                    byte[] encrypted = cipher.doFinal(fileBytes);
                    String encoded = Base64.getEncoder().encodeToString(encrypted);

                    File outFile = new File(cipherDir, file.getName() + ".enc");
                    Files.write(outFile.toPath(), encoded.getBytes("UTF-8"));
                    appendLog("已加密: " + file.getName() + " -> " + outFile.getName());
                    encryptedCount++;
                }

                appendLog("加密完成! 共處理 " + encryptedCount + " 個檔案");

            } catch (Exception ex) {
                appendLog("加密過程中發生錯誤: " + ex.getMessage());
            }
        }
    }

    // 解密按鈕事件處理
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
                if (key == null) {
                    return;
                }

                String cipherPath = getEffectiveCipherDir();
                String dataPath = getEffectiveDataDir();

                if (cipherPath.isEmpty() || dataPath.isEmpty()) {
                    appendLog("錯誤: 請指定加密檔案目錄和資料目錄");
                    return;
                }

                File cipherDir = new File(cipherPath);
                File dataDir = new File(dataPath);

                if (!cipherDir.exists() || !cipherDir.isDirectory()) {
                    appendLog("錯誤: 加密檔案目錄不存在或不是目錄");
                    return;
                }

                if (!dataDir.exists()) {
                    dataDir.mkdirs();
                    appendLog("已建立資料目錄: " + dataPath);
                }

                SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);

                File[] files = cipherDir.listFiles();
                if (files == null) {
                    appendLog("加密檔案目錄中沒有檔案");
                    return;
                }

                int decryptedCount = 0;
                for (File file : files) {
                    if (!file.isFile() || !file.getName().endsWith(".enc")) {
                        continue;
                    }

                    byte[] encodedBytes = Files.readAllBytes(file.toPath());
                    byte[] encrypted = Base64.getDecoder().decode(new String(encodedBytes, "UTF-8"));
                    byte[] decrypted = cipher.doFinal(encrypted);

                    String baseName = file.getName().replaceFirst("\\.enc$", "");
                    File outFile = new File(dataDir, baseName);
                    Files.write(outFile.toPath(), decrypted);
                    appendLog("已解密: " + file.getName() + " -> " + baseName);
                    decryptedCount++;
                }

                appendLog("解密完成! 共處理 " + decryptedCount + " 個檔案");

            } catch (Exception ex) {
                appendLog("解密過程中發生錯誤: " + ex.getMessage());
            }
        }
    }
}
