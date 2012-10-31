package com.joprovost.kata.datacenter.utils;

import com.joprovost.kata.datacenter.Datacenter;
import com.joprovost.kata.datacenter.Server;

import java.util.ArrayList;
import java.util.Collection;

public class DatacenterBuilder implements Builder<Datacenter> {
   private final Collection<Server> servers = new ArrayList<Server>();

   public static DatacenterBuilder datacenter() {
      return new DatacenterBuilder();
   }

   @Override
   public Datacenter build() {
      return new Datacenter(servers);
   }

   public DatacenterBuilder with(Server server) {
      servers.add(server);
      return this;
   }
}
