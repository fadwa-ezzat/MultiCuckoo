
/*    */ import java.util.LinkedList;

/*    */
/*    */ public class Cloud
/*    */ {
	/* 5 */ private LinkedList<Service> cloudServices = new LinkedList();
	/* 6 */ private int port = 0;
	/* 7 */ private String host = "";
	/* 8 */ private boolean srvcsSet = false;

	/*    */
	/*    */ public Cloud(Cloud cloud) {
		/* 11 */ this.port = cloud.getPort();
		/* 12 */ this.host = cloud.getHost();
		/*    */ }

	/*    */
	/*    */ public Cloud() {
		/* 16 */ this.cloudServices = new LinkedList();
		/* 17 */ this.srvcsSet = false;
		/*    */ }

	/*    */
	/*    */ public Cloud(String host, Integer port) {
		/* 21 */ this.cloudServices = new LinkedList();
		/* 22 */ this.host = host;
		/* 23 */ this.port = port.intValue();
		/* 24 */ this.srvcsSet = false;
		/*    */ }

	/*    */
	/*    */ public Cloud(String host, Integer port, LinkedList<Service> list) {
		/* 28 */ this.cloudServices = list;
		/* 29 */ this.host = host;
		/* 30 */ this.port = port.intValue();
		/* 31 */ this.srvcsSet = false;
		/*    */ }

	/*    */
	/*    */ public int numOfActualServices() {
		/* 35 */ LinkedList itsServices = getCloudServices();
		/* 36 */ int size = 0;
		/* 37 */ for (int i = 0; i < itsServices.size(); i++) {
			/* 38 */ size += ((Service) itsServices.get(i)).getNumOfStuff();
			/*    */ }
		/* 40 */ return size;
		/*    */ }

	/*    */
	/*    */ public LinkedList<Service> getCloudServices() {
		/* 44 */ return this.cloudServices;
		/*    */ }

	/*    */
	/*    */ public void addCloudService(Service service) {
		/* 48 */ this.cloudServices.add(service);
		/*    */ }

	/*    */
	/*    */ public void setCloudService(LinkedList<Service> service) {
		/* 52 */ this.cloudServices = service;
		/* 53 */ this.srvcsSet = true;
		/*    */ }

	/*    */
	/*    */ public boolean getSrvcsSet() {
		/* 57 */ return this.srvcsSet;
		/*    */ }

	/*    */
	/*    */ public void setSrvcsSet(boolean flag) {
		/* 61 */ this.srvcsSet = flag;
		/*    */ }

	/*    */
	/*    */ public int getPort() {
		/* 65 */ return this.port;
		/*    */ }

	/*    */
	/*    */ public void setPort(int port) {
		/* 69 */ this.port = port;
		/*    */ }

	/*    */
	/*    */ public String getHost() {
		/* 73 */ return this.host;
		/*    */ }

	/*    */
	/*    */ public void setHost(String host) {
		/* 77 */ this.host = host;
		/*    */ }
	/*    */ }