package com.joprovost.kata.datacenter;

import com.joprovost.kata.datacenter.utils.Builder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static com.joprovost.kata.datacenter.utils.AddOperation.adding;
import static com.joprovost.kata.datacenter.utils.Helpers.*;
import static com.joprovost.kata.datacenter.utils.ServerBuilder.server;
import static com.joprovost.kata.datacenter.utils.VmBuilder.vm;
import static org.junit.Assert.assertThat;

public class DatacenterTest {
   @Test
   public void addingAVmToADatacenterWithOneEmptyServerWorks() {
      assertThat(adding(a(vm().withSize(1))).to(a(datacenter().with(a(server().withCapacity(1)))) ), works());
   }

   @Test
   public void addingAVmToADatacenterWithNoServerFails() {
      assertThat(adding(a(vm().withSize(1))).to(a(datacenter())), fails());
   }

   @Test
   public void addingAVmToADatacenterWithOneServerFullFails() {
      assertThat(adding(a(vm().withSize(1))).to(a(datacenter().with(a(server().withCapacity(0))))), fails());
   }

   @Test
   public void addingAVmToADatacenterWithOneServerFullAndOneEmptyServerWorks() {
      assertThat(
            adding(a(vm().withSize(1)))
                  .to(a(datacenter()
                        .with(a(server().withCapacity(0)))
                        .with(a(server().withCapacity(1))))),
            works());
   }

   @Test
   public void addingAVmToADatacenterAddsTheVmToTheLessUsedServer() {
      Vm aVm = a(vm().withSize(1));
      Server lessUsedServer = a(server().withCapacity(15).containing(a(vm().withSize(8))));

      assertThat(
            adding(aVm)
                  .to(a(datacenter()
                        .with(a(server().withCapacity(10).containing(a(vm().withSize(7)))))
                        .with(lessUsedServer)
                        .with(a(server().withCapacity(100).containing(a(vm().withSize(70)))))
                        .with(a(server().withCapacity(1000).containing(a(vm().withSize(750)))))
                  )),
            works());

      andThat(lessUsedServer.contains(aVm));
   }

   private DatacenterBuilder datacenter() {
      return new DatacenterBuilder();
   }

   public static class DatacenterBuilder implements Builder<Datacenter> {
      private final Collection<Server> servers = new ArrayList<Server>();
      @Override
      public Datacenter build() {
         return new Datacenter(servers);
      }

      public DatacenterBuilder with(Server server) {
         servers.add(server);
         return this;
      }
   }
}
