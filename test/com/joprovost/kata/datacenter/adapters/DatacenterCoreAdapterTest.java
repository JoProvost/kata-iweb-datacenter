package com.joprovost.kata.datacenter.adapters;

import com.joprovost.kata.datacenter.Server;
import com.joprovost.kata.datacenter.Vm;
import com.joprovost.kata.datacenter.adapters.jsonrpc.JsonRpcEntityProxyFactory;
import com.joprovost.kata.datacenter.adapters.smilerpc.SmileRpcEntityProxyFactory;
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
            registeringTheServerAt("json-rpc://127.0.0.1:34568")
                  .to(a(datacenterCoreAdapter()
                        .of(theDatacenter = a(datacenter()))
                        .using(new JsonRpcEntityProxyFactory()))),
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
            registeringTheServerAt("json-rpc://127.0.0.1:34568")
                  .to(a(datacenterCoreAdapter()
                        .of(theDatacenter = a(datacenter()))
                        .using(new SmileRpcEntityProxyFactory()))),
            works());

      Vm theVm;
      assertThat(
            adding(theVm = a(vm().withSize(2).withId("allo")))
                  .to(theDatacenter),
            works());
      andThat(theServer.contains(theVm));
   }


   @Test
   public void addingAVmToADatacenterAssociatedToAJsoneAndASmileRpcServerAddsTheVmToTheServers() throws Exception {
      final DatacenterCore theDatacenter = a(datacenter());
      final DatacenterCoreAdapter theDatacenterCoreAdapter = a(datacenterCoreAdapter()
            .of(theDatacenter)
            .using(new MappingEntityProxyFactory() {{
               registerScheme("json-rpc", new JsonRpcEntityProxyFactory());
               registerScheme("smile-rpc", new SmileRpcEntityProxyFactory());
            }}));

      final Server theJsonServer;
      final Vm theVm;
      given(a(jsonRpcPrimaryAdapter().onTcpPort(34568).of(theJsonServer = a(server().withCapacity(2)))));
      assertThat(registeringTheServerAt("json-rpc://127.0.0.1:34568").to(theDatacenterCoreAdapter),works());
      assertThat(adding(theVm = a(vm().withSize(2).withId("allo"))).to(theDatacenter),works());
      andThat(theJsonServer.contains(theVm));

      final Server theSmileServer;
      final Vm theOtherVm;
      given(a(smileRpcPrimaryAdapter().onTcpPort(34569).of(theSmileServer = a(server().withCapacity(2)))));
      assertThat(registeringTheServerAt("smile-rpc://127.0.0.1:34569").to(theDatacenterCoreAdapter),works());
      assertThat(adding(theOtherVm = a(vm().withSize(2).withId("allo"))).to(theDatacenter),works());
      andThat(theSmileServer.contains(theOtherVm));
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
