/*     */ package sim.app.celegans;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.field.continuous.Continuous3D;
/*     */ import sim.field.network.Network;
/*     */ import sim.util.Double3D;
/*     */ 
/*     */ public class Cell
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public Cell parent;
/*     */   public int expressionPattern;
/* 136 */   static final String[] expressionPatterns = { "", "Expr8", "Expr12", "Expr15", "Expr21", "Expr24", "Expr28", "Expr29", "Expr35", "Expr38", "Expr29", "Expr49", "Expr56", "Expr67", "Expr68" };
/*     */   public int cellGroup;
/* 140 */   static final String[] cellGroups = { "", "GLR", "e1", "e2", "gon_herm_anch", "gon_herm_dish_A", "gon_herm_dish_P", "gon_herm_dut", "gon_herm_prsh_A", "gon_herm_prsh_P", "gon_herm_spth_A", "gon_herm_spth_P", "gon_herm_sujn_A", "gon_herm_sujn_P", "gon_herm_vut", "hyp10", "hyp3", "hyp4", "hyp5", "hyp6", "hyp7", "hyp8/9", "int_emb", "int_post", "m2", "m4", "m6", "m7", "mu_bod", "rectal epithelium", "se_herm", "seam", "um1", "um2", "vm1", "vm2", "vulvaA", "vulvaB", "vulvaC", "vulvaD", "vulvaE", "vulvaF" };
/*     */   public int fate;
/* 144 */   static final String[] fates = { "", "Dies", "Muscle", "Hypodermis", "Intestine", "Neuron", "Pharynx" };
/*     */   public int type;
/* 152 */   static final String[] types = { "Founding / Unknown", "Preembryonic", "Postembryonic", "Postembryonic Dual Origin", "Postembryonic Unknown" };
/*     */ 
/* 248 */   public Cell[] daughters = new Cell[2];
/*     */ 
/* 253 */   public int num_children = 0;
/*     */   public int birthday;
/*     */   public int death_day;
/* 191 */   public String official_name = "";
/*     */ 
/* 195 */   public String lineage_name = "";
/*     */ 
/* 199 */   public String remark = "";
/*     */   public double[] location_x;
/*     */   public double[] location_y;
/*     */   public double[] location_z;
/*     */   public double[] location_t;
/*     */   public int location_size;
/* 247 */   public ArrayList synapses = new ArrayList(0);
/*     */ 
/* 246 */   public float radius = 1.0F;
/*     */ 
/* 255 */   public double[] split_radius_distance = new double[3];
/*     */ 
/* 249 */   public Cell[] equivalence_origin = new Cell[2];
/* 250 */   public Cell[] equivalence_fate = new Cell[2];
/*     */   public int num_equivalence_fate;
/*     */   public int num_equivalence_origin;
/* 251 */   public double embryo_division_time = -1.0D;
/* 252 */   public double time_born = -1.0D;
/*     */ 
/* 254 */   public int location_max = 0;
/*     */   public static final int initial_location_size = 4;
/*     */   public static final double initial_split_radius_distance = 2.0D;
/*     */   public static final int post_embryonic_birthday = 500;
/*     */   public static final int maximum_death_day = 1000;
/*     */   public static final char cell_type_preembryonic_unknown_position = '\000';
/*     */   public static final char cell_type_preembryonic = '\001';
/*     */   public static final char cell_type_postembryonic = '\002';
/*     */   public static final char cell_type_postembryonic_dual_origin = '\003';
/*     */   public static final char cell_type_postembryonic_unknown_position = '\004';
/* 759 */   double[] loc_xyz = new double[3];
/*     */   public Stoppable stopper;
/*     */ 
/*     */   public Cell getParent()
/*     */   {
/* 133 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public String getExpressionPattern() {
/* 137 */     return expressionPatterns[this.expressionPattern];
/*     */   }
/*     */ 
/*     */   public String cellGroup() {
/* 141 */     return cellGroups[this.cellGroup];
/*     */   }
/*     */ 
/*     */   public String getFate() {
/* 145 */     return fates[this.fate]; } 
/* 146 */   boolean isNeuron() { return this.fate == 5; }
/*     */ 
/*     */ 
/*     */   public String getType()
/*     */   {
/* 153 */     return types[this.type];
/*     */   }
/*     */ 
/*     */   public Cell[] getDaughters()
/*     */   {
/* 172 */     return this.daughters;
/*     */   }
/*     */ 
/*     */   public int getNumChildren()
/*     */   {
/* 177 */     return this.num_children;
/*     */   }
/*     */ 
/*     */   public int getBirthday() {
/* 181 */     return this.birthday;
/*     */   }
/*     */ 
/*     */   public int getDeathday()
/*     */   {
/* 189 */     return this.death_day;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 193 */     return this.official_name;
/*     */   }
/*     */ 
/*     */   public String getLineageName() {
/* 197 */     return this.lineage_name;
/*     */   }
/*     */   public String getRemark() {
/* 200 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public ArrayList getSynapses()
/*     */   {
/* 211 */     return this.synapses;
/*     */   }
/* 213 */   public float getRadius() { return this.radius; }
/*     */ 
/*     */ 
/*     */   public Cell()
/*     */   {
/*     */     double tmp121_120 = (this.split_radius_distance[2] = 2.0D); this.split_radius_distance[1] = tmp121_120; this.split_radius_distance[0] = tmp121_120;
/*     */   }
/*     */ 
/*     */   public void addSynapse(Synapse s)
/*     */   {
/* 266 */     for (int x = 0; x < this.synapses.size(); x++)
/*     */     {
/* 268 */       Synapse test = (Synapse)this.synapses.get(x);
/* 269 */       if ((s.from == test.from) && (s.to == test.to) && (s.type == test.type) && (s.type == Synapse.type_chemical))
/*     */       {
/* 272 */         return;
/* 273 */       }if (((s.from == test.from) && (s.to == test.to) && (s.type == Synapse.type_gap)) || ((s.from == test.to) && (s.to == test.from) && (s.type == Synapse.type_gap)))
/*     */       {
/* 276 */         return;
/*     */       }
/*     */     }
/* 279 */     this.synapses.add(s);
/*     */   }
/*     */ 
/*     */   public void setVolume(float vol)
/*     */   {
/* 289 */     this.radius = ((float)Math.pow(vol, 0.333333343267441D));
/*     */ 
/* 292 */     if (this.official_name.equalsIgnoreCase("P0"))
/*     */     {
/* 294 */       for (int x = 0; x < this.num_children; x++)
/*     */       {
/* 299 */         if (this.daughters[x].official_name.equalsIgnoreCase("AB"))
/* 300 */           this.daughters[x].setVolume(vol * 0.6F);
/* 301 */         else this.daughters[x].setVolume(vol * 0.4F);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 306 */       for (int x = 0; x < this.num_children; x++)
/*     */       {
/* 311 */         this.daughters[x].setVolume(vol / this.num_children);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public float getRadius(int timestamp)
/*     */   {
/* 337 */     if ((timestamp >= this.birthday) && (timestamp < this.death_day) && (this.location_size > 1) && (this.parent != null))
/*     */     {
/* 339 */       if (this.location_t[1] < this.location_t[0]) { System.out.println("Uh oh"); return this.parent.radius; }
/* 340 */       if (this.location_t[1] == this.location_t[0]) return this.parent.radius;
/* 341 */       if (timestamp < this.location_t[1]) {
/* 342 */         return (float)(this.radius + (this.parent.radius - this.radius) * (1.0D - (timestamp - this.location_t[0]) / (this.location_t[1] - this.location_t[0])));
/*     */       }
/*     */     }
/* 345 */     return this.radius;
/*     */   }
/*     */ 
/*     */   public boolean getLocation(int timestamp, double[] xyz)
/*     */   {
/* 354 */     if ((timestamp >= this.birthday) && (timestamp < this.death_day))
/*     */     {
/* 356 */       if (this.location_size == 0)
/*     */       {
/* 358 */         System.out.println("No location information for cell" + this.official_name);
/* 359 */         return false;
/*     */       }
/*     */ 
/* 363 */       if (this.location_t[0] > timestamp)
/*     */       {
/* 365 */         xyz[0] = this.location_x[0]; xyz[1] = this.location_y[0]; xyz[2] = this.location_z[0];
/* 366 */         return true;
/*     */       }
/* 368 */       for (int x = 0; x < this.location_size; x++)
/*     */       {
/* 370 */         if (this.location_t[x] == timestamp)
/*     */         {
/* 373 */           xyz[0] = this.location_x[x]; xyz[1] = this.location_y[x]; xyz[2] = this.location_z[x];
/* 374 */           return true;
/*     */         }
/* 376 */         if (x == this.location_size - 1)
/*     */         {
/* 378 */           xyz[0] = this.location_x[x]; xyz[1] = this.location_y[x]; xyz[2] = this.location_z[x];
/* 379 */           return true;
/*     */         }
/* 381 */         if ((this.location_t[x] < timestamp) && (this.location_t[(x + 1)] > timestamp))
/*     */         {
/* 384 */           double p = (timestamp - this.location_t[x]) / (this.location_t[(x + 1)] - this.location_t[x]);
/* 385 */           xyz[0] = (this.location_x[x] * (1.0D - p) + this.location_x[(x + 1)] * p);
/* 386 */           xyz[1] = (this.location_y[x] * (1.0D - p) + this.location_y[(x + 1)] * p);
/* 387 */           xyz[2] = (this.location_z[x] * (1.0D - p) + this.location_z[(x + 1)] * p);
/* 388 */           return true;
/*     */         }
/*     */       }
/*     */ 
/* 392 */       return true;
/*     */     }
/* 394 */     return false;
/*     */   }
/*     */ 
/*     */   public void sortLocation()
/*     */   {
/* 403 */     for (int x = 0; x < this.location_size; x++)
/*     */     {
/* 405 */       for (int y = x + 1; y < this.location_size; y++)
/*     */       {
/* 407 */         if (this.location_t[y] < this.location_t[x])
/*     */         {
/* 411 */           double swap = this.location_x[y]; this.location_x[y] = this.location_x[x]; this.location_x[x] = swap;
/* 412 */           swap = this.location_y[y]; this.location_y[y] = this.location_y[x]; this.location_y[x] = swap;
/* 413 */           swap = this.location_z[y]; this.location_z[y] = this.location_z[x]; this.location_z[x] = swap;
/* 414 */           swap = this.location_t[y]; this.location_t[y] = this.location_t[x]; this.location_t[x] = swap;
/*     */         }
/* 416 */         else if (this.location_t[y] == this.location_t[x]) {
/* 417 */           System.out.println("Identical Times: " + x + " " + y + " in " + this.official_name);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void pushLocation(double x, double y, double z, double t)
/*     */   {
/* 427 */     if (this.location_max == 0)
/*     */     {
/* 430 */       this.location_x = new double[4];
/* 431 */       this.location_y = new double[4];
/* 432 */       this.location_z = new double[4];
/* 433 */       this.location_t = new double[4];
/* 434 */       this.location_max = 4;
/* 435 */       this.location_size = 0;
/*     */     }
/*     */ 
/* 438 */     this.location_x[this.location_size] = x;
/* 439 */     this.location_y[this.location_size] = y;
/* 440 */     this.location_z[this.location_size] = z;
/* 441 */     this.location_t[this.location_size] = t;
/* 442 */     this.location_size += 1;
/* 443 */     if (this.location_size == this.location_max)
/*     */     {
/* 446 */       double[] new_location_x = new double[this.location_max * 2];
/* 447 */       double[] new_location_y = new double[this.location_max * 2];
/* 448 */       double[] new_location_z = new double[this.location_max * 2];
/* 449 */       double[] new_location_t = new double[this.location_max * 2];
/* 450 */       System.arraycopy(this.location_x, 0, new_location_x, 0, this.location_max);
/* 451 */       System.arraycopy(this.location_y, 0, new_location_y, 0, this.location_max);
/* 452 */       System.arraycopy(this.location_z, 0, new_location_z, 0, this.location_max);
/* 453 */       System.arraycopy(this.location_t, 0, new_location_t, 0, this.location_max);
/* 454 */       this.location_x = new_location_x;
/* 455 */       this.location_y = new_location_y;
/* 456 */       this.location_z = new_location_z;
/* 457 */       this.location_t = new_location_t;
/* 458 */       this.location_max *= 2;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void postProcessBirthday(boolean root)
/*     */   {
/* 480 */     if ((root) && (this.time_born == -1.0D)) this.birthday = -1;
/* 481 */     else if (this.time_born != -1.0D) this.birthday = ((int)this.time_born);
/* 482 */     else if (this.parent.embryo_division_time != -1.0D) this.birthday = ((int)this.parent.embryo_division_time);
/* 483 */     else if (this.parent.birthday >= 500) this.birthday = (this.parent.birthday + 1); else {
/* 484 */       this.birthday = 500;
/*     */     }
/*     */ 
/* 487 */     for (int z = 0; z < this.num_children; z++) this.daughters[z].postProcessBirthday(false);
/*     */ 
/* 490 */     this.birthday += 2;
/*     */   }
/*     */ 
/*     */   public void modifyLocations(double x, double y, double z)
/*     */   {
/* 500 */     for (int a = 0; a < this.location_size; a++)
/*     */     {
/* 502 */       this.location_x[a] += x;
/* 503 */       this.location_y[a] += y;
/* 504 */       this.location_z[a] += z;
/*     */     }
/*     */ 
/* 507 */     for (int j = 0; j < this.num_children; j++) this.daughters[j].modifyLocations(x, y, z);
/*     */   }
/*     */ 
/*     */   public void postProcessDeathDay(boolean root)
/*     */   {
/* 530 */     if (this.num_children != 0) this.death_day = this.daughters[0].birthday;
/*     */     else {
/* 532 */       this.death_day = 1000;
/*     */     }
/* 534 */     for (int z = 0; z < this.num_children; z++) this.daughters[z].postProcessDeathDay(false);
/*     */   }
/*     */ 
/*     */   public void postProcessLocation(boolean root)
/*     */   {
/* 571 */     sortLocation();
/*     */ 
/* 573 */     double birth_location_x = 0.0D;
/* 574 */     double birth_location_y = 0.0D;
/* 575 */     double birth_location_z = 0.0D;
/*     */ 
/* 578 */     if ((this.official_name.equals("P0")) || (this.official_name.equals("P1'")) || (this.official_name.equals("P2'")) || (this.official_name.equals("AB")) || (this.official_name.equals("P3'")) || (this.official_name.equals("P4'")) || (this.official_name.equals("Z3")) || (this.official_name.equals("Z2")))
/*     */     {
/* 587 */       if (this.official_name.equals("P0"))
/*     */       {
/* 589 */         birth_location_x = 30.0D;
/* 590 */         birth_location_y = 17.949999999999999D;
/* 591 */         birth_location_z = 13.6D;
/*     */       }
/* 593 */       else if (this.official_name.equals("P1'"))
/*     */       {
/* 595 */         birth_location_x = 45.0D;
/* 596 */         birth_location_y = 17.949999999999999D;
/* 597 */         birth_location_z = 13.6D;
/*     */       }
/* 599 */       else if (this.official_name.equals("P2'"))
/*     */       {
/* 601 */         birth_location_x = 54.0D;
/* 602 */         birth_location_y = 31.300000000000001D;
/* 603 */         birth_location_z = 14.4D;
/*     */       }
/* 605 */       else if (this.official_name.equals("AB"))
/*     */       {
/* 607 */         birth_location_x = 22.050000000000001D;
/* 608 */         birth_location_y = 17.949999999999999D;
/* 609 */         birth_location_z = 13.6D;
/*     */       }
/* 611 */       else if (this.official_name.equals("P3'"))
/*     */       {
/* 613 */         birth_location_x = 55.0D;
/* 614 */         birth_location_y = 25.0D;
/* 615 */         birth_location_z = 9.6D;
/*     */       }
/* 617 */       else if (this.official_name.equals("P4'"))
/*     */       {
/* 619 */         birth_location_x = 53.5D;
/* 620 */         birth_location_y = 26.0D;
/* 621 */         birth_location_z = 6.5D;
/*     */       }
/* 623 */       else if (this.official_name.equals("Z3"))
/*     */       {
/* 625 */         birth_location_x = 53.0D;
/* 626 */         birth_location_y = 23.0D;
/* 627 */         birth_location_z = 6.5D;
/*     */       }
/* 629 */       else if (this.official_name.equals("Z2"))
/*     */       {
/* 631 */         birth_location_x = 53.0D;
/* 632 */         birth_location_y = 29.0D;
/* 633 */         birth_location_z = 6.5D;
/*     */       }
/*     */       else
/*     */       {
/* 637 */         birth_location_x = 0.0D;
/* 638 */         birth_location_y = 0.0D;
/* 639 */         birth_location_z = 0.0D;
/*     */       }
/*     */ 
/* 643 */       pushLocation(birth_location_x, birth_location_y, birth_location_z, this.birthday + (this.death_day - this.birthday) / 3);
/*     */ 
/* 646 */       sortLocation();
/*     */     }
/* 650 */     else if (root)
/*     */     {
/* 653 */       System.out.println("Whoa! " + this.official_name + "is root, but has no location!");
/*     */ 
/* 655 */       birth_location_x = 0.0D;
/* 656 */       birth_location_y = 0.0D;
/* 657 */       birth_location_z = 0.0D;
/*     */     }
/*     */ 
/* 662 */     if ((!root) && (this.parent != null))
/*     */     {
/* 666 */       if ((this.location_size != 0) && (this.parent.location_size != 0))
/*     */       {
/* 669 */         birth_location_x = this.parent.location_x[(this.parent.location_size - 1)];
/* 670 */         birth_location_y = this.parent.location_y[(this.parent.location_size - 1)];
/* 671 */         birth_location_z = this.parent.location_z[(this.parent.location_size - 1)];
/*     */       }
/* 673 */       else if (this.location_size != 0)
/*     */       {
/* 675 */         birth_location_x = this.location_x[0];
/* 676 */         birth_location_y = this.location_y[0];
/* 677 */         birth_location_z = this.location_z[0];
/*     */       }
/*     */       else
/*     */       {
/* 681 */         birth_location_x = this.parent.location_x[(this.parent.location_size - 1)];
/* 682 */         birth_location_y = this.parent.location_y[(this.parent.location_size - 1)];
/* 683 */         birth_location_z = this.parent.location_z[(this.parent.location_size - 1)];
/*     */ 
/* 685 */         if (this.lineage_name.equals(""))
/*     */         {
/* 687 */           System.out.println("Whoa! " + this.official_name + "has no lineage name!");
/*     */ 
/* 689 */           this.lineage_name = this.official_name;
/* 690 */           char sc = this.lineage_name.charAt(this.lineage_name.length() - 1);
/* 691 */           if (sc == 'd') { this.split_radius_distance[1] = (this.parent.split_radius_distance[1] / 2.0D);
/* 692 */             birth_location_y -= this.split_radius_distance[1];
/* 693 */           } else if (sc == 'v') { this.split_radius_distance[1] = (this.parent.split_radius_distance[1] / 2.0D);
/* 694 */             birth_location_y += this.split_radius_distance[1];
/* 695 */           } else if (sc == 'a') { this.split_radius_distance[0] = (this.parent.split_radius_distance[0] / 2.0D);
/* 696 */             birth_location_x -= this.split_radius_distance[0];
/* 697 */           } else if (sc == 'p') { this.split_radius_distance[0] = (this.parent.split_radius_distance[0] / 2.0D);
/* 698 */             birth_location_x += this.split_radius_distance[0];
/* 699 */           } else if (sc == 'l') { this.split_radius_distance[2] = (this.parent.split_radius_distance[2] / 2.0D);
/* 700 */             birth_location_z -= this.split_radius_distance[2];
/* 701 */           } else if (sc == 'r') { this.split_radius_distance[2] = (this.parent.split_radius_distance[2] / 2.0D);
/* 702 */             birth_location_z += this.split_radius_distance[2]; } else {
/* 703 */             System.out.println("Whoa! Lineage name with no split characteristics: " + this.lineage_name + " (" + this.official_name + ")");
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 718 */           char sc = this.lineage_name.charAt(this.lineage_name.length() - 1);
/* 719 */           if (sc == 'd') { this.split_radius_distance[1] = (this.parent.split_radius_distance[1] / 2.0D);
/* 720 */             birth_location_y -= this.split_radius_distance[1];
/* 721 */           } else if (sc == 'v') { this.split_radius_distance[1] = (this.parent.split_radius_distance[1] / 2.0D);
/* 722 */             birth_location_y += this.split_radius_distance[1];
/* 723 */           } else if (sc == 'a') { this.split_radius_distance[0] = (this.parent.split_radius_distance[0] / 2.0D);
/* 724 */             birth_location_x -= this.split_radius_distance[0];
/* 725 */           } else if (sc == 'p') { this.split_radius_distance[0] = (this.parent.split_radius_distance[0] / 2.0D);
/* 726 */             birth_location_x += this.split_radius_distance[0];
/* 727 */           } else if (sc == 'l') { this.split_radius_distance[2] = (this.parent.split_radius_distance[2] / 2.0D);
/* 728 */             birth_location_z -= this.split_radius_distance[2];
/* 729 */           } else if (sc == 'r') { this.split_radius_distance[2] = (this.parent.split_radius_distance[2] / 2.0D);
/* 730 */             birth_location_z += this.split_radius_distance[2]; } else {
/* 731 */             System.out.println("Whoa! Lineage name with no split characteristics: " + this.lineage_name + " (" + this.official_name + ")");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 737 */     pushLocation(birth_location_x, birth_location_y, birth_location_z, this.birthday);
/*     */ 
/* 740 */     sortLocation();
/*     */ 
/* 743 */     for (int z = 0; z < this.num_children; z++) this.daughters[z].postProcessLocation(false);
/*     */   }
/*     */ 
/*     */   public void descendentsOf(Cell cell, HashMap add_here)
/*     */   {
/* 752 */     add_here.put(this.official_name, this);
/* 753 */     for (int z = 0; z < this.num_children; z++) this.daughters[z].descendentsOf(cell, add_here);
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/* 764 */     Celegans celegans = (Celegans)state;
/* 765 */     double time = state.schedule.getTime();
/* 766 */     if ((time >= this.death_day) && (time < (1.0D / 0.0D)))
/*     */     {
/* 768 */       this.stopper.stop();
/* 769 */       celegans.cells.remove(this);
/* 770 */       if (this.daughters != null)
/*     */       {
/* 772 */         for (int i = 0; i < this.num_children; i++)
/*     */         {
/* 774 */           this.daughters[i].stopper = state.schedule.scheduleRepeating(this.daughters[i]);
/* 775 */           this.daughters[i].step(state);
/* 776 */           if (this.daughters[i].isNeuron())
/*     */           {
/* 778 */             int size = this.daughters[i].synapses.size();
/* 779 */             for (int j = 0; j < size; j++)
/*     */             {
/* 781 */               Synapse s = (Synapse)this.daughters[i].synapses.get(j);
/* 782 */               if ((celegans.neurons.exists(s.to)) && (celegans.neurons.exists(s.from)))
/* 783 */                 celegans.synapses.addEdge(s.to, s.from, s);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 791 */       getLocation((int)time, this.loc_xyz);
/* 792 */       celegans.cells.setObjectLocation(this, new Double3D(this.loc_xyz[0], this.loc_xyz[1], this.loc_xyz[2]));
/* 793 */       if (isNeuron())
/* 794 */         celegans.neurons.setObjectLocation(this, new Double3D(this.loc_xyz[0], this.loc_xyz[1], this.loc_xyz[2])); 
/*     */     }
/*     */   }
/*     */ 
/* 798 */   public String toString() { return "Cell " + this.official_name; }
/*     */ 
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.celegans.Cell
 * JD-Core Version:    0.6.2
 */