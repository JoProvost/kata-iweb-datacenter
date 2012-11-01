package com.joprovost.kata.datacenter;

import com.google.gson.Gson;
import org.junit.Test;

import static com.joprovost.kata.datacenter.utils.DatacenterBuilder.datacenter;
import static com.joprovost.kata.datacenter.utils.AddOperation.adding;
import static com.joprovost.kata.datacenter.utils.Helpers.*;
import static com.joprovost.kata.datacenter.utils.ServerBuilder.server;
import static com.joprovost.kata.datacenter.utils.VmBuilder.vm;
import static org.hamcrest.core.Is.is;
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
      Server lessUsedServer = a(server().withCapacity(10).containing(a(vm().withSize(6))));

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

   @Test
   public void addingTwoVmsToADatacenterAddsTheVmToTheLessUsedServer() {
      Server lessUsedServer = a(server().withCapacity(11).containing(a(vm().withSize(6))));
      Server secondLessUsedServer = a(server().withCapacity(10).containing(a(vm().withSize(6))));

      Datacenter datacenter = a(datacenter()
            .with(secondLessUsedServer)
            .with(lessUsedServer)
            .with(a(server().withCapacity(100).containing(a(vm().withSize(70)))))
            .with(a(server().withCapacity(1000).containing(a(vm().withSize(750))))));

      Vm aVm = a(vm().withSize(2));
      assertThat(adding(aVm).to(datacenter),works());
      andThat(lessUsedServer.contains(aVm));

      Vm anotherVm = a(vm().withSize(2));
      assertThat(adding(anotherVm).to(datacenter),works());
      andThat(secondLessUsedServer.contains(anotherVm));
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


   private String theJsonOutputOf(Datacenter datacenter) {
      Gson gson = new Gson();
      return gson.toJson(datacenter);
   }
}