package com.taguz91.api_serena;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.Serial;
import java.time.LocalDateTime;

@Configuration
public class JacksonCustomizations {

    @Bean
    public com.fasterxml.jackson.databind.Module apiRestModule() {
        return new ApiRestModules();
    }

    public static class ApiRestModules extends SimpleModule {
        @Serial
        private static final long serialVersionUID = -892211185288142887L;

        public ApiRestModules() {
            addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        }
    }

    public static class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {
        @Serial
        private static final long serialVersionUID = 409366482783302124L;

        protected LocalDateTimeSerializer() {
            super(LocalDateTime.class);
        }

        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (value == null) {
                gen.writeNull();
            } else {
                gen.writeString(value.format(Types.DATE_FORMAT));
            }

        }
    }
}
