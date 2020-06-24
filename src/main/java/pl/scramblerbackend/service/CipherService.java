package pl.scramblerbackend.service;

import pl.scramblerbackend.entity.KeyNMessage;
import pl.scramblerbackend.entity.OutPassword;

import java.io.FileNotFoundException;

public interface CipherService {

    OutPassword encrypt(KeyNMessage keyNMessage) throws FileNotFoundException;
}
