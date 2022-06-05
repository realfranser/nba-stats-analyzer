package validators;

import javax.swing.*;
import java.awt.*;

public class NameValidator {

    public static final String NAME_ERROR_TITLE = "ERROR";
    public static final String NAME_ERROR = "El nombre del jugador solo debe contener letras";

    public static boolean isValidName(final String name) {

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);

            if (!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == ' ')) {
                return false;
            }
        }

        return true;
    }
}
