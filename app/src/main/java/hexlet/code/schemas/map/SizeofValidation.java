package hexlet.code.schemas.map;

import hexlet.code.schemas.ValidationStrategy;

import java.util.Map;

public final class SizeofValidation<K, V> implements ValidationStrategy<Map<K, V>> {
    public static final String NAME = "sizeof";
    private final int validSize;

    public SizeofValidation(int validSize) {
        this.validSize = validSize;
    }

    @Override
    public boolean validate(Map<K, V> map) {
        return map.size() == this.validSize;
    }
}
