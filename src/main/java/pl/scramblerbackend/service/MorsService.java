package pl.scramblerbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.scramblerbackend.dao.MorsDao;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MorsService  {

    @Autowired
    MorsDao morsDao;

    public String encrypt(String inMessage) throws FileNotFoundException {

        Map<Integer, Character> mappedAlphabet = morsDao.alphabetReader();
        Map<Integer, String> mappedMors = morsDao.morsReader();

        Map<Integer, Character> mappedPassword = new HashMap<>();
        List<String> outMessage = new ArrayList<>();

        String result = new String();
        int firstLetter = inMessage.charAt(0);


        if (firstLetter >= 65) {
            for (int j = 0; j < inMessage.length(); j++)
                mappedPassword.put(j, inMessage.charAt(j));

            for (int t = 0; t < mappedPassword.size(); t++) {
                for (int r = 0; r < mappedAlphabet.size(); r++) {
                    if (mappedPassword.get(t) == mappedAlphabet.get(r)) outMessage.add(mappedMors.get(r));
                }
            }

            for (String letter : outMessage) {
                result += letter + "/ ";
            }
        } else {
            String[] splitedPassword = inMessage.split("/");
            for (int o = 0; o < splitedPassword.length; o++) {
                for (int p = 0; p < mappedMors.size(); p++) {
                    int a = splitedPassword[o].compareTo(mappedMors.get(p));
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
