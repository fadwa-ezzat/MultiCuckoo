/*     */ package sim.app.virus;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ 
/*     */ public class VirusInfectionDemo extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final double XMIN = 0.0D;
/*     */   public static final double XMAX = 800.0D;
/*     */   public static final double YMIN = 0.0D;
/*     */   public static final double YMAX = 600.0D;
/*     */   public static final double DIAMETER = 8.0D;
/*     */   public static final double HEALING_DISTANCE = 20.0D;
/*     */   public static final double HEALING_DISTANCE_SQUARED = 400.0D;
/*     */   public static final double INFECTION_DISTANCE = 20.0D;
/*     */   public static final double INFECTION_DISTANCE_SQUARED = 400.0D;
/*     */   public static final int NUM_HUMANS = 100;
/*     */   public static final int NUM_GOODS = 4;
/*     */   public static final int NUM_EVILS = 4;
/*  34 */   public Continuous2D environment = null;
/*     */ 
/*     */   public VirusInfectionDemo(long seed)
/*     */   {
/*  39 */     super(seed);
/*     */   }
/*     */ 
/*     */   boolean conflict(Agent agent1, Double2D a, Agent agent2, Double2D b)
/*     */   {
/*  44 */     if (((a.x > b.x) && (a.x < b.x + 8.0D)) || ((a.x + 8.0D > b.x) && (a.x + 8.0D < b.x + 8.0D) && (((a.y > b.y) && (a.y < b.y + 8.0D)) || ((a.y + 8.0D > b.y) && (a.y + 8.0D < b.y + 8.0D)))))
/*     */     {
/*  49 */       return true;
/*     */     }
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean withinInfectionDistance(Agent agent1, Double2D a, Agent agent2, Double2D b)
/*     */   {
/*  56 */     return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y) <= 400.0D;
/*     */   }
/*     */ 
/*     */   public boolean withinHealingDistance(Agent agent1, Double2D a, Agent agent2, Double2D b)
/*     */   {
/*  61 */     return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y) <= 400.0D;
/*     */   }
/*     */ 
/*     */   boolean acceptablePosition(Agent agent, Double2D location)
/*     */   {
/*  66 */     if ((location.x < 4.0D) || (location.x > 796.0D) || (location.y < 4.0D) || (location.y > 596.0D))
/*     */     {
/*  68 */       return false;
/*  69 */     }Bag mysteriousObjects = this.environment.getNeighborsWithinDistance(location, 16.0D);
/*  70 */     if (mysteriousObjects != null)
/*     */     {
/*  72 */       for (int i = 0; i < mysteriousObjects.numObjs; i++)
/*     */       {
/*  74 */         if ((mysteriousObjects.objs[i] != null) && (mysteriousObjects.objs[i] != agent))
/*     */         {
/*  76 */           Agent ta = (Agent)mysteriousObjects.objs[i];
/*  77 */           if (conflict(agent, location, ta, this.environment.getObjectLocation(ta)))
/*  78 */             return false;
/*     */         }
/*     */       }
/*     */     }
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  87 */     super.start();
/*     */ 
/*  89 */     this.environment = new Continuous2D(25.0D, 800.0D, 600.0D);
/*     */ 
/*  93 */     for (int x = 0; x < 108; x++)
/*     */     {
/*  95 */       Double2D loc = null;
/*  96 */       Agent agent = null;
/*  97 */       int times = 0;
/*     */       do
/*     */       {
/* 100 */         loc = new Double2D(this.random.nextDouble() * 792.0D + 0.0D + 4.0D, this.random.nextDouble() * 592.0D + 0.0D + 4.0D);
/*     */ 
/* 102 */         if (x < 100)
/* 103 */           agent = new Human("Human" + x, loc);
/* 104 */         else if (x < 104)
/* 105 */           agent = new Good("Good" + (x - 100), loc);
/*     */         else
/* 107 */           agent = new Evil("Evil" + (x - 100 - 4), loc);
/* 108 */         times++;
/* 109 */       }while ((times != 1000) && 
/* 114 */         (!acceptablePosition(agent, loc)));
/* 115 */       this.environment.setObjectLocation(agent, loc);
/* 116 */       this.schedule.scheduleRepeating(agent);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 122 */     doLoop(VirusInfectionDemo.class, args);
/* 123 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.virus.VirusInfectionDemo
 * JD-Core Version:    0.6.2
 */