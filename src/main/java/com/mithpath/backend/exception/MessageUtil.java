package com.mithpath.backend.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class MessageUtil {

    private static final ResourceBundle messages = ResourceBundle.getBundle("notification-messages");

    public static String getProperty(String key, Object... args) {
        String pattern = messages.getString(key);
        return MessageFormat.format(pattern, args);
    }
}
