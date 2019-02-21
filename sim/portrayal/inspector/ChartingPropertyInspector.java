/*     */ package sim.portrayal.inspector;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.awt.event.WindowListener;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Properties;
/*     */ import sim.util.gui.DisclosurePanel;
/*     */ import sim.util.gui.LabelledList;
/*     */ import sim.util.gui.NumberTextField;
/*     */ import sim.util.media.chart.ChartGenerator;
/*     */ import sim.util.media.chart.SeriesAttributes;
/*     */ 
/*     */ public abstract class ChartingPropertyInspector extends PropertyInspector
/*     */ {
/*     */   protected ChartGenerator generator;
/*  46 */   double lastTime = -1.0D;
/*     */   SeriesAttributes seriesAttributes;
/*     */   public static final String chartKey = "sim.portrayal.inspector.ChartingPropertyInspector";
/*     */   protected static final int AGGREGATIONMETHOD_CURRENT = 0;
/*     */   protected static final int AGGREGATIONMETHOD_MAX = 1;
/*     */   protected static final int AGGREGATIONMETHOD_MIN = 2;
/*     */   protected static final int AGGREGATIONMETHOD_MEAN = 3;
/*     */   protected static final int REDRAW_ALWAYS = 0;
/*     */   protected static final int REDRAW_TENTH_SEC = 1;
/*     */   protected static final int REDRAW_HALF_SEC = 2;
/*     */   protected static final int REDRAW_ONE_SEC = 3;
/*     */   protected static final int REDRAW_TWO_SECS = 4;
/*     */   protected static final int REDRAW_FIVE_SECS = 5;
/*     */   protected static final int REDRAW_TEN_SECS = 6;
/*     */   protected static final int REDRAW_DONT = 7;
/*     */   GlobalAttributes globalAttributes;
/* 304 */   JFrame chartFrame = null;
/*     */ 
/* 341 */   boolean updatedOnceAlready = false;
/*     */ 
/*     */   public ChartGenerator getGenerator()
/*     */   {
/*  45 */     return this.generator;
/*     */   }
/*     */ 
/*     */   protected abstract boolean validChartGenerator(ChartGenerator paramChartGenerator);
/*     */ 
/*     */   protected abstract ChartGenerator createNewGenerator();
/*     */ 
/*     */   protected abstract void updateSeries(double paramDouble1, double paramDouble2);
/*     */ 
/*     */   protected boolean includeAggregationMethodAttributes()
/*     */   {
/*  68 */     return true;
/*     */   }
/*     */   public SeriesAttributes getSeriesAttributes() {
/*  71 */     return this.seriesAttributes;
/*     */   }
/*     */ 
/*     */   public ChartingPropertyInspector(Properties properties, int index, Frame parent, GUIState simulation)
/*     */   {
/*  79 */     super(properties, index, parent, simulation);
/*  80 */     this.generator = chartToUse(properties.getName(index), parent, simulation);
/*  81 */     setValidInspector(this.generator != null);
/*     */ 
/*  83 */     if (isValidInspector())
/*     */     {
/*  85 */       this.globalAttributes = findGlobalAttributes();
/*     */ 
/*  87 */       WindowListener wl = new WindowListener() {
/*     */         public void windowActivated(WindowEvent e) {
/*     */         }
/*  90 */         public void windowClosed(WindowEvent e) { if (ChartingPropertyInspector.this.stopper != null) ChartingPropertyInspector.this.stopper.stop();
/*     */         }
/*     */ 
/*     */         public void windowClosing(WindowEvent e)
/*     */         {
/*     */         }
/*     */ 
/*     */         public void windowDeactivated(WindowEvent e)
/*     */         {
/*     */         }
/*     */ 
/*     */         public void windowDeiconified(WindowEvent e)
/*     */         {
/*     */         }
/*     */ 
/*     */         public void windowIconified(WindowEvent e)
/*     */         {
/*     */         }
/*     */ 
/*     */         public void windowOpened(WindowEvent e)
/*     */         {
/*     */         }
/*     */       };
/*  97 */       this.generator.getFrame().addWindowListener(wl);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ChartingPropertyInspector(Properties properties, int index, GUIState simulation, ChartGenerator generator)
/*     */   {
/* 109 */     super(properties, index, null, simulation);
/*     */ 
/* 111 */     if (generator != null)
/*     */     {
/* 113 */       if (!validChartGenerator(generator))
/* 114 */         throw new RuntimeException("Invalid generator: " + generator);
/* 115 */       this.generator = generator;
/*     */     }
/*     */     else {
/* 118 */       this.generator = createNewChart(simulation);
/*     */     }
/*     */ 
/* 121 */     WindowListener wl = new WindowListener() {
/*     */       public void windowActivated(WindowEvent e) {
/*     */       }
/*     */       public void windowClosed(WindowEvent e) {  } 
/* 125 */       public void windowClosing(WindowEvent e) { if (ChartingPropertyInspector.this.stopper != null) ChartingPropertyInspector.this.stopper.stop();
/*     */       }
/*     */ 
/*     */       public void windowDeactivated(WindowEvent e)
/*     */       {
/*     */       }
/*     */ 
/*     */       public void windowDeiconified(WindowEvent e)
/*     */       {
/*     */       }
/*     */ 
/*     */       public void windowIconified(WindowEvent e)
/*     */       {
/*     */       }
/*     */ 
/*     */       public void windowOpened(WindowEvent e)
/*     */       {
/*     */       }
/*     */     };
/* 131 */     this.generator.getFrame().addWindowListener(wl);
/*     */ 
/* 133 */     this.globalAttributes = findGlobalAttributes();
/* 134 */     setValidInspector(this.generator != null);
/*     */   }
/*     */ 
/*     */   GlobalAttributes findGlobalAttributes()
/*     */   {
/* 140 */     if (this.generator == null) return null;
/* 141 */     int len = this.generator.getNumGlobalAttributes();
/* 142 */     for (int i = 0; i < len; i++)
/*     */     {
/* 145 */       if ((this.generator.getGlobalAttribute(i) instanceof DisclosurePanel))
/*     */       {
/* 147 */         DisclosurePanel pan = (DisclosurePanel)this.generator.getGlobalAttribute(i);
/* 148 */         if ((pan.getDisclosedComponent() instanceof GlobalAttributes))
/* 149 */           return (GlobalAttributes)pan.getDisclosedComponent();
/*     */       }
/*     */     }
/* 152 */     return null;
/*     */   }
/*     */ 
/*     */   protected Bag getCharts(GUIState simulation)
/*     */   {
/* 160 */     Bag c = (Bag)simulation.storage.get("sim.portrayal.inspector.ChartingPropertyInspector");
/* 161 */     if (c == null)
/*     */     {
/* 163 */       c = new Bag();
/* 164 */       simulation.storage.put("sim.portrayal.inspector.ChartingPropertyInspector", c);
/*     */     }
/* 166 */     return c;
/*     */   }
/*     */ 
/*     */   ChartGenerator chartToUse(String sName, Frame parent, GUIState simulation)
/*     */   {
/* 172 */     Bag charts = new Bag(getCharts(simulation));
/*     */ 
/* 175 */     for (int i = 0; i < charts.numObjs; i++)
/*     */     {
/* 177 */       ChartGenerator g = (ChartGenerator)charts.objs[i];
/* 178 */       if (!validChartGenerator(g)) {
/* 179 */         charts.remove(g); i--;
/* 180 */         System.err.println(g);
/*     */       }
/*     */     }
/*     */ 
/* 184 */     if (charts.numObjs == 0) {
/* 185 */       return createNewChart(simulation);
/*     */     }
/*     */ 
/* 188 */     JPanel p = new JPanel();
/* 189 */     p.setLayout(new BorderLayout());
/*     */ 
/* 191 */     String[] chartNames = new String[charts.numObjs + 1];
/*     */ 
/* 193 */     chartNames[0] = "[Create a New Chart]";
/* 194 */     for (int i = 0; i < charts.numObjs; i++) {
/* 195 */       chartNames[(i + 1)] = ((ChartGenerator)(ChartGenerator)charts.objs[i]).getTitle();
/*     */     }
/*     */ 
/* 198 */     JPanel panel2 = new JPanel();
/* 199 */     panel2.setLayout(new BorderLayout());
/* 200 */     panel2.setBorder(new TitledBorder("Plot on Chart..."));
/* 201 */     JComboBox encoding = new JComboBox(chartNames);
/* 202 */     panel2.add(encoding, "Center");
/* 203 */     p.add(panel2, "South");
/*     */ 
/* 206 */     if (JOptionPane.showConfirmDialog(parent, p, "Create a New Chart...", 2) != 0)
/*     */     {
/* 208 */       return null;
/*     */     }
/* 210 */     if (encoding.getSelectedIndex() == 0) {
/* 211 */       return createNewChart(simulation);
/*     */     }
/* 213 */     return (ChartGenerator)charts.objs[(encoding.getSelectedIndex() - 1)];
/*     */   }
/*     */ 
/*     */   public GlobalAttributes getGlobalAttributes()
/*     */   {
/* 234 */     return this.globalAttributes;
/*     */   }
/*     */ 
/*     */   ChartGenerator createNewChart(GUIState simulation)
/*     */   {
/* 308 */     this.generator = createNewGenerator();
/* 309 */     this.globalAttributes = new GlobalAttributes();
/* 310 */     DisclosurePanel pan = new DisclosurePanel(this.globalAttributes.title, this.globalAttributes);
/* 311 */     this.generator.addGlobalAttribute(pan);
/*     */ 
/* 313 */     getCharts(simulation).add(this.generator);
/* 314 */     this.chartFrame = this.generator.createFrame(true);
/* 315 */     this.chartFrame.setDefaultCloseOperation(2);
/*     */ 
/* 317 */     WindowListener wl = new WindowListener() {
/*     */       public void windowActivated(WindowEvent e) {
/*     */       }
/*     */       public void windowClosed(WindowEvent e) {  } 
/* 321 */       public void windowClosing(WindowEvent e) { ChartingPropertyInspector.this.generator.quit(); }
/*     */ 
/*     */ 
/*     */       public void windowDeactivated(WindowEvent e)
/*     */       {
/*     */       }
/*     */ 
/*     */       public void windowDeiconified(WindowEvent e)
/*     */       {
/*     */       }
/*     */ 
/*     */       public void windowIconified(WindowEvent e)
/*     */       {
/*     */       }
/*     */ 
/*     */       public void windowOpened(WindowEvent e)
/*     */       {
/*     */       }
/*     */     };
/* 327 */     this.chartFrame.addWindowListener(wl);
/* 328 */     this.chartFrame.setVisible(true);
/* 329 */     return this.generator;
/*     */   }
/*     */ 
/*     */   static String ensureFileEndsWith(String filename, String ending)
/*     */   {
/* 336 */     if (filename.regionMatches(false, filename.length() - ending.length(), ending, 0, ending.length()))
/* 337 */       return filename;
/* 338 */     return filename + ending;
/*     */   }
/*     */ 
/*     */   protected boolean isAlwaysUpdateable()
/*     */   {
/* 349 */     return true;
/*     */   }
/*     */ 
/*     */   public void updateInspector() {
/* 353 */     double time = this.simulation.state.schedule.getTime();
/*     */ 
/* 359 */     if (((time >= 0.0D) && (time < (1.0D / 0.0D))) || ((isAlwaysUpdateable()) && ((this.lastTime < time) || (!this.updatedOnceAlready))))
/*     */     {
/* 362 */       this.updatedOnceAlready = true;
/*     */ 
/* 365 */       updateSeries(time, this.lastTime);
/* 366 */       this.lastTime = time;
/*     */ 
/* 369 */       switch (this.globalAttributes.redraw)
/*     */       {
/*     */       case 0:
/* 372 */         this.generator.update(this.simulation.state.schedule.getSteps(), true);
/* 373 */         break;
/*     */       case 1:
/* 375 */         this.generator.updateChartWithin(this.simulation.state.schedule.getSteps(), 100L);
/* 376 */         break;
/*     */       case 2:
/* 378 */         this.generator.updateChartWithin(this.simulation.state.schedule.getSteps(), 500L);
/* 379 */         break;
/*     */       case 3:
/* 381 */         this.generator.updateChartWithin(this.simulation.state.schedule.getSteps(), 1000L);
/* 382 */         break;
/*     */       case 4:
/* 384 */         this.generator.updateChartWithin(this.simulation.state.schedule.getSteps(), 2000L);
/* 385 */         break;
/*     */       case 5:
/* 387 */         this.generator.updateChartWithin(this.simulation.state.schedule.getSteps(), 5000L);
/* 388 */         break;
/*     */       case 6:
/* 390 */         this.generator.updateChartWithin(this.simulation.state.schedule.getSteps(), 10000L);
/* 391 */         break;
/*     */       case 7:
/* 393 */         break;
/*     */       default:
/* 395 */         throw new RuntimeException("Unknown redraw time specified.");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean shouldCreateFrame()
/*     */   {
/* 402 */     return false;
/*     */   }
/*     */ 
/*     */   public Stoppable reviseStopper(Stoppable stopper)
/*     */   {
/* 407 */     final Stoppable newStopper = super.reviseStopper(stopper);
/* 408 */     return new Stoppable()
/*     */     {
/*     */       public void stop()
/*     */       {
/* 412 */         if (newStopper != null) newStopper.stop();
/*     */ 
/* 414 */         ChartingPropertyInspector.this.generator.stopMovie();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public void disposeFrame()
/*     */   {
/* 421 */     if (this.chartFrame != null)
/* 422 */       this.chartFrame.dispose();
/* 423 */     this.chartFrame = null;
/*     */   }
/*     */ 
/*     */   protected class GlobalAttributes extends JPanel
/*     */   {
/* 239 */     public long interval = 1L;
/* 240 */     public int aggregationMethod = 0;
/* 241 */     public int redraw = 2;
/* 242 */     String title = "";
/*     */ 
/*     */     public GlobalAttributes()
/*     */     {
/* 246 */       setLayout(new BorderLayout());
/*     */ 
/* 248 */       this.title = (ChartingPropertyInspector.this.includeAggregationMethodAttributes() ? "Add Data..." : "Redraw");
/* 249 */       LabelledList list = new LabelledList(this.title);
/* 250 */       add(list, "Center");
/*     */ 
/* 252 */       if (ChartingPropertyInspector.this.includeAggregationMethodAttributes())
/*     */       {
/* 254 */         NumberTextField stepsField = new NumberTextField(1.0D, true)
/*     */         {
/*     */           public double newValue(double value)
/*     */           {
/* 258 */             value = ()value;
/* 259 */             if (value <= 0.0D) return this.currentValue;
/*     */ 
/* 262 */             ChartingPropertyInspector.GlobalAttributes.this.interval = (()value);
/* 263 */             return value;
/*     */           }
/*     */         };
/* 268 */         list.addLabelled("Every", stepsField);
/* 269 */         list.addLabelled("", new JLabel("...Timesteps"));
/*     */ 
/* 271 */         String[] optionsLabel = { "Current", "Maximum", "Minimum", "Mean" };
/* 272 */         final JComboBox optionsBox = new JComboBox(optionsLabel);
/* 273 */         optionsBox.setSelectedIndex(this.aggregationMethod);
/* 274 */         optionsBox.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 279 */             ChartingPropertyInspector.GlobalAttributes.this.aggregationMethod = optionsBox.getSelectedIndex();
/*     */           }
/*     */         });
/* 282 */         list.addLabelled("Using", optionsBox);
/*     */       }
/*     */ 
/* 285 */       String[] optionsLabel2 = { "When Adding Data", "Every 0.1 Seconds", "Every 0.5 Seconds", "Every Second", "Every 2 Seconds", "Every 5 Seconds", "Every 10 Seconds", "Never" };
/*     */ 
/* 287 */       final JComboBox optionsBox2 = new JComboBox(optionsLabel2);
/* 288 */       optionsBox2.setSelectedIndex(this.redraw);
/* 289 */       optionsBox2.addActionListener(new ActionListener()
/*     */       {
/*     */         public void actionPerformed(ActionEvent e)
/*     */         {
/* 294 */           ChartingPropertyInspector.GlobalAttributes.this.redraw = optionsBox2.getSelectedIndex();
/* 295 */           ChartingPropertyInspector.this.generator.update(-2L, false);
/*     */         }
/*     */       });
/* 298 */       if (ChartingPropertyInspector.this.includeAggregationMethodAttributes())
/* 299 */         list.addLabelled("Redraw", optionsBox2);
/* 300 */       else list.add(optionsBox2);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.inspector.ChartingPropertyInspector
 * JD-Core Version:    0.6.2
 */