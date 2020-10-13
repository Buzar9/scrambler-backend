package pl.scramblerbackend.dao;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class SyllabicDao {

    public List<Character> vowelReader() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("/Users/marek/Documents/GitHub/scrambler-backend/src/main/resources/static/Vowel.txt"));
        List<Character> listedVowel = new ArrayList<>();
        int counter = 0;

        while (scanner.hasNextLine()) {
            listedVowel.add(counter, scanner.nextLine().charAt(0));
            counter++;
        }

        return listedVowel;
    }
}
