/*     */ package sim.portrayal3d.simple;
/*     */ 
/*     */ import javax.media.j3d.Link;
/*     */ import javax.media.j3d.PolygonAttributes;
/*     */ import javax.media.j3d.SharedGroup;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import sim.display.GUIState;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal3d.FieldPortrayal3D;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ 
/*     */ public class SharedPortrayal3D extends SimplePortrayal3D
/*     */ {
/*     */   SimplePortrayal3D child;
/*  18 */   SharedGroup group = null;
/*     */ 
/*     */   public void setCurrentDisplay(Display3D display)
/*     */   {
/*  23 */     super.setCurrentDisplay(display);
/*  24 */     this.child.setCurrentDisplay(display);
/*     */   }
/*     */ 
/*     */   public void setCurrentFieldPortrayal(FieldPortrayal3D p)
/*     */   {
/*  30 */     super.setCurrentFieldPortrayal(p);
/*  31 */     this.child.setCurrentFieldPortrayal(p);
/*     */   }
/*     */ 
/*     */   public SharedPortrayal3D(SimplePortrayal3D child)
/*     */   {
/*  36 */     this.child = child;
/*     */   }
/*     */ 
/*     */   public PolygonAttributes polygonAttributes()
/*     */   {
/*  41 */     return this.child.polygonAttributes();
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/*  47 */     return this.child.getInspector(wrapper, state);
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/*  53 */     return this.child.getName(wrapper);
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/*  59 */     if (this.child.setSelected(wrapper, selected))
/*  60 */       return super.setSelected(wrapper, selected);
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */   public SimplePortrayal3D getChild(Object object)
/*     */   {
/*  66 */     if (this.child != null) return this.child;
/*     */ 
/*  69 */     if (!(object instanceof SimplePortrayal3D))
/*  70 */       throw new RuntimeException("Object provided to TransformedPortrayal3D is not a SimplePortrayal3D: " + object);
/*  71 */     return (SimplePortrayal3D)object;
/*     */   }
/*     */ 
/*     */   public TransformGroup getModel(Object obj, TransformGroup j3dModel)
/*     */   {
/*  77 */     if (j3dModel == null)
/*     */     {
/*  80 */       if (this.group == null)
/*     */       {
/*  82 */         this.group = new SharedGroup();
/*  83 */         this.group.addChild(this.child.getModel(obj, null));
/*     */       }
/*     */ 
/*  86 */       j3dModel = new TransformGroup();
/*  87 */       j3dModel.setCapability(12);
/*     */ 
/*  90 */       Link link = new Link(this.group);
/*  91 */       link.setCapability(12);
/*  92 */       link.clearCapabilityIsFrequent(12);
/*     */ 
/*  95 */       clearPickableFlags(link);
/*     */ 
/* 101 */       LocationWrapper pickI = new LocationWrapper(obj, null, getCurrentFieldPortrayal());
/*     */ 
/* 103 */       link.setUserData(pickI);
/*     */ 
/* 105 */       j3dModel.addChild(link);
/*     */     }
/* 107 */     return j3dModel;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.SharedPortrayal3D
 * JD-Core Version:    0.6.2
 */