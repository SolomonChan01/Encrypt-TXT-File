/*
 * Author Name: Solomon
 * File-Name: Panel.java
 * Copy Right: No Copy Right
 * Creation Date: 21/6/2024
 * Last Modification Date: 30/6/2024
 * Version: 1.3 -- Add in Documentary Comment
 */

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class Panel extends JFrame{
    private JPasswordField keyField;
    private JButton encryptButton;
    private JButton decryptButton;
    private JFileChooser fileChooser;
    private JOptionPane errorMessages;

    //Constructor
    public Panel() {
        setTitle("Encryption");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        keyField = new JPasswordField(20);
        encryptButton = new JButton("Encrypt File");
        decryptButton = new JButton("Decrypt File");
        fileChooser = new JFileChooser();

        //Encryption Button action
        encryptButton.addActionListener(e -> {
            try {
                encryption();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //Decryption button action
        decryptButton.addActionListener(e -> {
            try {
                decryption();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });



        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(new JLabel("Enter Key:"));
        panel.add(encryptButton);
        panel.add(keyField);
        panel.add(decryptButton);
        add(panel);
    }

    private void encryption() throws IOException {
        int filePanel = fileChooser.showOpenDialog(this);

        //If file option is valid, then do the encryption action
        if (filePanel == JFileChooser.APPROVE_OPTION) {
            Encryption encryption = new Encryption();
            encryption.newKey();

            File inputFile = fileChooser.getSelectedFile();
            Scanner infile = new Scanner(inputFile);

            //Get the file extension(e.g. .txt, .pdf)
            String fileExtension = inputFile.getAbsolutePath().substring(inputFile.getAbsolutePath().length() - 4);
            FileWriter file = new FileWriter(inputFile.getAbsolutePath().replace(fileExtension, ".enc"));

            //Write in encrypted messages
            PrintWriter writer = new PrintWriter(file);
            while (infile.hasNextLine()) {
                String encrypted = encryption.encrypt(infile.nextLine());
                writer.println(encrypted);
            }
            writer.close();

            //Print in the Key in Key.txt
            File keyFile = new File(inputFile.getParent(), "Key.txt");
            FileWriter keyFileWriter = new FileWriter(keyFile);
            PrintWriter keyWriter = new PrintWriter(keyFileWriter);
            keyWriter.println(encryption.getKey());
            keyWriter.close();

            //Delete the original file
            inputFile.delete();
        }
    }

    public void decryption() throws IOException {
        int filePanel = fileChooser.showOpenDialog(this);

        //If file option is valid, then do the decryption action
        if (filePanel == JFileChooser.APPROVE_OPTION) {
            String key =  new String(keyField.getPassword());
            Encryption encryption = new Encryption();

            //Check the Key is valid in length
            while (key.length() < 90) {
                JOptionPane.showMessageDialog(errorMessages, "Not valid Key", "Error", JOptionPane.ERROR_MESSAGE);
                key = null;
            }

            encryption.setKey(key);
            File inputFile = fileChooser.getSelectedFile();
            Scanner infile = new Scanner(inputFile);
            String outputFilePath = inputFile.getAbsolutePath().replace(".enc", ".txt");
            FileWriter file = new FileWriter(outputFilePath);
            PrintWriter writer = new PrintWriter(file);

            //Write in the decrypted messages
            while (infile.hasNextLine()) {
                String decrypt = encryption.decrypt(infile.nextLine());
                writer.println(decrypt);
            }
            
            writer.close();
            inputFile.delete();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Panel().setVisible(true));
    }
}
