/*    */ package sim.app.lsystem;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class LSystemData
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 2491061639713100804L;
/*    */   public ByteList code;
/*    */   public ArrayList rules;
/*    */   public String seed;
/*    */   public int expansions;
/* 34 */   public double theta = -1.570796326794897D;
/*    */ 
/* 36 */   public double segsize = 2.0D;
/*    */ 
/* 38 */   public double angle = 1.570796326794897D;
/*    */ 
/* 40 */   public double x = 50.0D; public double y = 50.0D;
/*    */ 
/*    */   public static void setVector(ByteList v, String dat)
/*    */   {
/* 45 */     v.clear();
/* 46 */     int p = 0;
/* 47 */     for (p = 0; p < dat.length(); p++)
/* 48 */       v.add((byte)dat.substring(p, p + 1).charAt(0));
/*    */   }
/*    */ 
/*    */   public static String fromVector(ByteList v)
/*    */   {
/* 54 */     int p = 0;
/* 55 */     String ret = "";
/* 56 */     for (p = 0; p < v.length; p++) {
/* 57 */       ret = ret + String.valueOf((char)v.b[p]);
/*    */     }
/* 59 */     return ret;
/*    */   }
/*    */ 
/*    */   LSystemData()
/*    */   {
/* 64 */     this.code = new ByteList();
/* 65 */     this.rules = new ArrayList();
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.lsystem.LSystemData
 * JD-Core Version:    0.6.2
 */