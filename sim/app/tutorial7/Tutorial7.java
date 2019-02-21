/*    */ package sim.app.tutorial7;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.grid.DoubleGrid2D;
/*    */ import sim.field.grid.SparseGrid3D;
/*    */ 
/*    */ public class Tutorial7 extends SimState
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public SparseGrid3D flies;
/*    */   public DoubleGrid2D xProjection;
/*    */   public DoubleGrid2D yProjection;
/*    */   public DoubleGrid2D zProjection;
/* 20 */   int width = 30;
/* 21 */   int height = 30;
/* 22 */   int length = 30;
/*    */ 
/* 23 */   public void setWidth(int val) { if (val > 0) this.width = val;  } 
/* 24 */   public int getWidth() { return this.width; } 
/* 25 */   public void setHeight(int val) { if (val > 0) this.height = val;  } 
/* 26 */   public int getHeight() { return this.height; } 
/* 27 */   public void setLength(int val) { if (val > 0) this.length = val;  } 
/* 28 */   public int getLength() { return this.length; }
/*    */ 
/*    */   public Tutorial7(long seed)
/*    */   {
/* 32 */     super(seed);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 37 */     super.start();
/*    */ 
/* 39 */     this.flies = new SparseGrid3D(this.width, this.height, this.length);
/* 40 */     this.xProjection = new DoubleGrid2D(this.height, this.length);
/* 41 */     this.yProjection = new DoubleGrid2D(this.width, this.length);
/* 42 */     this.zProjection = new DoubleGrid2D(this.width, this.height);
/*    */ 
/* 45 */     this.schedule.scheduleRepeating(new Steppable() {
/*    */       static final long serialVersionUID = 1L;
/*    */ 
/*    */       public void step(SimState state) {
/* 49 */         Tutorial7.this.xProjection.setTo(0.0D);
/* 50 */         Tutorial7.this.yProjection.setTo(0.0D);
/* 51 */         Tutorial7.this.zProjection.setTo(0.0D);
/*    */       }
/*    */     });
/* 59 */     for (int i = 0; i < 100; i++)
/*    */     {
/* 61 */       Fly fly = new Fly();
/* 62 */       this.flies.setObjectLocation(fly, this.random.nextInt(this.width), this.random.nextInt(this.height), this.random.nextInt(this.length));
/* 63 */       this.schedule.scheduleRepeating(0.0D, 1, fly, 1.0D);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 70 */     doLoop(Tutorial7.class, args);
/* 71 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial7.Tutorial7
 * JD-Core Version:    0.6.2
 */