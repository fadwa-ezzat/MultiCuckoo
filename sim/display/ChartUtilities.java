/*     */ package sim.display;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.jfree.data.xy.XYSeries;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.util.Valuable;
/*     */ import sim.util.media.chart.BarChartGenerator;
/*     */ import sim.util.media.chart.BarChartSeriesAttributes;
/*     */ import sim.util.media.chart.BoxPlotGenerator;
/*     */ import sim.util.media.chart.BoxPlotSeriesAttributes;
/*     */ import sim.util.media.chart.BubbleChartGenerator;
/*     */ import sim.util.media.chart.BubbleChartSeriesAttributes;
/*     */ import sim.util.media.chart.ChartGenerator;
/*     */ import sim.util.media.chart.HistogramGenerator;
/*     */ import sim.util.media.chart.HistogramSeriesAttributes;
/*     */ import sim.util.media.chart.PieChartGenerator;
/*     */ import sim.util.media.chart.PieChartSeriesAttributes;
/*     */ import sim.util.media.chart.ScatterPlotGenerator;
/*     */ import sim.util.media.chart.ScatterPlotSeriesAttributes;
/*     */ import sim.util.media.chart.TimeSeriesAttributes;
/*     */ import sim.util.media.chart.TimeSeriesChartGenerator;
/*     */ 
/*     */ public class ChartUtilities
/*     */ {
/*     */   public static TimeSeriesChartGenerator buildTimeSeriesChartGenerator(String title, String domainAxisLabel)
/*     */   {
/*  59 */     TimeSeriesChartGenerator chart = new TimeSeriesChartGenerator();
/*  60 */     if (title == null) title = "";
/*  61 */     chart.setTitle(title);
/*  62 */     if (domainAxisLabel == null) domainAxisLabel = "";
/*  63 */     chart.setXAxisLabel(domainAxisLabel);
/*  64 */     return chart;
/*     */   }
/*     */ 
/*     */   public static TimeSeriesChartGenerator buildTimeSeriesChartGenerator(GUIState state, String title, String domainAxisLabel)
/*     */   {
/*  70 */     TimeSeriesChartGenerator chart = buildTimeSeriesChartGenerator(title, domainAxisLabel);
/*  71 */     JFrame frame = chart.createFrame();
/*  72 */     frame.setVisible(true);
/*  73 */     frame.pack();
/*  74 */     state.controller.registerFrame(frame);
/*  75 */     return chart;
/*     */   }
/*     */ 
/*     */   public static TimeSeriesAttributes addSeries(TimeSeriesChartGenerator chart, String seriesName)
/*     */   {
/*  81 */     XYSeries series = new XYSeries(seriesName, false);
/*  82 */     return (TimeSeriesAttributes)chart.addSeries(series, null);
/*     */   }
/*     */ 
/*     */   public static Stoppable scheduleSeries(final GUIState state, TimeSeriesAttributes attributes, final Valuable valueProvider)
/*     */   {
/*  94 */     return state.scheduleRepeatingImmediatelyAfter(new Steppable()
/*     */     {
/*  96 */       final XYSeries series = this.val$attributes.getSeries();
/*  97 */       double last = -1.0D;
/*     */ 
/*     */       public void step(SimState state) {
/* 100 */         final double x = state.schedule.getTime();
/* 101 */         if ((x > this.last) && (x >= 0.0D) && (x < (1.0D / 0.0D)))
/*     */         {
/* 103 */           this.last = x;
/* 104 */           double value = valueProvider == null ? (0.0D / 0.0D) : valueProvider.doubleValue();
/*     */ 
/* 107 */           SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 111 */               ChartUtilities.1.this.val$attributes.possiblyCull();
/* 112 */               ChartUtilities.1.this.series.add(x, this.val$value, true);
/*     */             }
/*     */           });
/* 116 */           this.val$attributes.getGenerator().updateChartLater(state.schedule.getSteps());
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static HistogramGenerator buildHistogramGenerator(String title, String rangeAxisLabel)
/*     */   {
/* 125 */     HistogramGenerator chart = new HistogramGenerator();
/* 126 */     if (title == null) title = "";
/* 127 */     chart.setTitle(title);
/* 128 */     if (rangeAxisLabel == null) rangeAxisLabel = "";
/* 129 */     chart.setYAxisLabel(rangeAxisLabel);
/* 130 */     return chart;
/*     */   }
/*     */ 
/*     */   public static HistogramGenerator buildHistogramGenerator(GUIState state, String title, String rangeAxisLabel)
/*     */   {
/* 136 */     HistogramGenerator chart = buildHistogramGenerator(title, rangeAxisLabel);
/* 137 */     JFrame frame = chart.createFrame();
/* 138 */     frame.setVisible(true);
/* 139 */     frame.pack();
/* 140 */     state.controller.registerFrame(frame);
/* 141 */     return chart;
/*     */   }
/*     */ 
/*     */   public static HistogramSeriesAttributes addSeries(HistogramGenerator chart, String seriesName, int bins)
/*     */   {
/* 147 */     return (HistogramSeriesAttributes)chart.addSeries(new double[0], bins, seriesName, null);
/*     */   }
/*     */ 
/*     */   public static Stoppable scheduleSeries(GUIState state, final HistogramSeriesAttributes attributes, final ProvidesDoubles valueProvider)
/*     */   {
/* 154 */     return state.scheduleRepeatingImmediatelyAfter(new Steppable()
/*     */     {
/* 156 */       double last = -1.0D;
/*     */ 
/*     */       public void step(SimState state) {
/* 159 */         double x = state.schedule.getTime();
/* 160 */         if ((x > this.last) && (x >= 0.0D) && (x < (1.0D / 0.0D)))
/*     */         {
/* 162 */           this.last = x;
/* 163 */           if (valueProvider != null)
/*     */           {
/* 165 */             final double[] vals = valueProvider.provide();
/*     */ 
/* 168 */             if (vals != null) SwingUtilities.invokeLater(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 172 */                   ChartUtilities.2.this.val$attributes.setValues(vals);
/*     */                 }
/*     */               });
/*     */           }
/*     */ 
/* 177 */           attributes.getGenerator().updateChartLater(state.schedule.getSteps());
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static BoxPlotGenerator buildBoxPlotGenerator(String title, String rangeAxisLabel)
/*     */   {
/* 187 */     BoxPlotGenerator chart = new BoxPlotGenerator();
/* 188 */     if (title == null) title = "";
/* 189 */     chart.setTitle(title);
/* 190 */     if (rangeAxisLabel == null) rangeAxisLabel = "";
/* 191 */     chart.setYAxisLabel(rangeAxisLabel);
/* 192 */     return chart;
/*     */   }
/*     */ 
/*     */   public static BoxPlotGenerator buildBoxPlotGenerator(GUIState state, String title, String rangeAxisLabel)
/*     */   {
/* 198 */     BoxPlotGenerator chart = buildBoxPlotGenerator(title, rangeAxisLabel);
/* 199 */     JFrame frame = chart.createFrame();
/* 200 */     frame.setVisible(true);
/* 201 */     frame.pack();
/* 202 */     state.controller.registerFrame(frame);
/* 203 */     return chart;
/*     */   }
/*     */ 
/*     */   public static BoxPlotSeriesAttributes addSeries(BoxPlotGenerator chart, String seriesName)
/*     */   {
/* 209 */     return (BoxPlotSeriesAttributes)chart.addSeries(new double[0][0], new String[0], seriesName, null);
/*     */   }
/*     */ 
/*     */   public static Stoppable scheduleSeries(GUIState state, final BoxPlotSeriesAttributes attributes, final ProvidesDoubles valueProvider)
/*     */   {
/* 217 */     return state.scheduleRepeatingImmediatelyAfter(new Steppable()
/*     */     {
/* 219 */       double last = -1.0D;
/*     */ 
/*     */       public void step(SimState state) {
/* 222 */         double x = state.schedule.getTime();
/* 223 */         if ((x > this.last) && (x >= 0.0D) && (x < (1.0D / 0.0D)))
/*     */         {
/* 225 */           this.last = x;
/* 226 */           if (valueProvider != null)
/*     */           {
/* 228 */             final double[] vals = valueProvider.provide();
/*     */ 
/* 231 */             if (vals != null) SwingUtilities.invokeLater(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 235 */                   ChartUtilities.3.this.val$attributes.setValues(new double[][] { vals });
/* 236 */                   ChartUtilities.3.this.val$attributes.setLabels(null);
/*     */                 }
/*     */               });
/*     */           }
/*     */ 
/* 241 */           attributes.getGenerator().updateChartLater(state.schedule.getSteps());
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static Stoppable scheduleSeries(GUIState state, final BoxPlotSeriesAttributes attributes, final ProvidesDoubleDoublesAndLabels valueProvider)
/*     */   {
/* 251 */     return state.scheduleRepeatingImmediatelyAfter(new Steppable()
/*     */     {
/* 253 */       double last = -1.0D;
/*     */ 
/*     */       public void step(SimState state) {
/* 256 */         double x = state.schedule.getTime();
/* 257 */         if ((x > this.last) && (x >= 0.0D) && (x < (1.0D / 0.0D)))
/*     */         {
/* 259 */           this.last = x;
/* 260 */           if (valueProvider != null)
/*     */           {
/* 262 */             final double[][] vals = valueProvider.provide();
/* 263 */             final String[] labels = valueProvider.provideLabels();
/*     */ 
/* 266 */             if (vals != null) SwingUtilities.invokeLater(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 270 */                   ChartUtilities.4.this.val$attributes.setValues(vals);
/* 271 */                   ChartUtilities.4.this.val$attributes.setLabels(labels);
/*     */                 }
/*     */               });
/*     */           }
/*     */ 
/* 276 */           attributes.getGenerator().updateChartLater(state.schedule.getSteps());
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static ScatterPlotGenerator buildScatterPlotGenerator(String title, String rangeAxisLabel, String domainAxisLabel)
/*     */   {
/* 291 */     ScatterPlotGenerator chart = new ScatterPlotGenerator();
/* 292 */     if (title == null) title = "";
/* 293 */     chart.setTitle(title);
/* 294 */     if (rangeAxisLabel == null) rangeAxisLabel = "";
/* 295 */     chart.setYAxisLabel(rangeAxisLabel);
/* 296 */     if (domainAxisLabel == null) domainAxisLabel = "";
/* 297 */     chart.setXAxisLabel(domainAxisLabel);
/* 298 */     return chart;
/*     */   }
/*     */ 
/*     */   public static ScatterPlotGenerator buildScatterPlotGenerator(GUIState state, String title, String rangeAxisLabel, String domainAxisLabel)
/*     */   {
/* 304 */     ScatterPlotGenerator chart = buildScatterPlotGenerator(title, rangeAxisLabel, domainAxisLabel);
/* 305 */     JFrame frame = chart.createFrame();
/* 306 */     frame.setVisible(true);
/* 307 */     frame.pack();
/* 308 */     state.controller.registerFrame(frame);
/* 309 */     return chart;
/*     */   }
/*     */ 
/*     */   public static ScatterPlotSeriesAttributes addSeries(ScatterPlotGenerator chart, String seriesName)
/*     */   {
/* 315 */     return (ScatterPlotSeriesAttributes)chart.addSeries(new double[2][0], seriesName, null);
/*     */   }
/*     */ 
/*     */   public static Stoppable scheduleSeries(GUIState state, final ScatterPlotSeriesAttributes attributes, final ProvidesDoubleDoubles valueProvider)
/*     */   {
/* 322 */     return state.scheduleRepeatingImmediatelyAfter(new Steppable()
/*     */     {
/* 324 */       double last = -1.0D;
/*     */ 
/*     */       public void step(SimState state) {
/* 327 */         double x = state.schedule.getTime();
/* 328 */         if ((x > this.last) && (x >= 0.0D) && (x < (1.0D / 0.0D)))
/*     */         {
/* 330 */           this.last = x;
/* 331 */           if (valueProvider != null)
/*     */           {
/* 333 */             final double[][] vals = valueProvider.provide();
/*     */ 
/* 336 */             if (vals != null) SwingUtilities.invokeLater(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 340 */                   ChartUtilities.5.this.val$attributes.setValues(vals);
/*     */                 }
/*     */               });
/*     */           }
/*     */ 
/* 345 */           attributes.getGenerator().updateChartLater(state.schedule.getSteps());
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static BubbleChartGenerator buildBubbleChartGenerator(String title, String rangeAxisLabel, String domainAxisLabel)
/*     */   {
/* 355 */     BubbleChartGenerator chart = new BubbleChartGenerator();
/* 356 */     if (title == null) title = "";
/* 357 */     chart.setTitle(title);
/* 358 */     if (rangeAxisLabel == null) rangeAxisLabel = "";
/* 359 */     chart.setYAxisLabel(rangeAxisLabel);
/* 360 */     if (domainAxisLabel == null) domainAxisLabel = "";
/* 361 */     chart.setXAxisLabel(domainAxisLabel);
/* 362 */     return chart;
/*     */   }
/*     */ 
/*     */   public static BubbleChartGenerator buildBubbleChartGenerator(GUIState state, String title, String rangeAxisLabel, String domainAxisLabel)
/*     */   {
/* 369 */     BubbleChartGenerator chart = buildBubbleChartGenerator(title, rangeAxisLabel, domainAxisLabel);
/* 370 */     JFrame frame = chart.createFrame();
/* 371 */     frame.setVisible(true);
/* 372 */     frame.pack();
/* 373 */     state.controller.registerFrame(frame);
/* 374 */     return chart;
/*     */   }
/*     */ 
/*     */   public static BubbleChartSeriesAttributes addSeries(BubbleChartGenerator chart, String seriesName)
/*     */   {
/* 380 */     return (BubbleChartSeriesAttributes)chart.addSeries(new double[3][0], seriesName, null);
/*     */   }
/*     */ 
/*     */   public static Stoppable scheduleSeries(GUIState state, final BubbleChartSeriesAttributes attributes, final ProvidesTripleDoubles valueProvider)
/*     */   {
/* 387 */     return state.scheduleRepeatingImmediatelyAfter(new Steppable()
/*     */     {
/* 389 */       double last = -1.0D;
/*     */ 
/*     */       public void step(SimState state) {
/* 392 */         double x = state.schedule.getTime();
/* 393 */         if ((x > this.last) && (x >= 0.0D) && (x < (1.0D / 0.0D)))
/*     */         {
/* 395 */           this.last = x;
/* 396 */           if (valueProvider != null)
/*     */           {
/* 398 */             final double[][] vals = valueProvider.provide();
/*     */ 
/* 401 */             if (vals != null) SwingUtilities.invokeLater(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 405 */                   ChartUtilities.6.this.val$attributes.setValues(vals);
/*     */                 }
/*     */               });
/*     */           }
/*     */ 
/* 410 */           attributes.getGenerator().updateChartLater(state.schedule.getSteps());
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static PieChartGenerator buildPieChartGenerator(String title)
/*     */   {
/* 421 */     PieChartGenerator chart = new PieChartGenerator();
/* 422 */     if (title == null) title = "";
/* 423 */     chart.setTitle(title);
/* 424 */     return chart;
/*     */   }
/*     */ 
/*     */   public static PieChartGenerator buildPieChartGenerator(GUIState state, String title)
/*     */   {
/* 430 */     PieChartGenerator chart = buildPieChartGenerator(title);
/* 431 */     JFrame frame = chart.createFrame();
/* 432 */     frame.setVisible(true);
/* 433 */     frame.pack();
/* 434 */     state.controller.registerFrame(frame);
/* 435 */     return chart;
/*     */   }
/*     */ 
/*     */   public static PieChartSeriesAttributes addSeries(PieChartGenerator chart, String seriesName)
/*     */   {
/* 441 */     return (PieChartSeriesAttributes)chart.addSeries(new double[0], new String[0], seriesName, null);
/*     */   }
/*     */ 
/*     */   public static Stoppable scheduleSeries(GUIState state, final PieChartSeriesAttributes attributes, final ProvidesDoublesAndLabels valueProvider)
/*     */   {
/* 448 */     return state.scheduleRepeatingImmediatelyAfter(new Steppable()
/*     */     {
/* 450 */       double last = -1.0D;
/*     */ 
/*     */       public void step(SimState state) {
/* 453 */         double x = state.schedule.getTime();
/* 454 */         if ((x > this.last) && (x >= 0.0D) && (x < (1.0D / 0.0D)))
/*     */         {
/* 456 */           this.last = x;
/* 457 */           if (valueProvider != null)
/*     */           {
/* 459 */             final double[] vals = valueProvider.provide();
/* 460 */             final String[] labels = valueProvider.provideLabels();
/*     */ 
/* 463 */             if (vals != null) SwingUtilities.invokeLater(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 467 */                   ChartUtilities.7.this.val$attributes.setValues(vals);
/* 468 */                   ChartUtilities.7.this.val$attributes.setLabels(labels);
/*     */                 }
/*     */               });
/*     */           }
/*     */ 
/* 473 */           attributes.getGenerator().updateChartLater(state.schedule.getSteps());
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static Stoppable scheduleSeries(GUIState state, final PieChartSeriesAttributes attributes, final ProvidesObjects valueProvider)
/*     */   {
/* 483 */     return state.scheduleRepeatingImmediatelyAfter(new Steppable()
/*     */     {
/* 485 */       double last = -1.0D;
/*     */ 
/*     */       public void step(SimState state) {
/* 488 */         double x = state.schedule.getTime();
/* 489 */         if ((x > this.last) && (x >= 0.0D) && (x < (1.0D / 0.0D)))
/*     */         {
/* 491 */           this.last = x;
/* 492 */           if (valueProvider != null)
/*     */           {
/* 494 */             final Object[] vals = valueProvider.provide();
/*     */ 
/* 497 */             if (vals != null) SwingUtilities.invokeLater(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 501 */                   ChartUtilities.8.this.val$attributes.setElements(vals);
/*     */                 }
/*     */               });
/*     */           }
/*     */ 
/* 506 */           attributes.getGenerator().updateChartLater(state.schedule.getSteps());
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static Stoppable scheduleSeries(GUIState state, final PieChartSeriesAttributes attributes, final ProvidesCollection valueProvider)
/*     */   {
/* 516 */     return state.scheduleRepeatingImmediatelyAfter(new Steppable()
/*     */     {
/* 518 */       double last = -1.0D;
/*     */ 
/*     */       public void step(SimState state) {
/* 521 */         double x = state.schedule.getTime();
/* 522 */         if ((x > this.last) && (x >= 0.0D) && (x < (1.0D / 0.0D)))
/*     */         {
/* 524 */           this.last = x;
/* 525 */           if (valueProvider != null)
/*     */           {
/* 527 */             final Collection vals = valueProvider.provide();
/*     */ 
/* 530 */             if (vals != null) SwingUtilities.invokeLater(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 534 */                   ChartUtilities.9.this.val$attributes.setElements(new ArrayList(vals));
/*     */                 }
/*     */               });
/*     */           }
/*     */ 
/* 539 */           attributes.getGenerator().updateChartLater(state.schedule.getSteps());
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static BarChartGenerator buildBarChartGenerator(String title)
/*     */   {
/* 549 */     BarChartGenerator chart = new BarChartGenerator();
/* 550 */     if (title == null) title = "";
/* 551 */     chart.setTitle(title);
/* 552 */     return chart;
/*     */   }
/*     */ 
/*     */   public static BarChartGenerator buildBarChartGenerator(GUIState state, String title)
/*     */   {
/* 558 */     BarChartGenerator chart = buildBarChartGenerator(title);
/* 559 */     JFrame frame = chart.createFrame();
/* 560 */     frame.setVisible(true);
/* 561 */     frame.pack();
/* 562 */     state.controller.registerFrame(frame);
/* 563 */     return chart;
/*     */   }
/*     */ 
/*     */   public static BarChartSeriesAttributes addSeries(BarChartGenerator chart, String seriesName)
/*     */   {
/* 569 */     return (BarChartSeriesAttributes)chart.addSeries(new double[0], new String[0], seriesName, null);
/*     */   }
/*     */ 
/*     */   public static Stoppable scheduleSeries(GUIState state, final BarChartSeriesAttributes attributes, final ProvidesDoublesAndLabels valueProvider)
/*     */   {
/* 576 */     return state.scheduleRepeatingImmediatelyAfter(new Steppable()
/*     */     {
/* 578 */       double last = -1.0D;
/*     */ 
/*     */       public void step(SimState state) {
/* 581 */         double x = state.schedule.getTime();
/* 582 */         if ((x > this.last) && (x >= 0.0D) && (x < (1.0D / 0.0D)))
/*     */         {
/* 584 */           this.last = x;
/* 585 */           if (valueProvider != null)
/*     */           {
/* 587 */             final double[] vals = valueProvider.provide();
/* 588 */             final String[] labels = valueProvider.provideLabels();
/*     */ 
/* 591 */             if (vals != null) SwingUtilities.invokeLater(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 595 */                   ChartUtilities.10.this.val$attributes.setValues(vals);
/* 596 */                   ChartUtilities.10.this.val$attributes.setLabels(labels);
/*     */                 }
/*     */               });
/*     */           }
/*     */ 
/* 601 */           attributes.getGenerator().updateChartLater(state.schedule.getSteps());
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static Stoppable scheduleSeries(GUIState state, final BarChartSeriesAttributes attributes, final ProvidesObjects valueProvider)
/*     */   {
/* 611 */     return state.scheduleRepeatingImmediatelyAfter(new Steppable()
/*     */     {
/* 613 */       double last = -1.0D;
/*     */ 
/*     */       public void step(SimState state) {
/* 616 */         double x = state.schedule.getTime();
/* 617 */         if ((x > this.last) && (x >= 0.0D) && (x < (1.0D / 0.0D)))
/*     */         {
/* 619 */           this.last = x;
/* 620 */           if (valueProvider != null)
/*     */           {
/* 622 */             final Object[] vals = valueProvider.provide();
/*     */ 
/* 625 */             if (vals != null) SwingUtilities.invokeLater(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 629 */                   ChartUtilities.11.this.val$attributes.setElements(vals);
/*     */                 }
/*     */               });
/*     */           }
/*     */ 
/* 634 */           attributes.getGenerator().updateChartLater(state.schedule.getSteps());
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static Stoppable scheduleSeries(GUIState state, final BarChartSeriesAttributes attributes, final ProvidesCollection valueProvider)
/*     */   {
/* 644 */     return state.scheduleRepeatingImmediatelyAfter(new Steppable()
/*     */     {
/* 646 */       double last = -1.0D;
/*     */ 
/*     */       public void step(SimState state) {
/* 649 */         double x = state.schedule.getTime();
/* 650 */         if ((x > this.last) && (x >= 0.0D) && (x < (1.0D / 0.0D)))
/*     */         {
/* 652 */           this.last = x;
/* 653 */           if (valueProvider != null)
/*     */           {
/* 655 */             final Collection vals = valueProvider.provide();
/*     */ 
/* 658 */             if (vals != null) SwingUtilities.invokeLater(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 662 */                   ChartUtilities.12.this.val$attributes.setElements(new ArrayList(vals));
/*     */                 }
/*     */               });
/*     */           }
/*     */ 
/* 667 */           attributes.getGenerator().updateChartLater(state.schedule.getSteps());
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static abstract interface ProvidesCollection
/*     */   {
/*     */     public abstract Collection provide();
/*     */   }
/*     */ 
/*     */   public static abstract interface ProvidesObjects
/*     */   {
/*     */     public abstract Object[] provide();
/*     */   }
/*     */ 
/*     */   public static abstract interface ProvidesDoubleDoublesAndLabels extends ChartUtilities.ProvidesDoubleDoubles
/*     */   {
/*     */     public abstract String[] provideLabels();
/*     */   }
/*     */ 
/*     */   public static abstract interface ProvidesDoublesAndLabels extends ChartUtilities.ProvidesDoubles
/*     */   {
/*     */     public abstract String[] provideLabels();
/*     */   }
/*     */ 
/*     */   public static abstract interface ProvidesTripleDoubles
/*     */   {
/*     */     public abstract double[][] provide();
/*     */   }
/*     */ 
/*     */   public static abstract interface ProvidesDoubleDoubles
/*     */   {
/*     */     public abstract double[][] provide();
/*     */   }
/*     */ 
/*     */   public static abstract interface ProvidesDoubles
/*     */   {
/*     */     public abstract double[] provide();
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.display.ChartUtilities
 * JD-Core Version:    0.6.2
 */