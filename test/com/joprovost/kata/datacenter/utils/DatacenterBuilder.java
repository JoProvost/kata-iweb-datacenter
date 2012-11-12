package com.joprovost.kata.datacenter.utils;

import com.joprovost.kata.datacenter.adapters.SecondaryAdapterFactory;
import com.joprovost.kata.datacenter.adapters.jsonrpc.JsonRpcSecondaryAdapterFactory;
import com.joprovost.kata.datacenter.core.datacenter.DatacenterCore;
import com.joprovost.kata.datacenter.Datacenter;
import com.joprovost.kata.datacenter.Server;

public class DatacenterBuilder implements Builder<Datacenter> {
   private final SecondaryAdapterFactory secondaryAdapterFactory = new JsonRpcSecondaryAdapterFactory();
   private final DatacenterCore datacenter = new DatacenterCore(secondaryAdapterFactory);

   public static DatacenterBuilder datacenter() {
      return new DatacenterBuilder();
   }

   @Override
   public Datacenter build() {
      return datacenter;
   }

   public DatacenterBuilder with(Server server) {
      datacenter.addServer(server);
      return this;
   }

   public DatacenterBuilder associatedToServer(String address, int port) {
      datacenter.registerServer(address, port);
      return this;
   }
}
