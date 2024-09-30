package hexlet.code.schemas;

public class StringSchema {
    private boolean isRequired;

    private int minLength;

    private String containsString;

    public StringSchema() {
        isRequired = false;
        minLength = 0;
        containsString = "";
    }
    public StringSchema required() {
        isRequired = true;
        return this;
    }

    public StringSchema minLength(int length) {
        minLength = length;
        return this;
    }

    public StringSchema contains(String substring) {
        containsString = substring;
        return this;
    }

    public boolean isValid(String string) {
        if (string == null && isRequired) {
            return false;
        } else if (string != null && string.isEmpty() && isRequired) {
            return false;
        } else if (string != null && string.length() < minLength) {
            return false;
        } else {
            return string == null || string.contains(containsString);
        }
    }
}
