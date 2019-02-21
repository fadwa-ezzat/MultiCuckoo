/*    */ package sim.portrayal3d.grid;
/*    */ 
/*    */ import com.sun.j3d.utils.picking.PickIntersection;
/*    */ import com.sun.j3d.utils.picking.PickResult;
/*    */ import javax.vecmath.Vector3d;
/*    */ import sim.field.grid.SparseGrid2D;
/*    */ import sim.field.grid.SparseGrid3D;
/*    */ import sim.portrayal.FieldPortrayal;
/*    */ import sim.portrayal.LocationWrapper;
/*    */ import sim.portrayal.inspector.StableInt2D;
/*    */ import sim.portrayal.inspector.StableLocation;
/*    */ import sim.portrayal3d.SparseFieldPortrayal3D;
/*    */ import sim.portrayal3d.inspector.StableInt3D;
/*    */ import sim.util.Int2D;
/*    */ import sim.util.Int3D;
/*    */ 
/*    */ public class SparseGridPortrayal3D extends SparseFieldPortrayal3D
/*    */ {
/*    */   public Vector3d getLocationOfObjectAsVector3d(Object obj, Vector3d putInHere)
/*    */   {
/* 29 */     if ((this.field instanceof SparseGrid3D))
/*    */     {
/* 31 */       Int3D locationI3d = ((SparseGrid3D)this.field).getObjectLocation(obj);
/* 32 */       putInHere.x = locationI3d.x;
/* 33 */       putInHere.y = locationI3d.y;
/* 34 */       putInHere.z = locationI3d.z;
/*    */     }
/*    */     else
/*    */     {
/* 38 */       Int2D locationI2d = ((SparseGrid2D)this.field).getObjectLocation(obj);
/* 39 */       putInHere.x = locationI2d.x;
/* 40 */       putInHere.y = locationI2d.y;
/* 41 */       putInHere.z = 0.0D;
/*    */     }
/* 43 */     return putInHere;
/*    */   }
/*    */ 
/*    */   public void setField(Object field)
/*    */   {
/* 48 */     if (((field instanceof SparseGrid3D)) || ((field instanceof SparseGrid2D))) super.setField(field); else
/* 49 */       throw new RuntimeException("Invalid field for SparseGridPortrayal3D: " + field);
/*    */   }
/*    */ 
/*    */   public LocationWrapper completedWrapper(LocationWrapper w, PickIntersection pi, PickResult pr)
/*    */   {
/* 54 */     Object field = getField();
/* 55 */     StableLocation d = null;
/* 56 */     if ((field instanceof SparseGrid2D)) d = new StableInt2D((SparseGrid2D)field, w.getObject()); else
/* 57 */       d = new StableInt3D((SparseGrid3D)field, w.getObject());
/* 58 */     final StableLocation loc = d;
/* 59 */     return new LocationWrapper(w.getObject(), null, this)
/*    */     {
/*    */       public Object getLocation()
/*    */       {
/* 63 */         return loc;
/*    */       }
/*    */ 
/*    */       public String getLocationName()
/*    */       {
/* 68 */         return loc.toString();
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.grid.SparseGridPortrayal3D
 * JD-Core Version:    0.6.2
 */