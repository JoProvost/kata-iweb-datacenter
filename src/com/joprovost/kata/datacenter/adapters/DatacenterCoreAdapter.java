package com.joprovost.kata.datacenter.adapters;

import com.joprovost.kata.datacenter.Datacenter;
import com.joprovost.kata.datacenter.Server;
import com.joprovost.kata.datacenter.Vm;
import com.joprovost.kata.datacenter.core.datacenter.DatacenterCore;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DatacenterCoreAdapter implements Datacenter {

   private final DatacenterCore datacenterCore;
   private final EntityProxyFactory<URI> entityProxyFactory;

   public DatacenterCoreAdapter(DatacenterCore datacenterCore,
                                EntityProxyFactory<URI> entityProxyFactory) {
      this.datacenterCore = datacenterCore;
      this.entityProxyFactory = entityProxyFactory;
   }

   @Override
   public boolean registerServer(String uri) {
      try {
         Server server = entityProxyFactory.createEntityProxy(new URI(uri), Server.class);
         datacenterCore.addServer(server);
         return true;
      } catch (IOException e) {
         e.printStackTrace();
         return false;
      } catch (URISyntaxException e) {
         e.printStackTrace();
         return false;
      }
   }

   @Override
   public boolean add(Vm vm) {
      return datacenterCore.add(vm);
   }
}
