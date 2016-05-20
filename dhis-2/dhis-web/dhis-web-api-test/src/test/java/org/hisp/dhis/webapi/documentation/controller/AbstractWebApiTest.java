package org.hisp.dhis.webapi.documentation.controller;


/*
 * Copyright (c) 2004-2016, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import org.hisp.dhis.common.IdentifiableObject;
import org.hisp.dhis.dataelement.CategoryOptionGroup;
import org.hisp.dhis.dataelement.CategoryOptionGroupSet;
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.dataelement.DataElementCategory;
import org.hisp.dhis.dataelement.DataElementCategoryCombo;
import org.hisp.dhis.dataelement.DataElementCategoryOption;
import org.hisp.dhis.dataelement.DataElementCategoryOptionCombo;
import org.hisp.dhis.dataelement.DataElementGroup;
import org.hisp.dhis.dataelement.DataElementGroupSet;
import org.hisp.dhis.dataelement.DataElementOperand;
import org.hisp.dhis.dataelement.DataElementOperandService;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.program.Program;
import org.hisp.dhis.schema.Schema;
import org.hisp.dhis.webapi.DhisWebSpringTest;
import org.hisp.dhis.webapi.documentation.common.ResponseDocumentation;
import org.hisp.dhis.webapi.documentation.common.TestUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Viet Nguyen <viet@dhis.org>
 */
