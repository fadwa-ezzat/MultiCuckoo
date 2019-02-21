/*     */ package sim.portrayal.simple;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.io.PrintStream;
/*     */ import sim.display.GUIState;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.util.Valuable;
/*     */ 
/*     */ public class FacetedPortrayal2D extends SimplePortrayal2D
/*     */ {
/*     */   public SimplePortrayal2D[] children;
/*  25 */   boolean portrayAllChildren = false;
/*     */   boolean errorThrown;
/*     */ 
/*     */   public FacetedPortrayal2D(SimplePortrayal2D[] children, boolean portrayAllChildren)
/*     */   {
/*  32 */     this.children = children;
/*  33 */     this.portrayAllChildren = portrayAllChildren;
/*     */   }
/*     */ 
/*     */   public FacetedPortrayal2D(SimplePortrayal2D[] children)
/*     */   {
/*  40 */     this(children, false);
/*     */   }
/*     */ 
/*     */   public int getChildIndex(Object object, int numIndices)
/*     */   {
/*  51 */     int element = 0;
/*  52 */     if ((object instanceof Number))
/*  53 */       element = (int)((Number)object).doubleValue();
/*  54 */     else if ((object instanceof Valuable))
/*  55 */       element = (int)((Valuable)object).doubleValue();
/*  56 */     if ((element < 0) || (element >= this.children.length))
/*     */     {
/*  58 */       if (!this.errorThrown)
/*     */       {
/*  60 */         this.errorThrown = true;
/*  61 */         System.err.println("WARNING: FacetedPortrayal was given a value that doesn't correspond to any array element.");
/*     */       }
/*  63 */       element = 0;
/*     */     }
/*  65 */     return element;
/*     */   }
/*     */ 
/*     */   SimplePortrayal2D getChild(Object object)
/*     */   {
/*  70 */     int element = getChildIndex(object, this.children.length);
/*  71 */     if (this.children[element] == null) {
/*  72 */       if ((object instanceof SimplePortrayal2D))
/*  73 */         return (SimplePortrayal2D)object;
/*  74 */       throw new RuntimeException("FacetedPortrayal had a null child but the object is not itself a SimplePortrayal2D");
/*  75 */     }return this.children[element];
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/*  80 */     if (this.portrayAllChildren)
/*  81 */       for (int i = 0; i < this.children.length; i++)
/*  82 */         this.children[i].draw(object, graphics, info);
/*     */     else
/*  84 */       getChild(object).draw(object, graphics, info);
/*     */   }
/*     */ 
/*     */   public boolean hitObject(Object object, DrawInfo2D range)
/*     */   {
/*  89 */     if (this.portrayAllChildren)
/*     */     {
/*  91 */       for (int i = 0; i < this.children.length; i++)
/*  92 */         if (this.children[i].hitObject(object, range))
/*  93 */           return true;
/*  94 */       return false;
/*     */     }
/*     */ 
/*  97 */     return getChild(object).hitObject(object, range);
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/* 103 */     if (this.portrayAllChildren)
/*     */     {
/* 105 */       for (int i = 0; i < this.children.length; i++)
/* 106 */         if (this.children[i].setSelected(wrapper, selected))
/* 107 */           return true;
/* 108 */       return false;
/*     */     }
/*     */ 
/* 111 */     return getChild(wrapper.getObject()).setSelected(wrapper, selected);
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/* 117 */     if (this.portrayAllChildren) {
/* 118 */       return this.children[0].getInspector(wrapper, state);
/*     */     }
/* 120 */     return getChild(wrapper.getObject()).getInspector(wrapper, state);
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/* 126 */     if (this.portrayAllChildren) {
/* 127 */       return this.children[0].getName(wrapper);
/*     */     }
/* 129 */     return getChild(wrapper.getObject()).getName(wrapper);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.simple.FacetedPortrayal2D
 * JD-Core Version:    0.6.2
 */