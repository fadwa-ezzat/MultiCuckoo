/*     */ package sim.app.lsystem;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.Double3D;
/*     */ 
/*     */ public class LSystemDrawer
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   ByteList code;
/*     */   int draw_time;
/*     */   double x;
/*     */   double y;
/*     */   double theta;
/*     */   double angle;
/*     */   double segsize;
/*     */   public Bag stack;
/*     */   Segment s;
/*     */   public Stoppable stopper;
/*     */ 
/*     */   LSystemDrawer(LSystemData l)
/*     */   {
/*  39 */     this.code = new ByteList(l.code);
/*     */ 
/*  41 */     this.x = l.x;
/*  42 */     this.y = l.y;
/*  43 */     this.angle = l.angle;
/*  44 */     this.theta = l.theta;
/*  45 */     this.segsize = l.segsize;
/*     */ 
/*  48 */     this.draw_time = -1;
/*  49 */     this.stack = new Bag();
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/*  55 */     LSystem ls = (LSystem)state;
/*     */     while (true)
/*     */     {
/*  59 */       this.draw_time += 1;
/*     */ 
/*  62 */       if (this.draw_time >= this.code.length)
/*     */       {
/*  64 */         if (this.stopper != null) this.stopper.stop();
/*  65 */         return;
/*     */       }
/*     */ 
/*  69 */       if (this.code.b[this.draw_time] == 91)
/*     */       {
/*  72 */         this.stack.push(new Double3D(this.x, this.y, this.theta));
/*     */       }
/*  74 */       else if (this.code.b[this.draw_time] == 93)
/*     */       {
/*  76 */         Double3D d = (Double3D)this.stack.pop();
/*  77 */         this.x = d.x;
/*  78 */         this.y = d.y;
/*  79 */         this.theta = d.z;
/*     */       }
/*  83 */       else if (this.code.b[this.draw_time] == 45) {
/*  84 */         this.theta += this.angle;
/*     */       }
/*  86 */       else if (this.code.b[this.draw_time] == 43) {
/*  87 */         this.theta -= this.angle;
/*     */       } else {
/*  89 */         if ((this.code.b[this.draw_time] >= 65) && (this.code.b[this.draw_time] <= 90))
/*     */         {
/*  92 */           this.s = new Segment(this.x, this.y, this.segsize, this.theta);
/*  93 */           ls.drawEnvironment.setObjectLocation(this.s, new Double2D(this.s.x, this.s.y));
/*  94 */           this.x += this.segsize * Math.cos(this.theta);
/*  95 */           this.y += this.segsize * Math.sin(this.theta);
/*  96 */           return;
/*     */         }
/*     */ 
/*  99 */         if ((this.code.b[this.draw_time] < 97) || (this.code.b[this.draw_time] > 122)) {
/*     */           break;
/*     */         }
/* 102 */         this.x += this.segsize * Math.cos(this.theta);
/* 103 */         this.y += this.segsize * Math.sin(this.theta);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 109 */     System.err.println("Error--bad code:  " + (char)this.code.b[this.draw_time]);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.lsystem.LSystemDrawer
 * JD-Core Version:    0.6.2
 */