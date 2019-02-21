/*    */ package sim.engine;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import java.util.Collection;
/*    */ import sim.util.LocationLog;
/*    */ 
/*    */ public class RandomSequence extends Sequence
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   final boolean shouldSynchronize;
/*    */ 
/*    */   public RandomSequence(Steppable[] steps)
/*    */   {
/* 33 */     this(steps, false);
/*    */   }
/*    */ 
/*    */   public RandomSequence(Steppable[] steps, boolean shouldSynchronize)
/*    */   {
/* 39 */     super(steps);
/* 40 */     this.shouldSynchronize = shouldSynchronize;
/*    */   }
/*    */ 
/*    */   public RandomSequence(Collection steps)
/*    */   {
/* 46 */     this(steps, false);
/*    */   }
/*    */ 
/*    */   public RandomSequence(Collection steps, boolean shouldSynchronize)
/*    */   {
/* 52 */     super(steps);
/* 53 */     this.shouldSynchronize = shouldSynchronize;
/*    */   }
/*    */ 
/*    */   int nextInt(SimState state, int n)
/*    */   {
/* 58 */     synchronized (state.random) {
/* 59 */       return state.random.nextInt(n);
/*    */     }
/*    */   }
/* 62 */   protected boolean canEnsureOrder() { return false; }
/*    */ 
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 67 */     loadSteps();
/*    */ 
/* 69 */     boolean shouldSynchronize = this.shouldSynchronize;
/* 70 */     int size = this.size;
/* 71 */     Steppable[] steps = this.steps;
/*    */ 
/* 76 */     for (int x = size - 1; x >= 1; x--)
/*    */     {
/* 78 */       int i = shouldSynchronize ? nextInt(state, x + 1) : state.random.nextInt(x + 1);
/* 79 */       Steppable temp = steps[i];
/* 80 */       steps[i] = steps[x];
/* 81 */       steps[x] = temp;
/*    */     }
/*    */ 
/* 85 */     for (int x = 0; x < size; x++)
/*    */     {
/* 87 */       if (steps[x] != null)
/*    */       {
/* 89 */         assert (LocationLog.set(steps[x]));
/* 90 */         steps[x].step(state);
/* 91 */         assert (LocationLog.clear());
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.engine.RandomSequence
 * JD-Core Version:    0.6.2
 */