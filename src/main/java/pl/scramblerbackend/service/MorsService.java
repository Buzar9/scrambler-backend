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

    public OutPassword encrypt(KeyNMessage morsMessage) throws FileNotFoundException {

        Map<Integer, Character> mappedAlphabet = morsDao.latinReader();
        Map<Integer, String> mappedMors = morsDao.morsReader();
        List<String> listedPassword = new ArrayList<>();
        String result = new String();

//        Choosing method of encryption.
        String roughMessage = morsMessage.getMessage();

        int firstLetter = roughMessage.charAt(0);
        if (firstLetter >= 65) {

//           Standardization of messages to the supported format.
            Map<Integer, Character> mappedLetter = new HashMap<>();
            String standardizedMessage = roughMessage.toLowerCase();
            for (int j = 0; j < standardizedMessage.length(); j++) {
                mappedLetter.put(j, standardizedMessage.charAt(j));
            }

//            Encryption latin to mors.
            for (int t = 0; t < mappedLetter.size(); t++) {
                for (int r = 0; r < mappedAlphabet.size(); r++) {
                    if (mappedLetter.get(t) == mappedAlphabet.get(r)) {
                        listedPassword.add(mappedMors.get(r));
                        break;
                    }
                    if (mappedLetter.get(t) == 32){
                        listedPassword.add("");
                        break;
                    }
                }
            }

//            Prepare result format.
            result = "///";
            for (String letter : listedPassword) {
                result += letter + "/";
            }
            result += "//";

//            Validation.
            for (int y = 0; y < mappedLetter.size(); y++) {
                if (!mappedAlphabet.containsValue(mappedLetter.get(y)) && mappedLetter.get(y) != 32) {
                    result = "Niepoprawny znak";
                }
            }

        } else {

//            Standardization of messages to the supported format.
            String substringMessage = roughMessage
                    .substring(3, roughMessage.length()-3)
                    .replace("//","/|/");
            String[] splittedMessage = substringMessage.split("/");
            Map<Integer, String> mappedSign = new HashMap<>();

            for (int q = 0; q < splittedMessage.length; q++) {
                String trimMessage = splittedMessage[q].trim();
                mappedSign.put(q, trimMessage);
            }

//            Encryption mors to latin.
            for (int o = 0; o < mappedSign.size(); o++) {
                for (int p = 0; p < mappedMors.size(); p++) {
                    if (mappedSign.get(o).equals(mappedMors.get(p)) & !mappedSign.get(o).equals("|")) {
                        listedPassword.add(mappedAlphabet.get(p).toString());
                        break;
                    } else if (mappedSign.get(o).equals("|")) {
                        listedPassword.add(" ");
                    }
                }
            }

//            Prepare result format.
            for (String letter : listedPassword) {
                result += letter;
            }

//            Validation.
            for (int i = 0; i < mappedSign.size(); i++) {
                if (!mappedMors.containsValue(mappedSign.get(i)) & !mappedSign.get(i).equals("|")) {
                    result = "Niepoprawny znak";
                }
            }
        }

        return new OutPassword(result);
    }
}
