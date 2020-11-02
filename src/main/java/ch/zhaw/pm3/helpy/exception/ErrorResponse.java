package ch.zhaw.pm3.helpy.exception;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * This class is used by the {@link ApiExceptionHandler} to create
 * custom error responses.
 */
@XmlRootElement(name = "error")
public class ErrorResponse {

    private String message;
    private List<String> details;

    /**
     * Creates a new error response instance.
     * @param message String
     * @param details {@link List}<String>
     */
    public ErrorResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }

    /**
     * Returns the error message.
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     * @param message String
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns a list of details of what went wrong.
     * @return List<String>
     */
    public List<String> getDetails() {
        return details;
    }

    /**
     * Sets a list of details of what went wrong.
     * @param details List<String>
     */
    public void setDetails(List<String> details) {
        this.details = details;
    }
}
