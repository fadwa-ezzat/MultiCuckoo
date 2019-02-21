/*     */ package sim.util.media.chart;
/*     */ 
/*     */ import sim.util.IntBag;
/*     */ 
/*     */ public class MinGapDataCuller
/*     */   implements DataCuller
/*     */ {
/*     */   int maxPointCount;
/*     */   int pointCountAfterCulling;
/*     */   IntBag reusableIntBag;
/*     */ 
/*     */   public MinGapDataCuller(int maxPointCount)
/*     */   {
/*  44 */     this(maxPointCount, maxPointCount / 2 + 1);
/*     */   }
/*     */ 
/*     */   public MinGapDataCuller(int maxPointCount, int pointCountAfterCulling)
/*     */   {
/*  49 */     setMaxAndMinCounts(maxPointCount, pointCountAfterCulling);
/*  50 */     this.reusableIntBag = new IntBag(maxPointCount - pointCountAfterCulling + 1);
/*     */   }
/*     */ 
/*     */   public boolean tooManyPoints(int currentPointCount)
/*     */   {
/*  56 */     return currentPointCount > this.maxPointCount;
/*     */   }
/*     */ 
/*     */   void setMaxAndMinCounts(int maxPointCount, int pointCountAfterCulling)
/*     */   {
/*  61 */     this.maxPointCount = maxPointCount;
/*  62 */     this.pointCountAfterCulling = pointCountAfterCulling;
/*     */   }
/*     */ 
/*     */   static void sort(IntBag indices, int maxPoints)
/*     */   {
/*  68 */     boolean[] map = new boolean[maxPoints];
/*  69 */     for (int i = 0; i < indices.numObjs; i++)
/*  70 */       map[indices.objs[i]] = true;
/*  71 */     indices.clear();
/*  72 */     for (int i = 0; i < maxPoints; i++)
/*  73 */       if (map[i] != 0)
/*  74 */         indices.add(i);
/*     */   }
/*     */ 
/*     */   public IntBag cull(double[] xValues, boolean sortedOutput)
/*     */   {
/*  79 */     return cull(xValues, this.reusableIntBag, sortedOutput);
/*     */   }
/*     */ 
/*     */   IntBag cull(double[] xValues, IntBag droppedIndices, boolean sortOutput)
/*     */   {
/*  84 */     return cull(xValues, this.pointCountAfterCulling, droppedIndices, sortOutput);
/*     */   }
/*     */ 
/*     */   IntBag cull(double[] xValues, int size, IntBag droppedIndices, boolean sortOutput)
/*     */   {
/*  90 */     IntBag bag = cullToSize(xValues, size, droppedIndices);
/*  91 */     if (sortOutput)
/*  92 */       sort(bag, xValues.length);
/*  93 */     return bag;
/*     */   }
/*     */ 
/*     */   public static IntBag cullToSize(double[] xValues, int size, IntBag droppedIndices)
/*     */   {
/* 105 */     droppedIndices.clear();
/* 106 */     int pointsToDrop = xValues.length - size;
/* 107 */     if (pointsToDrop <= 0)
/* 108 */       return droppedIndices;
/* 109 */     if (xValues.length <= 2)
/*     */     {
/* 112 */       for (int i = 0; i < pointsToDrop; i++)
/* 113 */         droppedIndices.add(i);
/* 114 */       return droppedIndices;
/*     */     }
/* 116 */     if (pointsToDrop == 1)
/*     */     {
/* 120 */       double bestGapSumSoFar = 1.7976931348623157E+308D;
/* 121 */       int index = -1;
/* 122 */       double lastX = xValues[1];
/* 123 */       double lastGap = xValues[1] - xValues[0];
/* 124 */       for (int i = 2; i < xValues.length; i++)
/*     */       {
/* 126 */         double xi = xValues[i];
/* 127 */         double gap = xi - lastX;
/* 128 */         lastX = xi;
/* 129 */         double gapSum = gap + lastGap;
/* 130 */         lastGap = gap;
/* 131 */         if (gapSum < bestGapSumSoFar)
/*     */         {
/* 133 */           index = i - 1;
/* 134 */           bestGapSumSoFar = gapSum;
/*     */         }
/*     */       }
/* 137 */       droppedIndices.add(index);
/* 138 */       return droppedIndices;
/*     */     }
/*     */ 
/* 141 */     Heap h = new Heap(xValues);
/* 142 */     for (int i = 0; i < pointsToDrop; i++)
/*     */     {
/* 144 */       droppedIndices.add(h.extractMin().xValueIndex);
/*     */     }
/* 146 */     return droppedIndices;
/*     */   }
/*     */ 
/*     */   static class Heap
/*     */   {
/*     */     int heapsize;
/*     */     MinGapDataCuller.Record[] heap;
/*     */ 
/*     */     Heap(double[] xValues)
/*     */     {
/* 194 */       this.heapsize = (xValues.length - 2);
/*     */ 
/* 196 */       this.heap = new MinGapDataCuller.Record[this.heapsize];
/*     */ 
/* 198 */       double currentX = xValues[1];
/* 199 */       double lastGap = currentX - xValues[0];
/* 200 */       if (lastGap <= 0.0D)
/* 201 */         throw new RuntimeException("I expect xValues in strictly increasing order.");
/* 202 */       MinGapDataCuller.Record lastRecord = null;
/* 203 */       for (int i = 1; i < xValues.length - 1; i++)
/*     */       {
/* 205 */         double nextX = xValues[(i + 1)];
/* 206 */         double nextGap = nextX - currentX;
/* 207 */         if (nextGap <= 0.0D) {
/* 208 */           throw new RuntimeException("I expect xValues in strictly increasing order.");
/*     */         }
/* 210 */         MinGapDataCuller.Record ri = new MinGapDataCuller.Record(i, lastGap, nextGap, i);
/* 211 */         ri.leftRecord = lastRecord;
/* 212 */         if (lastRecord != null) {
/* 213 */           lastRecord.rightRecord = ri;
/*     */         }
/* 215 */         lastRecord = ri;
/* 216 */         currentX = nextX;
/* 217 */         lastGap = nextGap;
/*     */ 
/* 219 */         this.heap[(i - 1)] = ri;
/*     */       }
/*     */ 
/* 222 */       for (int i = this.heapsize / 2; i >= 1; i--)
/* 223 */         heapify(i);
/*     */     }
/*     */ 
/*     */     MinGapDataCuller.Record extractMin()
/*     */     {
/* 228 */       if (this.heapsize == 0) {
/* 229 */         return null;
/*     */       }
/* 231 */       MinGapDataCuller.Record result = this.heap[0];
/* 232 */       this.heap[0] = this.heap[(this.heapsize - 1)];
/* 233 */       this.heap[0].heapPosition = 1;
/* 234 */       this.heap[(this.heapsize - 1)] = null;
/* 235 */       this.heapsize -= 1;
/*     */ 
/* 237 */       if (this.heapsize > 1)
/*     */       {
/* 239 */         heapify(1);
/*     */ 
/* 242 */         MinGapDataCuller.Record leftRecord = result.leftRecord;
/* 243 */         MinGapDataCuller.Record rightRecord = result.rightRecord;
/* 244 */         if (rightRecord != null)
/*     */         {
/* 246 */           if (rightRecord.leftGap != result.rightGap)
/* 247 */             throw new RuntimeException("BUG");
/*     */         }
/* 249 */         if (leftRecord != null)
/*     */         {
/* 251 */           if (leftRecord.rightGap != result.leftGap) {
/* 252 */             throw new RuntimeException("BUG");
/*     */           }
/* 254 */           leftRecord.setRightGap(result.key);
/* 255 */           leftRecord.rightRecord = result.rightRecord;
/* 256 */           heapify(leftRecord.heapPosition);
/*     */         }
/* 258 */         if (rightRecord != null)
/*     */         {
/* 260 */           rightRecord.setLeftGap(result.key);
/* 261 */           rightRecord.leftRecord = result.leftRecord;
/* 262 */           heapify(rightRecord.heapPosition);
/*     */         }
/*     */       }
/*     */ 
/* 266 */       return result;
/*     */     }
/*     */ 
/*     */     void heapify(int i)
/*     */     {
/*     */       while (true)
/*     */       {
/* 273 */         int l = 2 * i;
/* 274 */         int r = 2 * i + 1;
/*     */         int smallest;
/*     */         int smallest;
/* 276 */         if ((l <= this.heapsize) && (this.heap[(l - 1)].compareTo(this.heap[(i - 1)]) < 0))
/* 277 */           smallest = l;
/*     */         else
/* 279 */           smallest = i;
/* 280 */         if ((r <= this.heapsize) && (this.heap[(r - 1)].compareTo(this.heap[(smallest - 1)]) < 0))
/* 281 */           smallest = r;
/* 282 */         if (smallest != i)
/*     */         {
/* 285 */           MinGapDataCuller.Record tmp = this.heap[(i - 1)];
/* 286 */           this.heap[(i - 1)] = this.heap[(smallest - 1)];
/* 287 */           this.heap[(smallest - 1)] = tmp;
/*     */ 
/* 289 */           this.heap[(i - 1)].heapPosition = i;
/* 290 */           this.heap[(smallest - 1)].heapPosition = smallest;
/*     */ 
/* 293 */           i = smallest;
/*     */         }
/*     */         else {
/* 296 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Record
/*     */     implements Comparable
/*     */   {
/*     */     int xValueIndex;
/*     */     double leftGap;
/*     */     double rightGap;
/*     */     double key;
/*     */     Record leftRecord;
/*     */     Record rightRecord;
/*     */     int heapPosition;
/*     */ 
/*     */     Record(int xValueIndex, double leftGap, double rightGap, int heapPosition)
/*     */     {
/* 159 */       this.xValueIndex = xValueIndex;
/* 160 */       this.leftGap = leftGap;
/* 161 */       this.rightGap = rightGap;
/* 162 */       this.key = (leftGap + rightGap);
/* 163 */       this.heapPosition = heapPosition;
/*     */     }
/*     */ 
/*     */     public int compareTo(Object o)
/*     */     {
/* 169 */       Record r = (Record)o;
/* 170 */       double keydiff = this.key - r.key;
/* 171 */       if (keydiff == 0.0D) {
/* 172 */         return this.xValueIndex - r.xValueIndex;
/*     */       }
/* 174 */       return keydiff <= 0.0D ? -1 : 1;
/*     */     }
/*     */ 
/*     */     void setLeftGap(double lg) {
/* 178 */       this.leftGap = lg;
/* 179 */       this.key = (this.leftGap + this.rightGap);
/*     */     }
/*     */ 
/*     */     void setRightGap(double rg) {
/* 183 */       this.rightGap = rg;
/* 184 */       this.key = (this.leftGap + this.rightGap);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.MinGapDataCuller
 * JD-Core Version:    0.6.2
 */