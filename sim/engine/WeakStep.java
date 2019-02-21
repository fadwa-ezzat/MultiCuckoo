/*    */ package sim.engine;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.lang.ref.WeakReference;
/*    */ 
/*    */ public class WeakStep
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   WeakReference weakStep;
/*    */   Stoppable stop;
/*    */ 
/*    */   private void writeObject(ObjectOutputStream p)
/*    */     throws IOException
/*    */   {
/* 50 */     p.writeObject(this.weakStep.get());
/* 51 */     p.writeBoolean(this.stop != null);
/* 52 */     if (this.stop != null)
/* 53 */       p.writeObject(this.stop);
/*    */   }
/*    */ 
/*    */   private void readObject(ObjectInputStream p)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 61 */     this.weakStep = new WeakReference(p.readObject());
/* 62 */     if (p.readBoolean())
/* 63 */       this.stop = ((Stoppable)p.readObject());
/* 64 */     else this.stop = null;
/*    */   }
/*    */ 
/*    */   public WeakStep(Steppable step)
/*    */   {
/* 69 */     this.weakStep = new WeakReference(step);
/*    */   }
/*    */ 
/*    */   public void setStoppable(Stoppable stop)
/*    */   {
/* 74 */     this.stop = stop;
/*    */   }
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 79 */     Steppable step = (Steppable)this.weakStep.get();
/* 80 */     if (step != null)
/* 81 */       step.step(state);
/* 82 */     else if (this.stop != null)
/* 83 */       this.stop.stop();
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.engine.WeakStep
 * JD-Core Version:    0.6.2
 */