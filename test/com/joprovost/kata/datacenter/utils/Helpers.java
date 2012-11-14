package com.joprovost.kata.datacenter.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;

import java.io.IOException;
import java.io.StringWriter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class Helpers {
   public static Matcher<Boolean> fails() {
      return is(false);
   }

   public static Matcher<Boolean> works() {
      return is(true);
   }

   public static <T> T a(Builder<T> builder) {
      return builder.build();
   }

   public static void andThat(boolean b) {
      assertTrue(b);
   }

   public static <T> T fromJson(String json, Class<T> clazz) {
      try {
         ObjectMapper mapper = new ObjectMapper();
         mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
         return mapper.readValue(json, clazz);
      } catch (IOException e) {
         fail(e.getMessage());
      }
      return null;
   }

   public static String toJson(Object o) {
      StringWriter writer = new StringWriter();
      try {
         ObjectMapper mapper = new ObjectMapper();
         mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
         mapper.writeValue(writer, o);
      } catch (IOException e) {
         fail(e.getMessage());
      }
      return writer.toString();
   }
}
