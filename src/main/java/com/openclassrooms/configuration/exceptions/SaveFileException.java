package com.openclassrooms.configuration.exceptions;

import java.io.IOException;

public class SaveFileException extends IOException {

    public SaveFileException(String message) {
        super(message);
    }

}
