/*    */ package sim.portrayal.network;
/*    */ 
/*    */ import sim.field.SparseField2D;
/*    */ import sim.field.network.Network;
/*    */ import sim.util.Double2D;
/*    */ 
/*    */ public class SpatialNetwork2D
/*    */ {
/*    */   SparseField2D field;
/*    */   SparseField2D field2;
/*    */   Network network;
/*    */ 
/*    */   public SpatialNetwork2D(SparseField2D field, Network network)
/*    */   {
/* 34 */     this.field = field;
/* 35 */     if (field == null)
/* 36 */       throw new RuntimeException("Null SparseField2D.");
/* 37 */     this.network = network;
/* 38 */     if (network == null)
/* 39 */       throw new RuntimeException("Null Network.");
/*    */   }
/*    */ 
/*    */   public void setAuxiliaryField(SparseField2D f)
/*    */   {
/* 44 */     this.field2 = f;
/*    */   }
/*    */ 
/*    */   public void setAuxillaryField(SparseField2D f)
/*    */   {
/* 52 */     setAuxiliaryField(f);
/*    */   }
/*    */ 
/*    */   public Double2D getObjectLocation(Object node)
/*    */   {
/* 63 */     Double2D loc = this.field.getObjectLocationAsDouble2D(node);
/* 64 */     if ((loc == null) && (this.field2 != null))
/* 65 */       loc = this.field2.getObjectLocationAsDouble2D(node);
/* 66 */     return loc;
/*    */   }
/*    */ 
/*    */   /** @deprecated */
/*    */   public Double2D getDimensions()
/*    */   {
/* 72 */     return this.field.getDimensions();
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.network.SpatialNetwork2D
 * JD-Core Version:    0.6.2
 */