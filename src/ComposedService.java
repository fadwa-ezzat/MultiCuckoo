
/*    */ import java.util.HashMap;

/*    */
/*    */ public class ComposedService
/*    */ {
	/*    */ private HashMap<String, Cloud> composedService;

	/*    */
	/*    */ public ComposedService()
	/*    */ {
		/* 8 */ this.composedService = new HashMap();
		/*    */ }

	/*    */
	/*    */ public Cloud getComposed(String serviceName) {
		/* 12 */ if (this.composedService.size() > 0)
		/*    */ {
			/* 14 */ if (this.composedService.get(serviceName) != null)
			/*    */ {
				/* 16 */ return this.composedService.get(serviceName);
				/*    */ }
			/*    */ }
		/* 19 */ return new Cloud();
		/*    */ }

	/*    */
	/*    */ public void addToComposedService(String serviceName, Cloud cloud) {
		/* 23 */ this.composedService.put(serviceName, cloud);
		/*    */ }

	/*    */
	/*    */ public int composedSize() {
		/* 27 */ return this.composedService.size();
		/*    */ }

	/*    */
	/*    */ public HashMap<String, Cloud> getHashMap() {
		/* 31 */ return this.composedService;
		/*    */ }
	/*    */ }
