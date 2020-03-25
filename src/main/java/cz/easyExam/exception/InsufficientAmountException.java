package cz.easyExam.exception;

/**
 * Indicates that insufficient amount of a product is available for processing, e.g. for creating order items.
 */
public class InsufficientAmountException extends EarException {

    public InsufficientAmountException(String message) {
        super(message);
    }
}
