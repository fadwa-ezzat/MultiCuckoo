/*     */ package sim.field.network;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import sim.util.Bag;
/*     */ 
/*     */ public class Network
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   boolean directed;
/* 119 */   public Map indexOutInHash = buildMap(0);
/*     */ 
/* 123 */   public Bag allNodes = new Bag();
/*     */ 
/* 126 */   final Bag emptyBag = new Bag();
/*     */ 
/* 279 */   static Edge[] emptyEdgeArray = new Edge[0];
/*     */   public static final int ANY_SIZE = 0;
/*     */ 
/*     */   public boolean isDirected()
/*     */   {
/*  98 */     return this.directed;
/*     */   }
/*     */   public Network(boolean directed) {
/* 101 */     this.directed = directed;
/*     */   }
/*     */   public Network() {
/* 104 */     this(true);
/*     */   }
/*     */   public Network(Network other) {
/* 107 */     this(); other.copyTo(this); this.directed = other.directed;
/*     */   }
/*     */ 
/*     */   public void reset(boolean directed)
/*     */   {
/* 112 */     clear();
/* 113 */     this.directed = directed;
/*     */   }
/*     */ 
/*     */   public Edge[][] getAdjacencyList(boolean outEdges)
/*     */   {
/* 146 */     Edge[][] list = new Edge[this.allNodes.numObjs][];
/* 147 */     for (int x = 0; x < this.allNodes.numObjs; x++)
/*     */     {
/* 150 */       Bag edges = outEdges ? getEdgesOut(this.allNodes.objs[x]) : getEdgesIn(this.allNodes.objs[x]);
/*     */ 
/* 152 */       list[x] = new Edge[edges.numObjs];
/* 153 */       Edge[] l = list[x];
/* 154 */       int n = edges.numObjs;
/* 155 */       Object[] objs = edges.objs;
/* 156 */       System.arraycopy(objs, 0, l, 0, n);
/*     */     }
/*     */ 
/* 161 */     return list;
/*     */   }
/*     */ 
/*     */   public Edge[][] getAdjacencyMatrix()
/*     */   {
/* 184 */     int n = this.allNodes.numObjs;
/* 185 */     Edge[][] matrix = new Edge[n][n];
/*     */ 
/* 187 */     Iterator nodeIO = this.indexOutInHash.values().iterator();
/* 188 */     while (nodeIO.hasNext())
/*     */     {
/* 190 */       IndexOutIn ioi = (IndexOutIn)nodeIO.next();
/* 191 */       if (ioi.out != null) {
/* 192 */         int outDegree = ioi.out.numObjs;
/* 193 */         Edge[] outEdges = matrix[ioi.index];
/* 194 */         Object sourceNode = this.allNodes.objs[ioi.index];
/*     */ 
/* 196 */         for (int i = 0; i < outDegree; i++)
/*     */         {
/* 198 */           Edge e = (Edge)ioi.out.objs[i];
/*     */ 
/* 200 */           outEdges[((IndexOutIn)this.indexOutInHash.get(e.getOtherNode(sourceNode))).index] = e;
/*     */         }
/*     */       }
/*     */     }
/* 203 */     return matrix;
/*     */   }
/*     */ 
/*     */   public Edge[][][] getMultigraphAdjacencyMatrix()
/*     */   {
/* 231 */     int n = this.allNodes.numObjs;
/* 232 */     Edge[][][] matrix = new Edge[n][n];
/*     */ 
/* 234 */     Iterator nodeIO = this.indexOutInHash.values().iterator();
/* 235 */     Bag[] tmp = new Bag[n];
/* 236 */     for (int i = 0; i < n; i++) {
/* 237 */       tmp[i] = new Bag(n);
/*     */     }
/* 239 */     while (nodeIO.hasNext())
/*     */     {
/* 241 */       IndexOutIn ioi = (IndexOutIn)nodeIO.next();
/* 242 */       if (ioi.out != null) {
/* 243 */         int outDegree = ioi.out.numObjs;
/* 244 */         Object sourceNode = this.allNodes.objs[ioi.index];
/*     */ 
/* 246 */         for (int i = 0; i < outDegree; i++)
/*     */         {
/* 248 */           Edge e = (Edge)ioi.out.objs[i];
/*     */ 
/* 250 */           int j = ((IndexOutIn)this.indexOutInHash.get(e.getOtherNode(sourceNode))).index;
/* 251 */           tmp[j].add(e);
/*     */         }
/*     */ 
/* 254 */         Edge[][] outEdges = matrix[ioi.index];
/* 255 */         for (int i = 0; i < n; i++)
/*     */         {
/* 257 */           Bag b = tmp[i];
/* 258 */           int ne = b.numObjs;
/* 259 */           outEdges[i] = new Edge[ne];
/* 260 */           if (ne > 0)
/*     */           {
/* 262 */             System.arraycopy(b.objs, 0, outEdges[i], 0, ne);
/* 263 */             b.clear();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 268 */     for (int i = 0; i < n; i++)
/*     */     {
/* 270 */       Edge[][] e2 = matrix[i];
/* 271 */       if (e2[0] == null) {
/* 272 */         for (int j = 0; j < n; j++)
/* 273 */           e2[j] = emptyEdgeArray;
/*     */       }
/*     */     }
/* 276 */     return matrix;
/*     */   }
/*     */ 
/*     */   public Bag getEdgesOut(Object node)
/*     */   {
/* 286 */     IndexOutIn ioi = (IndexOutIn)this.indexOutInHash.get(node);
/*     */     Bag b;
/* 288 */     if ((ioi == null) || ((b = ioi.out) == null)) return this.emptyBag;
/*     */     Bag b;
/* 289 */     return b;
/*     */   }
/*     */ 
/*     */   public Bag getEdgesIn(Object node)
/*     */   {
/* 296 */     IndexOutIn ioi = (IndexOutIn)this.indexOutInHash.get(node);
/*     */     Bag b;
/* 298 */     if ((ioi == null) || ((b = ioi.in) == null)) return this.emptyBag;
/*     */     Bag b;
/* 299 */     return b;
/*     */   }
/*     */ 
/*     */   public Bag getEdges(Object node, Bag bag)
/*     */   {
/* 310 */     if (!this.directed)
/*     */     {
/* 312 */       if (bag == null) bag = new Bag(); else {
/* 313 */         bag.clear();
/*     */       }
/* 315 */       IndexOutIn ioi = (IndexOutIn)this.indexOutInHash.get(node);
/* 316 */       if (ioi == null) return bag;
/* 317 */       if ((ioi.in != null) && (ioi.in.numObjs > 0)) bag.addAll(ioi.in);
/*     */ 
/* 319 */       return bag;
/*     */     }
/*     */ 
/* 323 */     if (bag == null) bag = new Bag(); else {
/* 324 */       bag.clear();
/*     */     }
/* 326 */     IndexOutIn ioi = (IndexOutIn)this.indexOutInHash.get(node);
/* 327 */     if (ioi == null) return bag;
/* 328 */     if ((ioi.in != null) && (ioi.in.numObjs > 0)) bag.addAll(ioi.in);
/* 329 */     if ((ioi.out != null) && (ioi.out.numObjs > 0)) bag.addAll(ioi.out);
/* 330 */     return bag;
/*     */   }
/*     */ 
/*     */   public Edge getEdge(Object from, Object to)
/*     */   {
/* 338 */     Bag b = getEdgesOut(from);
/* 339 */     for (int i = 0; i < b.size(); i++)
/*     */     {
/* 341 */       Edge e = (Edge)b.get(i);
/* 342 */       if (e.getOtherNode(from).equals(to))
/* 343 */         return e;
/*     */     }
/* 345 */     return null;
/*     */   }
/*     */ 
/*     */   public Bag getEdges(Object from, Object to, Bag bag)
/*     */   {
/* 353 */     if (bag == null) bag = new Bag(); else {
/* 354 */       bag.clear();
/*     */     }
/* 356 */     Bag b = getEdgesOut(from);
/* 357 */     for (int i = 0; i < b.size(); i++)
/*     */     {
/* 359 */       Edge e = (Edge)b.get(i);
/* 360 */       if (e.getOtherNode(from).equals(to))
/* 361 */         bag.add(e);
/*     */     }
/* 363 */     return bag;
/*     */   }
/*     */ 
/*     */   public void addNode(Object node)
/*     */   {
/* 369 */     if (this.indexOutInHash.get(node) != null)
/* 370 */       return;
/* 371 */     this.allNodes.add(node);
/* 372 */     IndexOutIn ioih = new IndexOutIn(this.allNodes.numObjs - 1, null, null);
/* 373 */     this.indexOutInHash.put(node, ioih);
/*     */   }
/*     */ 
/*     */   public void addEdge(Object from, Object to, Object info)
/*     */   {
/* 381 */     addEdge(new Edge(from, to, info));
/*     */   }
/*     */ 
/*     */   public void addEdge(Edge edge)
/*     */   {
/* 390 */     if (edge == null) {
/* 391 */       throw new RuntimeException("Attempted to add a null Edge.");
/*     */     }
/*     */ 
/* 394 */     if (edge.owner != null)
/* 395 */       throw new RuntimeException("Attempted to add an Edge already added elsewhere");
/* 396 */     edge.owner = this;
/*     */ 
/* 398 */     edge.indexFrom = 0;
/* 399 */     edge.indexTo = 0;
/* 400 */     IndexOutIn outNode = (IndexOutIn)this.indexOutInHash.get(edge.from);
/* 401 */     if (outNode == null)
/*     */     {
/* 403 */       addNode(edge.from);
/* 404 */       outNode = (IndexOutIn)this.indexOutInHash.get(edge.from);
/*     */     }
/* 406 */     if (outNode.out == null)
/*     */     {
/* 408 */       if (this.directed) {
/* 409 */         outNode.out = new Bag();
/*     */       }
/* 412 */       else if (outNode.in != null)
/* 413 */         outNode.out = outNode.in;
/*     */       else {
/* 415 */         outNode.out = (outNode.in = new Bag());
/*     */       }
/*     */     }
/* 418 */     outNode.out.add(edge);
/* 419 */     edge.indexFrom = (outNode.out.numObjs - 1);
/*     */ 
/* 421 */     IndexOutIn inNode = (IndexOutIn)this.indexOutInHash.get(edge.to);
/* 422 */     if (inNode == null)
/*     */     {
/* 424 */       addNode(edge.to);
/* 425 */       inNode = (IndexOutIn)this.indexOutInHash.get(edge.to);
/*     */     }
/* 427 */     if (inNode.in == null)
/*     */     {
/* 429 */       if (this.directed) {
/* 430 */         inNode.in = new Bag();
/*     */       }
/* 433 */       else if (inNode.out != null)
/* 434 */         inNode.in = inNode.out;
/*     */       else {
/* 436 */         inNode.in = (inNode.out = new Bag());
/*     */       }
/*     */     }
/* 439 */     inNode.in.add(edge);
/* 440 */     edge.indexTo = (inNode.in.numObjs - 1);
/*     */   }
/*     */ 
/*     */   public Edge updateEdge(Edge edge, Object from, Object to, Object info)
/*     */   {
/* 450 */     edge = removeEdge(edge);
/* 451 */     if (edge != null)
/*     */     {
/* 453 */       edge.setTo(from, to, info, -1, -1);
/* 454 */       addEdge(edge);
/*     */     }
/* 456 */     return edge;
/*     */   }
/*     */ 
/*     */   public Edge removeEdge(Edge edge)
/*     */   {
/* 465 */     if (edge == null) {
/* 466 */       return null;
/*     */     }
/*     */ 
/* 469 */     if (edge.owner != this)
/* 470 */       return null;
/* 471 */     edge.owner = null;
/*     */ 
/* 475 */     Bag outNodeBag = ((IndexOutIn)this.indexOutInHash.get(edge.from)).out;
/* 476 */     outNodeBag.remove(edge.indexFrom);
/* 477 */     if (outNodeBag.numObjs > edge.indexFrom)
/*     */     {
/* 479 */       Edge shiftedEdge = (Edge)outNodeBag.objs[edge.indexFrom];
/* 480 */       int shiftedIndex = outNodeBag.numObjs;
/* 481 */       if (this.directed)
/*     */       {
/* 484 */         shiftedEdge.indexFrom = edge.indexFrom;
/*     */       }
/* 489 */       else if ((shiftedEdge.indexFrom == shiftedIndex) && (shiftedEdge.from.equals(edge.from)))
/*     */       {
/* 491 */         shiftedEdge.indexFrom = edge.indexFrom;
/*     */       }
/* 493 */       else if ((shiftedEdge.indexTo == shiftedIndex) && (shiftedEdge.to.equals(edge.from)))
/*     */       {
/* 495 */         shiftedEdge.indexTo = edge.indexFrom;
/*     */       } else throw new InternalError("This shouldn't ever happen: #1");
/*     */ 
/*     */     }
/*     */ 
/* 501 */     Bag inNodeBag = ((IndexOutIn)this.indexOutInHash.get(edge.to)).in;
/* 502 */     inNodeBag.remove(edge.indexTo);
/* 503 */     if (inNodeBag.numObjs > edge.indexTo)
/*     */     {
/* 505 */       Edge shiftedEdge = (Edge)inNodeBag.objs[edge.indexTo];
/* 506 */       int shiftedIndex = inNodeBag.numObjs;
/* 507 */       if (this.directed)
/*     */       {
/* 510 */         shiftedEdge.indexTo = edge.indexTo;
/*     */       }
/* 515 */       else if ((shiftedEdge.indexTo == shiftedIndex) && (shiftedEdge.to.equals(edge.to)))
/*     */       {
/* 517 */         shiftedEdge.indexTo = edge.indexTo;
/*     */       }
/* 519 */       else if ((shiftedEdge.indexFrom == shiftedIndex) && (shiftedEdge.from.equals(edge.to)))
/*     */       {
/* 521 */         shiftedEdge.indexFrom = edge.indexTo;
/*     */       } else throw new InternalError("This shouldn't ever happen: #2");
/*     */ 
/*     */     }
/*     */ 
/* 526 */     return edge;
/*     */   }
/*     */ 
/*     */   public void removeAllEdges()
/*     */   {
/* 533 */     int n = this.allNodes.numObjs;
/* 534 */     Iterator i = this.indexOutInHash.values().iterator();
/* 535 */     for (int k = 0; k < n; k++)
/*     */     {
/* 537 */       IndexOutIn ioi = (IndexOutIn)i.next();
/* 538 */       if (ioi.in != null)
/* 539 */         ioi.in.clear();
/* 540 */       if (ioi.out != null)
/* 541 */         ioi.out.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object removeNode(Object node)
/*     */   {
/* 550 */     IndexOutIn ioi = (IndexOutIn)this.indexOutInHash.get(node);
/*     */ 
/* 552 */     if (ioi == null) return null;
/*     */ 
/* 555 */     while ((ioi.out != null) && (ioi.out.numObjs > 0))
/*     */     {
/* 557 */       removeEdge((Edge)ioi.out.objs[0]);
/*     */     }
/*     */ 
/* 561 */     while ((ioi.in != null) && (ioi.in.numObjs > 0))
/*     */     {
/* 563 */       removeEdge((Edge)ioi.in.objs[0]);
/*     */     }
/*     */ 
/* 567 */     this.allNodes.remove(ioi.index);
/* 568 */     if (this.allNodes.numObjs > ioi.index)
/*     */     {
/* 570 */       ((IndexOutIn)this.indexOutInHash.get(this.allNodes.objs[ioi.index])).index = ioi.index;
/*     */     }
/*     */ 
/* 574 */     this.indexOutInHash.remove(node);
/*     */ 
/* 577 */     return node;
/*     */   }
/*     */ 
/*     */   public Bag clear()
/*     */   {
/* 584 */     this.indexOutInHash = buildMap(0);
/* 585 */     Bag retval = this.allNodes;
/* 586 */     this.allNodes = new Bag();
/* 587 */     return retval;
/*     */   }
/*     */ 
/*     */   public Bag removeAllNodes()
/*     */   {
/* 595 */     return clear();
/*     */   }
/*     */ 
/*     */   public Bag getAllNodes()
/*     */   {
/* 603 */     return this.allNodes;
/*     */   }
/*     */ 
/*     */   public Iterator iterator()
/*     */   {
/* 617 */     return this.allNodes.iterator();
/*     */   }
/*     */ 
/*     */   public boolean nodeExists(Object node)
/*     */   {
/* 645 */     return (IndexOutIn)this.indexOutInHash.get(node) != null;
/*     */   }
/*     */ 
/*     */   public int getNodeIndex(Object node)
/*     */   {
/* 653 */     IndexOutIn ioi = (IndexOutIn)this.indexOutInHash.get(node);
/* 654 */     if (ioi == null)
/* 655 */       throw new RuntimeException("Object parameter is not a node in the network.");
/* 656 */     return ioi.index;
/*     */   }
/*     */ 
/*     */   public void reverseAllEdges()
/*     */   {
/* 675 */     if (!this.directed) return;
/* 676 */     int n = this.allNodes.numObjs;
/* 677 */     Iterator i = this.indexOutInHash.values().iterator();
/* 678 */     for (int k = 0; k < n; k++)
/*     */     {
/* 680 */       IndexOutIn ioi = (IndexOutIn)i.next();
/* 681 */       Bag tmpB = ioi.out;
/* 682 */       ioi.out = ioi.in;
/* 683 */       ioi.in = tmpB;
/* 684 */       if (ioi.in != null)
/* 685 */         for (int j = 0; j < ioi.in.numObjs; j++)
/*     */         {
/* 687 */           Edge e = (Edge)ioi.in.objs[j];
/*     */ 
/* 689 */           Object tmpO = e.from;
/* 690 */           e.from = e.to;
/* 691 */           e.to = tmpO;
/*     */ 
/* 693 */           int tmpI = e.indexFrom;
/* 694 */           e.indexFrom = e.indexTo;
/* 695 */           e.indexTo = tmpI;
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Network cloneGraph()
/*     */   {
/* 707 */     return copyTo(new Network(this.directed));
/*     */   }
/*     */ 
/*     */   Network copyTo(Network clone)
/*     */   {
/* 712 */     clone.allNodes.addAll(this.allNodes);
/* 713 */     int n = this.allNodes.numObjs;
/* 714 */     Iterator ioiIterator = this.indexOutInHash.values().iterator();
/* 715 */     IndexOutIn[] ioiArray = new IndexOutIn[n];
/*     */ 
/* 717 */     for (int k = 0; k < n; k++)
/*     */     {
/* 719 */       IndexOutIn oldIOI = (IndexOutIn)ioiIterator.next();
/* 720 */       int index = oldIOI.index;
/* 721 */       Bag copyOutBag = oldIOI.out == null ? new Bag() : new Bag(oldIOI.out.numObjs);
/* 722 */       Bag copyInBag = this.directed ? new Bag(oldIOI.in.numObjs) : oldIOI.in == null ? new Bag() : copyOutBag;
/* 723 */       IndexOutIn clonedIOI = new IndexOutIn(oldIOI.index, copyOutBag, copyInBag);
/* 724 */       clone.indexOutInHash.put(this.allNodes.objs[index], clonedIOI);
/* 725 */       ioiArray[k] = oldIOI;
/*     */     }
/*     */ 
/* 730 */     for (int k = 0; k < n; k++)
/*     */     {
/* 732 */       IndexOutIn oldIOI = ioiArray[k];
/* 733 */       Object node_k = this.allNodes.objs[oldIOI.index];
/* 734 */       if (oldIOI.out != null)
/* 735 */         for (int i = 0; i < oldIOI.out.numObjs; i++)
/*     */         {
/* 737 */           Edge e = (Edge)oldIOI.out.objs[i];
/* 738 */           if ((this.directed) || (e.from == node_k))
/* 739 */             clone.addEdge(new Edge(e.from, e.to, e.info));
/*     */         }
/*     */     }
/* 742 */     return clone;
/*     */   }
/*     */ 
/*     */   public Network getGraphComplement(boolean allowSelfLoops)
/*     */   {
/* 755 */     Network complement = new Network(this.directed);
/* 756 */     complement.allNodes.addAll(this.allNodes);
/* 757 */     int n = this.allNodes.numObjs;
/*     */ 
/* 759 */     Iterator ioiIterator = this.indexOutInHash.values().iterator();
/* 760 */     IndexOutIn[] ioiArray = new IndexOutIn[n];
/* 761 */     int maxDegree = n - 1 + (allowSelfLoops ? 1 : 0);
/*     */ 
/* 763 */     for (int k = 0; k < n; k++)
/*     */     {
/* 765 */       IndexOutIn oldIOI = (IndexOutIn)ioiIterator.next();
/* 766 */       int index = oldIOI.index;
/*     */ 
/* 769 */       Bag newOutBag = oldIOI.out == null ? new Bag(maxDegree) : new Bag(maxDegree - oldIOI.out.numObjs);
/*     */ 
/* 772 */       Bag newInBag = oldIOI.in == null ? new Bag(maxDegree) : !this.directed ? newOutBag : new Bag(maxDegree - oldIOI.in.numObjs);
/*     */ 
/* 777 */       IndexOutIn clonedIOI = new IndexOutIn(oldIOI.index, newOutBag, newInBag);
/* 778 */       complement.indexOutInHash.put(this.allNodes.objs[index], clonedIOI);
/*     */ 
/* 780 */       ioiArray[k] = oldIOI;
/*     */     }
/*     */ 
/* 784 */     boolean[] edgeArray = new boolean[n];
/* 785 */     for (int k = 0; k < n; k++)
/*     */     {
/* 787 */       IndexOutIn oldIOI = ioiArray[k];
/* 788 */       int nodeIndex = oldIOI.index;
/* 789 */       Object nodeObj = this.allNodes.objs[nodeIndex];
/* 790 */       for (int i = 0; i < n; i++) edgeArray[i] = true;
/* 791 */       if (!allowSelfLoops) edgeArray[nodeIndex] = false;
/*     */ 
/* 793 */       if (oldIOI.out != null) {
/* 794 */         for (int i = 0; i < oldIOI.out.numObjs; i++)
/*     */         {
/* 796 */           Edge e = (Edge)oldIOI.out.objs[i];
/* 797 */           Object otherNode = e.getOtherNode(nodeObj);
/* 798 */           int otherIndex = getNodeIndex(otherNode);
/* 799 */           edgeArray[otherIndex] = false;
/*     */         }
/*     */       }
/* 802 */       for (int i = 0; i < n; i++)
/* 803 */         if ((edgeArray[i] != 0) && ((this.directed) || (nodeIndex <= i)))
/* 804 */           complement.addEdge(nodeObj, this.allNodes.objs[i], null);
/*     */     }
/* 806 */     return complement;
/*     */   }
/*     */ 
/*     */   public Map buildMap(Map other)
/*     */   {
/* 812 */     return new HashMap(other);
/*     */   }
/*     */ 
/*     */   public Map buildMap(int size) {
/* 816 */     if (size <= 0) return new HashMap();
/* 817 */     return new HashMap(size);
/*     */   }
/*     */ 
/*     */   public static class IndexOutIn
/*     */     implements Serializable
/*     */   {
/*     */     public int index;
/*     */     public Bag out;
/*     */     public Bag in;
/*     */ 
/*     */     public IndexOutIn(int index, Bag out, Bag in)
/*     */     {
/* 633 */       this.index = index;
/* 634 */       this.out = out;
/* 635 */       this.in = in;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.network.Network
 * JD-Core Version:    0.6.2
 */