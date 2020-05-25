package ru.zenegix.carrental.type;

import org.springframework.context.annotation.Import;
import ru.zenegix.carrental.config.JacksonConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(JacksonConfig.class)
public @interface IntegrationTest {
}
