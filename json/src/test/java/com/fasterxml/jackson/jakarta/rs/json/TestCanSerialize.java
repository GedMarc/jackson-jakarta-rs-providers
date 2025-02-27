package com.fasterxml.jackson.jakarta.rs.json;

import java.io.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.fasterxml.jackson.databind.DefaultTyping;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import com.fasterxml.jackson.jakarta.rs.json.testutil.NoCheckSubTypeValidator;

public class TestCanSerialize extends JakartaRSTestBase
{
    static class Simple {
        protected List<String> list;

        public List<String> getList( ) { return list; }
        public void setList(List<String> l) { list = l; }
    }

    public void testCanSerialize() throws IOException
    {
        ObjectMapper mapper = JsonMapper.builder()
                .activateDefaultTyping(NoCheckSubTypeValidator.instance,
                        DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY)
                .build();
    
        // construct test object
        List<String> l = new ArrayList<String>();
        l.add("foo");
        l.add("bar");
    
        Simple s = new Simple();
        s.setList(l);

        // but with problem of [JACKSON-540], we get nasty surprise here...
        String json = mapper.writeValueAsString(s);
        
        Simple result = mapper.readValue(json, Simple.class);
        assertNotNull(result.list);
        assertEquals(2, result.list.size());
    }
}
