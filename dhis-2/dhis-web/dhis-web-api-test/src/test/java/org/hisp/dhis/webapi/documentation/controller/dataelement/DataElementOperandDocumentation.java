package org.hisp.dhis.webapi.documentation.controller.dataelement;


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

import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.dataelement.DataElementCategoryOptionCombo;
import org.hisp.dhis.dataelement.DataElementOperand;
import org.hisp.dhis.webapi.documentation.common.ResponseDocumentation;
import org.hisp.dhis.webapi.documentation.common.TestUtils;
import org.hisp.dhis.webapi.documentation.controller.AbstractWebApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Viet Nguyen <viet@dhis.org>
 */
public class DataElementOperandDocumentation
    extends AbstractWebApiTest<DataElementOperand>
{


    @Test
    public void testCreate() throws Exception
    {
/*
        MockHttpSession session = getSession( "ALL" );
        DataElementOperand object = createTestObject( testClass, 'A' );

        Set<FieldDescriptor> fieldDescriptors = TestUtils.getFieldDescriptors( schema );

        MvcResult result = mvc.perform( post( "/" + schema.getPlural() )
            .session( session )
            .contentType( TestUtils.APPLICATION_JSON_UTF8 )
            .content( TestUtils.convertObjectToJsonBytes( object ) ) )
            .andExpect( status().isOk() )
            .andDo( documentPrettyPrint( schema.getPlural() + "/create",
                requestFields( fieldDescriptors.toArray( new FieldDescriptor[fieldDescriptors.size()] ) ) )
            ).andReturn();

        System.out.println( "result.getResponse().getContentAsString() = " + result.getResponse().getContentAsString() );
        String lastImportedId = TestUtils.getCreatedUid( result.getResponse().getContentAsString() );

        object = manager.get( testClass, lastImportedId );

        assertNotNull( object );
        */
    }

    @Test
    public void testGetByIdOk() throws Exception
    {
        // TODO fix the issue of querying uid with format aaaa.bbbb
//        MockHttpSession session = getSession( "ALL" );
//
//        DataElementOperand object = createTestObject( testClass, 'A' );
//        manager.save( object );
//        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
//        fieldDescriptors.addAll( ResponseDocumentation.pager() );
//        fieldDescriptors.add( fieldWithPath( "dataElementOperands" ).description( "DataElementOperand" ) );
//
//        mvc.perform( get( "/" + schema.getPlural() + "?filter=id:eq:" + object.getDimensionItem() ).session( session ).accept( MediaType.APPLICATION_JSON ) )
//            .andDo( documentPrettyPrint( schema.getPlural() + "/id",
//                responseFields( fieldDescriptors.toArray( new FieldDescriptor[fieldDescriptors.size()] ))))
//            .andExpect( status().isOk() )
//            .andExpect( content().contentTypeCompatibleWith( MediaType.APPLICATION_JSON ) )
//            .andExpect( jsonPath( "$.dataElementOperands[*].displayName" ).value( object.getDisplayName() ) );
    }



    @Test
    public void testDeleteByIdOk() throws Exception
    {
          // TODO fix the issue of querying uid with format aaaa.bbbb
//        MockHttpSession session = getSession( "ALL" );
//
//        DataElementOperand object = createTestObject( testClass,  'A' );
//        manager.save( object );
//
//        mvc.perform( delete( "/" + apiEndPoint + "/{id}",  object.getOperandExpression() ).session( session ).accept( MediaType.APPLICATION_JSON ) )
//            .andDo( documentPrettyPrint( schema.getPlural() + "/delete" ) )
//            .andExpect( status().isNoContent() );
    }

    @Test
    public void testUpdate() throws Exception
    {

        // TODO fix the issue of querying uid with format aaaa.bbbb
        /*
        MockHttpSession session = getSession( "ALL" );

        DataElementOperand object = createTestObject( testClass, 'A' );
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
        */
    }


}
