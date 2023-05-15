package uz.pdp.shippingservice.exception;

public class NotEnoughSeat extends RuntimeException {
    public NotEnoughSeat(String message) {
        super(message);
    }
}
