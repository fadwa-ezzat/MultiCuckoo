/*     */ package sim.app.cto;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.awt.Color;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.portrayal.simple.OvalPortrayal2D;
/*     */ import sim.util.Double2D;
/*     */ 
/*     */ public class CTOAgent extends OvalPortrayal2D
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public String id;
/*  19 */   public int intID = -1;
/*     */ 
/*  53 */   Double2D desiredLocation = null;
/*  54 */   Double2D suggestedLocation = null;
/*  55 */   int steps = 0;
/*     */   public static final int AGENT = 0;
/*     */   public static final int TARGET = 1;
/*     */   protected int agentState;
/* 153 */   protected Color agentColor = new Color(0, 0, 0);
/* 154 */   protected Color targetColor = new Color(255, 0, 0);
/*     */ 
/* 157 */   public Double2D agentLocation = null;
/*     */ 
/*     */   public CTOAgent(Double2D location, int state, String id)
/*     */   {
/*  23 */     super(8.0D);
/*     */ 
/*  25 */     this.agentLocation = location;
/*  26 */     setState(state);
/*  27 */     this.id = id;
/*     */ 
/*  29 */     if (id.startsWith("A"))
/*     */     {
/*     */       try
/*     */       {
/*  33 */         this.intID = Integer.parseInt(id.substring(5));
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  37 */         throw new RuntimeException(e);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/*  44 */         this.intID = Integer.parseInt(id.substring(6));
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  48 */         throw new RuntimeException(e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/*  59 */     CooperativeObservation hb = (CooperativeObservation)state;
/*     */ 
/*  61 */     Double2D location = this.agentLocation;
/*     */ 
/*  63 */     if (this.agentState == 0)
/*     */     {
/*  65 */       hb.agentPos[this.intID] = location;
/*     */     }
/*     */     else
/*     */     {
/*  69 */       hb.targetPos[this.intID] = location;
/*     */     }
/*     */ 
/*  72 */     if (this.agentState == 0)
/*     */     {
/*  74 */       this.suggestedLocation = hb.kMeansEngine.getGoalPosition(this.intID);
/*  75 */       if (this.suggestedLocation != null)
/*     */       {
/*  77 */         this.desiredLocation = this.suggestedLocation;
/*     */       }
/*     */       else
/*     */       {
/*  81 */         this.steps -= 1;
/*  82 */         if (this.steps <= 0)
/*     */         {
/*  84 */           this.desiredLocation = new Double2D(state.random.nextDouble() * 392.0D + 0.0D + 4.0D, state.random.nextDouble() * 392.0D + 0.0D + 4.0D);
/*     */ 
/*  86 */           this.steps = 100;
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  92 */       this.steps -= 1;
/*  93 */       if ((this.desiredLocation == null) || (this.steps <= 0))
/*     */       {
/*  95 */         this.desiredLocation = new Double2D(state.random.nextDouble() * 392.0D + 0.0D + 4.0D, state.random.nextDouble() * 392.0D + 0.0D + 4.0D);
/*     */ 
/*  97 */         this.steps = 100;
/*     */       }
/*     */     }
/*     */ 
/* 101 */     double dx = this.desiredLocation.x - location.x;
/* 102 */     double dy = this.desiredLocation.y - location.y;
/* 103 */     if (dx > 0.5D)
/* 104 */       dx = 0.5D;
/* 105 */     else if (dx < -0.5D)
/* 106 */       dx = -0.5D;
/* 107 */     if (dy > 0.5D)
/* 108 */       dy = 0.5D;
/* 109 */     else if (dy < -0.5D)
/* 110 */       dy = -0.5D;
/* 111 */     if ((dx < 0.5D) && (dx > -0.5D) && (dy < 0.5D) && (dy > -0.5D)) {
/* 112 */       this.steps = 0;
/*     */     }
/* 114 */     if (this.agentState == 0)
/*     */     {
/* 116 */       dx *= 2.0D;
/* 117 */       dy *= 2.0D;
/*     */     }
/*     */ 
/* 120 */     if (!hb.acceptablePosition(this, new Double2D(location.x + dx, location.y + dy)))
/*     */     {
/* 122 */       this.steps = 0;
/*     */     }
/*     */     else
/*     */     {
/* 126 */       this.agentLocation = new Double2D(location.x + dx, location.y + dy);
/* 127 */       hb.environment.setObjectLocation(this, this.agentLocation);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getState()
/*     */   {
/* 137 */     return this.agentState;
/*     */   }
/*     */ 
/*     */   void setState(int agentState)
/*     */   {
/* 143 */     if ((agentState != 0) && (agentState != 1)) {
/* 144 */       throw new RuntimeException("Unknown state desired to be set (command ignored!): " + agentState);
/*     */     }
/* 146 */     this.agentState = agentState;
/*     */ 
/* 149 */     if (agentState == 0) this.paint = this.agentColor; else
/* 150 */       this.paint = this.targetColor;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.cto.CTOAgent
 * JD-Core Version:    0.6.2
 */