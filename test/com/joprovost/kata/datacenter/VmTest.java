package com.joprovost.kata.datacenter;

import org.junit.Test;

import static com.joprovost.kata.datacenter.utils.Helpers.a;
import static com.joprovost.kata.datacenter.utils.Helpers.fromJson;
import static com.joprovost.kata.datacenter.utils.VmBuilder.vm;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class VmTest {
   @Test
   public void aVmCanBeImportedFromJson()
   {
      assertThat(theVmCreatedFromJson("{\"id\":\"VM1\",\"size\":1}"), equalTo(a(vm().withId("VM1").withSize(1))));

      assertThat(theVmCreatedFromJson("{\"id\":\"VM1\",\"size\":1}"), not(equalTo(a(vm().withId("VM1").withSize(2)))));
      assertThat(theVmCreatedFromJson("{\"id\":\"VM1\",\"size\":1}"), not(equalTo(a(vm().withId("VM2").withSize(1)))));
   }

   private Vm theVmCreatedFromJson(String json) {
      return fromJson(json, Vm.class);
   }
}