public abstract class AbstractWebApiTest<T extends IdentifiableObject>
    extends DhisWebSpringTest
{
    private T t;

    protected Class<T> testClass;

    protected Schema schema;

    protected String apiEndPoint;

    @Autowired
    private DataElementOperandService operandService;

    @Override
    @SuppressWarnings( "unchecked" )
    public void setUpTest()
    {
        testClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        schema = schemaService.getSchema( testClass );
        apiEndPoint = schema.getPlural();

    }

    @SuppressWarnings( "unchecked" )
    protected <T> T createTestObject( Class<?> clazz, char uniqueName, Object... params )
    {
        if ( DataElementGroup.class.isAssignableFrom( clazz ) )
        {
            return (T) createDataElementGroup( uniqueName );
        }
        else if ( DataElementCategoryCombo.class.isAssignableFrom( clazz ) )
        {
            return (T) createCategoryCombo( uniqueName, Arrays.copyOf( params, params.length, DataElementCategory[].class ) );
        }
        else if ( DataElementCategoryOption.class.isAssignableFrom( clazz ) )
        {
            return (T) createCategoryOption( uniqueName );
        }
        else if ( DataElement.class.isAssignableFrom( clazz ) )
        {
            return (T) createDataElement( uniqueName );
        }
        else if ( DataElementCategory.class.isAssignableFrom( clazz ) )
        {
            return (T) createDataElementCategory( uniqueName, Arrays.copyOf( params, params.length, DataElementCategoryOption[].class ) );
        }
        else if ( Program.class.isAssignableFrom( clazz ) )
        {
            OrganisationUnit organisationUnitA = createOrganisationUnit( uniqueName );
            manager.save( organisationUnitA );
            Program programA = createProgram( uniqueName, new HashSet<>(), organisationUnitA );

            return (T) programA;
        }
        else if ( DataElementOperand.class.isAssignableFrom( clazz ) )
        {
            DataElement deA = createDataElement( uniqueName );
            manager.save( deA );
            DataElementCategoryCombo cc = createCategoryCombo( uniqueName );
            DataElementCategoryOption co = createCategoryOption( uniqueName );
            manager.save( cc );
            manager.save( co );
            DataElementCategoryOptionCombo coc = createCategoryOptionCombo( cc, co );
            manager.save( coc );
            DataElementOperand opA = new DataElementOperand( deA, coc );
            return (T) opA;
        }
        else if ( DataElementGroupSet.class.isAssignableFrom( clazz ) )
        {
            return (T) createDataElementGroupSet( uniqueName );
        }
        else if ( DataElementCategoryOptionCombo.class.isAssignableFrom( clazz ) )
        {
            DataElementCategoryCombo cc = createCategoryCombo( uniqueName );
            DataElementCategoryOption co = createCategoryOption( uniqueName );
            manager.save( cc );
            manager.save( co );
            return (T) createCategoryOptionCombo( cc, co );
        }
        else if ( CategoryOptionGroup.class.isAssignableFrom( clazz ) )
        {
            DataElementCategoryOption co = createCategoryOption( uniqueName );
            manager.save( co );
            CategoryOptionGroup cog = createCategoryOptionGroup( uniqueName, co );
            return (T) cog;

        }
        else if ( CategoryOptionGroupSet.class.isAssignableFrom( clazz ) )
        {
            return (T) createCategoryOptionGroupSet( uniqueName );
        }
        return null;
    }

    @Test
    public void testGetAll() throws Exception
    {

        Map<Class<? extends IdentifiableObject>, IdentifiableObject> defaultObjectMap = manager.getDefaults();
        IdentifiableObject defaultTestObject = defaultObjectMap.get( testClass );
        int valueToTest = defaultTestObject != null ? 5 : 4;

        manager.save( createTestObject( testClass, 'A' ) );
        manager.save( createTestObject( testClass, 'B' ) );
        manager.save( createTestObject( testClass, 'C' ) );
        manager.save( createTestObject( testClass, 'D' ) );
        MockHttpSession session = getSession( "ALL" );

        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        fieldDescriptors.addAll( ResponseDocumentation.pager() );
        fieldDescriptors.add( fieldWithPath( schema.getPlural() ).description( schema.getPlural() ) );

        mvc.perform( get( "/" + schema.getPlural() ).session( session ).accept( TestUtils.APPLICATION_JSON_UTF8 ) )
            .andExpect( status().isOk() )
            .andExpect( content().contentTypeCompatibleWith( TestUtils.APPLICATION_JSON_UTF8 ) )
            .andExpect( jsonPath( "$." + schema.getPlural() ).isArray() )
            .andExpect( jsonPath( "$." + schema.getPlural() + ".length()" ).value( valueToTest ) )
            .andDo( documentPrettyPrint( schema.getPlural() + "/all",
                responseFields( fieldDescriptors.toArray( new FieldDescriptor[fieldDescriptors.size()] ) )
            ) );
    }

    @Test
    public void testGetByIdOk() throws Exception
    {
        MockHttpSession session = getSession( "ALL" );

        T object = createTestObject( testClass, 'A' );
        manager.save( object );
        Set<FieldDescriptor> fieldDescriptors = TestUtils.getFieldDescriptors( schema );

        mvc.perform( get( "/" + schema.getPlural() + "/{id}", object.getUid() ).session( session ).accept( MediaType.APPLICATION_JSON ) )
            .andExpect( status().isOk() )
            .andExpect( content().contentTypeCompatibleWith( MediaType.APPLICATION_JSON ) )
            .andExpect( jsonPath( "$.name" ).value( object.getName() ) )
            .andDo( documentPrettyPrint( schema.getPlural() + "/id",
                responseFields( fieldDescriptors.toArray( new FieldDescriptor[fieldDescriptors.size()] ) ) ) );
    }

    @Test
    public void testCreate() throws Exception
    {
        MockHttpSession session = getSession( "ALL" );
        T object = createTestObject( testClass, 'A' );

        Set<FieldDescriptor> fieldDescriptors = TestUtils.getFieldDescriptors( schema );

        MvcResult result = mvc.perform( post( "/" + schema.getPlural() )
            .session( session )
            .contentType( TestUtils.APPLICATION_JSON_UTF8 )
            .content( TestUtils.convertObjectToJsonBytes( object ) ) )
            .andExpect( status().isOk() )
            .andDo( documentPrettyPrint( schema.getPlural() + "/create",
                requestFields( fieldDescriptors.toArray( new FieldDescriptor[fieldDescriptors.size()] ) ) )
            ).andReturn();

        String lastImportedId = TestUtils.getCreatedUid( result.getResponse().getContentAsString() );

        object = manager.get( testClass, lastImportedId );

        assertNotNull( object );
    }

    @Test
    public void testUpdate() throws Exception
    {
        MockHttpSession session = getSession( "ALL" );

        T object = createTestObject( testClass, 'A' );
        manager.save( object );

        object.setHref( "updatedHref" );

        mvc.perform( put( "/" + schema.getPlural() + "/" + object.getUid() )
            .session( session )
            .contentType( TestUtils.APPLICATION_JSON_UTF8 )
            .content( TestUtils.convertObjectToJsonBytes( object ) ) )
            .andExpect( status().isOk() )
            .andDo( documentPrettyPrint( schema.getPlural() + "/update" ) );

        object = manager.getByName( testClass, object.getName() );

        assertTrue( "updatedHref".equalsIgnoreCase( object.getHref() ) );
    }

    @Test
    public void testDeleteByIdOk() throws Exception
    {
        MockHttpSession session = getSession( "ALL" );

        T object = createTestObject( testClass, 'A' );
        manager.save( object );

        mvc.perform( delete( "/" + apiEndPoint + "/{id}", object.getUid() ).session( session ).accept( MediaType.APPLICATION_JSON ) )
            .andExpect( status().isNoContent() )
            .andDo( documentPrettyPrint( schema.getPlural() + "/delete" ) );
    }

    @Test
    public void testDeleteById404() throws Exception
    {
        MockHttpSession session = getSession( "ALL" );

        mvc.perform( delete( "/" + apiEndPoint + "/{id}", "deabcdefghA" ).session( session ).accept( MediaType.APPLICATION_JSON ) )
            .andExpect( status().isNotFound() );
    }
}
