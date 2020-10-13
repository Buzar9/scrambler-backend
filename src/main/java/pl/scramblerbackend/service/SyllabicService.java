package pl.scramblerbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.scramblerbackend.dao.SyllabicDao;
import pl.scramblerbackend.entity.KeyNMessage;
import pl.scramblerbackend.entity.OutPassword;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SyllabicService implements CipherService {

    @Autowired
    private SyllabicDao syllabicDao;

    @Override
    public OutPassword encrypt(KeyNMessage keyNMessage) throws FileNotFoundException {


//        List<Character> vowel = syllabicDao.vowelReader();
//        if (keyReadyToEncrypt.size() % 2 == 1) {
//            result = "To nie jest szyfr sylabiczny";
//        } else {
//            for (int p = 0; p < vowel.size(); p++) {
//                if (!keyReadyToEncrypt.containsValue(vowel.get(p))) {
//                    result += " - Brakuje samogłosek. Ten szyfr może być lepszy";
//                    break;
//                }
//            }
//        }

        return new OutPassword(result);
    }

    private List<Character> changingLettersByKey(KeyNMessage keyNMessage) {
        Map<Integer, Character> keyReadyToEncrypt = prepareKeyToEncrypt(keyNMessage);
        Map<Integer, Character> mappedMessage = messageStandardization(keyNMessage);
        ArrayList<Character> roughPassword = new ArrayList<>();
        for (int t = 0; t < mappedMessage.size(); t++) {
            if (keyReadyToEncrypt.containsValue(mappedMessage.get(t))) {
                for (int r = 0; r < keyReadyToEncrypt.size(); r++) {
                    if (mappedMessage.get(t) == keyReadyToEncrypt.get(r)) {
                        roughPassword.add(keyReadyToEncrypt.get(r));
                        break;
                    }
                }
            } else {
                roughPassword.add(mappedMessage.get(t));
            }
        }
        return roughPassword;
    }

    private Map<Integer, Character> prepareKeyToEncrypt(KeyNMessage keyNMessage) {
        Map<Integer, Character> standardizedKey = keyStandardization(keyNMessage);
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

    private Map<Integer, Character> keyStandardization(KeyNMessage keyNMessage) {
        Map<Integer, Character> standardizedKey = new HashMap<>();
        String key = keyNMessage.getKey().trim().toLowerCase();
        int letterPosition = 0;
        for (int i = 0; i < key.length(); i++) {
            char letterFromKey = key.charAt(i);
            if (letterFromKey != ' ') {
                standardizedKey.put(letterPosition, letterFromKey);
                letterPosition++;
            }
        }
        return standardizedKey;
    }

    private boolean isFirstInPair(int letterPosition) {
        boolean isFirst;
        if (letterPosition % 2 == 0) {
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



    private String createSentenceFromLetters(List<Character> roughPassword) {
        String readyPassword = new String();
        for (char letter : roughPassword) {
            readyPassword += letter;
        }
        return readyPassword;
    }

    private boolean isKeyEven(KeyNMessage keyNMessage) {
        Map<Integer, Character> keyReadyToEncrypt = prepareKeyToEncrypt(keyNMessage);
        if (keyReadyToEncrypt.size() % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isKeyPerfectSyllabic(KeyNMessage keyNMessage) throws FileNotFoundException {
        Map<Integer, Character> keyReadyToEncrypt = prepareKeyToEncrypt(keyNMessage);
        List<Character> vowels = syllabicDao.vowelReader();
        boolean isPerfect = true;
        for (int p = 0; p < vowels.size(); p++) {
            if (!keyReadyToEncrypt.containsValue(vowels.get(p))) {
                isPerfect = false;
                break;
            }
        }
        return isPerfect;
    }


}
