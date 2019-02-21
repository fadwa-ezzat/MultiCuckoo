
/*     */ import java.util.LinkedList;

/*     */
/*     */ public class MultiCuckoo
/*     */ {
	/* 6 */ private LinkedList<Cloud> clouds = new LinkedList();
	/* 7 */ private Request request = new Request();
	/* 8 */ private int numServicesChecked = 0;
	/*     */
	/* 10 */ private ComposedService Lc = new ComposedService();

	/*     */
	/*     */ public LinkedList<Cloud> getClouds()
	/*     */ {
		/* 16 */ return this.clouds;
		/*     */ }

	/*     */
	/*     */ public void setClouds(LinkedList<Cloud> clouds) {
		/* 20 */ this.clouds = clouds;
		/*     */ }

	/*     */
	/*     */ public Request getRequest() {
		/* 24 */ return this.request;
		/*     */ }

	/*     */
	/*     */ public void setRequest(Request request) {
		/* 28 */ this.request = request;
		/*     */ }

	/*     */
	/*     */ public int getNumServicesChecked() {
		/* 32 */ return this.numServicesChecked;
		/*     */ }

	/*     */
	/*     */ public void setNumServicesChecked(int numServicesChecked) {
		/* 36 */ this.numServicesChecked = numServicesChecked;
		/*     */ }

	/*     */
	/*     */ public ComposedService getLc() {
		/* 40 */ return this.Lc;
		/*     */ }

	/*     */
	/*     */ public void setLc(ComposedService lc) {
		/* 44 */ this.Lc = lc;
		/*     */ }

	/*     */
	/*     */ public ComposedService composeService() {
		/* 48 */ boolean stop = false;
		/* 49 */ int n = getClouds().size() - 1;
		/* 50 */ double x = Levy.sample_positive(2.0D);
		/* 51 */ int y = (int) Math.floor(x * this.clouds.size()) % this.clouds.size();
		/*     */
		/* 53 */ Cloud Cm = getClouds().get(y);
		/*     */
		/* 58 */ while ((n >= 0) && (!stop)) {
			/* 59 */ LinkedList cloudServices = Cm.getCloudServices();
			/*     */
			/* 61 */ if (cloudServices.size() == 0) {
				/* 62 */ cloudServices = MainClass.setServices(Cm.getPort());
				/* 63 */ Cm.setCloudService(cloudServices);
				/* 64 */ Cm.setSrvcsSet(true);
				/*     */ }
			/*     */
			/* 68 */ LinkedList reqCloudIntersect = Helper.cloudRequestIntersect(Cm, getRequest());
			/*     */
			/* 72 */ int numOfCloudServices = cloudServices.size();
			/* 73 */ int numStuff = 0;
			/* 74 */ for (int i = 0; i < numOfCloudServices; i++) {
				/* 75 */ numStuff += ((Service) cloudServices.get(i)).getNumOfStuff();
				/*     */ }
			/* 77 */ int num = getNumServicesChecked();
			/* 78 */ setNumServicesChecked(num + numStuff);
			/*     */
			/* 80 */ int size = reqCloudIntersect.size();
			/*     */
			/* 82 */ if (size > 0)
			/*     */ {
				/* 84 */ if (!Helper.intersectWithList(getLc(), reqCloudIntersect))
				/*     */ {
					/* 86 */ String savedCloud = "";
					/*     */
					/* 88 */ for (int j = 0; j < size; j++) {
						/* 89 */ savedCloud = ((Service) reqCloudIntersect.get(j)).getServiceFile();
						/* 90 */ if (!getLc().getHashMap().containsKey(savedCloud)) {
							/* 91 */ getLc().addToComposedService(savedCloud, Cm);
							/*     */ }
						/*     */ }
					/* 94 */ if (Helper.foundAllNeededServices(getLc(), getRequest())) {
						/* 95 */ stop = true;
						/*     */ }
					/*     */ }
				/*     */ }
			/*     */
			/* 100 */ if ((n >= 0) && (!stop)) {
				/* 101 */ if (y < getClouds().size()) {
					/* 102 */ x = Levy.sample_positive(2.0D);
					/* 103 */ y = (int) Math.floor(x * this.clouds.size()) % this.clouds.size();
					/*     */
					/* 105 */ Cm = getClouds().get(y);
					/*     */ }
				/* 107 */ n--;
				/*     */ }
			/*     */ }
		/*     */
		/* 111 */ return getLc();
		/*     */ }
	/*     */ }

/*
 * Location: D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar Qualified Name:
 * MultiCuckoo JD-Core Version: 0.6.2
 */