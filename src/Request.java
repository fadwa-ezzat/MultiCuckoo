
/*    */ import java.util.LinkedList;

/*    */
/*    */ public class Request
/*    */ {
	/* 5 */ private LinkedList<Service> requestServices = new LinkedList();

	/*    */
	/*    */ public Request(LinkedList<Service> requestServices) {
		/* 8 */ this.requestServices = requestServices;
		/*    */ }

	/*    */
	/*    */ public Request() {
		/*    */ }

	/*    */
	/*    */ public LinkedList<Service> getRequestServices() {
		/* 15 */ return this.requestServices;
		/*    */ }

	/*    */
	/*    */ public void setRequestService(LinkedList<Service> request) {
		/* 19 */ this.requestServices = request;
		/*    */ }
	/*    */ }