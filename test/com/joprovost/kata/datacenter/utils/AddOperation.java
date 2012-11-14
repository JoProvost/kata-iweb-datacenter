package com.joprovost.kata.datacenter.utils;

import com.joprovost.kata.datacenter.Datacenter;
import com.joprovost.kata.datacenter.Server;
import com.joprovost.kata.datacenter.Vm;
import com.joprovost.kata.datacenter.core.datacenter.DatacenterCore;

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

   public boolean to(DatacenterCore datacenter) {
      return datacenter.add(vm);
   }

   public boolean to(Datacenter datacenter) {
      return datacenter.add(vm);
   }
}
