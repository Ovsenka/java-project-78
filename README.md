### Hexlet tests and linter status:
[![Hexlet Check](https://github.com/Ovsenka/java-project-78/actions/workflows/hexlet-check.yml/badge.svg?branch=main)](https://github.com/Ovsenka/java-project-78/actions/workflows/hexlet-check.yml)
[![Java CI](https://github.com/Ovsenka/java-project-78/actions/workflows/main.yml/badge.svg?branch=main)](https://github.com/Ovsenka/java-project-78/actions/workflows/main.yml)
[![Maintainability](https://api.codeclimate.com/v1/badges/35fe8f5842302dea53ba/maintainability)](https://codeclimate.com/github/Ovsenka/java-project-78/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/35fe8f5842302dea53ba/test_coverage)](https://codeclimate.com/github/Ovsenka/java-project-78/test_coverage)

# Data  Validator

Data validator it's simple fluent-interfaced library that can validate given values with concrete rules.

Supports validation of data types such as:
* Integer 
* String 
* Map

If the **value is null** and there is **no required rule**, then **all** instructions will be validated automatically.

## Usage

### Create validator schemas

```java
Validator v = new Validator();

NumberSchema num = v.number();
StringSchema str = v.string();
MapSchema<?, ?> map = v.map();
```

### StringSchema validation

```java
StringSchema strSchema = str.required().minLength(4);  
boolean isValid = strRules.isValid("Some text"); // true
```


### Validation rules

##### Number validation rules

* ```required()``` - the number must  **not**  be  equal to **null**;
* ```positive()``` - is the number positive;
* ```range(1, 3)``` - is the number in the specified range, from  1  to  3  including.

##### String validation rules

* ```required()``` - the string must  **not**  be  equal to **null**;
* ```contains("substring")``` - the specified substring must be in the string;
* ```minLength(5)``` - the length of the string  must  be  greater than or  equal to the specified length.

##### Map validation rules

* ```required()``` - the map must  **not**  be  equal to **null**;
* ```sizeof(2)``` - the map must be of the specified size.

You can also create rules for the key value in the Map:

```java
Validator v = new Validator();  
MapSchema<String, String> schema = v.map();  
  
Map<String, BaseSchema<String>> schemas = new HashMap<>();  
schemas.put("firstName", v.string().required());  
schemas.put("lastName", v.string().required().minLength(2));  
  
schema.shape(schemas);  
  
Map<String, String> human1 = new HashMap<>();  
human1.put("firstName", "John");  
human1.put("lastName", "Smith");  
schema.isValid(human1); // true
```
