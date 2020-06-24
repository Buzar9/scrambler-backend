package pl.scramblerbackend.dao;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Component
public class PangramDao {

    public List<Character> polishReader() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("/Users/marek/Documents/GitHub/scrambler-backend/src/main/resources/static/PolishAlphabet.txt"));
        List<Character> listedPolish = new ArrayList<>();
        int counter = 0;

        while (scanner.hasNextLine()) {
            listedPolish.add(counter, scanner.nextLine().charAt(0));
            counter++;
        }

        return listedPolish;
    }

    public List<Character> punctuationMarksReader() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("/Users/marek/Documents/GitHub/scrambler-backend/src/main/resources/static/PunctuationMarks.txt"));
        List<Character> listedPunctuation = new ArrayList<>();
        int counter = 0;

        while (scanner.hasNextLine()) {
            listedPunctuation.add(counter, scanner.nextLine().charAt(0));
            counter++;
        }

        return listedPunctuation;
    }

   }
