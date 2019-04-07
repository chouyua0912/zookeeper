package z.learn.baseline.annotations;

import z.learn.baseline.processor.ProcessorEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute {

    ProcessorEnum[] value() default ProcessorEnum.JSON;
}
