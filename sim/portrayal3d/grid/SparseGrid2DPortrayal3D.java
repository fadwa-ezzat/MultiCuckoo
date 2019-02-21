/*     */ package sim.portrayal3d.grid;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import javax.media.j3d.BranchGroup;
/*     */ import javax.media.j3d.Transform3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.vecmath.Vector3d;
/*     */ import sim.field.grid.SparseGrid2D;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.MutableDouble;
/*     */ 
/*     */ public class SparseGrid2DPortrayal3D extends SparseGridPortrayal3D
/*     */ {
/*     */   public double zScale;
/*     */ 
/*     */   public SparseGrid2DPortrayal3D(double zScale)
/*     */   {
/*  33 */     this.zScale = zScale;
/*     */   }
/*     */ 
/*     */   public SparseGrid2DPortrayal3D()
/*     */   {
/*  39 */     this(1.0D);
/*     */   }
/*     */ 
/*     */   public void setField(Object field)
/*     */   {
/*  44 */     if ((field instanceof SparseGrid2D)) super.setField(field); else
/*  45 */       throw new RuntimeException("Invalid field for StackedSparse2DPortrayal3D: " + field);
/*     */   }
/*     */ 
/*     */   public TransformGroup createModel()
/*     */   {
/*  50 */     TransformGroup globalTG = new TransformGroup();
/*  51 */     globalTG.setCapability(12);
/*  52 */     globalTG.setCapability(13);
/*  53 */     globalTG.setCapability(14);
/*     */ 
/*  55 */     if (this.field == null) return globalTG;
/*     */ 
/*  57 */     Vector3d tmpV3D = new Vector3d();
/*  58 */     Transform3D tmpLocalT = new Transform3D();
/*  59 */     HashMap map = new HashMap();
/*  60 */     SparseGrid2D grid = (SparseGrid2D)this.field;
/*  61 */     Bag allobjs = grid.getAllObjects();
/*     */ 
/*  63 */     for (int i = 0; i < allobjs.numObjs; i++)
/*     */     {
/*  65 */       if (!map.containsKey(allobjs.objs[i]))
/*     */       {
/*  67 */         Int2D location = grid.getObjectLocation(allobjs.objs[i]);
/*  68 */         Bag b = grid.getObjectsAtLocation(location);
/*  69 */         tmpV3D.x = location.x;
/*  70 */         tmpV3D.y = location.y;
/*  71 */         if (b != null) {
/*  72 */           for (int z = 0; z < b.numObjs; z++)
/*     */           {
/*  74 */             map.put(b.objs[z], b.objs[z]);
/*  75 */             tmpV3D.z = (z * this.zScale);
/*  76 */             tmpLocalT.setTranslation(tmpV3D);
/*     */ 
/*  79 */             BranchGroup localBG = wrapModelForNewObject(b.objs[z], tmpLocalT);
/*  80 */             globalTG.addChild(localBG);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  85 */     return globalTG;
/*     */   }
/*     */ 
/*     */   public void updateModel(TransformGroup globalTG)
/*     */   {
/*  90 */     SparseGrid2D grid = (SparseGrid2D)this.field;
/*  91 */     if (grid == null) return;
/*  92 */     Vector3d tmpV3D = new Vector3d();
/*  93 */     Bag b = grid.getAllObjects();
/*  94 */     Transform3D tmpLocalT = new Transform3D();
/*  95 */     HashMap hm = new HashMap();
/*     */ 
/*  97 */     HashMap stackCountByLocation = new HashMap();
/*     */ 
/*  99 */     for (int i = 0; i < b.numObjs; i++) {
/* 100 */       hm.put(b.objs[i], b.objs[i]);
/*     */     }
/* 102 */     for (int t = globalTG.numChildren() - 1; t >= 0; t--)
/*     */     {
/* 104 */       BranchGroup localBG = (BranchGroup)globalTG.getChild(t);
/* 105 */       Object fieldObj = localBG.getUserData();
/* 106 */       if (hm.remove(fieldObj) != null)
/*     */       {
/* 108 */         TransformGroup localTG = (TransformGroup)localBG.getChild(0);
/* 109 */         Portrayal p = getPortrayalForObject(fieldObj);
/* 110 */         if (!(p instanceof SimplePortrayal3D)) {
/* 111 */           throw new RuntimeException("Unexpected Portrayal " + p + " for object " + fieldObj + " -- expecting a SimplePortrayal3D");
/*     */         }
/* 113 */         SimplePortrayal3D p3d = (SimplePortrayal3D)p;
/* 114 */         p3d.setCurrentFieldPortrayal(this);
/* 115 */         TransformGroup localTG2 = p3d.getModel(fieldObj, localTG);
/*     */ 
/* 118 */         Int2D location = grid.getObjectLocation(fieldObj);
/* 119 */         tmpV3D.x = location.x;
/* 120 */         tmpV3D.y = location.y;
/* 121 */         MutableDouble d = (MutableDouble)stackCountByLocation.get(location);
/* 122 */         if (d == null)
/*     */         {
/* 124 */           d = new MutableDouble(0.0D);
/* 125 */           stackCountByLocation.put(location, d);
/*     */         } else {
/* 127 */           d.val += 1.0D;
/*     */         }
/* 129 */         tmpV3D.z = (d.val * this.zScale);
/*     */ 
/* 131 */         tmpLocalT.setTranslation(tmpV3D);
/* 132 */         localTG2.setTransform(tmpLocalT);
/*     */ 
/* 134 */         if (localTG != localTG2)
/*     */         {
/* 136 */           localTG2.setCapability(18);
/* 137 */           localTG2.setCapability(17);
/*     */ 
/* 139 */           BranchGroup newlocalBG = new BranchGroup();
/* 140 */           newlocalBG.setCapability(12);
/* 141 */           newlocalBG.setCapability(17);
/* 142 */           newlocalBG.setUserData(fieldObj);
/* 143 */           newlocalBG.addChild(localTG2);
/*     */ 
/* 145 */           globalTG.setChild(newlocalBG, t);
/*     */         }
/*     */       }
/*     */       else {
/* 149 */         globalTG.removeChild(t);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 155 */     if (!hm.isEmpty())
/*     */     {
/* 157 */       Iterator newObjs = hm.values().iterator();
/* 158 */       while (newObjs.hasNext())
/*     */       {
/* 160 */         Object fieldObj = newObjs.next();
/*     */ 
/* 162 */         Int2D location = grid.getObjectLocation(fieldObj);
/* 163 */         tmpV3D.x = location.x;
/* 164 */         tmpV3D.y = location.y;
/* 165 */         MutableDouble d = (MutableDouble)stackCountByLocation.get(location);
/* 166 */         if (d == null)
/*     */         {
/* 168 */           d = new MutableDouble(0.0D);
/* 169 */           stackCountByLocation.put(location, d);
/*     */         } else {
/* 171 */           d.val += 1.0D;
/*     */         }
/* 173 */         tmpV3D.z = (d.val * this.zScale);
/* 174 */         tmpLocalT.setTranslation(tmpV3D);
/*     */ 
/* 177 */         BranchGroup localBG = wrapModelForNewObject(fieldObj, tmpLocalT);
/* 178 */         globalTG.addChild(localBG);
/*     */       }
/* 180 */       hm.clear();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.grid.SparseGrid2DPortrayal3D
 * JD-Core Version:    0.6.2
 */