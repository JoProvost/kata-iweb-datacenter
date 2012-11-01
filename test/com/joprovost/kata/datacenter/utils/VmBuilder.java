package com.joprovost.kata.datacenter.utils;

import com.joprovost.kata.datacenter.Vm;

public class VmBuilder implements Builder<Vm> {
   private String id;
   private int size;

   public static VmBuilder vm() {
      return new VmBuilder();
   }

   @Override
   public Vm build() {
      return new Vm(id, size);
   }

   public VmBuilder withSize(final int i) {
      size = i;
      return this;
   }

   public VmBuilder withId(String id) {
      this.id = id;
      return this;
   }
}
