/*    */ package sim.app.networktest;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class EdgeInfo
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   String label;
/*    */ 
/*    */   public String getLabel()
/*    */   {
/* 14 */     return this.label; } 
/* 15 */   public void setLabel(String id) { this.label = id; } 
/* 16 */   public EdgeInfo(String val) { this.label = val; } 
/* 17 */   public String toString() { return this.label; }
/*    */ 
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.networktest.EdgeInfo
 * JD-Core Version:    0.6.2
 */