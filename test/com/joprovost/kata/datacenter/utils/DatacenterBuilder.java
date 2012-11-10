package com.joprovost.kata.datacenter.utils;

import com.joprovost.kata.datacenter.adapters.SecondaryAdapterFactory;
import com.joprovost.kata.datacenter.adapters.jsonrpc.JsonRpcSecondaryAdapterFactory;
import com.joprovost.kata.datacenter.core.datacenter.DatacenterCore;
import com.joprovost.kata.datacenter.Datacenter;
import com.joprovost.kata.datacenter.Server;

import java.util.ArrayList;
import java.util.Collection;

public class DatacenterBuilder implements Builder<Datacenter> {
   private final Collection<Server> servers = new ArrayList<Server>();
   private final SecondaryAdapterFactory secondaryAdapterFactory = new JsonRpcSecondaryAdapterFactory();

   public static DatacenterBuilder datacenter() {
      return new DatacenterBuilder();
   }

   @Override
   public Datacenter build() {
      DatacenterCore datacenter = new DatacenterCore(secondaryAdapterFactory);
      datacenter.registerServers(servers);
      return datacenter;
   }

   public DatacenterBuilder with(Server server) {
      servers.add(server);
      return this;
   }
}
