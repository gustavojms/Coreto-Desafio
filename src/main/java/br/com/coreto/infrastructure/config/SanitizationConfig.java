package br.com.coreto.infrastructure.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class SanitizationConfig {

    @Bean
    public SimpleModule htmlSanitizationModule() {
        SimpleModule module = new SimpleModule("HtmlSanitization");
        module.addDeserializer(String.class, new SanitizingStringDeserializer());
        return module;
    }

    private static class SanitizingStringDeserializer extends StringDeserializer {

        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String value = super.deserialize(p, ctxt);
            if (value == null) {
                return null;
            }
            return sanitize(value);
        }

        private String sanitize(String input) {
            return input
                    .replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;");
        }
    }
}
