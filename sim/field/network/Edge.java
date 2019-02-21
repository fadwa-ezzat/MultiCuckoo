/*     */ package sim.field.network;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import sim.util.Valuable;
/*     */ 
/*     */ public class Edge
/*     */   implements Serializable, Comparable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   Network owner;
/*     */   Object from;
/*     */   Object to;
/*     */   public Object info;
/*     */   int indexFrom;
/*     */   int indexTo;
/*     */ 
/*     */   public Object getFrom()
/*     */   {
/*  61 */     return this.from;
/*     */   }
/*  63 */   public Object getTo() { return this.to; }
/*     */ 
/*     */ 
/*     */   public boolean getDirected()
/*     */   {
/*  68 */     Network o = this.owner;
/*  69 */     if (o == null) return true;
/*  70 */     return o.isDirected();
/*     */   }
/*     */ 
/*     */   public Object from() {
/*  74 */     return this.from;
/*     */   }
/*  76 */   public Object to() { return this.to; } 
/*     */   public Network owner() {
/*  78 */     return this.owner;
/*     */   }
/*     */   public int indexFrom() {
/*  81 */     return this.indexFrom;
/*     */   }
/*     */   public int indexTo() {
/*  84 */     return this.indexTo;
/*     */   }
/*     */ 
/*     */   public Edge(Edge e) {
/*  88 */     setTo(e.from, e.to, e.info, e.indexFrom, e.indexTo);
/*     */   }
/*     */ 
/*     */   public Edge(Object from, Object to, Object info)
/*     */   {
/*  93 */     setTo(from, to, info, -1, -1);
/*     */   }
/*     */ 
/*     */   void setTo(Object from, Object to, Object info, int indexFrom, int indexTo)
/*     */   {
/*  99 */     this.from = from;
/* 100 */     this.to = to;
/* 101 */     this.info = info;
/* 102 */     this.indexFrom = indexFrom;
/* 103 */     this.indexTo = indexTo;
/*     */   }
/*     */ 
/*     */   public void setWeight(double weight)
/*     */   {
/* 109 */     this.info = new Double(weight);
/*     */   }
/*     */ 
/*     */   public double getWeight()
/*     */   {
/* 118 */     if ((this.info instanceof Number))
/* 119 */       return ((Number)this.info).doubleValue();
/* 120 */     if ((this.info instanceof Valuable)) {
/* 121 */       return ((Valuable)this.info).doubleValue();
/*     */     }
/* 123 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   public Object getOtherNode(Object node)
/*     */   {
/* 137 */     if (node.equals(this.from))
/* 138 */       return this.to;
/* 139 */     return this.from;
/*     */   }
/*     */   public Object getInfo() {
/* 142 */     return this.info; } 
/* 143 */   public void setInfo(Object val) { this.info = val; }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 147 */     if (this.owner == null)
/* 148 */       return "Unowned Edge[" + this.from + "->" + this.to + ": " + this.info + "]";
/* 149 */     if (this.owner.isDirected()) {
/* 150 */       return "Edge[" + this.from + "->" + this.to + ": " + this.info + "]";
/*     */     }
/* 152 */     return "Edge[" + this.from + "<->" + this.to + ": " + this.info + "]";
/*     */   }
/*     */ 
/*     */   public int compareTo(Object obj)
/*     */   {
/* 158 */     if ((this.info == null) || (obj == null) || (!(obj instanceof Edge)))
/*     */     {
/* 160 */       return 0;
/*     */     }
/*     */ 
/* 163 */     Edge other = (Edge)obj;
/*     */ 
/* 165 */     if (((other.info instanceof Long)) && ((this.info instanceof Long)))
/*     */     {
/* 167 */       long l = ((Long)this.info).longValue();
/* 168 */       long l2 = ((Long)other.info).longValue();
/* 169 */       if (l == l2) return 0;
/* 170 */       return l < l2 ? -1 : 1;
/*     */     }
/* 172 */     if ((this.info instanceof Number))
/*     */     {
/* 174 */       double d = ((Number)this.info).doubleValue();
/* 175 */       if ((other.info instanceof Number))
/*     */       {
/* 177 */         double d2 = ((Number)other.info).doubleValue();
/* 178 */         if (d == d2) return 0;
/* 179 */         return d < d2 ? -1 : 1;
/*     */       }
/* 181 */       if ((other.info instanceof Valuable))
/*     */       {
/* 183 */         double d2 = ((Valuable)other.info).doubleValue();
/* 184 */         if (d == d2) return 0;
/* 185 */         return d < d2 ? -1 : 1;
/*     */       }
/* 187 */       return 0;
/*     */     }
/* 189 */     if ((this.info instanceof Valuable))
/*     */     {
/* 191 */       double d = ((Valuable)this.info).doubleValue();
/* 192 */       if ((other.info instanceof Number))
/*     */       {
/* 194 */         double d2 = ((Number)other.info).doubleValue();
/* 195 */         if (d == d2) return 0;
/* 196 */         return d < d2 ? -1 : 1;
/*     */       }
/* 198 */       if ((other.info instanceof Valuable))
/*     */       {
/* 200 */         double d2 = ((Valuable)other.info).doubleValue();
/* 201 */         if (d == d2) return 0;
/* 202 */         return d < d2 ? -1 : 1;
/*     */       }
/* 204 */       return 0;
/*     */     }
/* 206 */     return 0;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.network.Edge
 * JD-Core Version:    0.6.2
 */