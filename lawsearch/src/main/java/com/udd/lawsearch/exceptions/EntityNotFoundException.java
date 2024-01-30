package com.udd.lawsearch.exceptions;

import java.io.Serial;

public class EntityNotFoundException extends Exception{
    @Serial
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String type, Long id) {
        super(String.format("%s with id %d doesn't exist.", type, id));
    }
}
