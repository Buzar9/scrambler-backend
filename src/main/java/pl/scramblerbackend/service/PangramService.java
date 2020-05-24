package pl.scramblerbackend.service;

import org.springframework.stereotype.Component;
import pl.scramblerbackend.entity.Pangram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// todo przygotuj i przytnij key ze spacji

@Component
public class PangramService {

    public static String encrypt (Pangram keyNMessage){

        String key = keyNMessage.getKey();
        String message = keyNMessage.getMessage();

        Map<Integer, String> keyPattern = new HashMap<>();
        Map<Integer, Integer> mappedNumbers = new HashMap<>();
        Map<Integer, String> mappedMessage = new HashMap<>();
        ArrayList<String> outMessage = new ArrayList();

        for (int a = 0; a < key.length(); a++) {
            char tempChar = key.charAt(a);
            String tempString = Character.toString(tempChar);
            keyPattern.put(a, tempString);
        }

        for (int b = 0; b <= 31; b++) {
            mappedNumbers.put(b, b);
        }

        String standardizedMessage = message.toLowerCase();
        for (int l = 0; l < standardizedMessage.length(); l++) {
            char c = standardizedMessage.charAt(l);
            String temporary = Character.toString(c);
            mappedMessage.put(l, temporary);
            if (mappedMessage.size() != message.length()) continue;
        }

        for (int t = 0; t < mappedMessage.size(); t++) {
            if (keyPattern.containsValue(mappedMessage.get(t))) {
                for (int r = 0; r < keyPattern.size(); r++) {
                    if (mappedMessage.get(t).equals(keyPattern.get(r))) {
                        outMessage.add(String.valueOf(mappedNumbers.get(r)));
                        break;
                    } }
                if (outMessage.size() == mappedMessage.size()) break;

            } else {
                outMessage.add(mappedMessage.get(t));
            }}

        String result = new String();

        for (String letter : outMessage) {
            result += letter + " ";
        }

        return result;
    }
}
