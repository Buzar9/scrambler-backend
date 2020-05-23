package pl.scramblerbackend.dao;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class MorsDao {

    public Map<Integer, Character> alphabetReader() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("/Users/marek/Documents/GitHub/scrambler-backend/src/main/resources/static/Alphabet.txt"));
        Map<Integer, Character> mappedAlphabet = new HashMap<>();
        int counter = 0;

        while (scanner.hasNextLine()) {
            mappedAlphabet.put(counter, scanner.nextLine().charAt(0));
            counter++;
        }

        return mappedAlphabet;
    }

    public Map<Integer, String> morsReader() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("/Users/marek/Documents/GitHub/scrambler-backend/src/main/resources/static/Mors.txt"));
        Map<Integer, String> mappedMors = new HashMap<>();
        int counter = 0;

        while (scanner.hasNextLine()) {
            mappedMors.put(counter, scanner.nextLine());
            counter++;
        }

        return mappedMors;
    }
}
