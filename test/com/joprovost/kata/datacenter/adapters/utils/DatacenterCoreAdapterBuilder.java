package com.joprovost.kata.datacenter.adapters.utils;

import com.joprovost.kata.datacenter.adapters.DatacenterCoreAdapter;
import com.joprovost.kata.datacenter.adapters.EntityProxyFactory;
import com.joprovost.kata.datacenter.core.datacenter.DatacenterCore;
import com.joprovost.kata.datacenter.utils.Builder;

import java.net.URI;

public class DatacenterCoreAdapterBuilder implements Builder<DatacenterCoreAdapter> {
   private DatacenterCore datacenterCore;
   private EntityProxyFactory<URI> entityProxyFactory;

   public static DatacenterCoreAdapterBuilder datacenterCoreAdapter() {
      return new DatacenterCoreAdapterBuilder();
   }

   @Override
   public DatacenterCoreAdapter build() {
      return new DatacenterCoreAdapter(datacenterCore, entityProxyFactory);
   }

   public DatacenterCoreAdapterBuilder of(DatacenterCore datacenterCore) {
      this.datacenterCore = datacenterCore;
      return this;
   }

   public DatacenterCoreAdapterBuilder using(EntityProxyFactory<URI> entityProxyFactory) {
      this.entityProxyFactory = entityProxyFactory;
      return this;
   }
}
