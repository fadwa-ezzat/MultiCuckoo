/*    */ package sim.app.tutorial4;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import sim.engine.SimState;
/*    */ import sim.util.Proxiable;
/*    */ 
/*    */ public class BigParticle extends Particle
/*    */   implements Proxiable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Object propertiesProxy()
/*    */   {
/* 34 */     return new MyProxy();
/*    */   }
/*    */   public BigParticle(int xdir, int ydir) {
/* 37 */     super(xdir, ydir);
/*    */   }
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 42 */     this.randomize = false;
/* 43 */     super.step(state);
/*    */   }
/*    */ 
/*    */   public class MyProxy
/*    */     implements Serializable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */ 
/*    */     public MyProxy()
/*    */     {
/*    */     }
/*    */ 
/*    */     public int getXDir()
/*    */     {
/* 26 */       return BigParticle.this.xdir; } 
/* 27 */     public int getYDir() { return BigParticle.this.ydir; }
/*    */ 
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial4.BigParticle
 * JD-Core Version:    0.6.2
 */