
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;

/*     */
/*     */ public class Helper
/*     */ {
	// the similarity between a new request and a previously received request
	/* 19 */ public static double similarityRatio = 0.8D;

	/*     */
	/*     */ public static DynamicList similarRequest(LinkedList<DynamicList> dynamicList,
			LinkedList<Service> newRequest)
	/*     */ {
		/* 30 */ double currentMaxRatio = 0.0D;
		/* 31 */ DynamicList currentRequest = new DynamicList();
		/* 32 */ for (int i = 0; i < dynamicList.size(); i++) {
			/* 33 */ DynamicList list = dynamicList.get(i);
			/* 34 */ LinkedList listRequest = list.getLSRequest().getRequestServices();
			/*     */
			/* 36 */ double ratio = compareRequests(listRequest, newRequest);
			/* 37 */ if ((ratio >= similarityRatio) && (ratio >= currentMaxRatio)) {
				/* 38 */ currentMaxRatio = ratio;
				/* 39 */ currentRequest = list;
				/*     */ }
			/*     */ }
		/* 42 */ return currentRequest;
		/*     */ }

	/*     */
	/*     */ public static double compareRequests(LinkedList<Service> listRequest, LinkedList<Service> newRequest)
	/*     */ {
		/* 52 */ double ratio = 0.0D;
		/* 53 */ for (Service ser : newRequest) {
			/* 54 */ boolean found = false;
			/* 55 */ for (Service cache : listRequest) {
				/* 56 */ if (ser.getServiceFile().equalsIgnoreCase(cache.getServiceFile())) {
					/* 57 */ found = true;
					/* 58 */ break;
					/*     */ }
				/*     */ }
			/*     */
			/* 62 */ if (!found) {
				/* 63 */ ratio = 0.0D;
				/* 64 */ break;
				/*     */ }
			/* 66 */ ratio += 1.0D / listRequest.size();
			/*     */ }
		/*     */
		/* 69 */ return ratio;
		/*     */ }

	/*     */
	// create a file in the working directory to save statistics to
	/*     */ public static void writeToCSV(List<String> toFile)
	/*     */ {
		/*     */ try {
			/* 75 */ FileWriter pw = new FileWriter(new File("analytics.csv"), true);
			/* 76 */ StringBuilder sb = new StringBuilder();
			/*     */
			/* 78 */ for (String string : toFile) {
				/* 79 */ sb.append(string);
				/* 80 */ if (!string.equals("\n"))
					/* 81 */ sb.append(",");
				/*     */ }
			/* 83 */ sb.append('\n');
			/* 84 */ pw.write(sb.toString());
			/* 85 */ pw.close();
			/*     */ } catch (IOException ioe) {
			/* 87 */ System.err.println("IOException: " + ioe.getMessage());
			/*     */ }
		/*     */ }

	/*     */
	/*     */ public static LinkedList<Service> cloudRequestIntersect(Cloud Cm, Request request)
	/*     */ {
		/* 99 */ LinkedList<Service> requestServices = request.getRequestServices();
		/* 100 */ LinkedList<Service> cloudServices = Cm.getCloudServices();
		/* 101 */ LinkedList<Service> intersectingServices = new LinkedList<Service>();
		/*     */
		/* 103 */ Service cloudService = new Service();
		/* 104 */ Service requestService = new Service();
		/* 105 */ String cloudFile = "";
		/* 106 */ String requestFile = "";
		/*     */
		/* 108 */ int requestLength = requestServices.size();
		/* 109 */ for (int i = 0; i < requestLength; i++) {
			/* 110 */ requestService = (Service) requestServices.get(i);
			/* 111 */ requestFile = requestService.getServiceFile();
			/* 112 */ for (int j = 0; j < cloudServices.size(); j++) {
				/* 113 */ cloudService = (Service) cloudServices.get(j);
				/* 114 */ cloudFile = cloudService.getServiceFile();
				/*     */
				/* 116 */ if (requestFile.equalsIgnoreCase(cloudFile))
				/*     */ {
					/* 118 */ intersectingServices.add(cloudService);
					/*     */ }
				/*     */ }
			/*     */ }
		/* 122 */ return intersectingServices;
		/*     */ }

	/*     */
	/*     */ public static boolean foundAllNeededServices(ComposedService lc2, Request request2)
	/*     */ {
		/* 134 */ Service inRequest = new Service();
		/* 135 */ LinkedList<Service> requestService = request2.getRequestServices();
		/* 136 */ HashMap composed = lc2.getHashMap();
		/* 137 */ boolean found = true;
		/* 138 */ int size = requestService.size();
		/* 139 */ for (int i = 0; i < size; i++) {
			/* 140 */ inRequest = (Service) requestService.get(i);
			/*     */
			/* 142 */ if (!composed.containsKey(inRequest.getServiceFile())) {
				/* 143 */ return !found;
				/*     */ }
			/*     */ }
		/*     */
		/* 147 */ return found;
		/*     */ }

	/*     */
	/*     */ public static boolean intersectWithList(ComposedService Lc, LinkedList<Service> cloudServices)
	/*     */ {
		/* 158 */ Service service = new Service();
		/* 159 */ int length = cloudServices.size();
		/*     */
		/* 161 */ for (int i = 0; i < length; i++) {
			/* 162 */ service = cloudServices.get(i);
			/* 163 */ String file = service.getServiceFile();
			/* 164 */ Cloud cloud = Lc.getComposed(file);
			/* 165 */ LinkedList<Service> cloudServ = cloud.getCloudServices();
			/* 166 */ if (cloudServ.size() == 0) {
				/* 167 */ return false;
				/*     */ }
			/*     */ }
		/*     */
		/* 171 */ return true;
		/*     */ }

	/*     */
	/*     */ private static void emptySet(Set<Set<Cloud>> resultingCombinations) {
		/* 204 */ resultingCombinations.add(new HashSet());
		/*     */ }

	/*     */
	/*     */ private static Cloud popLast(List<Cloud> elementsExclusiveX) {
		/* 208 */ return elementsExclusiveX.remove(elementsExclusiveX.size() - 1);
		/*     */ }
	/*     */ }