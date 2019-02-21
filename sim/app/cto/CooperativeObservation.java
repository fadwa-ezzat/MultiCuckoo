/*     */ package sim.app.cto;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.io.PrintStream;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ 
/*     */ public class CooperativeObservation extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final double XMIN = 0.0D;
/*     */   public static final double XMAX = 400.0D;
/*     */   public static final double YMIN = 0.0D;
/*     */   public static final double YMAX = 400.0D;
/*     */   public static final double DIAMETER = 8.0D;
/*     */   public static final int NUM_TARGETS = 40;
/*     */   public static final int NUM_AGENTS = 10;
/*     */   public static final double KMEANS_REPEAT_INTERVAL = 20.0D;
/*     */   Double2D[] agentPos;
/*     */   Double2D[] targetPos;
/*  30 */   public Continuous2D environment = null;
/*     */   KMeansEngine kMeansEngine;
/*     */ 
/*     */   public CooperativeObservation(long seed)
/*     */   {
/*  37 */     super(seed);
/*     */   }
/*     */ 
/*     */   boolean conflict(Object agent1, Double2D a, Object agent2, Double2D b)
/*     */   {
/*  42 */     if (((a.x > b.x) && (a.x < b.x + 8.0D)) || ((a.x + 8.0D > b.x) && (a.x + 8.0D < b.x + 8.0D) && (((a.y > b.y) && (a.y < b.y + 8.0D)) || ((a.y + 8.0D > b.y) && (a.y + 8.0D < b.y + 8.0D)))))
/*     */     {
/*  47 */       return true;
/*     */     }
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */   boolean acceptablePosition(Object agent, Double2D location)
/*     */   {
/*  54 */     if ((location.x < 4.0D) || (location.x > 396.0D) || (location.y < 4.0D) || (location.y > 396.0D))
/*     */     {
/*  56 */       return false;
/*  57 */     }Bag misteriousObjects = this.environment.getNeighborsWithinDistance(location, Math.max(16.0D, 16.0D));
/*  58 */     if (misteriousObjects != null)
/*     */     {
/*  60 */       for (int i = 0; i < misteriousObjects.numObjs; i++)
/*     */       {
/*  62 */         if ((misteriousObjects.objs[i] != null) && (misteriousObjects.objs[i] != agent))
/*     */         {
/*  64 */           Object ta = (CTOAgent)misteriousObjects.objs[i];
/*  65 */           if (conflict(agent, location, ta, this.environment.getObjectLocation(ta)))
/*  66 */             return false;
/*     */         }
/*     */       }
/*     */     }
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  75 */     super.start();
/*     */ 
/*  77 */     this.agentPos = new Double2D[10];
/*  78 */     for (int i = 0; i < 10; i++) {
/*  79 */       this.agentPos[i] = new Double2D();
/*     */     }
/*  81 */     this.targetPos = new Double2D[40];
/*  82 */     for (int i = 0; i < 40; i++) {
/*  83 */       this.targetPos[i] = new Double2D();
/*     */     }
/*  85 */     this.kMeansEngine = new KMeansEngine(this);
/*  86 */     this.schedule.scheduleRepeating(this.kMeansEngine, -1, 20.0D);
/*     */ 
/*  88 */     this.environment = new Continuous2D(8.0D, 400.0D, 400.0D);
/*     */ 
/*  92 */     for (int x = 0; x < 50; x++)
/*     */     {
/*  94 */       Double2D loc = null;
/*  95 */       CTOAgent agent = null;
/*  96 */       int times = 0;
/*     */       do
/*     */       {
/*  99 */         loc = new Double2D(this.random.nextDouble() * 392.0D + 0.0D + 4.0D, this.random.nextDouble() * 392.0D + 0.0D + 4.0D);
/*     */ 
/* 101 */         if (x < 10)
/*     */         {
/* 103 */           agent = new CTOAgent(loc, 0, "Agent" + x);
/*     */         }
/*     */         else
/*     */         {
/* 107 */           agent = new CTOAgent(loc, 1, "Target" + (x - 10));
/*     */         }
/* 109 */         times++;
/* 110 */         if (times == 1000)
/*     */         {
/* 113 */           System.err.println("Cannot place agents. Exiting....");
/* 114 */           break;
/*     */         }
/*     */       }
/* 116 */       while (!acceptablePosition(agent, loc));
/* 117 */       this.environment.setObjectLocation(agent, loc);
/* 118 */       this.schedule.scheduleRepeating(agent);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 126 */     doLoop(CooperativeObservation.class, args);
/* 127 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.cto.CooperativeObservation
 * JD-Core Version:    0.6.2
 */