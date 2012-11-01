package com.joprovost.kata.datacenter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Server {
   private final String id;
   private final int capacity;
   private int usePercentage;
   private final Collection<Vm> virtualMachines =
         Collections.synchronizedCollection(new HashSet<Vm>());

   public Server(String id, final int capacity, final Collection<Vm> virtualMachines) {
      this.id = id;
      this.capacity = capacity;
      this.virtualMachines.addAll(virtualMachines);
      updateUsePercentage();
   }

   public boolean add(final Vm vm) {
      if (freeCapacity() >= vm.size() && virtualMachines.add(vm)) {
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

   private int usedCapacity() {
      int usage = 0;
      for (final Vm vm : virtualMachines) {
         usage += vm.size();
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
