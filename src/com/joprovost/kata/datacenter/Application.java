package com.joprovost.kata.datacenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStreamReader;
import java.util.*;

public class Application {

   private final List<Server> servers =
         Collections.synchronizedList(new ArrayList<Server>());

   private final Collection<Vm> virtualMachines =
         Collections.synchronizedCollection(new ArrayList<Vm>());

   public static void main(String[] args) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      Application application = gson.fromJson(new InputStreamReader(System.in), Application.class);
      Datacenter datacenter = new Datacenter(application.servers);
      for (Vm vm : application.virtualMachines) {
         datacenter.add(vm);
      }
      System.out.println(gson.toJson(datacenter));
   }
}
