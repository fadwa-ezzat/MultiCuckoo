/*     */ package sim.app.virus;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ 
/*     */ public class Evil extends Agent
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  18 */   protected boolean greedy = false;
/*     */ 
/*  35 */   Double2D desiredLocation = null;
/*  36 */   Double2D suggestedLocation = null;
/*  37 */   int steps = 0;
/*     */ 
/* 126 */   protected Color evilColor = new Color(255, 0, 0);
/*     */ 
/*     */   public final boolean getIsGreedy()
/*     */   {
/*  19 */     return this.greedy; } 
/*  20 */   public final void setIsGreedy(boolean b) { this.greedy = b; }
/*     */ 
/*     */   public Evil(String id, Double2D location)
/*     */   {
/*  24 */     super(id, location);
/*     */     try
/*     */     {
/*  27 */       this.intID = Integer.parseInt(id.substring(4));
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
/*  43 */     this.desiredLocation = null;
/*  44 */     double distance2DesiredLocation = 1.0E+030D;
/*     */ 
/*  46 */     Bag mysteriousObjects = hb.environment.getNeighborsWithinDistance(this.agentLocation, 200.0D);
/*  47 */     if (mysteriousObjects != null)
/*     */     {
/*  49 */       for (int i = 0; i < mysteriousObjects.numObjs; i++)
/*     */       {
/*  51 */         if ((mysteriousObjects.objs[i] != null) && (mysteriousObjects.objs[i] != this))
/*     */         {
/*  55 */           if (((Agent)mysteriousObjects.objs[i] instanceof Human))
/*     */           {
/*  57 */             Human ta = (Human)mysteriousObjects.objs[i];
/*     */ 
/*  59 */             if (!ta.isInfected())
/*     */             {
/*  61 */               if (hb.withinInfectionDistance(this, this.agentLocation, ta, ta.agentLocation)) {
/*  62 */                 ta.setInfected(true);
/*     */               }
/*  65 */               else if (getIsGreedy())
/*     */               {
/*  67 */                 double tmpDist = distanceSquared(this.agentLocation, ta.agentLocation);
/*  68 */                 if (tmpDist < distance2DesiredLocation)
/*     */                 {
/*  70 */                   this.desiredLocation = ta.agentLocation;
/*  71 */                   distance2DesiredLocation = tmpDist;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  79 */     this.steps -= 1;
/*  80 */     if ((this.desiredLocation == null) || (!getIsGreedy()))
/*     */     {
/*  82 */       if (this.steps <= 0)
/*     */       {
/*  84 */         this.suggestedLocation = new Double2D((state.random.nextDouble() - 0.5D) * 152.0D + this.agentLocation.x, (state.random.nextDouble() - 0.5D) * 112.0D + this.agentLocation.y);
/*     */ 
/*  94 */         this.steps = 100;
/*     */       }
/*  96 */       this.desiredLocation = this.suggestedLocation;
/*     */     }
/*     */ 
/*  99 */     double dx = this.desiredLocation.x - this.agentLocation.x;
/* 100 */     double dy = this.desiredLocation.y - this.agentLocation.y;
/*     */ 
/* 103 */     double temp = 0.5D * Math.sqrt(dx * dx + dy * dy);
/* 104 */     if (temp < 1.0D)
/*     */     {
/* 106 */       this.steps = 0;
/*     */     }
/*     */     else
/*     */     {
/* 110 */       dx /= temp;
/* 111 */       dy /= temp;
/*     */     }
/*     */ 
/* 115 */     if (!hb.acceptablePosition(this, new Double2D(this.agentLocation.x + dx, this.agentLocation.y + dy)))
/*     */     {
/* 117 */       this.steps = 0;
/*     */     }
/*     */     else
/*     */     {
/* 121 */       this.agentLocation = new Double2D(this.agentLocation.x + dx, this.agentLocation.y + dy);
/* 122 */       hb.environment.setObjectLocation(this, this.agentLocation);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/* 129 */     double diamx = info.draw.width * 8.0D;
/* 130 */     double diamy = info.draw.height * 8.0D;
/*     */ 
/* 132 */     graphics.setColor(this.evilColor);
/* 133 */     graphics.fillOval((int)(info.draw.x - diamx / 2.0D), (int)(info.draw.y - diamy / 2.0D), (int)diamx, (int)diamy);
/*     */   }
/*     */   public String getType() {
/* 136 */     return "Evil";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.virus.Evil
 * JD-Core Version:    0.6.2
 */