/*     */ package sim.portrayal3d.simple;
/*     */ 
/*     */ import javax.media.j3d.PolygonAttributes;
/*     */ import javax.media.j3d.Transform3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.vecmath.Vector3d;
/*     */ import sim.display.GUIState;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal3d.FieldPortrayal3D;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ 
/*     */ public class TransformedPortrayal3D extends SimplePortrayal3D
/*     */ {
/*     */   SimplePortrayal3D child;
/*     */   Transform3D internalTransform;
/* 122 */   boolean updateInternalTransform = false;
/*     */ 
/*     */   public TransformedPortrayal3D(SimplePortrayal3D child, Transform3D transform)
/*     */   {
/*  40 */     this.child = child;
/*  41 */     setTransform(transform);
/*     */   }
/*     */ 
/*     */   public TransformedPortrayal3D(SimplePortrayal3D child)
/*     */   {
/*  46 */     this(child, null);
/*     */   }
/*     */ 
/*     */   public PolygonAttributes polygonAttributes()
/*     */   {
/*  51 */     return this.child.polygonAttributes();
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/*  56 */     return this.child.getInspector(wrapper, state);
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/*  61 */     return this.child.getName(wrapper);
/*     */   }
/*     */ 
/*     */   public void setCurrentDisplay(Display3D display)
/*     */   {
/*  67 */     super.setCurrentDisplay(display);
/*  68 */     this.child.setCurrentDisplay(display);
/*     */   }
/*     */ 
/*     */   public void setCurrentFieldPortrayal(FieldPortrayal3D p)
/*     */   {
/*  74 */     super.setCurrentFieldPortrayal(p);
/*  75 */     this.child.setCurrentFieldPortrayal(p);
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/*  80 */     return this.child.setSelected(wrapper, selected);
/*     */   }
/*     */ 
/*     */   public SimplePortrayal3D getChild(Object object)
/*     */   {
/*  85 */     if (this.child != null) return this.child;
/*     */ 
/*  88 */     if (!(object instanceof SimplePortrayal3D))
/*  89 */       throw new RuntimeException("Object provided to TransformedPortrayal3D is not a SimplePortrayal3D: " + object);
/*  90 */     return (SimplePortrayal3D)object;
/*     */   }
/*     */ 
/*     */   public TransformGroup getModel(Object obj, TransformGroup previousTransformGroup)
/*     */   {
/*     */     TransformGroup internalTransformGroup;
/*  97 */     if (previousTransformGroup == null)
/*     */     {
/*  99 */       TransformGroup internalTransformGroup = getChild(obj).getModel(obj, null);
/* 100 */       internalTransformGroup.setCapability(18);
/* 101 */       internalTransformGroup.setCapability(12);
/* 102 */       internalTransformGroup.setCapability(13);
/* 103 */       internalTransformGroup.clearCapabilityIsFrequent(12);
/* 104 */       internalTransformGroup.clearCapabilityIsFrequent(13);
/* 105 */       this.updateInternalTransform = true;
/* 106 */       previousTransformGroup = new TransformGroup();
/* 107 */       previousTransformGroup.setCapability(12);
/* 108 */       previousTransformGroup.clearCapabilityIsFrequent(12);
/* 109 */       previousTransformGroup.addChild(internalTransformGroup);
/*     */     }
/*     */     else
/*     */     {
/* 113 */       internalTransformGroup = (TransformGroup)previousTransformGroup.getChild(0);
/* 114 */       getChild(obj).getModel(obj, internalTransformGroup);
/*     */     }
/* 116 */     if (this.updateInternalTransform)
/* 117 */       internalTransformGroup.setTransform(this.internalTransform);
/* 118 */     return previousTransformGroup;
/*     */   }
/*     */ 
/*     */   public void setTransform(Transform3D transform)
/*     */   {
/* 130 */     if (transform == null) this.internalTransform = getDefaultTransform(); else
/* 131 */       this.internalTransform = new Transform3D(transform);
/* 132 */     this.updateInternalTransform = true;
/*     */   }
/*     */ 
/*     */   public Transform3D getDefaultTransform()
/*     */   {
/* 138 */     return new Transform3D();
/*     */   }
/*     */ 
/*     */   public Transform3D getTransform()
/*     */   {
/* 144 */     return new Transform3D(this.internalTransform);
/*     */   }
/*     */ 
/*     */   public void transform(Transform3D transform)
/*     */   {
/* 151 */     Transform3D current = getTransform();
/* 152 */     current.mul(transform, current);
/* 153 */     setTransform(current);
/*     */   }
/*     */ 
/*     */   public void resetTransform()
/*     */   {
/* 159 */     setTransform(getDefaultTransform());
/*     */   }
/*     */ 
/*     */   public void rotateX(double degrees)
/*     */   {
/* 165 */     Transform3D other = new Transform3D();
/* 166 */     other.rotX(degrees * 3.141592653589793D / 180.0D);
/* 167 */     transform(other);
/*     */   }
/*     */ 
/*     */   public void rotateY(double degrees)
/*     */   {
/* 173 */     Transform3D other = new Transform3D();
/* 174 */     other.rotY(degrees * 3.141592653589793D / 180.0D);
/* 175 */     transform(other);
/*     */   }
/*     */ 
/*     */   public void rotateZ(double degrees)
/*     */   {
/* 181 */     Transform3D other = new Transform3D();
/* 182 */     other.rotZ(degrees * 3.141592653589793D / 180.0D);
/* 183 */     transform(other);
/*     */   }
/*     */ 
/*     */   public void translate(double dx, double dy, double dz)
/*     */   {
/* 189 */     Transform3D other = new Transform3D();
/* 190 */     other.setTranslation(new Vector3d(dx, dy, dz));
/* 191 */     transform(other);
/*     */   }
/*     */ 
/*     */   public void scale(double value)
/*     */   {
/* 197 */     Transform3D other = new Transform3D();
/* 198 */     other.setScale(value);
/* 199 */     transform(other);
/*     */   }
/*     */ 
/*     */   public void scale(double sx, double sy, double sz)
/*     */   {
/* 205 */     Transform3D other = new Transform3D();
/* 206 */     other.setScale(new Vector3d(sx, sy, sz));
/* 207 */     transform(other);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.TransformedPortrayal3D
 * JD-Core Version:    0.6.2
 */