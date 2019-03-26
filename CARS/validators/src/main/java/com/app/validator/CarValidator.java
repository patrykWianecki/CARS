package com.app.validator;

import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
import com.app.model.Car;
import com.app.model.enums.Color;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CarValidator {
    private Map<String, String> errors = new LinkedHashMap<>();

    public Map<String, String> validate(Car car) {
        errors.clear();
        if (car == null) {
            errors.put("car", "object is null");
        }
        if (!isModelValid(car.getModel())) {
            errors.put("model", "model is not valid");
        }
        if (!isPriceValid(car.getPrice())) {
            errors.put("price", "price is not valid");
        }
        if (!isColorValid(car.getColor())) {
            errors.put("color", "color is not valid");
        }
        if (!isMileageValid(car.getMileage())) {
            errors.put("mileage", "mileage is not valid");
        }
        if (!areComponentsValid(car.getComponents())) {
            errors.put("components", "components are not valid");
        }
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    private boolean isModelValid(String model) {
        if (model == null || !model.matches("[A-Z]+\\s{0,1}[A-Z]*")) {
            throw new MyException(ExceptionCode.VALIDATOR, "MODEL IS NOT VALID: " + model);
        }
        return true;
    }

    private boolean isPriceValid(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new MyException(ExceptionCode.VALIDATOR, "PRICE IS NOT VALID: " + price);
        }
        return true;
    }

    private boolean isColorValid(Color color) {
        if (color == null) {
            throw new MyException(ExceptionCode.VALIDATOR, "COLOR IS NULL");
        }
        Arrays.stream(Color.values()).anyMatch(enumColor -> enumColor.equals(color));
        return true;
    }

    private boolean isMileageValid(Long mileage) {
        if (mileage == null || mileage < 0L) {
            throw new MyException(ExceptionCode.VALIDATOR, "MILEAGE IS NOT VALID: " + mileage);
        }
        return true;
    }

    private boolean areComponentsValid(Set<String> components) {
        if (components == null || !(components.stream().allMatch(this::isComponentsValid))) {
            throw new MyException(ExceptionCode.VALIDATOR, "COMPONENTS ARE NOT VALID: " + components);
        }
        return true;
    }

    private boolean isComponentsValid(String component) {
        return component.matches("[A-Z]+\\s{0,1}[A-Z]*");
    }
}
