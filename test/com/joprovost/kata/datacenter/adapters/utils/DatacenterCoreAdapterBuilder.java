package com.joprovost.kata.datacenter.adapters.utils;

import com.joprovost.kata.datacenter.adapters.DatacenterCoreAdapter;
import com.joprovost.kata.datacenter.adapters.SecondaryAdapterFactory;
import com.joprovost.kata.datacenter.core.datacenter.DatacenterCore;
import com.joprovost.kata.datacenter.utils.Builder;

import java.net.SocketAddress;

public class DatacenterCoreAdapterBuilder implements Builder<DatacenterCoreAdapter> {
   private DatacenterCore datacenterCore;
   private SecondaryAdapterFactory<SocketAddress> secondaryAdapterFactory;

   public static DatacenterCoreAdapterBuilder datacenterCoreAdapter() {
      return new DatacenterCoreAdapterBuilder();
   }

   @Override
   public DatacenterCoreAdapter build() {
      return new DatacenterCoreAdapter(datacenterCore, secondaryAdapterFactory);
   }

   public DatacenterCoreAdapterBuilder of(DatacenterCore datacenterCore) {
      this.datacenterCore = datacenterCore;
      return this;
   }

   public DatacenterCoreAdapterBuilder using(SecondaryAdapterFactory<SocketAddress> secondaryAdapterFactory) {
      this.secondaryAdapterFactory = secondaryAdapterFactory;
      return this;
   }
}
