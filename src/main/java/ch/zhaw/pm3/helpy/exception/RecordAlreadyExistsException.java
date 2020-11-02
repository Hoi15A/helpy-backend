package ch.zhaw.pm3.helpy.exception;

/**
 * Is thrown if a record already exists in the database.
 */
public class RecordAlreadyExistsException extends RuntimeException {
    /**
     * Creates a new record already exists exception instance.
     * @param message String
     */
    public RecordAlreadyExistsException(String message) {
        super(message);
    }
}
