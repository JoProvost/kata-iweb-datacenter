package com.joprovost.kata.datacenter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.joprovost.kata.datacenter.core.datacenter.DatacenterCore;
import com.joprovost.kata.datacenter.core.server.ServerCore;

import java.io.*;
import java.util.*;

public class Application {

   private final List<ServerCore> servers =
         Collections.synchronizedList(new ArrayList<ServerCore>());

   private final Collection<Vm> virtualMachines =
         Collections.synchronizedCollection(new ArrayList<Vm>());

   public static void main(String[] args) throws IOException {
      Application application = fromJson(System.in, Application.class);
      DatacenterCore datacenter = new DatacenterCore();
      datacenter.addServers(new ArrayList<Server>(application.servers));
      for (Vm vm : application.virtualMachines) {
         datacenter.add(vm);
      }
      toJson(System.out, datacenter);
   }

   private static <T> T fromJson(InputStream json, Class<T> clazz) throws IOException {
      ObjectMapper mapper = new ObjectMapper();
      mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
      return mapper.readValue(json, clazz);
   }

   private static void toJson(OutputStream json, Object object) throws IOException {
      ObjectMapper mapper = new ObjectMapper();
      mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
      ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
      writer.writeValue(json, object);
   }

}
