/*     */ package sim.portrayal3d;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import javax.media.j3d.BranchGroup;
/*     */ import javax.media.j3d.Node;
/*     */ import javax.media.j3d.Transform3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.vecmath.Vector3d;
/*     */ import sim.field.SparseField;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.util.Bag;
/*     */ 
/*     */ public abstract class SparseFieldPortrayal3D extends FieldPortrayal3D
/*     */ {
/*     */   public abstract Vector3d getLocationOfObjectAsVector3d(Object paramObject, Vector3d paramVector3d);
/*     */ 
/*     */   public TransformGroup createModel()
/*     */   {
/*  39 */     SparseField field = (SparseField)this.field;
/*  40 */     Vector3d locationV3d = new Vector3d();
/*  41 */     TransformGroup globalTG = new TransformGroup();
/*  42 */     globalTG.setCapability(12);
/*  43 */     globalTG.setCapability(13);
/*  44 */     globalTG.setCapability(14);
/*     */ 
/*  46 */     if (field == null) return globalTG;
/*  47 */     Bag objects = field.getAllObjects();
/*  48 */     Transform3D tmpLocalT = new Transform3D();
/*     */ 
/*  50 */     for (int z = 0; z < objects.numObjs; z++)
/*     */     {
/*  52 */       getLocationOfObjectAsVector3d(objects.objs[z], locationV3d);
/*  53 */       tmpLocalT.setTranslation(locationV3d);
/*  54 */       globalTG.addChild(wrapModelForNewObject(objects.objs[z], tmpLocalT));
/*     */     }
/*     */ 
/*  57 */     return globalTG;
/*     */   }
/*     */ 
/*     */   protected BranchGroup wrapModelForNewObject(Object o, Transform3D localT)
/*     */   {
/*  70 */     Portrayal p = getPortrayalForObject(o);
/*  71 */     if (!(p instanceof SimplePortrayal3D)) {
/*  72 */       throw new RuntimeException("Unexpected Portrayal " + p + " for object " + o + " -- expecting a SimplePortrayal3D");
/*     */     }
/*  74 */     SimplePortrayal3D p3d = (SimplePortrayal3D)p;
/*     */ 
/*  76 */     p3d.setCurrentFieldPortrayal(this);
/*  77 */     TransformGroup localTG = p3d.getModel(o, null);
/*  78 */     localTG.setCapability(18);
/*  79 */     localTG.setCapability(17);
/*  80 */     localTG.setTransform(localT);
/*     */ 
/*  82 */     BranchGroup localBG = new BranchGroup();
/*  83 */     localBG.setCapability(12);
/*  84 */     localBG.setCapability(17);
/*  85 */     localBG.addChild(localTG);
/*  86 */     localBG.setUserData(o);
/*  87 */     return localBG;
/*     */   }
/*     */ 
/*     */   public void updateModel(TransformGroup globalTG)
/*     */   {
/*  93 */     SparseField field = (SparseField)this.field;
/*  94 */     if (field == null) return;
/*  95 */     Bag b = field.getAllObjects();
/*  96 */     HashMap hm = new HashMap();
/*  97 */     Transform3D tmpLocalT = new Transform3D();
/*  98 */     Vector3d locationV3d = new Vector3d();
/*     */ 
/* 101 */     for (int i = 0; i < b.numObjs; i++) {
/* 102 */       hm.put(b.objs[i], b.objs[i]);
/*     */     }
/*     */ 
/* 110 */     Bag toRemove = new Bag();
/*     */ 
/* 113 */     for (int t = 0; t < globalTG.numChildren(); t++)
/*     */     {
/* 115 */       BranchGroup localBG = (BranchGroup)globalTG.getChild(t);
/*     */ 
/* 118 */       Object fieldObj = localBG.getUserData();
/*     */ 
/* 121 */       if (hm.remove(fieldObj) != null)
/*     */       {
/* 126 */         TransformGroup localTG = (TransformGroup)localBG.getChild(0);
/* 127 */         Portrayal p = getPortrayalForObject(fieldObj);
/* 128 */         if (!(p instanceof SimplePortrayal3D)) {
/* 129 */           throw new RuntimeException("Unexpected Portrayal " + p + " for object " + fieldObj + " -- expecting a SimplePortrayal3D");
/*     */         }
/* 131 */         SimplePortrayal3D p3d = (SimplePortrayal3D)p;
/*     */ 
/* 133 */         p3d.setCurrentFieldPortrayal(this);
/* 134 */         TransformGroup localTG2 = p3d.getModel(fieldObj, localTG);
/* 135 */         getLocationOfObjectAsVector3d(fieldObj, locationV3d);
/* 136 */         tmpLocalT.setTranslation(locationV3d);
/* 137 */         localTG2.setTransform(tmpLocalT);
/*     */ 
/* 139 */         if (localTG != localTG2)
/*     */         {
/* 141 */           localTG2.setCapability(18);
/* 142 */           localTG2.setCapability(17);
/*     */ 
/* 144 */           BranchGroup newlocalBG = new BranchGroup();
/* 145 */           newlocalBG.setCapability(12);
/* 146 */           newlocalBG.setCapability(17);
/* 147 */           newlocalBG.setUserData(fieldObj);
/* 148 */           newlocalBG.addChild(localTG2);
/*     */ 
/* 150 */           globalTG.setChild(newlocalBG, t);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 155 */         toRemove.add(localBG);
/*     */       }
/*     */     }
/*     */ 
/* 159 */     for (int i = 0; i < toRemove.numObjs; i++) {
/* 160 */       globalTG.removeChild((Node)toRemove.objs[i]);
/*     */     }
/*     */ 
/* 165 */     if (!hm.isEmpty())
/*     */     {
/* 167 */       Iterator newObjs = hm.values().iterator();
/* 168 */       while (newObjs.hasNext())
/*     */       {
/* 170 */         Object fieldObj = newObjs.next();
/* 171 */         locationV3d = getLocationOfObjectAsVector3d(fieldObj, locationV3d);
/* 172 */         if (locationV3d != null) {
/* 173 */           tmpLocalT.setTranslation(locationV3d);
/*     */         }
/* 175 */         BranchGroup localBG = wrapModelForNewObject(fieldObj, tmpLocalT);
/* 176 */         globalTG.addChild(localBG);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.SparseFieldPortrayal3D
 * JD-Core Version:    0.6.2
 */