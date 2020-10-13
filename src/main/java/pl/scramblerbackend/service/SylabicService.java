package pl.scramblerbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.scramblerbackend.dao.SylabicDao;
import pl.scramblerbackend.entity.KeyNMessage;
import pl.scramblerbackend.entity.OutPassword;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SylabicService implements CipherService {

    @Autowired
    private SylabicDao sylabicDao;

    @Override
    public OutPassword encrypt(KeyNMessage keyNMessage) throws FileNotFoundException {

        Map<Integer, Character> readyKey = new HashMap<>();
        String key = keyNMessage.getKey().trim().toLowerCase();
        int letterPosition = 0;
        for (int i = 0; i < key.length(); i++) {
            char letterFromKey = key.charAt(i);
            if (letterFromKey != ' ')  {
                readyKey.put(letterPosition, letterFromKey);
                letterPosition++;
            }
        }


        Map<Integer, Character> finalKey = new HashMap<>();
        for (int j = 0; j < readyKey.size(); j++) {
            int k = j % 2;
            if (k == 0) {
                finalKey.put(j, readyKey.get(j + 1));
            } else {
                finalKey.put(j, readyKey.get(j - 1));
            }
        }

//           Standardization of messages to the supported format.
        String message = keyNMessage.getMessage();
        Map<Integer, Character> mappedMessage = new HashMap<>();
        String roughingPassword = message.toLowerCase();
        for (int l = 0; l < roughingPassword.length(); l++) {
            char temporary = roughingPassword.charAt(l);
            mappedMessage.put(l, temporary);
        }

//        Encryption
        ArrayList<Character> outMessage = new ArrayList<>();
        for (int t = 0; t < mappedMessage.size(); t++) {
            if (readyKey.containsValue(mappedMessage.get(t))) {
                for (int r = 0; r < readyKey.size(); r++) {
                    if (mappedMessage.get(t) == readyKey.get(r)) {
                        outMessage.add(finalKey.get(r));
                        break;
                    }
                }
            } else {
                outMessage.add(mappedMessage.get(t));
            }
        }

//            Prepare result format.
        String result = new String();
        for (Character letter : outMessage) {
            result += letter;
        }

//        Validation
        List<Character> vowel = sylabicDao.vowelReader();
        if (readyKey.size() % 2 == 1) {
            result = "To nie jest szyfr sylabiczny";
        } else {
            for (int p = 0; p < vowel.size(); p++) {
                if (!readyKey.containsValue(vowel.get(p))) {
                    result += " - Brakuje samogłosek. Ten szyfr może być lepszy";
                    break;
                }
            }
        }

        return new OutPassword(result);
    }
}
