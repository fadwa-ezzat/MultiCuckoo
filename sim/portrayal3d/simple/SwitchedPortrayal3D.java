/*     */ package sim.portrayal3d.simple;
/*     */ 
/*     */ import javax.media.j3d.PolygonAttributes;
/*     */ import javax.media.j3d.Switch;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import sim.display.GUIState;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal3d.FieldPortrayal3D;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ import sim.util.Valuable;
/*     */ 
/*     */ public class SwitchedPortrayal3D extends SimplePortrayal3D
/*     */ {
/*     */   SimplePortrayal3D child;
/*     */ 
/*     */   public SwitchedPortrayal3D(SimplePortrayal3D child)
/*     */   {
/*  23 */     this.child = child;
/*     */   }
/*     */ 
/*     */   public PolygonAttributes polygonAttributes()
/*     */   {
/*  28 */     return this.child.polygonAttributes();
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/*  33 */     return this.child.getInspector(wrapper, state);
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/*  38 */     return this.child.getName(wrapper);
/*     */   }
/*     */ 
/*     */   public void setCurrentDisplay(Display3D display)
/*     */   {
/*  44 */     super.setCurrentDisplay(display);
/*  45 */     this.child.setCurrentDisplay(display);
/*     */   }
/*     */ 
/*     */   public void setCurrentFieldPortrayal(FieldPortrayal3D p)
/*     */   {
/*  51 */     super.setCurrentFieldPortrayal(p);
/*  52 */     this.child.setCurrentFieldPortrayal(p);
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/*  57 */     return this.child.setSelected(wrapper, selected);
/*     */   }
/*     */ 
/*     */   public SimplePortrayal3D getChild(Object object)
/*     */   {
/*  62 */     if (this.child != null) return this.child;
/*     */ 
/*  65 */     if (!(object instanceof SimplePortrayal3D))
/*  66 */       throw new RuntimeException("Object provided to SwitchedPortrayal3D is not a SimplePortrayal3D: " + object);
/*  67 */     return (SimplePortrayal3D)object;
/*     */   }
/*     */ 
/*     */   public boolean getShowsChild(Object obj)
/*     */   {
/*  73 */     if (obj == null) return true;
/*  74 */     if ((obj instanceof Number))
/*     */     {
/*  76 */       return ((Number)obj).doubleValue() != 0.0D;
/*     */     }
/*  78 */     if ((obj instanceof Valuable))
/*     */     {
/*  80 */       return ((Valuable)obj).doubleValue() != 0.0D;
/*     */     }
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */   public TransformGroup getModel(Object obj, TransformGroup previousTransformGroup)
/*     */   {
/*     */     Switch internalSwitch;
/*  89 */     if (previousTransformGroup == null)
/*     */     {
/*  91 */       TransformGroup internalTransformGroup = getChild(obj).getModel(obj, null);
/*  92 */       internalTransformGroup.setCapability(12);
/*  93 */       internalTransformGroup.clearCapabilityIsFrequent(12);
/*     */ 
/*  95 */       Switch internalSwitch = new Switch();
/*  96 */       internalSwitch.addChild(internalTransformGroup);
/*  97 */       internalSwitch.setCapability(17);
/*  98 */       internalSwitch.setCapability(18);
/*  99 */       internalSwitch.setCapability(12);
/* 100 */       internalSwitch.clearCapabilityIsFrequent(12);
/*     */ 
/* 102 */       previousTransformGroup = new TransformGroup();
/* 103 */       previousTransformGroup.setCapability(12);
/* 104 */       previousTransformGroup.clearCapabilityIsFrequent(12);
/* 105 */       previousTransformGroup.addChild(internalSwitch);
/*     */     }
/*     */     else
/*     */     {
/* 109 */       internalSwitch = (Switch)previousTransformGroup.getChild(0);
/* 110 */       TransformGroup internalTransformGroup = (TransformGroup)internalSwitch.getChild(0);
/* 111 */       getChild(obj).getModel(obj, internalTransformGroup);
/*     */     }
/*     */ 
/* 114 */     internalSwitch.setWhichChild(getShowsChild(obj) ? -2 : -1);
/* 115 */     return previousTransformGroup;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.SwitchedPortrayal3D
 * JD-Core Version:    0.6.2
 */