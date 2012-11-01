package com.joprovost.kata.datacenter.utils;

import com.joprovost.kata.datacenter.Server;
import com.joprovost.kata.datacenter.Vm;

import java.util.ArrayList;
import java.util.Collection;

public class ServerBuilder implements Builder<Server> {
   private String id;
   private int capacity;
   private final Collection<Vm> vms = new ArrayList<Vm>();

   public static ServerBuilder server() {
      return new ServerBuilder();
   }

   @Override
   public Server build() {
      return new Server(id, capacity, vms);
   }

   public ServerBuilder withCapacity(final int i) {
      capacity = i;
      return this;
   }

   public ServerBuilder containing(Vm vm) {
      vms.add(vm);
      return this;
   }

   public ServerBuilder withId(final String id) {
      this.id = id;
      return this;
   }
}
