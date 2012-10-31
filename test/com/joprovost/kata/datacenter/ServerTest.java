package com.joprovost.kata.datacenter;

import org.junit.Test;

import static com.joprovost.kata.datacenter.utils.AddOperation.adding;
import static com.joprovost.kata.datacenter.utils.Helpers.a;
import static com.joprovost.kata.datacenter.utils.Helpers.fails;
import static com.joprovost.kata.datacenter.utils.Helpers.works;
import static com.joprovost.kata.datacenter.utils.ServerBuilder.server;
import static com.joprovost.kata.datacenter.utils.VmBuilder.vm;
import static org.junit.Assert.assertThat;

public class ServerTest {
   @Test
   public void addingAVmToAnEmptyServerWorks() {
      assertThat(adding(a(vm().withSize(1))).to(a(server().withCapacity(1))), works());
   }

   @Test
   public void addingAVmToAFullServerFails() {
      assertThat(adding(a(vm().withSize(1))).to(a(server().withCapacity(0))), fails());
   }

   @Test
   public void addingASecondVmToAOneSlotServerFails() {
      assertThat(adding(a(vm().withSize(1))).to(a(server().withCapacity(1).containing(a(vm().withSize(1))))), fails());
   }

   @Test
   public void addingASecondVmToATwoSlotServerWorks() {
      assertThat(adding(a(vm().withSize(1))).to(a(server().withCapacity(2).containing(a(vm().withSize(1))))), works());
   }

   @Test
   public void addingASecondVmOfSize1ToATwoSlotServerContainingAVmOfSize2Fails() {
      assertThat(adding(a(vm().withSize(1))).to(a(server().withCapacity(2).containing(a(vm().withSize(2))))), fails());
   }

   @Test
   public void addingASecondVmOfSize2ToATwoSlotServerContainingAVmOfSize1Fails() {
      assertThat(adding(a(vm().withSize(2))).to(a(server().withCapacity(2).containing(a(vm().withSize(1))))), fails());
   }
}
