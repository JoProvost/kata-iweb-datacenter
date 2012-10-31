package com.joprovost.kata.datacenter.utils;

import com.joprovost.kata.datacenter.Server;
import com.joprovost.kata.datacenter.Vm;

public class AddOperation {
   private final Vm vm;

   public static AddOperation adding(final Vm vm) {
      return new AddOperation(vm);
   }

   public AddOperation(final Vm vm) {
      this.vm = vm;
   }

   public boolean to(final Server server) {
      return server.add(vm);
   }
}
