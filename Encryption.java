/*
 * Author Name: Solomon
 * File-Name: Encryption.java
 * Copy Right: No Copy Right
 * Creation Date: 21/6/2024
 * Last Modification Date: 30/6/2024
 * Version: 1.3 -- Add in Documentary Comment
 */

import java.util.ArrayList;
import java.util.Collections;

public class Encryption {
    private ArrayList<Character> list;
    private ArrayList<Character> shuffledList;

    //Constructor
    public Encryption() {
        list = new ArrayList<>();
        shuffledList = new ArrayList<>();
        initializeList();
    }

    //Create a char list from Ascii
    private void initializeList() {
        list.clear();
        for (int i = 32; i <= 127; i++) {
            list.add((char) i);
        }
    }

    //Shuffle the Ascii char list and use as the decryption key
    public void newKey() {
        shuffledList.clear();
        shuffledList.addAll(list);
        Collections.shuffle(shuffledList);
        System.out.println("New key generated");
    }

    /**
     * Use as Key return
     * @return Key
     */
    public String getKey() {
        StringBuilder key = new StringBuilder();
        for (char c : shuffledList) {
            key.append(c);
        }
        return key.toString();
    }

    /**
     * Set a key to work with
     * @param key Key to decrypt message
     */
    public void setKey(String key) {
        shuffledList.clear();
        for (char c : key.toCharArray()) {
            shuffledList.add(c);
        }
    }

    /**
     * To Encrypt message
     * @param line Message to be encrypted
     * @return Encrypted message
     */
    public String encrypt(String line) {
        char[] letters = line.toCharArray();
        StringBuilder secretMessages = new StringBuilder();
        for (char letter : letters) {
            secretMessages.append(shuffledList.get(list.indexOf(letter)));
        }
        return secretMessages.toString();
    }

    /**
     * To decrypt message
     * @param encryptedMessage Message to be decrypted
     * @return Decrypted message
     */
    public String decrypt(String encryptedMessage) {
        char[] letters = encryptedMessage.toCharArray();
        StringBuilder decryptedMessage = new StringBuilder();
        for (char letter : letters) {
            decryptedMessage.append(list.get(shuffledList.indexOf(letter)));
        }
        return decryptedMessage.toString();
    }
}

