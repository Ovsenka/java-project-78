package hexlet.code;

import hexlet.code.schemas.string.StringSchema;
import hexlet.code.schemas.number.NumberSchema;

public class Validator {
    public Validator() {
    }

    public StringSchema string() {
        return new StringSchema();
    }

    public NumberSchema number() {
        return new NumberSchema();
    }
}
