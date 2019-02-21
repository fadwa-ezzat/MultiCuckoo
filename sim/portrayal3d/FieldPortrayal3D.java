/*     */ package sim.portrayal3d;
/*     */ 
/*     */ import com.sun.j3d.utils.picking.PickIntersection;
/*     */ import com.sun.j3d.utils.picking.PickResult;
/*     */ import javax.media.j3d.PolygonAttributes;
/*     */ import javax.media.j3d.Transform3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.vecmath.Vector3d;
/*     */ import sim.display.GUIState;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.portrayal.FieldPortrayal;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.portrayal3d.simple.SpherePortrayal3D;
/*     */ 
/*     */ public abstract class FieldPortrayal3D extends FieldPortrayal
/*     */   implements Portrayal3D
/*     */ {
/*     */   Transform3D internalTransform;
/*  51 */   boolean updateInternalTransform = false;
/*     */ 
/*  53 */   Display3D display = null;
/*     */ 
/* 170 */   SimplePortrayal3D defaultPortrayal = new SpherePortrayal3D();
/*     */ 
/*     */   public PolygonAttributes polygonAttributes()
/*     */   {
/*  48 */     return null;
/*     */   }
/*     */ 
/*     */   public void setCurrentDisplay(Display3D display)
/*     */   {
/*  56 */     this.display = display;
/*     */   }
/*     */ 
/*     */   public Display3D getCurrentDisplay()
/*     */   {
/*  61 */     return this.display;
/*     */   }
/*     */ 
/*     */   public GUIState getCurrentGUIState()
/*     */   {
/*  66 */     Display3D d = getCurrentDisplay();
/*  67 */     return d == null ? null : d.getSimulation();
/*     */   }
/*     */ 
/*     */   public void setTransform(Transform3D transform)
/*     */   {
/*  76 */     if (transform == null) this.internalTransform = getDefaultTransform(); else
/*  77 */       this.internalTransform = new Transform3D(transform);
/*  78 */     this.updateInternalTransform = true;
/*     */   }
/*     */ 
/*     */   protected Transform3D getDefaultTransform()
/*     */   {
/*  84 */     return new Transform3D();
/*     */   }
/*     */ 
/*     */   public Transform3D getTransform()
/*     */   {
/*  90 */     return new Transform3D(this.internalTransform);
/*     */   }
/*     */ 
/*     */   public void transform(Transform3D transform)
/*     */   {
/*  97 */     Transform3D current = getTransform();
/*  98 */     current.mul(transform, current);
/*  99 */     setTransform(current);
/*     */   }
/*     */ 
/*     */   public void resetTransform()
/*     */   {
/* 105 */     setTransform(getDefaultTransform());
/*     */   }
/*     */ 
/*     */   public void rotateX(double degrees)
/*     */   {
/* 111 */     Transform3D other = new Transform3D();
/* 112 */     other.rotX(degrees * 3.141592653589793D / 180.0D);
/* 113 */     transform(other);
/*     */   }
/*     */ 
/*     */   public void rotateY(double degrees)
/*     */   {
/* 119 */     Transform3D other = new Transform3D();
/* 120 */     other.rotY(degrees * 3.141592653589793D / 180.0D);
/* 121 */     transform(other);
/*     */   }
/*     */ 
/*     */   public void rotateZ(double degrees)
/*     */   {
/* 127 */     Transform3D other = new Transform3D();
/* 128 */     other.rotZ(degrees * 3.141592653589793D / 180.0D);
/* 129 */     transform(other);
/*     */   }
/*     */ 
/*     */   public void translate(double dx, double dy, double dz)
/*     */   {
/* 135 */     Transform3D other = new Transform3D();
/* 136 */     other.setTranslation(new Vector3d(dx, dy, dz));
/* 137 */     transform(other);
/*     */   }
/*     */ 
/*     */   public void scale(double value)
/*     */   {
/* 143 */     Transform3D other = new Transform3D();
/* 144 */     other.setScale(value);
/* 145 */     transform(other);
/*     */   }
/*     */ 
/*     */   public void scale(double sx, double sy, double sz)
/*     */   {
/* 151 */     Transform3D other = new Transform3D();
/* 152 */     other.setScale(new Vector3d(sx, sy, sz));
/* 153 */     transform(other);
/*     */   }
/*     */   public FieldPortrayal3D() {
/* 156 */     this(null);
/*     */   }
/*     */ 
/*     */   public FieldPortrayal3D(Transform3D transform) {
/* 160 */     setTransform(transform);
/*     */   }
/*     */ 
/*     */   public Portrayal getDefaultPortrayal()
/*     */   {
/* 174 */     return this.defaultPortrayal;
/*     */   }
/*     */ 
/*     */   public TransformGroup getModel(Object obj, TransformGroup previousTransformGroup)
/*     */   {
/*     */     TransformGroup internalTransformGroup;
/* 188 */     if (previousTransformGroup == null)
/*     */     {
/* 190 */       TransformGroup internalTransformGroup = createModel();
/* 191 */       internalTransformGroup.setCapability(18);
/* 192 */       this.updateInternalTransform = true;
/* 193 */       previousTransformGroup = new TransformGroup();
/* 194 */       previousTransformGroup.setCapability(12);
/* 195 */       previousTransformGroup.clearCapabilityIsFrequent(12);
/* 196 */       previousTransformGroup.addChild(internalTransformGroup);
/*     */     }
/*     */     else
/*     */     {
/* 200 */       internalTransformGroup = (TransformGroup)previousTransformGroup.getChild(0);
/* 201 */       if ((!this.immutableField) || (isDirtyField())) updateModel(internalTransformGroup);
/*     */     }
/* 203 */     if (this.updateInternalTransform)
/* 204 */       internalTransformGroup.setTransform(this.internalTransform);
/* 205 */     setDirtyField(false);
/* 206 */     return previousTransformGroup;
/*     */   }
/*     */ 
/*     */   protected abstract TransformGroup createModel();
/*     */ 
/*     */   protected void updateModel(TransformGroup previousTransformGroup)
/*     */   {
/*     */   }
/*     */ 
/*     */   public abstract LocationWrapper completedWrapper(LocationWrapper paramLocationWrapper, PickIntersection paramPickIntersection, PickResult paramPickResult);
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.FieldPortrayal3D
 * JD-Core Version:    0.6.2
 */