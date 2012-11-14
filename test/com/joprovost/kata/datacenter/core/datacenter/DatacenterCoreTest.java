package com.joprovost.kata.datacenter.core.datacenter;

import com.joprovost.kata.datacenter.*;
import org.junit.Test;

import static com.joprovost.kata.datacenter.utils.AddOperation.adding;
import static com.joprovost.kata.datacenter.utils.DatacenterCoreBuilder.datacenter;
import static com.joprovost.kata.datacenter.utils.Helpers.*;
import static com.joprovost.kata.datacenter.utils.Helpers.andThat;
import static com.joprovost.kata.datacenter.utils.Helpers.works;
import static com.joprovost.kata.datacenter.utils.ServerBuilder.server;
import static com.joprovost.kata.datacenter.utils.VmBuilder.vm;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DatacenterCoreTest {
   @Test
   public void addingAVmToADatacenterWithEnoughCapacityWorks() {
      assertThat(adding(a(vm().withSize(1))).to(a(datacenter().with(a(server().withCapacity(1))))), works());
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
      Vm theVm;
      Server theLessUsedServer;

      assertThat(
            adding(theVm = a(vm().withSize(1)))
                  .to(a(datacenter()
                        .with(a(server().withCapacity(10).containing(a(vm().withSize(7)))))
                        .with(theLessUsedServer = a(server().withCapacity(10).containing(a(vm().withSize(6)))))
                        .with(a(server().withCapacity(100).containing(a(vm().withSize(70)))))
                        .with(a(server().withCapacity(1000).containing(a(vm().withSize(750)))))
                  )),
            works());

      andThat(theLessUsedServer.contains(theVm));
   }

   @Test
   public void addingTwoVmsToADatacenterAddsTheVmToTheLessUsedServer() {
      Server theLessUsedServer;
      Server theSecondLessUsedServer;

      DatacenterCore datacenter = a(datacenter()
            .with(theSecondLessUsedServer = a(server().withCapacity(10).containing(a(vm().withSize(6)))))
            .with(theLessUsedServer = a(server().withCapacity(11).containing(a(vm().withSize(6)))))
            .with(a(server().withCapacity(100).containing(a(vm().withSize(70)))))
            .with(a(server().withCapacity(1000).containing(a(vm().withSize(750))))));

      Vm theVm;
      assertThat(adding(theVm = a(vm().withSize(2))).to(datacenter), works());
      andThat(theLessUsedServer.contains(theVm));

      Vm anotherVm;
      assertThat(adding(anotherVm = a(vm().withSize(2))).to(datacenter), works());
      andThat(theSecondLessUsedServer.contains(anotherVm));
   }

   @Test
   public void anEmptyDatacenterExportedInJsonShowsCorrectOutput() {
      assertThat(
            theJsonOutputOf(a(datacenter())),
            is("{\"servers\":[]}"));
   }

   @Test
   public void aDatacenterWithAnEmptyServerExportedInJsonShowsCorrectOutput() {
      assertThat(
            theJsonOutputOf(a(datacenter()
                  .with(a(server().withId("server1").withCapacity(4)))
            )),
            is("{\"servers\":[{\"id\":\"server1\",\"capacity\":4,\"usePercentage\":0,\"virtualMachines\":[]}]}"));
   }

   @Test
   public void aDatacenterWithAServerWithAVmExportedInJsonShowsCorrectOutput() {
      assertThat(
            theJsonOutputOf(a(datacenter()
                  .with(a(server().withId("server1").withCapacity(4).containing(
                        a(vm().withId("VM1").withSize(1))
                  )))
            )),
            is("{\"servers\":[{\"id\":\"server1\",\"capacity\":4,\"usePercentage\":25,\"virtualMachines\":[{\"id\":\"VM1\",\"size\":1}]}]}"));
   }

   private String theJsonOutputOf(DatacenterCore datacenter) {
      return toJson(datacenter);
   }

}
