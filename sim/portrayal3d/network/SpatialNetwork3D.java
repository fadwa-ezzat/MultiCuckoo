/*    */ package sim.portrayal3d.network;
/*    */ 
/*    */ import sim.field.SparseField2D;
/*    */ import sim.field.SparseField3D;
/*    */ import sim.field.continuous.Continuous3D;
/*    */ import sim.field.grid.SparseGrid3D;
/*    */ import sim.field.network.Network;
/*    */ import sim.util.Double3D;
/*    */ 
/*    */ public class SpatialNetwork3D
/*    */ {
/*    */   Object field;
/*    */   Object field2;
/*    */   Network network;
/*    */ 
/*    */   public SpatialNetwork3D(SparseField3D field, Network network)
/*    */   {
/* 27 */     this.field = field;
/* 28 */     if (field == null)
/* 29 */       throw new RuntimeException("Null SparseField3D.");
/* 30 */     this.network = network;
/* 31 */     if (network == null)
/* 32 */       throw new RuntimeException("Null Network.");
/*    */   }
/*    */ 
/*    */   public SpatialNetwork3D(SparseField2D grid, Network network)
/*    */   {
/* 37 */     this.field = grid;
/* 38 */     if (this.field == null)
/* 39 */       throw new RuntimeException("Null SparseField2D.");
/* 40 */     this.network = network;
/* 41 */     if (network == null)
/* 42 */       throw new RuntimeException("Null Network.");
/*    */   }
/*    */ 
/*    */   public void setAuxiliaryField(SparseField3D f)
/*    */   {
/* 47 */     this.field2 = f;
/*    */   }
/*    */ 
/*    */   public void setAuxiliaryField(SparseField2D f)
/*    */   {
/* 52 */     this.field2 = f;
/*    */   }
/*    */ 
/*    */   /** @deprecated */
/*    */   public void setAuxillaryField(Continuous3D f)
/*    */   {
/* 58 */     setAuxiliaryField(f);
/*    */   }
/*    */ 
/*    */   /** @deprecated */
/*    */   public void setAuxillaryField(SparseGrid3D f)
/*    */   {
/* 64 */     setAuxiliaryField(f);
/*    */   }
/*    */ 
/*    */   public Double3D getObjectLocation(Object node)
/*    */   {
/*    */     Double3D loc;
/*    */     Double3D loc;
/* 70 */     if ((this.field instanceof SparseField3D))
/* 71 */       loc = ((SparseField3D)this.field).getObjectLocationAsDouble3D(node);
/*    */     else {
/* 73 */       loc = new Double3D(((SparseField2D)this.field).getObjectLocationAsDouble2D(node));
/*    */     }
/* 75 */     if ((loc == null) && (this.field2 != null))
/*    */     {
/* 77 */       if ((this.field2 instanceof SparseField3D))
/* 78 */         loc = ((SparseField3D)this.field2).getObjectLocationAsDouble3D(node);
/*    */       else
/* 80 */         loc = new Double3D(((SparseField2D)this.field2).getObjectLocationAsDouble2D(node));
/*    */     }
/* 82 */     return loc;
/*    */   }
/*    */ 
/*    */   /** @deprecated */
/*    */   public Double3D getDimensions()
/*    */   {
/* 88 */     if ((this.field instanceof SparseField3D))
/* 89 */       return ((SparseField3D)this.field).getDimensions();
/* 90 */     return new Double3D(((SparseField2D)this.field).getDimensions());
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.network.SpatialNetwork3D
 * JD-Core Version:    0.6.2
 */