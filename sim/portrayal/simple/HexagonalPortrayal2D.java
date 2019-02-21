/*    */ package sim.portrayal.simple;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Paint;
/*    */ 
/*    */ public class HexagonalPortrayal2D extends ShapePortrayal2D
/*    */ {
/* 23 */   static final double stretch = 1.0D / (2.0D * Math.sin(1.047197551196598D));
/*    */ 
/* 24 */   public HexagonalPortrayal2D() { this(Color.gray, 1.0D, true); } 
/* 25 */   public HexagonalPortrayal2D(Paint paint) { this(paint, 1.0D, true); } 
/* 26 */   public HexagonalPortrayal2D(double scale) { this(Color.gray, scale, true); } 
/* 27 */   public HexagonalPortrayal2D(boolean filled) { this(Color.gray, 1.0D, filled); } 
/* 28 */   public HexagonalPortrayal2D(Paint paint, double scale) { this(paint, scale, true); } 
/* 29 */   public HexagonalPortrayal2D(Paint paint, boolean filled) { this(paint, 1.0D, filled); } 
/* 30 */   public HexagonalPortrayal2D(double scale, boolean filled) { this(Color.gray, scale, filled); }
/*    */ 
/*    */ 
/*    */   public HexagonalPortrayal2D(Paint paint, double scale, boolean filled)
/*    */   {
/* 39 */     super(new double[] { 1.0D * stretch, 0.5D * stretch, -0.5D * stretch, -1.0D * stretch, -0.5D * stretch, 0.5D * stretch }, new double[] { 0.0D, -0.5D, -0.5D, 0.0D, 0.5D, 0.5D }, paint, scale, filled);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.simple.HexagonalPortrayal2D
 * JD-Core Version:    0.6.2
 */