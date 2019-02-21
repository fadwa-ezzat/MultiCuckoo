/*    */ package sim.app.virus;
/*    */ 
/*    */ import java.awt.geom.Ellipse2D.Double;
/*    */ import java.awt.geom.Rectangle2D.Double;
/*    */ import sim.engine.Steppable;
/*    */ import sim.portrayal.DrawInfo2D;
/*    */ import sim.portrayal.SimplePortrayal2D;
/*    */ import sim.util.Double2D;
/*    */ 
/*    */ public abstract class Agent extends SimplePortrayal2D
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public String id;
/*    */   public Double2D agentLocation;
/* 22 */   public int intID = -1;
/*    */ 
/*    */   public Agent(String id, Double2D location)
/*    */   {
/* 26 */     this.id = id;
/* 27 */     this.agentLocation = location;
/*    */   }
/*    */ 
/*    */   double distanceSquared(Double2D loc1, Double2D loc2)
/*    */   {
/* 32 */     return (loc1.x - loc2.x) * (loc1.x - loc2.x) + (loc1.y - loc2.y) * (loc1.y - loc2.y);
/*    */   }
/*    */ 
/*    */   public abstract String getType();
/*    */ 
/*    */   public boolean hitObject(Object object, DrawInfo2D info)
/*    */   {
/* 40 */     double diamx = info.draw.width * 8.0D;
/* 41 */     double diamy = info.draw.height * 8.0D;
/*    */ 
/* 43 */     Ellipse2D.Double ellipse = new Ellipse2D.Double((int)(info.draw.x - diamx / 2.0D), (int)(info.draw.y - diamy / 2.0D), (int)diamx, (int)diamy);
/* 44 */     return ellipse.intersects(info.clip.x, info.clip.y, info.clip.width, info.clip.height);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.virus.Agent
 * JD-Core Version:    0.6.2
 */