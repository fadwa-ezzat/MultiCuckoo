/*    */ package sim.app.networktest;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import java.awt.Color;
/*    */ import java.awt.Font;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.geom.Ellipse2D.Double;
/*    */ import java.awt.geom.Rectangle2D.Double;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.portrayal.DrawInfo2D;
/*    */ import sim.portrayal.SimplePortrayal2D;
/*    */ import sim.util.Double2D;
/*    */ 
/*    */ public class CustomNode extends SimplePortrayal2D
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public String id;
/* 28 */   Double2D desiredLocation = null;
/* 29 */   Double2D suggestedLocation = null;
/* 30 */   int steps = 0;
/*    */ 
/* 72 */   public Font nodeFont = new Font("SansSerif", 0, 12);
/*    */ 
/*    */   public String getID()
/*    */   {
/* 20 */     return this.id; } 
/* 21 */   public void setID(String id) { this.id = id; }
/*    */ 
/*    */   public CustomNode(String id)
/*    */   {
/* 25 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 34 */     NetworkTest nt = (NetworkTest)state;
/* 35 */     Double2D location = nt.environment.getObjectLocation(this);
/*    */ 
/* 37 */     this.steps -= 1;
/* 38 */     if ((this.desiredLocation == null) || (this.steps <= 0))
/*    */     {
/* 40 */       this.desiredLocation = new Double2D((state.random.nextDouble() - 0.5D) * 152.0D + location.x, (state.random.nextDouble() - 0.5D) * 112.0D + location.y);
/*    */ 
/* 42 */       this.steps = (50 + state.random.nextInt(50));
/*    */     }
/*    */ 
/* 45 */     double dx = this.desiredLocation.x - location.x;
/* 46 */     double dy = this.desiredLocation.y - location.y;
/*    */ 
/* 49 */     double temp = Math.sqrt(dx * dx + dy * dy);
/* 50 */     if (temp < 1.0D)
/*    */     {
/* 52 */       this.steps = 0;
/*    */     }
/*    */     else
/*    */     {
/* 56 */       dx /= temp;
/* 57 */       dy /= temp;
/*    */     }
/*    */ 
/* 61 */     if (!nt.acceptablePosition(this, new Double2D(location.x + dx, location.y + dy)))
/*    */     {
/* 63 */       this.steps = 0;
/*    */     }
/*    */     else
/*    */     {
/* 67 */       nt.environment.setObjectLocation(this, new Double2D(location.x + dx, location.y + dy));
/*    */     }
/*    */   }
/*    */ 
/*    */   public final void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*    */   {
/* 76 */     double diamx = info.draw.width * 8.0D;
/* 77 */     double diamy = info.draw.height * 8.0D;
/*    */ 
/* 79 */     graphics.setColor(Color.red);
/* 80 */     graphics.fillOval((int)(info.draw.x - diamx / 2.0D), (int)(info.draw.y - diamy / 2.0D), (int)diamx, (int)diamy);
/* 81 */     graphics.setFont(this.nodeFont.deriveFont(this.nodeFont.getSize2D() * (float)info.draw.width));
/* 82 */     graphics.setColor(Color.blue);
/* 83 */     graphics.drawString(this.id, (int)(info.draw.x - diamx / 2.0D), (int)(info.draw.y - diamy / 2.0D));
/*    */   }
/*    */ 
/*    */   public boolean hitObject(Object object, DrawInfo2D info)
/*    */   {
/* 88 */     double diamx = info.draw.width * 8.0D;
/* 89 */     double diamy = info.draw.height * 8.0D;
/*    */ 
/* 91 */     Ellipse2D.Double ellipse = new Ellipse2D.Double((int)(info.draw.x - diamx / 2.0D), (int)(info.draw.y - diamy / 2.0D), (int)diamx, (int)diamy);
/* 92 */     return ellipse.intersects(info.clip.x, info.clip.y, info.clip.width, info.clip.height);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.networktest.CustomNode
 * JD-Core Version:    0.6.2
 */