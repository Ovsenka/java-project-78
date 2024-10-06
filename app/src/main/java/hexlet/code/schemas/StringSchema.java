package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {
    public StringSchema required() {
        addCheck("required", value -> !value.isEmpty());
        return this;
    }

    public StringSchema contains(String substring) {
        addCheck("contains", value -> value.contains(substring));
        return this;
    }

    public StringSchema minLength(int minLength) {
        addCheck("minLength", value -> value.length() >= minLength);
        return this;
    }
}
