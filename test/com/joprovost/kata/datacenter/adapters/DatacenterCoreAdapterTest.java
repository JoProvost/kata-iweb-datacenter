package com.joprovost.kata.datacenter.adapters;

import com.joprovost.kata.datacenter.Server;
import com.joprovost.kata.datacenter.Vm;
import com.joprovost.kata.datacenter.adapters.jsonrpc.JsonRpcSecondaryAdapterFactory;
import com.joprovost.kata.datacenter.adapters.smilerpc.SmileRpcSecondaryAdapterFactory;
import com.joprovost.kata.datacenter.core.datacenter.DatacenterCore;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.joprovost.kata.datacenter.utils.AddOperation.adding;
import static com.joprovost.kata.datacenter.utils.DatacenterCoreBuilder.datacenter;
import static com.joprovost.kata.datacenter.adapters.utils.DatacenterCoreAdapterBuilder.datacenterCoreAdapter;
import static com.joprovost.kata.datacenter.utils.Helpers.a;
import static com.joprovost.kata.datacenter.utils.Helpers.andThat;
import static com.joprovost.kata.datacenter.utils.Helpers.works;
import static com.joprovost.kata.datacenter.adapters.utils.PrimaryAdapterBuilder.jsonRpcPrimaryAdapter;
import static com.joprovost.kata.datacenter.adapters.utils.PrimaryAdapterBuilder.smileRpcPrimaryAdapter;
import static com.joprovost.kata.datacenter.adapters.utils.RegisterServerOperation.registeringTheServerAt;
import static com.joprovost.kata.datacenter.utils.ServerBuilder.server;
import static com.joprovost.kata.datacenter.utils.VmBuilder.vm;
import static org.junit.Assert.assertThat;

public class DatacenterCoreAdapterTest {
   private List<PrimaryAdapter> primaryAdapters = new ArrayList<PrimaryAdapter>();

   @Test
   public void addingAVmToADatacenterAssociatedToAJsonRpcServerAddsTheVmToTheServer() throws Exception {
      Server theServer;
      given(a(jsonRpcPrimaryAdapter().onTcpPort(34568).of(theServer = a(server().withCapacity(2)))));

      DatacenterCore theDatacenter;
      assertThat(
            registeringTheServerAt("127.0.0.1", 34568)
                  .to(a(datacenterCoreAdapter()
                        .of(theDatacenter = a(datacenter()))
                        .using(new JsonRpcSecondaryAdapterFactory()))),
            works());

      Vm theVm;
      assertThat(
            adding(theVm = a(vm().withSize(2).withId("allo")))
                  .to(theDatacenter),
            works());
      andThat(theServer.contains(theVm));
   }

   @Test
   public void addingAVmToADatacenterAssociatedToASmileRpcServerAddsTheVmToTheServer() throws Exception {
      Server theServer;
      given(a(smileRpcPrimaryAdapter().onTcpPort(34568).of(theServer = a(server().withCapacity(2)))));

      DatacenterCore theDatacenter;
      assertThat(
            registeringTheServerAt("127.0.0.1", 34568)
                  .to(a(datacenterCoreAdapter()
                        .of(theDatacenter = a(datacenter()))
                        .using(new SmileRpcSecondaryAdapterFactory()))),
            works());

      Vm theVm;
      assertThat(
            adding(theVm = a(vm().withSize(2).withId("allo")))
                  .to(theDatacenter),
            works());
      andThat(theServer.contains(theVm));
   }


   @After
   public void stopServices() throws Exception {
      for (PrimaryAdapter adapter : primaryAdapters) {
         adapter.stop();
      }
      primaryAdapters.clear();
   }

   private void given(PrimaryAdapter primaryAdapter)
   {
      primaryAdapter.start();
      primaryAdapters.add(primaryAdapter);
   }

}
