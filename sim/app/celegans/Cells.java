/*     */ package sim.app.celegans;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Scanner;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ 
/*     */ public class Cells
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public HashMap cell_dictionary;
/*     */   public ArrayList roots;
/*     */   int num_processed_cells;
/*     */   public Cell P0;
/*     */ 
/*     */   public Cells()
/*     */   {
/*  41 */     this.cell_dictionary = new HashMap(300);
/*     */ 
/*  45 */     this.roots = new ArrayList();
/*  46 */     this.num_processed_cells = 0;
/*     */     try
/*     */     {
/*  49 */       Reader r = new InputStreamReader(new GZIPInputStream(Cells.class.getResourceAsStream("cells.ace4.gz")));
/*  50 */       readCells(r);
/*  51 */       r.close();
/*  52 */       postProcess();
/*     */     } catch (IOException e) {
/*  54 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void postProcess()
/*     */   {
/*  68 */     System.out.println("-----Assigning Cell Types, and Parents to equivalent-origin cells");
/*  69 */     Iterator cells = this.cell_dictionary.values().iterator();
/*  70 */     while (cells.hasNext())
/*     */     {
/*  72 */       Cell cell = (Cell)cells.next();
/*     */ 
/*  74 */       if (cell.official_name.equals("P0")) {
/*  75 */         this.P0 = cell;
/*     */       }
/*  77 */       if ((cell.parent == null) && (!cell.official_name.equals("P0")) && (cell.num_equivalence_origin == 0))
/*  78 */         System.out.println("Whoa!  This ain't right: " + cell.official_name + "Has no parent.");
/*  79 */       if ((cell.parent == null) && (cell.num_equivalence_origin == 0)) this.roots.add(cell);
/*     */ 
/*  81 */       if (cell.num_equivalence_origin != 0)
/*     */       {
/*  83 */         cell.type = 3;
/*  84 */         cell.parent = cell.equivalence_origin[0];
/*  85 */         if (cell.parent.equivalence_fate[0] != cell)
/*  86 */           cell.parent.equivalence_fate[0].parent = cell.equivalence_origin[1];
/*  87 */         else cell.parent.equivalence_fate[1].parent = cell.equivalence_origin[1];
/*     */       }
/*  89 */       else if ((cell.official_name.equals("P0")) || (cell.official_name.equals("P1'")) || (cell.official_name.equals("P2'")) || (cell.official_name.equals("AB")) || (cell.official_name.equals("P3'")) || (cell.official_name.equals("P4'")))
/*     */       {
/*  95 */         cell.type = 0;
/*  96 */       } else if ((cell.official_name.equals("Z3")) || (cell.official_name.equals("Z2")))
/*     */       {
/*  98 */         cell.type = 4;
/*  99 */       } else if (cell.birthday < 500) {
/* 100 */         cell.type = 2; } else {
/* 101 */         cell.type = 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 106 */     System.out.println("-----Assigning Birthdays and Deaths");
/* 107 */     cells = this.roots.iterator();
/* 108 */     while (cells.hasNext())
/*     */     {
/* 110 */       Cell cell = (Cell)cells.next();
/* 111 */       cell.postProcessBirthday(true);
/*     */     }
/* 113 */     cells = this.roots.iterator();
/* 114 */     while (cells.hasNext())
/*     */     {
/* 116 */       Cell cell = (Cell)cells.next();
/* 117 */       cell.postProcessDeathDay(true);
/*     */     }
/*     */ 
/* 123 */     System.out.println("-----Assigning Locations");
/* 124 */     cells = this.roots.iterator();
/* 125 */     while (cells.hasNext())
/*     */     {
/* 127 */       Cell cell = (Cell)cells.next();
/* 128 */       cell.postProcessLocation(true);
/*     */     }
/*     */ 
/* 135 */     cells = this.roots.iterator();
/* 136 */     while (cells.hasNext())
/*     */     {
/* 138 */       Cell cell = (Cell)cells.next();
/* 139 */       cell.modifyLocations(-30.0D, -20.0D, -20.0D);
/*     */     }
/*     */ 
/* 143 */     cells = this.roots.iterator();
/* 144 */     while (cells.hasNext())
/*     */     {
/* 146 */       Cell cell = (Cell)cells.next();
/* 147 */       cell.setVolume(1.0F);
/*     */     }
/*     */ 
/* 152 */     System.out.println("-----Finished PostProcessing");
/*     */   }
/*     */ 
/*     */   public void readCells(Reader r)
/*     */   {
/* 163 */     Cell currentCell = null;
/* 164 */     ArrayList v = new ArrayList();
/* 165 */     this.num_processed_cells = 0;
/* 166 */     Scanner d = new Scanner(r);
/* 167 */     System.out.println("-----Loading 2237 Cells....");
/* 168 */     System.out.println();
/*     */     try
/*     */     {
/* 172 */       while (readCellLine(d, v) != -1)
/* 173 */         currentCell = processCellLine(currentCell, v);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 177 */       System.out.println(e.getMessage());
/*     */     }
/* 179 */     System.out.println(); System.out.println("-----Phew! Finally finished loading...");
/*     */   }
/*     */ 
/*     */   int readCellLine(Scanner i, ArrayList v)
/*     */     throws IOException
/*     */   {
/* 187 */     v.clear();
/* 188 */     if (!i.hasNextLine()) return -1;
/* 189 */     String s = i.nextLine();
/*     */ 
/* 191 */     int pos = 0;
/*     */ 
/* 193 */     int size = s.length();
/*     */     while (true)
/*     */     {
/* 196 */       int newpos = s.indexOf('|', pos);
/* 197 */       if ((newpos < 0) || (newpos >= size))
/*     */         break;
/* 199 */       if (newpos == pos) v.add(""); else
/* 200 */         v.add(s.substring(pos, newpos));
/* 201 */       pos = newpos + 1;
/*     */     }
/* 203 */     if (pos != size)
/*     */     {
/* 206 */       v.add(s.substring(pos, size));
/*     */     }
/* 208 */     return 1;
/*     */   }
/*     */ 
/*     */   public Cell processCellLine(Cell current, ArrayList v)
/*     */     throws NumberFormatException
/*     */   {
/* 216 */     if (v == null) return current;
/* 217 */     if (v.size() == 0) return current;
/* 218 */     String title = (String)v.get(0);
/* 219 */     if (title.equals("Cell"))
/*     */     {
/* 221 */       current = fetchCell((String)v.get(2));
/* 222 */       if (this.num_processed_cells % 100 == 0)
/* 223 */         System.out.println(this.num_processed_cells + 1 + ": " + current.official_name);
/* 224 */       this.num_processed_cells += 1;
/*     */     }
/*     */     else {
/* 227 */       if (current == null) return current;
/*     */ 
/* 231 */       if (title.equals("Parent"))
/*     */       {
/* 233 */         current.parent = fetchCell((String)v.get(1));
/* 234 */         if (current.parent.num_children >= 2)
/*     */         {
/* 239 */           Cell[] tmp = new Cell[current.parent.num_children + 1];
/*     */ 
/* 245 */           System.arraycopy(current.parent.daughters, 0, tmp, 0, current.parent.num_children);
/* 246 */           current.parent.daughters = tmp;
/* 247 */           System.out.print(current.parent.official_name + " has more than 2 children: ");
/* 248 */           for (int zz = 0; zz < current.parent.num_children; zz++)
/* 249 */             System.out.print(current.parent.daughters[zz].official_name + ", ");
/* 250 */           System.out.println("and " + current.official_name);
/*     */         }
/* 252 */         current.parent.daughters[(current.parent.num_children++)] = current;
/*     */       }
/* 254 */       else if (!title.equals("Daughter"))
/*     */       {
/* 258 */         if (title.equals("Lineage_name"))
/*     */         {
/* 260 */           current.lineage_name = ((String)v.get(1));
/*     */         }
/* 262 */         else if (title.equals("Embryo_division_time"))
/*     */         {
/* 264 */           current.embryo_division_time = Double.valueOf((String)v.get(1)).doubleValue();
/*     */         }
/* 266 */         else if (title.equals("Reconstruction"))
/*     */         {
/* 269 */           if (v.get(2).equals("Birth"))
/*     */           {
/* 271 */             current.time_born = Double.valueOf((String)v.get(3)).doubleValue();
/*     */           }
/* 273 */           else if (v.get(2).equals("Timepoint"))
/*     */           {
/* 275 */             current.pushLocation(Double.valueOf((String)v.get(5)).doubleValue(), Double.valueOf((String)v.get(6)).doubleValue(), Double.valueOf((String)v.get(7)).doubleValue(), Double.valueOf((String)v.get(3)).doubleValue());
/*     */           }
/*     */ 
/*     */         }
/* 282 */         else if (title.equals("Neurodata"))
/*     */         {
/* 295 */           if (v.get(3).equals("N2U"))
/*     */           {
/* 297 */             if ((v.get(2).equals("Send")) || (v.get(2).equals("Send_joint")))
/*     */             {
/* 299 */               Synapse s = new Synapse();
/* 300 */               Cell to = fetchCell((String)v.get(1));
/* 301 */               s.to = to;
/* 302 */               s.from = current;
/* 303 */               s.type = Synapse.type_chemical;
/* 304 */               s.number = Integer.valueOf((String)v.get(4)).intValue();
/* 305 */               current.synapses.add(s);
/* 306 */               to.synapses.add(s);
/*     */             }
/* 308 */             else if (v.get(2).equals("Gap_junction"))
/*     */             {
/* 310 */               Synapse s = new Synapse();
/* 311 */               Cell to = fetchCell((String)v.get(1));
/* 312 */               s.to = to;
/* 313 */               s.from = current;
/* 314 */               s.type = Synapse.type_gap;
/* 315 */               s.number = Integer.valueOf((String)v.get(4)).intValue();
/* 316 */               current.synapses.add(s);
/* 317 */               to.synapses.add(s);
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/* 322 */         else if (title.equals("Equivalence_origin"))
/*     */         {
/* 324 */           Cell equiv = fetchCell((String)v.get(1));
/* 325 */           current.equivalence_origin[(current.num_equivalence_origin++)] = equiv;
/* 326 */           equiv.equivalence_fate[(equiv.num_equivalence_fate++)] = current;
/*     */         }
/* 328 */         else if (!title.equals("Equivalence_fate"))
/*     */         {
/* 332 */           if (title.equals("Cell_group"))
/*     */           {
/* 334 */             current.cellGroup = fetchGroup((String)v.get(1));
/*     */           }
/* 336 */           else if (title.equals("Expr_pattern"))
/*     */           {
/* 338 */             current.expressionPattern = fetchPattern((String)v.get(1));
/*     */           }
/* 340 */           else if (title.equals("Fate"))
/*     */           {
/* 342 */             current.fate = fetchFate((String)v.get(1));
/*     */           }
/* 344 */           else if (title.equals("Remark"))
/*     */           {
/* 346 */             if (current.remark.equals("")) current.remark = ((String)v.get(1)); else {
/* 347 */               current.remark = (current.remark + "; " + (String)v.get(1));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 354 */     return current;
/*     */   }
/*     */ 
/*     */   public Cell fetchCell(String key)
/*     */   {
/* 369 */     if (this.cell_dictionary.containsKey(key)) {
/* 370 */       return (Cell)this.cell_dictionary.get(key);
/*     */     }
/*     */ 
/* 374 */     Cell c = new Cell();
/* 375 */     c.official_name = key;
/* 376 */     this.cell_dictionary.put(key, c);
/* 377 */     return c;
/*     */   }
/*     */ 
/*     */   public int fetchGroup(String key)
/*     */   {
/* 386 */     for (int i = 0; i < Cell.cellGroups.length; i++)
/* 387 */       if (Cell.cellGroups[i].equalsIgnoreCase(key))
/* 388 */         return i;
/* 389 */     System.out.println("Unknown cell group: " + key);
/* 390 */     return 0;
/*     */   }
/*     */ 
/*     */   public int fetchPattern(String key)
/*     */   {
/* 398 */     for (int i = 0; i < Cell.expressionPatterns.length; i++)
/* 399 */       if (Cell.expressionPatterns[i].equalsIgnoreCase(key))
/* 400 */         return i;
/* 401 */     System.out.println("Unknown expression pattern: " + key);
/* 402 */     return 0;
/*     */   }
/*     */ 
/*     */   public int fetchFate(String key)
/*     */   {
/* 409 */     for (int i = 0; i < Cell.fates.length; i++)
/* 410 */       if (Cell.fates[i].equalsIgnoreCase(key))
/* 411 */         return i;
/* 412 */     System.out.println("Unknown fate: " + key);
/* 413 */     return 0;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.celegans.Cells
 * JD-Core Version:    0.6.2
 */