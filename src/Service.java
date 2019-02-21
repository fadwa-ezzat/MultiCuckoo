/*    */ public class Service
/*    */ {
	/* 4 */ private String serviceFile = "";
	/* 5 */ private int numOfStuff = 0;

	/*    */
	/*    */ public Service(String serviceFile, int numOfStuff) {
		/* 8 */ this.serviceFile = serviceFile;
		/* 9 */ this.numOfStuff = numOfStuff;
		/*    */ }

	/*    */
	/*    */ public Service(String serviceFile) {
		/* 13 */ this.serviceFile = serviceFile;
		/*    */ }

	/*    */
	/*    */ public Service()
	/*    */ {
		/*    */ }

	/*    */
	/*    */ public String getServiceFile() {
		/* 21 */ return this.serviceFile;
		/*    */ }

	/*    */
	/*    */ public void setServiceFile(String fileName) {
		/* 25 */ this.serviceFile = fileName;
		/*    */ }

	/*    */
	/*    */ public int getNumOfStuff() {
		/* 29 */ return this.numOfStuff;
		/*    */ }

	/*    */
	/*    */ public void setNumOfStuff(int numOfStuff) {
		/* 33 */ this.numOfStuff = numOfStuff;
		/*    */ }
	/*    */ }
