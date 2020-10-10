package pl.scramblerbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.scramblerbackend.dao.MorsDao;
import pl.scramblerbackend.entity.KeyNMessage;
import pl.scramblerbackend.entity.OutPassword;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MorsService {

    @Autowired
    private MorsDao morsDao;

    public OutPassword encrypt(KeyNMessage keyNMessage) throws FileNotFoundException {

        String roughMessage = keyNMessage.getMessage().toLowerCase();
        Map<Integer, Character> mappedAlphabet = morsDao.latinReader();
        Map<Integer, String> mappedMors = morsDao.morsReader();
        String password;


        if (isMessageInLatin(roughMessage)) {
            Map<Integer, Character> lettersFromMessage = mappingLettersFromMessage(roughMessage);
            password = encryptionLatinToMors(lettersFromMessage, mappedMors, mappedAlphabet);

        } else {
            Map<Integer, String> mappedSign = mappingSignFromMessage(roughMessage);
            password = encryptionMorsToLatin(mappedSign, mappedMors, mappedAlphabet);

        }

        return new OutPassword(password);
    }

    private boolean isMessageInLatin(String roughMessage) {
        int firstLetter = roughMessage.charAt(0);
        boolean isLatin = firstLetter >= 65;
        return isLatin;
    }

    private Map<Integer, Character> mappingLettersFromMessage(String roughMessage) {
        Map<Integer, Character> mappedLetters = new HashMap<>();
        for (int i = 0; i < roughMessage.length(); i++) {
            mappedLetters.put(i, roughMessage.charAt(i));
        }
        return mappedLetters;
    }

    private String encryptionLatinToMors(Map<Integer, Character> lettersFromMessage,
                                         Map<Integer, String> mappedMors,
                                         Map<Integer, Character> mappedAlphabet) {
        List<String> roughPassword = latinEncryptionToRoughMorsPassword(lettersFromMessage, mappedMors, mappedAlphabet);
        String readyPassword = adaptPasswordToConvention(roughPassword);

        if (morsPasswordValidation(lettersFromMessage, mappedAlphabet)) {
            return readyPassword;
        }
        return "Niepoprawny znak";
    }

    private List<String> latinEncryptionToRoughMorsPassword(Map<Integer, Character> lettersFromMessage,
                                                        Map<Integer, String> mappedMors,
                                                        Map<Integer, Character> mappedAlphabet) {
        List<String> roughPassword = new ArrayList<>();

        for (int t = 0; t < lettersFromMessage.size(); t++) {
            for (int r = 0; r < mappedAlphabet.size(); r++) {
                if (lettersFromMessage.get(t) == mappedAlphabet.get(r)) {
                    roughPassword.add(mappedMors.get(r));
                    break;
                } else if (lettersFromMessage.get(t) == 32) {
                    roughPassword.add("");
                    break;
                }
            }
        }
        return roughPassword;
    }

    private String adaptPasswordToConvention(List<String> roughPassword) {
        String readyPassword = "///";
        for (String letter : roughPassword) {
            readyPassword += letter + "/";
        }
        readyPassword += "//";
        return readyPassword;
    }


    private boolean morsPasswordValidation(Map<Integer, Character> lettersFromMessage,
                                           Map<Integer, Character> mappedAlphabet) {
        for (int y = 0; y < lettersFromMessage.size(); y++) {
            if (!mappedAlphabet.containsValue(lettersFromMessage.get(y)) && lettersFromMessage.get(y) != 32) {
                return false;
            }
        }
        return true;
    }

    private Map<Integer, String> mappingSignFromMessage(String roughMessage) {
        String substringMessage = erasingUnnecessaryContentFromMessage(roughMessage);

        String[] splitMessage = substringMessage.split("/");
        Map<Integer, String> mappedSign = new HashMap<>();

        for (int q = 0; q < splitMessage.length; q++) {
            String trimMessage = splitMessage[q].trim();
            mappedSign.put(q, trimMessage);
        }
        return mappedSign;
    }

    private String erasingUnnecessaryContentFromMessage(String roughMessage) {
        String substringMessage = roughMessage
                .substring(3, roughMessage.length() - 3)
                .replace("//", "/|/");
        return substringMessage;
    }

    private String encryptionMorsToLatin(Map<Integer, String> mappedSign,
                                         Map<Integer, String> mappedMors,
                                         Map<Integer, Character> mappedAlphabet) {

        List<String> roughPassword = morsEncryptionToRoughLatinPassword(mappedSign, mappedMors, mappedAlphabet);
        String readyPassword = createSentenceFromLetters(roughPassword);

        if (latinPasswordValidation(mappedSign, mappedMors)) {
            return readyPassword;
        }

        return "Niepoprawny znak";

    }

    private List<String> morsEncryptionToRoughLatinPassword(Map<Integer, String> mappedSign,
                                                            Map<Integer, String> mappedMors,
                                                            Map<Integer, Character> mappedAlphabet) {

        List<String> roughPassword = new ArrayList<>();

        for (int o = 0; o < mappedSign.size(); o++) {
            for (int p = 0; p < mappedMors.size(); p++) {
                if (mappedSign.get(o).equals(mappedMors.get(p)) & !mappedSign.get(o).equals("|")) {
                    roughPassword.add(mappedAlphabet.get(p).toString());
                    break;
                } else if (mappedSign.get(o).equals("|")) {
                    roughPassword.add(" ");
                }
            }
        }
        return roughPassword;
    }

    private String createSentenceFromLetters(List<String> roughPassword) {
        String readyPassword = new String();
        for (String letter : roughPassword) {
            readyPassword += letter;
        }

        return readyPassword;
    }

    private boolean latinPasswordValidation(Map<Integer, String> mappedSign,
                                           Map<Integer, String> mappedMors) {
        for (int i = 0; i < mappedSign.size(); i++) {
            if (!mappedMors.containsValue(mappedSign.get(i)) & !mappedSign.get(i).equals("|")) {
                return false;
            }
        }
        return true;

    }
}
