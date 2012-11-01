package com.joprovost.kata.datacenter;

import com.google.gson.Gson;
import org.junit.Test;

import static com.joprovost.kata.datacenter.utils.AddOperation.adding;
import static com.joprovost.kata.datacenter.utils.Helpers.a;
import static com.joprovost.kata.datacenter.utils.Helpers.fails;
import static com.joprovost.kata.datacenter.utils.Helpers.works;
import static com.joprovost.kata.datacenter.utils.ServerBuilder.server;
import static com.joprovost.kata.datacenter.utils.VmBuilder.vm;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
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

   @Test
   public void aServerWithNoCapacityShowsALoadOf100() {
      assertThat(theLoadOf(a(server().withCapacity(0))), is(100));
   }

   @Test
   public void aServerWithCapacity5AndAVmOfSize5ShowsALoadOf100() {
      assertThat(theLoadOf(a(server().withCapacity(5).containing(a(vm().withSize(5))))), is(100));
   }

   @Test
   public void aServerWithCapacity20AndAVmOfSize5ShowsALoadOf25() {
      assertThat(theLoadOf(a(server().withCapacity(20).containing(a(vm().withSize(5))))), is(25));
   }

   @Test
   public void anEmptyServerCanBeImportedFromJson()
   {
      assertThat(theServerCreatedFromJson("{\"id\":\"Server1\",\"capacity\":1}"), equalTo(a(server().withId("Server1").withCapacity(1))));

      assertThat(theServerCreatedFromJson("{\"id\":\"Server1\",\"capacity\":1}"), not(equalTo(a(server().withId("Server2").withCapacity(1)))));
      assertThat(theServerCreatedFromJson("{\"id\":\"Server1\",\"capacity\":1}"), not(equalTo(a(server().withId("Server1").withCapacity(2)))));
   }



   private Server theServerCreatedFromJson(String json) {
      Gson gson = new Gson();
      return gson.fromJson(json, Server.class);
   }

   private static int theLoadOf(Server server)
   {
      return server.usePercentage();
   }
}
