package com.joprovost.kata.datacenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joprovost.kata.datacenter.adapters.jsonrpc.JsonRpcSecondaryAdapterFactory;
import com.joprovost.kata.datacenter.core.datacenter.DatacenterCore;
import com.joprovost.kata.datacenter.core.server.ServerCore;

import java.io.InputStreamReader;
import java.util.*;

public class Application {

   private final List<ServerCore> servers =
         Collections.synchronizedList(new ArrayList<ServerCore>());

   private final Collection<Vm> virtualMachines =
         Collections.synchronizedCollection(new ArrayList<Vm>());

   public static void main(String[] args) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      Application application = gson.fromJson(new InputStreamReader(System.in), Application.class);
      DatacenterCore datacenter = new DatacenterCore(new JsonRpcSecondaryAdapterFactory());
      datacenter.registerServers(new ArrayList<Server>(application.servers));
      for (Vm vm : application.virtualMachines) {
         datacenter.add(vm);
      }
      System.out.println(gson.toJson(datacenter));
   }
}
