package com.joprovost.kata.datacenter.utils;

import org.hamcrest.Matcher;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

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
}
