package ru.arcsinus.salesblast.event;

/**
 * Created by Andrei on 15.11.2016.
 */

public class ErrorEvent {
    public static final int ERROR_CONNECTION = 1;

    private final int error;

    public ErrorEvent(int error) {
        this.error = error;
    }

    public int getError() {
        return error;
    }
}
