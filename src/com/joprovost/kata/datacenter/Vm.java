package com.joprovost.kata.datacenter;

public class Vm {
   private final String id;
   private final int size;

   public Vm(final String id, final int size) {
      this.id = id;
      this.size = size;
   }

   public int size() {
      return size;
   }
}
