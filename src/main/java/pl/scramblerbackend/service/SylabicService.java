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


        Map<Integer, Character> standardizedKey = keyStandardization(keyNMessage);
        Map<Integer, Character> keyReadyToEncrypt = prepareKeyToEncrypt(standardizedKey);
        Map<Integer, Character> mappedMessage = messageStandardization(keyNMessage);


//        Encryption
        ArrayList<Character> outMessage = new ArrayList<>();
        for (int t = 0; t < mappedMessage.size(); t++) {
            if (standardizedKey.containsValue(mappedMessage.get(t))) {
                for (int r = 0; r < standardizedKey.size(); r++) {
                    if (mappedMessage.get(t) == standardizedKey.get(r)) {
                        outMessage.add(keyReadyToEncrypt.get(r));
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
        if (standardizedKey.size() % 2 == 1) {
            result = "To nie jest szyfr sylabiczny";
        } else {
            for (int p = 0; p < vowel.size(); p++) {
                if (!standardizedKey.containsValue(vowel.get(p))) {
                    result += " - Brakuje samogłosek. Ten szyfr może być lepszy";
                    break;
                }
            }
        }

        return new OutPassword(result);
    }

    private Map<Integer, Character> keyStandardization(KeyNMessage keyNMessage) {
        Map<Integer, Character> standardizedKey = new HashMap<>();
        String key = keyNMessage.getKey().trim().toLowerCase();
        int letterPosition = 0;
        for (int i = 0; i < key.length(); i++) {
            char letterFromKey = key.charAt(i);
            if (letterFromKey != ' ')  {
                standardizedKey.put(letterPosition, letterFromKey);
                letterPosition++;
            }
        }
        return standardizedKey;
    }

    private Map<Integer, Character> prepareKeyToEncrypt(Map<Integer, Character> standardizedKey) {
        Map<Integer, Character> keyReadyToEncrypt = new HashMap<>();
        for (int letterPosition = 0; letterPosition < standardizedKey.size(); letterPosition++) {
            if (isFirstInPair(letterPosition)) {
                keyReadyToEncrypt.put(letterPosition, standardizedKey.get(letterPosition + 1));
            } else {
                keyReadyToEncrypt.put(letterPosition, standardizedKey.get(letterPosition - 1));
            }
        }

        return keyReadyToEncrypt;
    }

    private boolean isFirstInPair(int letterPosition) {
        boolean isFirst;
        if(letterPosition % 2 == 0) {
            isFirst = true;
        } else {
            isFirst = false;
        }
        return isFirst;
    }

    private Map<Integer, Character> messageStandardization(KeyNMessage keyNMessage) {
        Map<Integer, Character> mappedMessage = new HashMap<>();
        String message = keyNMessage.getMessage().toLowerCase();
        for (int letterPosition = 0; letterPosition < message.length(); letterPosition++) {
            char letter = message.charAt(letterPosition);
            mappedMessage.put(letterPosition, letter);
        }
        return mappedMessage;
    }

}
