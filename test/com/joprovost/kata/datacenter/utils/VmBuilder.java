package com.joprovost.kata.datacenter.utils;

import com.joprovost.kata.datacenter.Vm;

public class VmBuilder implements Builder<Vm> {
   private int size;


   public static VmBuilder vm() {
      return new VmBuilder();
   }

   @Override
   public Vm build() {
      return new Vm(size);
   }

   public VmBuilder withSize(final int i) {
      size = i;
      return this;
   }
}
