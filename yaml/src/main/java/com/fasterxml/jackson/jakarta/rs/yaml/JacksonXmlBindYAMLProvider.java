package com.fasterxml.jackson.jakarta.rs.yaml;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.AnnotationIntrospector;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationIntrospector;

/**
 * JSON content type provider automatically configured to use both Jackson
 * and Jakarta XmlBind annotations (in that order of priority). Otherwise functionally
 * same as {@link JacksonYAMLProvider}.
 *<p>
 * Typical usage pattern is to just instantiate instance of this
 * provider for Jakarta-RS and use as is: this will use both Jackson and
 * Jakarta XmlBind annotations (with Jackson annotations having priority).
 *<p>
 * Note: class annotations are duplicated from super class, since it
 * is not clear whether Jakarta-RS implementations are required to
 * check settings of super-classes. It is important to keep annotations
 * in sync if changed.
 */
@Provider
@Consumes(MediaType.WILDCARD) // NOTE: required to support "non-standard" type variants
@Produces(MediaType.WILDCARD)
public class JacksonXmlBindYAMLProvider extends JacksonYAMLProvider
{
    /**
     * Default constructor, usually used when provider is automatically
     * configured to be used with JAX-RS implementation.
     */
    public JacksonXmlBindYAMLProvider() {
        this(null, JaxbHolder.get());
    }

    /**
     * Constructor to use when a custom mapper (usually components
     * like serializer/deserializer factories that have been configured)
     * is to be used.
     */
    public JacksonXmlBindYAMLProvider(YAMLMapper mapper, AnnotationIntrospector aiOverride) {
        super(mapper, aiOverride);
    }

    // Silly class to encapsulate reference to JAXB introspector class so that
    // loading of parent class does not require it; only happens if and when
    // introspector needed
    private static class JaxbHolder {
        public static AnnotationIntrospector get() {
            return new JakartaXmlBindAnnotationIntrospector();
        }
    }
}
