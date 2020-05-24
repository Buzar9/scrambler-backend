package pl.scramblerbackend.service;

import org.springframework.stereotype.Component;
import pl.scramblerbackend.entity.Sylabic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class SylabicService {

    public String encrypt(Sylabic keyNMessage) {

        String message = keyNMessage.getMessage();
        String key = keyNMessage.getKey();

        ArrayList<Character> outMessage = new ArrayList<>();
        ArrayList <Character> standardizedKey = new ArrayList();
        Map<Integer, Character> readyKey = new HashMap<>();
        Map<Integer, Character> finalKey = new HashMap<>();
        Map<Integer, Character> mappedMessage = new HashMap<>();

        String roughingKey = key.toLowerCase().trim();
        for (int i = 0; i < key.length(); i++) {
            char temporary = roughingKey.charAt(i);
            if (temporary != ' ') standardizedKey.add(temporary);
            if (standardizedKey.size() == roughingKey.length()) continue;
        }

        for (int g = 0; g < standardizedKey.size(); g++) {
            String stringKey = standardizedKey.get(g).toString();
            for (int h = 0; h < standardizedKey.size(); h++) {
                if (readyKey.size() == standardizedKey.size()) break;
                char temporary = stringKey.charAt(h);
                readyKey.put(g, temporary);
                if (readyKey.size() != standardizedKey.size()) break;
            }
        }

        standardizedKey. clear();
        outMessage.clear();

        for (int j = 0; j < readyKey.size(); j++) {
            int k = j % 2;
            if (k == 0) {
                finalKey.put(j, readyKey.get(j + 1));
            } else {
                finalKey.put(j, readyKey.get(j - 1));
            }
            if (finalKey.size() != readyKey.size()) continue;
        }

        String roughingPassword = message.toLowerCase();
        for (int l = 0; l < roughingPassword.length(); l++) {
            char temporary = roughingPassword.charAt(l);
            mappedMessage.put(l, temporary);
            if (mappedMessage.size() != message.length()) continue;
        }

        for (int t = 0; t < mappedMessage.size(); t++) {
            if (readyKey.containsValue(mappedMessage.get(t))) {
                for (int r = 0; r < readyKey.size(); r++) {
                    if (mappedMessage.get(t) == readyKey.get(r)) {
                        outMessage.add(finalKey.get(r));
                        break;
                    }
                }
                if (outMessage.size() == mappedMessage.size()) break;

            } else {
                outMessage.add(mappedMessage.get(t));
            }
        }

        readyKey.clear();
        finalKey.clear();
        mappedMessage.clear();

        String result = new String();
        for (Character letter : outMessage) {
            result += letter;
        }

        return result;
    }
}
