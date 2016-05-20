package org.hisp.dhis.webapi.documentation.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.codehaus.jackson.JsonNode;
import org.hisp.dhis.schema.Property;
import org.hisp.dhis.schema.Schema;
import org.hisp.dhis.security.AuthorityType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

/**
 * @author Viet Nguyen <viet@dhis.org>
 */
public class TestUtils
{
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType( MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName( "utf8" ) );

    public static byte[] convertObjectToJsonBytes( Object object ) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion( JsonInclude.Include.NON_NULL );
        objectMapper.configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false );
        objectMapper.configure( SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false );
        objectMapper.configure( SerializationFeature.FAIL_ON_EMPTY_BEANS, false );
        objectMapper.configure( SerializationFeature.WRAP_EXCEPTIONS, true );

        objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        objectMapper.configure( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true );
        objectMapper.configure( DeserializationFeature.WRAP_EXCEPTIONS, true );

        objectMapper.disable( MapperFeature.AUTO_DETECT_FIELDS );
        objectMapper.disable( MapperFeature.AUTO_DETECT_CREATORS );
        objectMapper.disable( MapperFeature.AUTO_DETECT_GETTERS );
        objectMapper.disable( MapperFeature.AUTO_DETECT_SETTERS );
        objectMapper.disable( MapperFeature.AUTO_DETECT_IS_GETTERS );

        return objectMapper.writeValueAsBytes( object );
    }

    public static String getFieldDescription( Property p )
    {
        String desc = "";
        desc += p.isRequired() ? "Required, " : "";
        desc += p.isAttribute() ? "Attribute, " : "";
        desc += p.isReadable() ? "Readable" : "";

        return desc;
    }

    public static Set<FieldDescriptor> getFieldDescriptors( Schema schema )
    {
        Set<FieldDescriptor> fieldDescriptors = new HashSet<>();
        Map<String, Property> persistedPropertyMap = schema.getPersistedProperties();

        Iterator persistedItr = persistedPropertyMap.keySet().iterator();

        while ( persistedItr.hasNext() )
        {
            Property p = persistedPropertyMap.get( persistedItr.next() );
            String pName = p.isCollection() ? p.getCollectionName() : p.getName();
            FieldDescriptor f = fieldWithPath( pName ).description( TestUtils.getFieldDescription( p ) );

            if ( !p.isRequired() )
            {
                f.optional().type( p.getPropertyType() );
            }

            fieldDescriptors.add( f );
        }

        Map<String, Property> nonPersistedPropertyMap = schema.getNonPersistedProperties();
        Iterator nonPersistedItr = nonPersistedPropertyMap.keySet().iterator();

        while ( nonPersistedItr.hasNext() )
        {
            Property p = nonPersistedPropertyMap.get( nonPersistedItr.next() );
            String pName = p.isCollection() ? p.getCollectionName() : p.getName();
            FieldDescriptor f = fieldWithPath( pName ).description( TestUtils.getFieldDescription( p ) );

            if ( !p.isRequired() )
            {
                f.optional().type( p.getPropertyType() );
            }
            fieldDescriptors.add( f );
        }

        return fieldDescriptors;
    }

    public static String getCreatedUid( String responseJson ) throws IOException
    {
        org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
        JsonNode node = mapper.readTree( responseJson );
        return node.get( "response" ).get( "lastImported" ).asText();
    }

    public static String getAuthority( Schema schema, AuthorityType authType )
    {
        List<String> auth = schema.getAuthorityByType( authType );
        if ( auth == null || auth.size() == 0 )
        {
            return "ALL";
        }
        else
        {
            return auth.get( 0 );
        }
    }
}
