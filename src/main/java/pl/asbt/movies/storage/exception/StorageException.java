package pl.asbt.movies.storage.exception;

import lombok.Builder;

@Builder
public class StorageException extends Exception {
    private ErrorType errorType;
    private String message;
}
