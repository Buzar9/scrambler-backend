package pl.scramblerbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.scramblerbackend.dao.MorsDao;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// todo nie rozpoznaje uniwersalnego formatu wprowadzania morsa
// todo nie wprowadza spacji z łacińskiego na morsa


@Component
public class MorsService  {

    @Autowired
    private MorsDao morsDao;

    public String encrypt(String inMessage) throws FileNotFoundException {

        Map<Integer, Character> mappedAlphabet = morsDao.alphabetReader();
        Map<Integer, String> mappedMors = morsDao.morsReader();

        Map<Integer, Character> mappedPassword = new HashMap<>();
        List<String> outMessage = new ArrayList<>();

        String result = new String();
        int firstLetter = inMessage.charAt(0);

        String message = inMessage.toLowerCase();

        if (firstLetter >= 65) {
//            for (int j = 0; j < inMessage.length(); j++) {
//                mappedPassword.put(j, inMessage.charAt(j));
//            }

            for (int j =0; j < message.length(); j++) {
                mappedPassword.put(j, message.charAt(j));
            }

            for (int t = 0; t < mappedPassword.size(); t++) {
                for (int r = 0; r < mappedAlphabet.size(); r++) {
                    if (mappedPassword.get(t) == mappedAlphabet.get(r)) {
                        outMessage.add(mappedMors.get(r));
                        break;
                    }
                }
            }

            for (String letter : outMessage) {
                result += letter + "/ ";
            }
        } else {
            String[] splittedPassword = inMessage.split("/");
            String[] splittedLetter = new String[splittedPassword.length];
            String trimLetter;

            for (int q = 0; q < splittedPassword.length; q++) {
                trimLetter = splittedPassword[q].trim();
                splittedLetter[q] = trimLetter;
            }

            for (int o = 0; o < splittedPassword.length; o++) {
                for (int p = 0; p < mappedMors.size(); p++) {
                    int a = splittedPassword[o].compareTo(mappedMors.get(p));
                    if (a == 0) {
                        outMessage.add(mappedAlphabet.get(p).toString());
                        break;
                    }
                }
            }

            for (String letter : outMessage) {
                result += letter;
            }
        }

        mappedPassword.clear();
        outMessage.clear();

        return result;
    }
}
