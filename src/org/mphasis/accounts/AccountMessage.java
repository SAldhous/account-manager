package org.mphasis.accounts;

import java.time.LocalDateTime;

public class AccountMessage {
    private String message;
    private LocalDateTime dateTime;

    public AccountMessage(String message) {
        this.message = message;
        dateTime = LocalDateTime.now();
    }

    // Returns a message with included date/time of creation
    @Override
    public String toString() {
        return message + " - (" + dateTime.toString() + ")";
    }
}
