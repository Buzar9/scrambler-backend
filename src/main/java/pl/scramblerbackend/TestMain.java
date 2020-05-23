package pl.scramblerbackend;

import pl.scramblerbackend.dao.MorsDao;
import pl.scramblerbackend.service.MorsService;

import java.io.FileNotFoundException;

public class TestMain {
    public static void main(String[] args) throws FileNotFoundException {

        MorsService morsService = new MorsService();

        System.out.println(morsService.encrypt("A"));

    }
}
