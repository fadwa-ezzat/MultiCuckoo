/*    */ package sim.app.cto;
/*    */ 
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.util.Double2D;
/*    */ 
/*    */ public class KMeansEngine
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   static final double ALFA = 0.25D;
/*    */   Double2D[] clusterPoints;
/*    */   boolean[] usable;
/*    */   Double2D[] means;
/*    */   int[] labels;
/*    */   int[] n;
/*    */   double[] weight;
/*    */   CooperativeObservation co;
/*    */ 
/*    */   public KMeansEngine(CooperativeObservation co)
/*    */   {
/* 29 */     this.co = co;
/*    */ 
/* 31 */     this.clusterPoints = new Double2D[10];
/* 32 */     this.usable = new boolean[10];
/* 33 */     this.means = new Double2D[10];
/* 34 */     for (int i = 0; i < 10; i++)
/*    */     {
/* 36 */       this.clusterPoints[i] = new Double2D();
/* 37 */       this.means[i] = new Double2D();
/*    */     }
/* 39 */     this.labels = new int[40];
/* 40 */     this.n = new int[10];
/* 41 */     this.weight = new double[10];
/*    */   }
/*    */ 
/*    */   public Double2D getGoalPosition(int id)
/*    */   {
/* 46 */     if (this.usable[id] != 0) {
/* 47 */       return this.clusterPoints[id];
/*    */     }
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */   public final double distanceBetweenPointsSQR(double x1, double y1, double x2, double y2)
/*    */   {
/* 54 */     return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
/*    */   }
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 59 */     for (int i = 0; i < 10; i++)
/*    */     {
/* 61 */       this.weight[i] = 0.0D;
/* 62 */       if ((this.means[i].x == 0.0D) && (this.means[i].y == 0.0D))
/*    */       {
/* 64 */         this.clusterPoints[i] = this.means[i];
/*    */       }
/* 66 */       this.means[i] = this.co.agentPos[i];
/*    */     }
/* 68 */     for (int i = 0; i < 40; i++)
/*    */     {
/* 70 */       int min = -1;
/* 71 */       double distance = -1.0D;
/* 72 */       for (int j = 0; j < 10; j++)
/*    */       {
/* 74 */         double currDist = distanceBetweenPointsSQR(this.co.targetPos[i].x, this.co.targetPos[i].y, this.co.agentPos[j].x, this.co.agentPos[j].y);
/*    */ 
/* 76 */         if ((distance == -1.0D) || (distance > currDist))
/*    */         {
/* 78 */           min = j;
/* 79 */           distance = currDist;
/*    */         }
/*    */       }
/* 82 */       this.labels[i] = min;
/*    */     }
/* 84 */     for (int i = 0; i < 10; i++)
/*    */     {
/* 86 */       this.means[i] = new Double2D(0.0D, 0.0D);
/* 87 */       this.n[i] = 0;
/*    */     }
/* 89 */     for (int i = 0; i < 40; i++)
/*    */     {
/* 91 */       if (this.labels[i] != -1)
/*    */       {
/* 93 */         this.means[this.labels[i]] = new Double2D(this.means[this.labels[i]].x + this.co.targetPos[i].x, this.means[this.labels[i]].y + this.co.targetPos[i].y);
/*    */ 
/* 95 */         this.n[this.labels[i]] += 1;
/*    */       }
/*    */     }
/* 98 */     for (int i = 0; i < 10; i++)
/*    */     {
/* 100 */       if (this.n[i] != 0)
/*    */       {
/* 102 */         this.means[i] = new Double2D(this.means[i].x / this.n[i], this.means[i].y / this.n[i]);
/* 103 */         this.clusterPoints[i] = new Double2D(0.75D * this.clusterPoints[i].x + 0.25D * this.means[i].x, 0.75D * this.clusterPoints[i].y + 0.25D * this.means[i].y);
/*    */ 
/* 105 */         this.usable[i] = true;
/*    */       }
/*    */       else
/*    */       {
/* 109 */         this.usable[i] = false;
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.cto.KMeansEngine
 * JD-Core Version:    0.6.2
 */