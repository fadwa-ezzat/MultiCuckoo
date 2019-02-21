/*     */ package sim.portrayal.network;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import java.util.HashMap;
/*     */ import sim.field.SparseField2D;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.field.network.Edge;
/*     */ import sim.field.network.Network;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.FieldPortrayal;
/*     */ import sim.portrayal.FieldPortrayal2D;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ 
/*     */ public class NetworkPortrayal2D extends FieldPortrayal2D
/*     */ {
/*  27 */   SimpleEdgePortrayal2D defaultPortrayal = new SimpleEdgePortrayal2D();
/*     */ 
/*  28 */   public Portrayal getDefaultPortrayal() { return this.defaultPortrayal; }
/*     */ 
/*     */   public void setField(Object field)
/*     */   {
/*  32 */     if ((field instanceof SpatialNetwork2D)) super.setField(field); else
/*  33 */       throw new RuntimeException("Invalid field for FieldPortrayal2D: " + field);
/*     */   }
/*     */ 
/*     */   protected void hitOrDraw(Graphics2D graphics, DrawInfo2D info, Bag putInHere)
/*     */   {
/*  38 */     SpatialNetwork2D field = (SpatialNetwork2D)this.field;
/*  39 */     if (field == null) return;
/*     */ 
/*  42 */     SparseField2D otherField = field.field2;
/*  43 */     if (otherField == null) otherField = field.field;
/*     */ 
/*  45 */     Double2D dimensions = field.field.getDimensions();
/*  46 */     double xScale = info.draw.width / dimensions.x;
/*  47 */     double yScale = info.draw.height / dimensions.y;
/*     */ 
/*  49 */     EdgeDrawInfo2D newinfo = new EdgeDrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, xScale, yScale), info.clip, new Point2D.Double(0.0D, 0.0D));
/*     */ 
/*  55 */     newinfo.fieldPortrayal = this;
/*  56 */     newinfo.precise = info.precise;
/*     */ 
/*  60 */     Bag nodes = field.network.getAllNodes();
/*  61 */     HashMap edgemap = new HashMap();
/*     */ 
/*  63 */     for (int x = 0; x < nodes.numObjs; x++)
/*     */     {
/*  65 */       Object node = nodes.objs[x];
/*  66 */       Bag edges = field.network.getEdgesOut(node);
/*  67 */       Double2D locStart = field.field.getObjectLocationAsDouble2D(node);
/*  68 */       if (locStart != null)
/*     */       {
/*  71 */         if ((field.field instanceof Continuous2D))
/*     */         {
/*  73 */           newinfo.draw.x = (info.draw.x + xScale * locStart.x);
/*  74 */           newinfo.draw.y = (info.draw.y + yScale * locStart.y);
/*     */         }
/*     */         else
/*     */         {
/*  78 */           newinfo.draw.x = ((int)Math.floor(info.draw.x + xScale * locStart.x));
/*  79 */           newinfo.draw.y = ((int)Math.floor(info.draw.y + yScale * locStart.y));
/*  80 */           double width = (int)Math.floor(info.draw.x + xScale * (locStart.x + 1.0D)) - newinfo.draw.x;
/*  81 */           double height = (int)Math.floor(info.draw.y + yScale * (locStart.y + 1.0D)) - newinfo.draw.y;
/*     */ 
/*  84 */           newinfo.draw.x += width / 2.0D;
/*  85 */           newinfo.draw.y += height / 2.0D;
/*     */         }
/*     */ 
/*  88 */         for (int y = 0; y < edges.numObjs; y++)
/*     */         {
/*  90 */           Edge edge = (Edge)edges.objs[y];
/*     */ 
/*  92 */           Double2D locStop = otherField.getObjectLocationAsDouble2D(edge.getOtherNode(node));
/*  93 */           if (locStop != null)
/*     */           {
/*  96 */             if (!field.network.isDirected())
/*     */             {
/*  98 */               if (!edgemap.containsKey(edge))
/*  99 */                 edgemap.put(edge, edge);
/*     */             }
/*     */             else
/*     */             {
/* 103 */               if ((otherField instanceof Continuous2D))
/*     */               {
/* 105 */                 newinfo.secondPoint.x = (info.draw.x + xScale * locStop.x);
/* 106 */                 newinfo.secondPoint.y = (info.draw.y + yScale * locStop.y);
/*     */               }
/*     */               else
/*     */               {
/* 110 */                 newinfo.secondPoint.x = ((int)Math.floor(info.draw.x + xScale * locStop.x));
/* 111 */                 newinfo.secondPoint.y = ((int)Math.floor(info.draw.y + yScale * locStop.y));
/* 112 */                 double width = (int)Math.floor(info.draw.x + xScale * (locStop.x + 1.0D)) - newinfo.secondPoint.x;
/* 113 */                 double height = (int)Math.floor(info.draw.y + yScale * (locStop.y + 1.0D)) - newinfo.secondPoint.y;
/*     */ 
/* 116 */                 newinfo.secondPoint.x += width / 2.0D;
/* 117 */                 newinfo.secondPoint.y += height / 2.0D;
/*     */               }
/*     */ 
/* 124 */               Portrayal p = getPortrayalForObject(edge);
/* 125 */               if (!(p instanceof SimpleEdgePortrayal2D)) {
/* 126 */                 throw new RuntimeException("Unexpected Portrayal " + p + " for object " + edge + " -- expected a SimpleEdgePortrayal2D");
/*     */               }
/* 128 */               SimpleEdgePortrayal2D portrayal = (SimpleEdgePortrayal2D)p;
/*     */ 
/* 130 */               newinfo.location = edge;
/*     */ 
/* 132 */               if (graphics == null)
/*     */               {
/* 134 */                 if (portrayal.hitObject(edge, newinfo))
/*     */                 {
/* 136 */                   putInHere.add(getWrapper(edge));
/*     */                 }
/*     */ 
/*     */               }
/*     */               else
/*     */               {
/* 143 */                 portrayal.draw(edge, graphics, newinfo);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   String edgeLocation(Edge edge)
/*     */   {
/* 154 */     if (edge == null)
/* 155 */       return "(Null)";
/* 156 */     if (edge.owner() == null)
/* 157 */       return "(Unowned)" + edge.from() + " --> " + edge.to();
/* 158 */     if (edge.owner().isDirected()) {
/* 159 */       return edge.from() + " --> " + edge.to();
/*     */     }
/* 161 */     return edge.from() + " <-> " + edge.to();
/*     */   }
/*     */ 
/*     */   public LocationWrapper getWrapper(Edge edge)
/*     */   {
/* 168 */     final SpatialNetwork2D field = (SpatialNetwork2D)this.field;
/* 169 */     return new LocationWrapper(edge.info, edge, this)
/*     */     {
/*     */       public String getLocationName()
/*     */       {
/* 173 */         Edge edge = (Edge)getLocation();
/* 174 */         if ((field != null) && (field.network != null))
/*     */         {
/* 177 */           Bag b = field.network.getEdgesOut(edge.from());
/*     */ 
/* 179 */           for (int x = 0; x < b.numObjs; x++)
/* 180 */             if (b.objs[x] == edge)
/*     */             {
/* 182 */               return NetworkPortrayal2D.this.edgeLocation(edge);
/*     */             }
/*     */         }
/* 184 */         return "Gone.  Was: " + NetworkPortrayal2D.this.edgeLocation(edge);
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.network.NetworkPortrayal2D
 * JD-Core Version:    0.6.2
 */