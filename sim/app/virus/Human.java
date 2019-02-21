/*     */ package sim.app.virus;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.util.Double2D;
/*     */ 
/*     */ public class Human extends Agent
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  18 */   protected boolean infected = false;
/*     */ 
/*  35 */   Double2D desiredLocation = null;
/*  36 */   Double2D suggestedLocation = null;
/*  37 */   int steps = 0;
/*     */ 
/*  87 */   protected Color humanColor = new Color(192, 128, 128);
/*  88 */   protected Color infectedColor = new Color(128, 255, 128);
/*     */ 
/*     */   public final boolean isInfected()
/*     */   {
/*  19 */     return this.infected; } 
/*  20 */   public final void setInfected(boolean b) { this.infected = b; }
/*     */ 
/*     */   public Human(String id, Double2D location)
/*     */   {
/*  24 */     super(id, location);
/*     */     try
/*     */     {
/*  27 */       this.intID = Integer.parseInt(id.substring(5));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  31 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/*  41 */     VirusInfectionDemo hb = (VirusInfectionDemo)state;
/*     */ 
/*  43 */     this.steps -= 1;
/*  44 */     if ((this.desiredLocation == null) || (this.steps <= 0))
/*     */     {
/*  46 */       this.desiredLocation = new Double2D((state.random.nextDouble() - 0.5D) * 152.0D + this.agentLocation.x, (state.random.nextDouble() - 0.5D) * 112.0D + this.agentLocation.y);
/*     */ 
/*  56 */       this.steps = (50 + state.random.nextInt(50));
/*     */     }
/*     */ 
/*  59 */     double dx = this.desiredLocation.x - this.agentLocation.x;
/*  60 */     double dy = this.desiredLocation.y - this.agentLocation.y;
/*     */ 
/*  63 */     double temp = Math.sqrt(dx * dx + dy * dy);
/*  64 */     if (temp < 1.0D)
/*     */     {
/*  66 */       this.steps = 0;
/*     */     }
/*     */     else
/*     */     {
/*  70 */       dx /= temp;
/*  71 */       dy /= temp;
/*     */     }
/*     */ 
/*  75 */     if (!hb.acceptablePosition(this, new Double2D(this.agentLocation.x + dx, this.agentLocation.y + dy)))
/*     */     {
/*  77 */       this.steps = 0;
/*     */     }
/*     */     else
/*     */     {
/*  81 */       this.agentLocation = new Double2D(this.agentLocation.x + dx, this.agentLocation.y + dy);
/*  82 */       hb.environment.setObjectLocation(this, this.agentLocation);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/*  91 */     double diamx = info.draw.width * 8.0D;
/*  92 */     double diamy = info.draw.height * 8.0D;
/*     */ 
/*  94 */     if (isInfected())
/*  95 */       graphics.setColor(this.infectedColor);
/*  96 */     else graphics.setColor(this.humanColor);
/*  97 */     graphics.fillOval((int)(info.draw.x - diamx / 2.0D), (int)(info.draw.y - diamy / 2.0D), (int)diamx, (int)diamy);
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 103 */     if (isInfected()) {
/* 104 */       return "Infected Human";
/*     */     }
/* 106 */     return "Healthy Human";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.virus.Human
 * JD-Core Version:    0.6.2
 */