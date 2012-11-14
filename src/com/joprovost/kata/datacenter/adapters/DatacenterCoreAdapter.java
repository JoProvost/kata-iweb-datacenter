package com.joprovost.kata.datacenter.adapters;

import com.joprovost.kata.datacenter.Datacenter;
import com.joprovost.kata.datacenter.Server;
import com.joprovost.kata.datacenter.Vm;
import com.joprovost.kata.datacenter.core.datacenter.DatacenterCore;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class DatacenterCoreAdapter implements Datacenter {

   private final DatacenterCore datacenterCore;
   private final SecondaryAdapterFactory<SocketAddress> secondaryAdapterFactory;

   public DatacenterCoreAdapter(DatacenterCore datacenterCore,
                                SecondaryAdapterFactory<SocketAddress> secondaryAdapterFactory) {
      this.datacenterCore = datacenterCore;
      this.secondaryAdapterFactory = secondaryAdapterFactory;
   }

   @Override
   public boolean registerServer(String address, int port) {
      try {
         Server server = secondaryAdapterFactory.createSecondaryAdapter(new InetSocketAddress(address, port), Server.class);
         datacenterCore.addServer(server);
         return true;
      } catch (IOException e) {
         e.printStackTrace();
         return false;
      }
   }

   @Override
   public boolean add(Vm vm) {
      return datacenterCore.add(vm);
   }
}
