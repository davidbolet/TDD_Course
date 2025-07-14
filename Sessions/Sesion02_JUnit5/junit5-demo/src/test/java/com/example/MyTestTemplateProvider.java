package com.example;

import org.junit.jupiter.api.extension.*;

import java.util.List;
import java.util.stream.Stream;

public class MyTestTemplateProvider implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        return Stream.of("foo", "bar", "baz")
                .map(str -> new TestTemplateInvocationContext() {
                    @Override
                    public String getDisplayName(int invocationIndex) {
                        return "Input: " + str;
                    }

                    @Override
                    public List<Extension> getAdditionalExtensions() {
                        return List.of(new ParameterResolver() {
                            @Override
                            public boolean supportsParameter(ParameterContext pc, ExtensionContext ec) {
                                return pc.getParameter().getType() == String.class;
                            }

                            @Override
                            public Object resolveParameter(ParameterContext pc, ExtensionContext ec) {
                                return str;
                            }
                        });
                    }
                });
    }
}
