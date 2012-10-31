package com.joprovost.kata.datacenter;

public class Vm {
   private final int size;

   public Vm(final int size) {
      this.size = size;
   }

   public int size() {
      return size;
   }
}
