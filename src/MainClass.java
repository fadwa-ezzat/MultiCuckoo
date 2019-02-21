
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
import java.util.List;
/*     */ import java.util.Scanner;

/*     */
/*     */ public class MainClass
/*     */ {
	/*     */ public static void main(String[] args)
	/*     */ {
		/* 16 */ LinkedList dynamicList = new LinkedList();
		/* 17 */ LinkedList<Cloud> totalClouds = new LinkedList<Cloud>();
		/* 18 */ LinkedList<Cloud> cuckooClouds = new LinkedList<Cloud>();
		/* 19 */ LinkedList<Cloud> comClouds = new LinkedList<Cloud>();
		/*     */ try
		/*     */ {
			// reading config.txt
			/* 24 */ BufferedReader br = new BufferedReader(
					new FileReader(System.getProperty("user.dir") + "//..\\CloudSim\\configurations\\config.txt"));
			/*     */ String line;
			/* 26 */ while ((line = br.readLine()) != null)
			/*     */ {
				/* 27 */ String[] lineSplits = line.split(":");
				/* 28 */ int port = Integer.parseInt(lineSplits[1]);
				/* 29 */ if (lineSplits.length == 3) {
					/* 30 */ Cloud cloud = new Cloud("localhost", Integer.valueOf(port));
					/* 31 */ totalClouds.add(cloud);
					/* 32 */ comClouds.add(cloud);
					/*     */ }
				/*     */ }
			/*     */ } catch (FileNotFoundException e) {
			/* 36 */ e.printStackTrace();
			/*     */ } catch (IOException e) {
			/* 38 */ e.printStackTrace();
			/*     */ }
		/*     */
		/* 41 */ List<String> toFile = new LinkedList<String>();
		/* 42 */ toFile.add("request");
		/* 43 */ toFile.add("MC Clds");
		/* 44 */ toFile.add("MC Srvc");
		/* 45 */ toFile.add("MC time");
		/*     */

		/* 58 */ toFile.add("\n");
		/*     */ while (true)
		/*     */ {
			/* 63 */ System.out.println("\n Enter the user request separated by spaces or press 'q' to exit");
			/* 64 */ Scanner scanner = new Scanner(System.in);
			/* 65 */ String input = scanner.nextLine();
			/* 66 */ if (input.equalsIgnoreCase("q"))
				/* 67 */ return;
			/* 68 */ String request = "";
			/* 69 */ String requests = "";
			/* 70 */ LinkedList requestServices = new LinkedList();
			/* 71 */ for (int j = 0; j < input.split(" ").length; j++) {
				/* 72 */ request = input.split(" ")[j];
				/* 73 */ requests = requests + request + " ";
				/* 74 */ requestServices.add(new Service(request));
				/*     */ }
			/* 76 */ Request userRequest = new Request(requestServices);
			/* 77 */ toFile.add(requests);
			/*     */

			
			//Here starts the code for MultiCuckoo	 
			
			long csStartTime = System.nanoTime();
			/* 85 */ MultiCuckoo multiCuckoo = new MultiCuckoo();
			/* 86 */ multiCuckoo.setRequest(userRequest);
			/*     */
			/* 91 */ if (dynamicList.size() == 0)
				/* 92 */ DynamicList.time = new Date().getTime();

			/* 94 */ long difference = new Date().getTime() - DynamicList.time;
			/* 95 */ long diffMinutes = difference / 60000L % 60L;
			/*     */
			/* 97 */ if (diffMinutes > 50L)
				/* 98 */ dynamicList = new LinkedList();
			/*     */
			/* 100 */ DynamicList composedList = Helper.similarRequest(dynamicList, userRequest.getRequestServices());
			/* 101 */ int size = composedList.getLSRequest().getRequestServices().size();
			/*     */ int port;
			/* 102 */ if (size > 0) {
				/* 103 */ System.out.println(
						"For MultiCuckoo, request similar to one saved from before,so examined services and combined clouds = 0 ");
				/* 105 */ toFile.add("0");
				// zero combined clouds
				/* 106 */ toFile.add("0");
				// zero examined services
				/* 107 */ toFile.add("0");
				// zero time taken
				/*     */ }
			/*     */ else {
				// start service composition
				/* 110 */ for (Object cloud : totalClouds)
					/* 111 */ cuckooClouds.add(new Cloud((Cloud) cloud));
				/*     */
				/* 116 */ multiCuckoo.setClouds(cuckooClouds);
				/* 117 */ ComposedService composed = multiCuckoo.composeService();
				/* 118 */ if (composed.composedSize() > 0) {
					// found a service composition sequence
					/* 119 */ dynamicList.add(new DynamicList(userRequest, composed));
					/* 120 */ HashMap servicesMap = composed.getHashMap();
					/*     */
					/* 123 */ int numClouds = 0;
					/*     */
					/* 125 */ LinkedList cloudsCombined = new LinkedList();
					/*     */
					/* 127 */ for (Object cloud : servicesMap.values()) {
						/* 128 */ port = ((Cloud) cloud).getPort();
						/* 129 */ if (!cloudsCombined.contains(Integer.valueOf(port))) {
							/* 130 */ numClouds++;
							/* 131 */ cloudsCombined.add(Integer.valueOf(port));
							/*     */ }
						/*     */ }
					/*     */
					/* 135 */ int services = multiCuckoo.getNumServicesChecked();
					/* 136 */ toFile.add(numClouds + "");
					/* 137 */ toFile.add(services + "");
					/* 138 */ toFile.add(System.nanoTime() - csStartTime + "");
					/*     */ } else {
					// did not find a sequence
						toFile.add("---");
						toFile.add("---");
						toFile.add("---");
					/* 140 */ System.out.println("services not found in clouds");
					/*     */ }
				/*     */
				/*     */ }
			
			//Here ends the code for MultiCuckoo 
			 
			/* 273 */ Helper.writeToCSV(toFile);// write the statistics to file so as to draw charts and compare
			/* 274 */ toFile = new LinkedList();
			/*     */ }
		/*     */ }

	/*     */
	/*     */ public static LinkedList<Service> setServices(int port)
	/*     */ {
		/* 287 */ LinkedList services = new LinkedList();
		/* 288 */ String cloudServices = TCPClient.connectToCloud(port, "list");
		/*     */
		/* 290 */ for (String service : cloudServices.split(",")) {
			/* 291 */ String[] split = service.split("-");
			/* 292 */ Service srv = new Service(split[1], Integer.parseInt(split[0]));
			/* 293 */ services.add(srv);
			/*     */ }
		/* 295 */ return services;
		/*     */ }
	/*     */ }
