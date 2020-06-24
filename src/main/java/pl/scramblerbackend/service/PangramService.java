package pl.scramblerbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.scramblerbackend.dao.PangramDao;
import pl.scramblerbackend.entity.KeyNMessage;
import pl.scramblerbackend.entity.OutPassword;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PangramService implements CipherService {

    @Autowired
    private PangramDao pangramDao;

    @Override
    public OutPassword encrypt(KeyNMessage keyNMessage) throws FileNotFoundException {

        String key = keyNMessage.getKey();
        List<Character> standardizedKey = new ArrayList();

//        Standardization of key to the supported format.
        List<Character> listedPunctuation = pangramDao.punctuationMarksReader();
        String roughingKey = key.toLowerCase().trim();
        for (int i = 0; i < roughingKey.length(); i++) {
            char temporary = roughingKey.charAt(i);
            if (temporary != ' ' && !listedPunctuation.contains(temporary)) {
                standardizedKey.add(temporary);
            }
        }

        String readyKey = new String();
        for (Character letter : standardizedKey) {
            readyKey += letter.toString();
        }

        Map<Integer, String> keyPattern = new HashMap<>();
        for (int a = 0; a < readyKey.length(); a++) {
            char tempChar = readyKey.charAt(a);
            String tempString = Character.toString(tempChar);
            keyPattern.put(a, tempString);
        }

        Map<Integer, String> mappedNumbers = new HashMap<>();
        for (int b = 0; b <= 31; b++) {
            String tempString = String.valueOf(b+1);
            mappedNumbers.put(b, tempString);
        }

        String message = keyNMessage.getMessage();
        List<String> listedPassword = new ArrayList();
        String result = new String();

//        Choosing of encryption method.
        int firstLetter = message.charAt(0);
        if(firstLetter >= 65) {

//           Standardization of messages to the supported format.
            Map<Integer, String> mappedMessage = new HashMap<>();
            String standardizedMessage = message.toLowerCase();
            for (int l = 0; l < standardizedMessage.length(); l++) {
                char tempChar = standardizedMessage.charAt(l);
                String tempString = Character.toString(tempChar);
                mappedMessage.put(l, tempString);
            }

//            Encryption polish to pangram.
            for (int t = 0; t < mappedMessage.size(); t++) {
                if (keyPattern.containsValue(mappedMessage.get(t))) {
                    for (int r = 0; r < keyPattern.size(); r++) {
                        if (mappedMessage.get(t).equals(keyPattern.get(r))) {
                            listedPassword.add(mappedNumbers.get(r));
                            break;
                        }
                    }
                } else {
                    listedPassword.add(mappedMessage.get(t));
                }
            }

        } else {

//            Standardization of messages to the supported format.
            String[] splittedMessage = message.split(" ");
            Map<Integer, String> mappedMessage = new HashMap<>();

            for (int q = 0; q < splittedMessage.length; q++) {
                String trimMessage = splittedMessage[q].trim();
                mappedMessage.put(q, trimMessage);
            }

//            Encryption pangram to polish.
            for (int t = 0; t < mappedMessage.size(); t++) {
                if (mappedNumbers.containsValue(mappedMessage.get(t))) {
                    for (int r = 0; r < keyPattern.size(); r++) {
                        if (mappedMessage.get(t).equals(mappedNumbers.get(r))) {
                            listedPassword.add(keyPattern.get(r));
                            break;
                        }
                    }
                    if (listedPassword.size() == mappedMessage.size()) break;

                } else {
                    listedPassword.add(mappedMessage.get(t));
                }
            }
        }

//        Prepare result format.
        for (String letter : listedPassword) {
            result += letter + " ";
        }

        String validateMessage = validation(result, standardizedKey);

        return new OutPassword(validateMessage);
    }

    private String validation(String result, List<Character> standardizedKey) throws FileNotFoundException {

        List<Character> listedPolish = pangramDao.polishReader();

        if (!(standardizedKey.size() == 32)) {
            result = "Nieodpowiednia ilość liter w kluczu";
        } else {
            for (int d = 0; d < listedPolish.size(); d++) {
                if (!listedPolish.contains(standardizedKey.get(d))) result = "W kluczu wsytępuje litera spoza polskiego afabetu.";
            }

            for (int w = 0; w < standardizedKey.size(); w++) {
                int counter = 0;
                for (int e = 0; e < standardizedKey.size(); e++) {
                    int tempCondition = standardizedKey.get(w).compareTo(standardizedKey.get(e));
                    if (tempCondition == 0) {
                        counter += 1;
                    }
                }
                if (counter != 1) result = "Litery w kluczu się dublują";
            }
        }

        return result;
    }
}
