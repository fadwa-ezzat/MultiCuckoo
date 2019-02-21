/*     */ package sim.portrayal.simple;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.display.GUIState;
/*     */ import sim.display.Manipulating2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ 
/*     */ public class LabelledPortrayal2D extends SimplePortrayal2D
/*     */ {
/*     */   public static final double DEFAULT_SCALE_X = 0.0D;
/*     */   public static final double DEFAULT_SCALE_Y = 0.5D;
/*     */   public static final double DEFAULT_OFFSET_X = 0.0D;
/*     */   public static final double DEFAULT_OFFSET_Y = 10.0D;
/*     */   public static final int ALIGN_CENTER = 0;
/*     */   public static final int ALIGN_LEFT = 1;
/*     */   public static final int ALIGN_RIGHT = -1;
/*     */   public double scalex;
/*     */   public double scaley;
/*     */   public double offsetx;
/*     */   public double offsety;
/*     */   public int align;
/*     */   public Font font;
/*     */   public Paint paint;
/*     */   public String label;
/*     */   public SimplePortrayal2D child;
/*  90 */   boolean showLabel = true;
/*     */   public boolean onlyLabelWhenSelected;
/*     */   Font scaledFont;
/* 101 */   int labelScaling = 0;
/*     */   public static final int NEVER_SCALE = 0;
/*     */   public static final int SCALE_WHEN_SMALLER = 1;
/*     */   public static final int ALWAYS_SCALE = 2;
/*     */ 
/*     */   public void setOnlyLabelWhenSelected(boolean val)
/*     */   {
/*  94 */     this.onlyLabelWhenSelected = val; } 
/*  95 */   public boolean getOnlyLabelWhenSelected() { return this.onlyLabelWhenSelected; } 
/*     */   public boolean isLabelShowing() {
/*  97 */     return this.showLabel; } 
/*  98 */   public void setLabelShowing(boolean val) { this.showLabel = val; }
/*     */ 
/*     */ 
/*     */   public int getLabelScaling()
/*     */   {
/* 106 */     return this.labelScaling; } 
/* 107 */   public void setLabelScaling(int val) { if ((val >= 0) && (val <= 2)) this.labelScaling = val;
/*     */   }
/*     */ 
/*     */   public LabelledPortrayal2D(SimplePortrayal2D child, double offsetx, double offsety, double scalex, double scaley, Font font, int align, String label, Paint paint, boolean onlyLabelWhenSelected)
/*     */   {
/* 116 */     this.offsetx = offsetx;
/* 117 */     this.offsety = offsety;
/* 118 */     this.scalex = scalex;
/* 119 */     this.scaley = scaley;
/* 120 */     this.font = font;
/* 121 */     this.align = align;
/* 122 */     this.label = label;
/* 123 */     this.child = child;
/* 124 */     this.paint = paint;
/* 125 */     this.onlyLabelWhenSelected = onlyLabelWhenSelected;
/*     */   }
/*     */ 
/*     */   public LabelledPortrayal2D(SimplePortrayal2D child, String label)
/*     */   {
/* 135 */     this(child, label, Color.blue, false);
/*     */   }
/*     */ 
/*     */   public LabelledPortrayal2D(SimplePortrayal2D child, double scaley, String label, Paint paint, boolean onlyLabelWhenSelected)
/*     */   {
/* 145 */     this(child, 0.0D, 10.0D, 0.0D, scaley, new Font("SansSerif", 0, 10), 1, label, paint, onlyLabelWhenSelected);
/*     */   }
/*     */ 
/*     */   public LabelledPortrayal2D(SimplePortrayal2D child, String label, Paint paint, boolean onlyLabelWhenSelected)
/*     */   {
/* 155 */     this(child, 0.0D, 10.0D, 0.0D, 0.5D, new Font("SansSerif", 0, 10), 1, label, paint, onlyLabelWhenSelected);
/*     */   }
/*     */ 
/*     */   public SimplePortrayal2D getChild(Object object)
/*     */   {
/* 160 */     if (this.child != null) return this.child;
/*     */ 
/* 163 */     if (!(object instanceof SimplePortrayal2D))
/* 164 */       throw new RuntimeException("Object provided to LabelledPortrayal2D is not a SimplePortrayal2D: " + object);
/* 165 */     return (SimplePortrayal2D)object;
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/* 171 */     getChild(object).draw(object, graphics, info);
/*     */ 
/* 173 */     if ((this.showLabel) && ((info.selected) || (!this.onlyLabelWhenSelected)))
/*     */     {
/* 176 */       Font labelFont = this.font;
/* 177 */       Font scaledFont = this.scaledFont;
/*     */ 
/* 180 */       float size = (this.labelScaling == 2) || ((this.labelScaling == 1) && (info.draw.width < 1.0D)) ? (float)(info.draw.width * labelFont.getSize2D()) : labelFont.getSize2D();
/*     */ 
/* 184 */       if ((scaledFont == null) || (scaledFont.getSize2D() != size) || (scaledFont.getFamily() != labelFont.getFamily()) || (scaledFont.getStyle() != labelFont.getStyle()))
/*     */       {
/* 188 */         scaledFont = this.scaledFont = labelFont.deriveFont(size);
/*     */       }
/* 190 */       String s = getLabel(object, info);
/* 191 */       if ((s != null) && (!s.equals("")))
/*     */       {
/* 193 */         int x = (int)(info.draw.x + this.scalex * info.draw.width + this.offsetx);
/* 194 */         int y = (int)(info.draw.y + this.scaley * info.draw.height + this.offsety);
/* 195 */         graphics.setPaint(this.paint);
/* 196 */         graphics.setFont(scaledFont);
/*     */ 
/* 198 */         if (this.align == 0)
/*     */         {
/* 200 */           x -= graphics.getFontMetrics().stringWidth(s) / 2;
/*     */         }
/* 202 */         else if (this.align == -1)
/*     */         {
/* 204 */           x -= graphics.getFontMetrics().stringWidth(s);
/*     */         }
/*     */ 
/* 207 */         if (s.contains("\n"))
/*     */         {
/* 209 */           String[] split = s.split("\n");
/* 210 */           float height = graphics.getFontMetrics().getHeight();
/* 211 */           for (int i = 0; i < split.length; i++)
/* 212 */             graphics.drawString(split[i], x, y + i * height);
/*     */         }
/*     */         else {
/* 215 */           graphics.drawString(s, x, y);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean hitObject(Object object, DrawInfo2D range) {
/* 222 */     return getChild(object).hitObject(object, range);
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/* 228 */     return getChild(wrapper.getObject()).setSelected(wrapper, selected);
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/* 233 */     return getChild(wrapper.getObject()).getInspector(wrapper, state);
/*     */   }
/*     */ 
/*     */   public String getLabel(Object object, DrawInfo2D info)
/*     */   {
/* 242 */     if (this.label == null) return "" + object;
/* 243 */     return this.label;
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/* 248 */     return getChild(wrapper.getObject()).getName(wrapper);
/*     */   }
/*     */ 
/*     */   public boolean handleMouseEvent(GUIState guistate, Manipulating2D manipulating, LocationWrapper wrapper, MouseEvent event, DrawInfo2D fieldPortrayalDrawInfo, int type)
/*     */   {
/* 254 */     return getChild(wrapper.getObject()).handleMouseEvent(guistate, manipulating, wrapper, event, fieldPortrayalDrawInfo, type);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.simple.LabelledPortrayal2D
 * JD-Core Version:    0.6.2
 */