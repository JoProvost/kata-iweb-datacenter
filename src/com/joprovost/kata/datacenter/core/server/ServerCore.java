package com.joprovost.kata.datacenter.core.server;

import com.joprovost.kata.datacenter.Server;
import com.joprovost.kata.datacenter.Vm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ServerCore implements Server {
   private final String id;
   private final int capacity;
   private int usePercentage;
   private final Collection<Vm> virtualMachines =
         Collections.synchronizedCollection(new ArrayList<Vm>());

   public ServerCore(String id, final int capacity, final Collection<Vm> virtualMachines) {
      this.id = id;
      this.capacity = capacity;
      this.virtualMachines.addAll(virtualMachines);
      updateUsePercentage();
   }

   private ServerCore() {
      this.id = "";
      this.capacity = 0;
      this.usePercentage = 0;
   }

   public boolean add(final Vm vm) {
      if (freeCapacity() >= vm.getSize() && virtualMachines.add(vm)) {
         updateUsePercentage();
         return true;
      }
      return false;
   }

   public boolean contains(Vm vm) {
      return virtualMachines.contains(vm);
   }

   public int usePercentage() {
      return usePercentage;
   }

   public boolean equals(Object o) {
      if (o instanceof ServerCore) {
         ServerCore server = (ServerCore) o;
         return id.equals(server.id) && capacity == server.capacity;
      }
      return false;
   }

   private int usedCapacity() {
      int usage = 0;
      for (final Vm vm : virtualMachines) {
         usage += vm.getSize();
      }
      return usage;
   }

   private int freeCapacity() {
      return capacity - usedCapacity();
   }

   private void updateUsePercentage() {
      usePercentage = (capacity == 0) ? 100 : (int) (usedCapacity() / (double) capacity * 100);
   }

}
