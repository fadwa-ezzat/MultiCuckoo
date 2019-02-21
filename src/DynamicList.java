/*    */ public class DynamicList
/*    */ {
	/*    */ private Request LSRequest;
	/*    */ private ComposedService LSComposed;
	/*    */ public static long time;

	/*    */
	/*    */ public Request getLSRequest()
	/*    */ {
		/* 8 */ return this.LSRequest;
		/*    */ }

	/*    */
	/*    */ public void setLSRequest(Request lSRequest) {
		/* 12 */ this.LSRequest = lSRequest;
		/*    */ }

	/*    */
	/*    */ public ComposedService getLSComposed() {
		/* 16 */ return this.LSComposed;
		/*    */ }

	/*    */
	/*    */ public void setLSComposed(ComposedService lSComposed) {
		/* 20 */ this.LSComposed = lSComposed;
		/*    */ }

	/*    */
	/*    */ public DynamicList() {
		/* 24 */ this.LSComposed = new ComposedService();
		/* 25 */ this.LSRequest = new Request();
		/*    */ }

	/*    */
	/*    */ public DynamicList(Request request, ComposedService composedService) {
		/* 29 */ this.LSRequest = request;
		/* 30 */ this.LSComposed = composedService;
		/*    */ }
	/*    */ }