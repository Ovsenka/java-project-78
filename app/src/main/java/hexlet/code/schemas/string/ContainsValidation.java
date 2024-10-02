package hexlet.code.schemas.string;

import hexlet.code.schemas.ValidationStrategy;

public class ContainsValidation implements ValidationStrategy<String> {

    public static final String NAME = "contains";
    private final String substring;

    public ContainsValidation(String substring) {
        this.substring = substring;
    }
    public boolean validate(String input) {
        return input.contains(substring);
    }
}
