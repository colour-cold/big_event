package me.colourcold.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import me.colourcold.annotion.State;

public class StateValidation implements ConstraintValidator<State, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.equals("已发布") || s.equals("草稿");
    }
}
