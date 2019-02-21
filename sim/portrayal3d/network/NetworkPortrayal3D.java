/*     */ package sim.portrayal3d.network;
/*     */ 
/*     */ import com.sun.j3d.utils.picking.PickIntersection;
/*     */ import com.sun.j3d.utils.picking.PickResult;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import javax.media.j3d.BranchGroup;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import sim.field.network.Edge;
/*     */ import sim.field.network.Network;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.portrayal3d.FieldPortrayal3D;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ import sim.util.Bag;
/*     */ 
/*     */ public class NetworkPortrayal3D extends FieldPortrayal3D
/*     */ {
/*  26 */   SimpleEdgePortrayal3D defaultPortrayal = new SimpleEdgePortrayal3D();
/*     */ 
/*  27 */   public Portrayal getDefaultPortrayal() { return this.defaultPortrayal; }
/*     */ 
/*     */   public void setField(Object field)
/*     */   {
/*  31 */     if ((field instanceof SpatialNetwork3D)) super.setField(field); else
/*  32 */       throw new RuntimeException("Invalid field for FieldPortrayal3D: " + field + ".  You probably wanted a SpatialNetwork3D."); 
/*     */   }
/*     */ 
/*  35 */   public Object getField() { return this.field; }
/*     */ 
/*     */ 
/*     */   public TransformGroup createModel()
/*     */   {
/*  40 */     TransformGroup globalTG = new TransformGroup();
/*  41 */     globalTG.setCapability(12);
/*  42 */     globalTG.setCapability(13);
/*  43 */     globalTG.setCapability(14);
/*     */ 
/*  46 */     SpatialNetwork3D field = (SpatialNetwork3D)this.field;
/*     */ 
/*  48 */     if (field == null) return globalTG;
/*     */ 
/*  52 */     Bag nodes = field.network.getAllNodes();
/*  53 */     for (int x = 0; x < nodes.numObjs; x++)
/*     */     {
/*  55 */       Bag edges = field.network.getEdgesOut(nodes.objs[x]);
/*     */ 
/*  57 */       for (int y = 0; y < edges.numObjs; y++)
/*     */       {
/*  59 */         Edge edge = (Edge)edges.objs[y];
/*  60 */         globalTG.addChild(wrapModelForNewEdge(edge));
/*     */       }
/*     */     }
/*  63 */     return globalTG;
/*     */   }
/*     */ 
/*     */   protected BranchGroup wrapModelForNewEdge(Edge edge)
/*     */   {
/*  76 */     LocationWrapper newwrapper = new LocationWrapper(edge.info, edge, this);
/*     */ 
/*  78 */     Portrayal p = getPortrayalForObject(newwrapper);
/*  79 */     if (!(p instanceof SimpleEdgePortrayal3D)) {
/*  80 */       throw new RuntimeException("Unexpected Portrayal " + p + " for object " + edge + " -- expected a SimpleEdgePortrayal3D");
/*     */     }
/*  82 */     SimpleEdgePortrayal3D portrayal = (SimpleEdgePortrayal3D)p;
/*     */ 
/*  84 */     portrayal.setCurrentFieldPortrayal(this);
/*  85 */     TransformGroup localTG = portrayal.getModel(newwrapper, null);
/*  86 */     localTG.setCapability(12);
/*  87 */     localTG.setUserData(newwrapper);
/*     */ 
/*  89 */     BranchGroup localBG = new BranchGroup();
/*  90 */     localBG.setCapability(12);
/*  91 */     localBG.setCapability(17);
/*  92 */     localBG.addChild(localTG);
/*  93 */     localBG.setUserData(newwrapper);
/*     */ 
/*  95 */     return localBG;
/*     */   }
/*     */ 
/*     */   public void updateModel(TransformGroup globalTG)
/*     */   {
/* 102 */     SpatialNetwork3D field = (SpatialNetwork3D)this.field;
/*     */ 
/* 104 */     if (field == null) return;
/* 105 */     HashMap hm = new HashMap();
/* 106 */     Network net = field.network;
/* 107 */     Bag nodes = net.getAllNodes();
/* 108 */     for (int n = 0; n < nodes.numObjs; n++)
/*     */     {
/* 110 */       Bag edges = net.getEdgesOut(nodes.objs[n]);
/* 111 */       for (int i = 0; i < edges.numObjs; i++)
/*     */       {
/* 113 */         Object edge = edges.objs[i];
/* 114 */         hm.put(edge, edge);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 122 */     for (int t = globalTG.numChildren() - 1; t >= 0; t--)
/*     */     {
/* 124 */       BranchGroup localBG = (BranchGroup)globalTG.getChild(t);
/* 125 */       LocationWrapper wrapper = (LocationWrapper)localBG.getUserData();
/* 126 */       Object edge = wrapper.getLocation();
/*     */ 
/* 128 */       if (hm.remove(edge) != null)
/*     */       {
/* 130 */         TransformGroup localTG = (TransformGroup)localBG.getChild(0);
/* 131 */         Portrayal p = getPortrayalForObject(wrapper);
/* 132 */         if (!(p instanceof SimplePortrayal3D)) {
/* 133 */           throw new RuntimeException("Unexpected Portrayal " + p + " for object " + wrapper + " -- expecting a SimplePortrayal3D");
/*     */         }
/* 135 */         SimplePortrayal3D p3d = (SimplePortrayal3D)p;
/*     */ 
/* 137 */         p3d.setCurrentFieldPortrayal(this);
/* 138 */         TransformGroup localTG2 = p3d.getModel(wrapper, localTG);
/*     */ 
/* 140 */         if (localTG != localTG2)
/*     */         {
/* 142 */           localTG2.setCapability(18);
/* 143 */           localTG2.setCapability(17);
/* 144 */           localTG2.setUserData(wrapper);
/* 145 */           BranchGroup newlocalBG = new BranchGroup();
/* 146 */           newlocalBG.setCapability(12);
/* 147 */           newlocalBG.setCapability(17);
/* 148 */           newlocalBG.setUserData(wrapper);
/* 149 */           newlocalBG.addChild(localTG2);
/*     */ 
/* 151 */           globalTG.setChild(newlocalBG, t);
/*     */         }
/*     */       }
/*     */       else {
/* 155 */         globalTG.removeChild(t);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 161 */     if (!hm.isEmpty())
/*     */     {
/* 163 */       Iterator newObjs = hm.values().iterator();
/* 164 */       while (newObjs.hasNext())
/*     */       {
/* 166 */         Edge edge = (Edge)newObjs.next();
/* 167 */         globalTG.addChild(wrapModelForNewEdge(edge));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public LocationWrapper completedWrapper(LocationWrapper w, PickIntersection pi, PickResult pr)
/*     */   {
/* 175 */     return w;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.network.NetworkPortrayal3D
 * JD-Core Version:    0.6.2
 */