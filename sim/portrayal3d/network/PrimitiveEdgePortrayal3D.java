/*     */ package sim.portrayal3d.network;
/*     */ 
/*     */ import com.sun.j3d.utils.geometry.Primitive;
/*     */ import com.sun.j3d.utils.geometry.Text2D;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import javax.media.j3d.Appearance;
/*     */ import javax.media.j3d.Node;
/*     */ import javax.media.j3d.OrientedShape3D;
/*     */ import javax.media.j3d.Shape3D;
/*     */ import javax.media.j3d.Transform3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.vecmath.Color3f;
/*     */ import javax.vecmath.Point3f;
/*     */ import sim.field.network.Edge;
/*     */ import sim.portrayal.FieldPortrayal;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.util.Double3D;
/*     */ 
/*     */ public abstract class PrimitiveEdgePortrayal3D extends SimpleEdgePortrayal3D
/*     */ {
/*     */   public static final double DEFAULT_RADIUS = 0.5D;
/*     */   public static final double DEFAULT_HEIGHT = 2.0D;
/*     */   Node edgeModelPrototype;
/*  53 */   double[] transformData = new double[16];
/*     */ 
/* 202 */   Transform3D transform = new Transform3D();
/*     */   Appearance appearance;
/* 397 */   int DEFAULT_SHAPE = 0;
/*     */ 
/* 400 */   boolean pickable = true;
/*     */ 
/*     */   public PrimitiveEdgePortrayal3D(Node model, Appearance appearance, Color labelColor, Font labelFont)
/*     */   {
/*  58 */     super(null, null, labelColor, labelFont);
/*     */ 
/*  61 */     this.transformData[12] = 0.0D;
/*  62 */     this.transformData[13] = 0.0D;
/*  63 */     this.transformData[14] = 0.0D;
/*  64 */     this.transformData[15] = 1.0D;
/*     */ 
/*  66 */     if (appearance != null) setAppearance(null, appearance);
/*  67 */     init(model);
/*     */   }
/*     */ 
/*     */   protected void init(Node model)
/*     */   {
/*  75 */     this.edgeModelPrototype = model;
/*     */   }
/*     */ 
/*     */   public TransformGroup getModel(Object object, TransformGroup j3dModel)
/*     */   {
/*  84 */     Transform3D trans = null;
/*     */ 
/*  86 */     LocationWrapper wrapper = (LocationWrapper)object;
/*  87 */     Edge edge = (Edge)wrapper.getLocation();
/*  88 */     SpatialNetwork3D field = (SpatialNetwork3D)wrapper.fieldPortrayal.getField();
/*     */ 
/*  90 */     Double3D secondPoint = field.getObjectLocation(edge.to());
/*  91 */     Double3D firstPoint = field.getObjectLocation(edge.from());
/*     */ 
/*  93 */     this.startPoint[0] = firstPoint.x;
/*  94 */     this.startPoint[1] = firstPoint.y;
/*  95 */     this.startPoint[2] = firstPoint.z;
/*     */ 
/*  97 */     this.endPoint[0] = secondPoint.x;
/*  98 */     this.endPoint[1] = secondPoint.y;
/*  99 */     this.endPoint[2] = secondPoint.z;
/*     */ 
/* 101 */     if (this.showLabels) {
/* 102 */       trans = transformForOffset((firstPoint.x + secondPoint.x) / 2.0D, (firstPoint.y + secondPoint.y) / 2.0D, (firstPoint.z + secondPoint.z) / 2.0D);
/*     */     }
/*     */ 
/* 107 */     if (j3dModel == null)
/*     */     {
/* 110 */       j3dModel = new TransformGroup();
/* 111 */       j3dModel.setCapability(14);
/* 112 */       j3dModel.setCapability(12);
/*     */ 
/* 114 */       TransformGroup tg = new TransformGroup(getTransform(this.startPoint, this.endPoint));
/*     */ 
/* 116 */       tg.setCapability(18);
/* 117 */       tg.setCapability(12);
/* 118 */       Node edgeModelClone = this.edgeModelPrototype.cloneTree(true);
/* 119 */       tg.addChild(edgeModelClone);
/*     */ 
/* 121 */       j3dModel.addChild(tg);
/*     */ 
/* 123 */       passWrapperToShapes(j3dModel, wrapper);
/*     */ 
/* 126 */       if (this.showLabels)
/*     */       {
/* 128 */         String str = getLabel(edge);
/* 129 */         Text2D text = new Text2D(str, new Color3f(this.labelColor), this.labelFont.getFamily(), this.labelFont.getSize(), this.labelFont.getStyle());
/*     */ 
/* 132 */         text.setRectangleScaleFactor((float)(this.labelScale * 0.2D));
/*     */ 
/* 136 */         OrientedShape3D o3d = new OrientedShape3D(text.getGeometry(), text.getAppearance(), 1, new Point3f(0.0F, 0.0F, 0.0F));
/*     */ 
/* 140 */         o3d.setCapability(15);
/* 141 */         o3d.setCapability(13);
/* 142 */         o3d.clearCapabilityIsFrequent(15);
/* 143 */         o3d.clearCapabilityIsFrequent(13);
/*     */ 
/* 146 */         TransformGroup o = new TransformGroup();
/* 147 */         o.setCapability(12);
/* 148 */         o.setCapability(17);
/* 149 */         o.setCapability(18);
/* 150 */         o.clearCapabilityIsFrequent(12);
/* 151 */         o.setTransform(trans);
/* 152 */         o.setUserData(str);
/*     */ 
/* 156 */         clearPickableFlags(o);
/* 157 */         o.addChild(o3d);
/* 158 */         j3dModel.addChild(o);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 163 */       TransformGroup tg0 = (TransformGroup)j3dModel.getChild(0);
/* 164 */       tg0.setTransform(getTransform(this.startPoint, this.endPoint));
/*     */ 
/* 166 */       if (this.showLabels)
/*     */       {
/* 168 */         TransformGroup tg = (TransformGroup)j3dModel.getChild(1);
/* 169 */         String str = getLabel(edge);
/*     */ 
/* 172 */         if (!tg.getUserData().equals(str))
/*     */         {
/* 175 */           Text2D text = new Text2D(str, new Color3f(this.labelColor), this.labelFont.getFamily(), this.labelFont.getSize(), this.labelFont.getStyle());
/*     */ 
/* 179 */           text.setRectangleScaleFactor((float)(this.labelScale * 0.2D));
/*     */ 
/* 184 */           OrientedShape3D o3d = (OrientedShape3D)tg.getChild(0);
/*     */ 
/* 187 */           o3d.setGeometry(text.getGeometry());
/* 188 */           o3d.setAppearance(text.getAppearance());
/*     */ 
/* 191 */           tg.setUserData(str);
/*     */         }
/*     */ 
/* 195 */         tg.setTransform(trans);
/*     */       }
/*     */     }
/*     */ 
/* 199 */     return j3dModel;
/*     */   }
/*     */ 
/*     */   Transform3D getTransform(double[] from, double[] to)
/*     */   {
/* 339 */     double fx = from[0];
/* 340 */     double fy = from[1];
/* 341 */     double fz = from[2];
/*     */ 
/* 343 */     double vx = to[0] - fx;
/* 344 */     double vy = to[1] - fy;
/* 345 */     double vz = to[2] - fz;
/*     */ 
/* 347 */     double vx2 = vx * vx;
/* 348 */     double vy2 = vy * vy;
/* 349 */     double vz2 = vz * vz;
/*     */ 
/* 351 */     double vxz2 = vx2 + vz2;
/* 352 */     double v2 = vxz2 + vy2;
/*     */ 
/* 354 */     double v = vxz2 == 0.0D ? Math.abs(vy) : Math.sqrt(v2);
/*     */ 
/* 356 */     double halfVx = this.transformData[1] = vx / 2.0D;
/* 357 */     double halfVy = this.transformData[5] = vy / 2.0D;
/* 358 */     double halfVz = this.transformData[9] = vz / 2.0D;
/*     */ 
/* 360 */     this.transformData[3] = (fx + halfVx);
/* 361 */     this.transformData[7] = (fy + halfVy);
/* 362 */     this.transformData[11] = (fz + halfVz);
/*     */ 
/* 365 */     if (vxz2 != 0.0D)
/*     */     {
/* 367 */       this.transformData[4] = (-vx / v);
/* 368 */       this.transformData[6] = (-vz / v);
/* 369 */       double vxz2v = v * vxz2;
/* 370 */       double vxvz = vx * vz;
/* 371 */       double a = (v - vy) / vxz2v;
/*     */ 
/* 373 */       this.transformData[0] = (1.0D - vx2 * a);
/* 374 */       this.transformData[10] = (1.0D - vz2 * a);
/*     */       double tmp274_273 = (-vxvz * a); this.transformData[2] = tmp274_273; this.transformData[8] = tmp274_273;
/*     */     }
/*     */     else
/*     */     {
/* 379 */       this.transformData[4] = 0.0D;
/* 380 */       this.transformData[6] = 0.0D;
/*     */       double tmp307_306 = 0.0D; this.transformData[2] = tmp307_306; this.transformData[8] = tmp307_306;
/* 382 */       this.transformData[0] = 1.0D;
/* 383 */       this.transformData[10] = (vy >= 0.0D ? 1.0D : -1.0D);
/*     */     }
/*     */ 
/* 386 */     this.transform.set(this.transformData);
/* 387 */     return this.transform;
/*     */   }
/*     */ 
/*     */   public static void setShape3DFlags(Shape3D shape)
/*     */   {
/* 405 */     shape.setCapability(15);
/* 406 */     shape.setCapability(14);
/* 407 */     shape.setCapability(12);
/* 408 */     shape.setCapability(13);
/* 409 */     shape.clearCapabilityIsFrequent(14);
/* 410 */     shape.clearCapabilityIsFrequent(15);
/* 411 */     shape.clearCapabilityIsFrequent(12);
/* 412 */     shape.clearCapabilityIsFrequent(13);
/*     */   }
/*     */ 
/*     */   protected Appearance getAppearance(TransformGroup j3dModel)
/*     */   {
/* 423 */     if (j3dModel == null)
/*     */     {
/* 425 */       Appearance a = new Appearance();
/* 426 */       setAppearanceFlags(a);
/* 427 */       return a;
/*     */     }
/* 429 */     return getShape(j3dModel, this.DEFAULT_SHAPE).getAppearance();
/*     */   }
/*     */ 
/*     */   protected void setAppearance(TransformGroup j3dModel, Appearance app)
/*     */   {
/* 440 */     if (j3dModel == null)
/*     */     {
/* 442 */       this.appearance = app;
/*     */     }
/*     */     else
/*     */     {
/* 446 */       int numShapes = numShapes();
/* 447 */       for (int i = 0; i < numShapes; i++)
/* 448 */         getShape(j3dModel, i).setAppearance(app);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract int numShapes();
/*     */ 
/*     */   protected Shape3D getShape(TransformGroup j3dModel, int shapeIndex)
/*     */   {
/* 466 */     TransformGroup g = (TransformGroup)j3dModel.getChild(0);
/* 467 */     Primitive p = (Primitive)g.getChild(0);
/* 468 */     return p.getShape(shapeIndex);
/*     */   }
/*     */ 
/*     */   public void setPickable(boolean val)
/*     */   {
/* 473 */     this.pickable = val;
/*     */   }
/*     */ 
/*     */   void passWrapperToShapes(TransformGroup j3dModel, LocationWrapper wrapper) {
/* 477 */     if (j3dModel != null)
/*     */     {
/* 479 */       int numShapes = numShapes();
/* 480 */       for (int i = 0; i < numShapes; i++)
/*     */       {
/* 482 */         Shape3D s = getShape(j3dModel, i);
/* 483 */         if (this.appearance != null)
/* 484 */           s.setAppearance(this.appearance);
/* 485 */         if (this.pickable)
/* 486 */           setPickableFlags(s);
/* 487 */         s.setUserData(wrapper);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.network.PrimitiveEdgePortrayal3D
 * JD-Core Version:    0.6.2
 */