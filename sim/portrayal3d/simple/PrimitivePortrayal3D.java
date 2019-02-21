/*     */ package sim.portrayal3d.simple;
/*     */ 
/*     */ import com.sun.j3d.utils.geometry.Primitive;
/*     */ import javax.media.j3d.Appearance;
/*     */ import javax.media.j3d.Node;
/*     */ import javax.media.j3d.Shape3D;
/*     */ import javax.media.j3d.Transform3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ 
/*     */ public abstract class PrimitivePortrayal3D extends SimplePortrayal3D
/*     */ {
/*     */   Transform3D transform;
/*     */   Appearance appearance;
/*     */   protected Node group;
/*     */   static final int DEFAULT_SHAPE = 0;
/*  54 */   boolean pickable = true;
/*     */ 
/*     */   public static void setShape3DFlags(Shape3D shape)
/*     */   {
/*  59 */     shape.setCapability(15);
/*  60 */     shape.setCapability(14);
/*  61 */     shape.setCapability(12);
/*  62 */     shape.setCapability(13);
/*  63 */     shape.clearCapabilityIsFrequent(14);
/*  64 */     shape.clearCapabilityIsFrequent(15);
/*  65 */     shape.clearCapabilityIsFrequent(12);
/*  66 */     shape.clearCapabilityIsFrequent(13);
/*     */   }
/*     */ 
/*     */   protected Shape3D getShape(TransformGroup j3dModel, int shapeIndex)
/*     */   {
/*  75 */     Node n = j3dModel;
/*  76 */     while ((n instanceof TransformGroup))
/*  77 */       n = ((TransformGroup)n).getChild(0);
/*  78 */     Primitive p = (Primitive)n;
/*  79 */     return p.getShape(shapeIndex);
/*     */   }
/*     */ 
/*     */   protected void setAppearance(TransformGroup j3dModel, Appearance app)
/*     */   {
/*  88 */     if (j3dModel == null)
/*     */     {
/*  90 */       this.appearance = app;
/*     */     }
/*     */     else
/*     */     {
/*  94 */       int numShapes = numShapes();
/*  95 */       for (int i = 0; i < numShapes; i++)
/*  96 */         getShape(j3dModel, i).setAppearance(app);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Appearance getAppearance(TransformGroup j3dModel)
/*     */   {
/* 107 */     if ((j3dModel == null) || (numShapes() == 0))
/*     */     {
/* 109 */       Appearance a = new Appearance();
/* 110 */       setAppearanceFlags(a);
/* 111 */       return a;
/*     */     }
/* 113 */     return getShape(j3dModel, 0).getAppearance();
/*     */   }
/*     */ 
/*     */   public boolean setTransform(TransformGroup j3dModel, Transform3D transform)
/*     */   {
/* 123 */     if ((j3dModel == null) || (transform == null))
/*     */     {
/* 125 */       this.transform = transform;
/*     */     }
/*     */     else
/*     */     {
/* 129 */       Node n = j3dModel.getChild(0);
/* 130 */       if ((n instanceof TransformGroup))
/*     */       {
/* 132 */         TransformGroup g = (TransformGroup)j3dModel.getChild(0);
/* 133 */         g.setTransform(transform);
/*     */       }
/*     */     }
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean setScale(TransformGroup j3dModel, double val)
/*     */   {
/* 146 */     if ((this.transform != null) || (val != 1.0D))
/*     */     {
/* 148 */       Transform3D tr = new Transform3D();
/* 149 */       tr.setScale((float)val);
/* 150 */       return setTransform(j3dModel, tr);
/*     */     }
/* 152 */     return setTransform(j3dModel, this.transform);
/*     */   }
/*     */ 
/*     */   protected abstract int numShapes();
/*     */ 
/*     */   public void setPickable(boolean val)
/*     */   {
/* 164 */     this.pickable = val;
/*     */   }
/*     */ 
/*     */   public TransformGroup getModel(Object obj, TransformGroup j3dModel)
/*     */   {
/* 174 */     if (j3dModel == null)
/*     */     {
/* 176 */       j3dModel = new TransformGroup();
/* 177 */       j3dModel.setCapability(12);
/*     */ 
/* 180 */       LocationWrapper pickI = new LocationWrapper(obj, null, getCurrentFieldPortrayal());
/*     */ 
/* 182 */       Node g = this.group.cloneTree(true);
/*     */ 
/* 184 */       if (this.transform != null)
/*     */       {
/* 186 */         TransformGroup tg = new TransformGroup();
/* 187 */         tg.setTransform(this.transform);
/* 188 */         tg.setCapability(17);
/* 189 */         tg.setCapability(18);
/* 190 */         tg.setCapability(12);
/* 191 */         tg.addChild(g);
/* 192 */         g = tg;
/*     */       }
/* 194 */       j3dModel.addChild(g);
/*     */ 
/* 196 */       int numShapes = numShapes();
/* 197 */       for (int i = 0; i < numShapes; i++)
/*     */       {
/* 199 */         Shape3D shape = getShape(j3dModel, i);
/* 200 */         shape.setAppearance(this.appearance);
/* 201 */         if (this.pickable) setPickableFlags(shape);
/*     */ 
/* 204 */         shape.setUserData(pickI);
/*     */       }
/*     */     }
/* 207 */     return j3dModel;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.PrimitivePortrayal3D
 * JD-Core Version:    0.6.2
 */