/*     */ package sim.app.mav;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.GlyphVector;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Area;
/*     */ import java.awt.geom.Ellipse2D.Double;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import java.awt.geom.RoundRectangle2D.Double;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ 
/*     */ public class Region extends SimplePortrayal2D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  24 */   static final Shape[] shapes = { new Ellipse2D.Double(0.0D, 0.0D, 100.0D, 100.0D), AffineTransform.getRotateInstance(0.610865238198015D).createTransformedShape(new RoundRectangle2D.Double(0.0D, 0.0D, 100.0D, 100.0D, 15.0D, 15.0D)), new Font("Serif", 0, 128).createGlyphVector(new FontRenderContext(new AffineTransform(), false, true), "MAV").getOutline() };
/*     */   public double originx;
/*     */   public double originy;
/*     */   int shapeNum;
/*  39 */   static final Color[] surfacecolors = { Color.white, Color.blue, Color.green, Color.red };
/*     */   public Shape shape;
/*     */   public Area area;
/*     */   public int surface;
/*     */   Shape oldShape;
/*  54 */   Rectangle2D.Double oldDraw = null;
/*     */ 
/*     */   public Region(int num, int s, double x, double y)
/*     */   {
/*  45 */     this.shapeNum = num;
/*  46 */     this.shape = shapes[this.shapeNum]; this.surface = s;
/*  47 */     this.area = new Area(this.shape); this.originx = x; this.originy = y;
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/*  57 */     if ((this.oldDraw == null) || (this.oldDraw.x != info.draw.x) || (this.oldDraw.y != info.draw.y) || (this.oldDraw.width != info.draw.width) || (this.oldDraw.height != info.draw.height))
/*     */     {
/*  63 */       this.oldDraw = info.draw;
/*  64 */       AffineTransform transform = new AffineTransform();
/*  65 */       transform.translate(this.oldDraw.x, this.oldDraw.y);
/*  66 */       transform.scale(this.oldDraw.width, this.oldDraw.height);
/*  67 */       this.oldShape = transform.createTransformedShape(this.shape);
/*     */     }
/*     */ 
/*  71 */     graphics.setColor(surfacecolors[this.surface]);
/*  72 */     graphics.fill(this.oldShape);
/*     */   }
/*     */ 
/*     */   public boolean hitObject(Object object, DrawInfo2D range)
/*     */   {
/*  79 */     AffineTransform transform = new AffineTransform();
/*  80 */     transform.translate(range.draw.x, range.draw.y);
/*  81 */     transform.scale(range.draw.width, range.draw.height);
/*  82 */     Shape s = transform.createTransformedShape(this.shape);
/*     */ 
/*  84 */     return s.intersects(range.clip.x, range.clip.y, range.clip.width, range.clip.height);
/*     */   }
/*     */ 
/*     */   private void writeObject(ObjectOutputStream p)
/*     */     throws IOException
/*     */   {
/*  94 */     p.writeDouble(this.originx);
/*  95 */     p.writeDouble(this.originy);
/*  96 */     p.writeInt(this.shapeNum);
/*  97 */     p.writeInt(this.surface);
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream p)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 103 */     this.originx = p.readDouble();
/* 104 */     this.originy = p.readDouble();
/* 105 */     this.shapeNum = p.readInt();
/* 106 */     this.surface = p.readInt();
/*     */ 
/* 108 */     this.shape = shapes[this.shapeNum];
/* 109 */     this.area = new Area(this.shape);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.mav.Region
 * JD-Core Version:    0.6.2
 */