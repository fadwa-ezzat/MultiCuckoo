/*     */ package sim.portrayal3d.network;
/*     */ 
/*     */ import com.sun.j3d.utils.geometry.Text2D;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import javax.media.j3d.LineArray;
/*     */ import javax.media.j3d.OrientedShape3D;
/*     */ import javax.media.j3d.Shape3D;
/*     */ import javax.media.j3d.Transform3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.vecmath.Color3f;
/*     */ import javax.vecmath.Point3f;
/*     */ import javax.vecmath.Vector3d;
/*     */ import sim.field.network.Edge;
/*     */ import sim.portrayal.FieldPortrayal;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ import sim.util.Double3D;
/*     */ 
/*     */ public class SimpleEdgePortrayal3D extends SimplePortrayal3D
/*     */ {
/*     */   Color fromColor;
/*     */   Color toColor;
/*     */   Color labelColor;
/*     */   Font labelFont;
/*     */   boolean showLabels;
/*     */   static final int FONT_SIZE = 18;
/*     */   static final double SCALING_MODIFIER = 0.2D;
/*  36 */   double labelScale = 1.0D;
/*     */ 
/* 106 */   double[] startPoint = new double[3];
/* 107 */   double[] endPoint = new double[3];
/* 108 */   double[] middlePoint = new double[3];
/*     */ 
/*     */   public double getLabelScale()
/*     */   {
/*  37 */     return this.labelScale; } 
/*  38 */   public void setLabelScale(double s) { this.labelScale = Math.abs(s); } 
/*     */   /** @deprecated */
/*     */   public void setShowLabels(boolean val) {
/*  41 */     this.showLabels = val;
/*     */   }
/*  44 */   /** @deprecated */
/*     */   public boolean getShowLabels() { return this.showLabels; }
/*     */ 
/*     */   public SimpleEdgePortrayal3D()
/*     */   {
/*  48 */     this(Color.gray, Color.gray, Color.white, null);
/*     */   }
/*     */ 
/*     */   public SimpleEdgePortrayal3D(Color edgeColor, Color labelColor)
/*     */   {
/*  53 */     this(edgeColor, edgeColor, labelColor, null);
/*     */   }
/*     */ 
/*     */   public SimpleEdgePortrayal3D(Color edgeColor, Color labelColor, Font labelFont)
/*     */   {
/*  58 */     this(edgeColor, edgeColor, labelColor, labelFont);
/*     */   }
/*     */ 
/*     */   public SimpleEdgePortrayal3D(Color fromColor, Color toColor, Color labelColor)
/*     */   {
/*  63 */     this(fromColor, toColor, labelColor, null);
/*     */   }
/*     */ 
/*     */   public SimpleEdgePortrayal3D(Color fromColor, Color toColor, Color labelColor, Font labelFont)
/*     */   {
/*  72 */     this.fromColor = fromColor;
/*  73 */     this.toColor = toColor;
/*  74 */     this.labelColor = labelColor;
/*  75 */     if (labelFont == null)
/*  76 */       labelFont = new Font("SansSerif", 0, 18);
/*  77 */     this.labelFont = labelFont;
/*     */ 
/*  79 */     this.showLabels = (labelColor != null);
/*  80 */     if (this.labelColor == null)
/*  81 */       this.labelColor = Color.white;
/*     */   }
/*     */ 
/*     */   Transform3D transformForOffset(double x, double y, double z)
/*     */   {
/*  86 */     Transform3D offset = new Transform3D();
/*  87 */     offset.setTranslation(new Vector3d(x, y, z));
/*  88 */     return offset;
/*     */   }
/*     */ 
/*     */   public String getLabel(Edge edge)
/*     */   {
/*  99 */     Object obj = edge.info;
/* 100 */     if (obj == null)
/* 101 */       return "";
/* 102 */     return "" + obj;
/*     */   }
/*     */ 
/*     */   public TransformGroup getModel(Object object, TransformGroup j3dModel)
/*     */   {
/* 116 */     Transform3D trans = null;
/*     */ 
/* 118 */     LocationWrapper wrapper = (LocationWrapper)object;
/* 119 */     Edge edge = (Edge)wrapper.getLocation();
/* 120 */     SpatialNetwork3D field = (SpatialNetwork3D)wrapper.fieldPortrayal.getField();
/*     */ 
/* 122 */     Double3D secondPoint = field.getObjectLocation(edge.to());
/* 123 */     Double3D firstPoint = field.getObjectLocation(edge.from());
/*     */ 
/* 125 */     this.startPoint[0] = firstPoint.x;
/* 126 */     this.startPoint[1] = firstPoint.y;
/* 127 */     this.startPoint[2] = firstPoint.z;
/* 128 */     this.endPoint[0] = secondPoint.x;
/* 129 */     this.endPoint[1] = secondPoint.y;
/* 130 */     this.endPoint[2] = secondPoint.z;
/*     */ 
/* 132 */     this.middlePoint[0] = ((secondPoint.x + firstPoint.x) / 2.0D);
/* 133 */     this.middlePoint[1] = ((secondPoint.y + firstPoint.y) / 2.0D);
/* 134 */     this.middlePoint[2] = ((secondPoint.z + firstPoint.z) / 2.0D);
/* 135 */     if (this.showLabels) {
/* 136 */       trans = transformForOffset(this.middlePoint[0], this.middlePoint[1], this.middlePoint[2]);
/*     */     }
/* 138 */     if (j3dModel == null)
/*     */     {
/* 141 */       j3dModel = new TransformGroup();
/* 142 */       j3dModel.setCapability(14);
/*     */ 
/* 144 */       LineArray lineGeometry1 = new LineArray(2, 1);
/* 145 */       lineGeometry1.setCoordinate(0, this.startPoint);
/* 146 */       lineGeometry1.setCoordinate(1, this.middlePoint);
/* 147 */       lineGeometry1.setCapability(1);
/* 148 */       Shape3D lineShape1 = new Shape3D(lineGeometry1, SimplePortrayal3D.appearanceForColor(this.fromColor));
/* 149 */       lineShape1.setCapability(13);
/* 150 */       setPickableFlags(lineShape1);
/* 151 */       lineShape1.setUserData(wrapper);
/* 152 */       j3dModel.addChild(lineShape1);
/*     */ 
/* 154 */       LineArray lineGeometry2 = new LineArray(2, 1);
/* 155 */       lineGeometry2.setCoordinate(0, this.middlePoint);
/* 156 */       lineGeometry2.setCoordinate(1, this.endPoint);
/* 157 */       lineGeometry2.setCapability(1);
/* 158 */       Shape3D lineShape2 = new Shape3D(lineGeometry2, SimplePortrayal3D.appearanceForColor(this.toColor));
/* 159 */       lineShape2.setCapability(13);
/* 160 */       setPickableFlags(lineShape2);
/* 161 */       lineShape2.setUserData(wrapper);
/* 162 */       j3dModel.addChild(lineShape2);
/*     */ 
/* 166 */       if (this.showLabels)
/*     */       {
/* 168 */         String str = getLabel(edge);
/* 169 */         Text2D text = new Text2D(str, new Color3f(this.labelColor), this.labelFont.getFamily(), this.labelFont.getSize(), this.labelFont.getStyle());
/*     */ 
/* 172 */         text.setRectangleScaleFactor((float)(this.labelScale * 0.2D));
/*     */ 
/* 176 */         OrientedShape3D o3d = new OrientedShape3D(text.getGeometry(), text.getAppearance(), 1, new Point3f(0.0F, 0.0F, 0.0F));
/*     */ 
/* 180 */         o3d.setCapability(15);
/* 181 */         o3d.setCapability(13);
/* 182 */         o3d.clearCapabilityIsFrequent(15);
/* 183 */         o3d.clearCapabilityIsFrequent(13);
/*     */ 
/* 186 */         TransformGroup o = new TransformGroup();
/* 187 */         o.setCapability(12);
/* 188 */         o.setCapability(17);
/* 189 */         o.setCapability(18);
/* 190 */         o.clearCapabilityIsFrequent(12);
/* 191 */         o.setTransform(trans);
/* 192 */         o.setUserData(str);
/*     */ 
/* 196 */         clearPickableFlags(o);
/* 197 */         o.addChild(o3d);
/* 198 */         j3dModel.addChild(o);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 203 */       Shape3D shape = (Shape3D)j3dModel.getChild(0);
/* 204 */       LineArray geo = (LineArray)shape.getGeometry();
/* 205 */       geo.setCoordinate(0, this.startPoint);
/* 206 */       geo.setCoordinate(1, this.middlePoint);
/*     */ 
/* 208 */       shape = (Shape3D)j3dModel.getChild(1);
/* 209 */       geo = (LineArray)shape.getGeometry();
/* 210 */       geo.setCoordinate(0, this.startPoint);
/* 211 */       geo.setCoordinate(1, this.endPoint);
/*     */ 
/* 213 */       if (this.showLabels)
/*     */       {
/* 215 */         TransformGroup tg = (TransformGroup)j3dModel.getChild(2);
/* 216 */         String str = getLabel(edge);
/*     */ 
/* 219 */         if (!tg.getUserData().equals(str))
/*     */         {
/* 222 */           Text2D text = new Text2D(str, new Color3f(this.labelColor), this.labelFont.getFamily(), this.labelFont.getSize(), this.labelFont.getStyle());
/*     */ 
/* 226 */           text.setRectangleScaleFactor((float)(this.labelScale * 0.2D));
/*     */ 
/* 231 */           OrientedShape3D o3d = (OrientedShape3D)tg.getChild(0);
/*     */ 
/* 234 */           o3d.setGeometry(text.getGeometry());
/* 235 */           o3d.setAppearance(text.getAppearance());
/*     */ 
/* 238 */           tg.setUserData(str);
/*     */         }
/*     */ 
/* 242 */         tg.setTransform(trans);
/*     */       }
/*     */     }
/*     */ 
/* 246 */     return j3dModel;
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/* 252 */     return "Edge: " + super.getName(wrapper);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.network.SimpleEdgePortrayal3D
 * JD-Core Version:    0.6.2
 */