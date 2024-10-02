package hexlet.code.schemas.number;

import hexlet.code.schemas.BaseSchema;

public class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        super.strategies.put(RequiredValidation.NAME, new RequiredValidation());
        return this;
    }

    public NumberSchema positive() {
        strategies.put(PositiveValidation.NAME, new PositiveValidation());
        return this;
    }

    public NumberSchema range(Integer minValue, Integer maxValue) {
        super.strategies.put(RangeValidation.NAME, new RangeValidation(minValue, maxValue));
        return this;
    }
}
