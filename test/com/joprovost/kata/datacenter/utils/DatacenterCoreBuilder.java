package com.joprovost.kata.datacenter.utils;

import com.joprovost.kata.datacenter.Server;
import com.joprovost.kata.datacenter.core.datacenter.DatacenterCore;

public class DatacenterCoreBuilder implements Builder<DatacenterCore> {
   private final DatacenterCore datacenter = new DatacenterCore();

   public static DatacenterCoreBuilder datacenter() {
      return new DatacenterCoreBuilder();
   }

   @Override
   public DatacenterCore build() {
      return datacenter;
   }

   public DatacenterCoreBuilder with(Server server) {
      datacenter.addServer(server);
      return this;
   }
}
