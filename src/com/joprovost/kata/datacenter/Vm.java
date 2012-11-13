package com.joprovost.kata.datacenter;

public class Vm {
   private final String id;
   private final int size;

   private Vm() {
      id = null;
      size = 0;
   }

   public Vm(final String id, final int size) {
      this.id = id;
      this.size = size;
   }

   public int getSize() {
      return size;
   }

   public boolean equals(Object o) {
      if (o instanceof Vm) {
         Vm vm = (Vm) o;
         return areEqual(id, vm.id) && size == vm.size;
      }
      return false;
   }

   private static boolean areEqual(Object o1, Object o2) {
      return o1 == o2 || o1 != null && o2 != null && o1.equals(o2);
   }
}
