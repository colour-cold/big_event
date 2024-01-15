package me.colourcold.annotion;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import me.colourcold.validation.StateValidation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {StateValidation.class}
)
public @interface State {
    String message() default "状态只能为已发布或者草稿";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
