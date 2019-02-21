/*     */ package sim.util.media.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.jfree.chart.ChartPanel;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import sim.util.gui.LabelledList;
/*     */ import sim.util.gui.PropertyField;
/*     */ 
/*     */ public abstract class SeriesAttributes extends LabelledList
/*     */ {
/*     */   SeriesChangeListener stoppable;
/*     */   int seriesIndex;
/*     */   ChartGenerator generator;
/*     */   String seriesName;
/*  88 */   public static final ImageIcon I_DOWN = iconFor("DownArrow.png");
/*  89 */   public static final ImageIcon I_DOWN_PRESSED = iconFor("DownArrowPressed.png");
/*  90 */   public static final ImageIcon I_CLOSE = iconFor("CloseBox.png");
/*  91 */   public static final ImageIcon I_CLOSE_PRESSED = iconFor("CloseBoxPressed.png");
/*  92 */   public static final ImageIcon I_UP = iconFor("UpArrow.png");
/*  93 */   public static final ImageIcon I_UP_PRESSED = iconFor("UpArrowPressed.png");
/*     */   Box manipulators;
/* 157 */   boolean plotVisible = true;
/*     */ 
/*     */   public SeriesChangeListener getStoppable()
/*     */   {
/*  33 */     return this.stoppable; } 
/*  34 */   public void setStoppable(SeriesChangeListener obj) { this.stoppable = obj; }
/*     */ 
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setName(String val)
/*     */   {
/*  44 */     super.setName(val); } 
/*  45 */   public void setSeriesName(String val) { this.seriesName = val; } 
/*     */   /** @deprecated */
/*     */   public String getName() {
/*  48 */     return super.getName(); } 
/*  49 */   public String getSeriesName() { return this.seriesName; }
/*     */ 
/*     */ 
/*     */   public abstract void rebuildGraphicsDefinitions();
/*     */ 
/*     */   public abstract void buildAttributes();
/*     */ 
/*     */   public Color reviseColor(Color c, double opacity)
/*     */   {
/*  64 */     return new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)(opacity * 255.0D));
/*     */   }
/*     */ 
/*     */   public Plot getPlot()
/*     */   {
/*  70 */     return this.generator.getChartPanel().getChart().getPlot();
/*     */   }
/*     */ 
/*     */   public ChartGenerator getGenerator() {
/*  74 */     return this.generator;
/*     */   }
/*     */   public int getSeriesIndex() {
/*  77 */     return this.seriesIndex;
/*     */   }
/*     */   public void setSeriesIndex(int val) {
/*  80 */     this.seriesIndex = val;
/*     */   }
/*     */ 
/*     */   private XYItemRenderer getRenderer()
/*     */   {
/*  85 */     return ((XYPlot)getPlot()).getRenderer();
/*     */   }
/*     */ 
/*     */   static ImageIcon iconFor(String name)
/*     */   {
/*  97 */     return new ImageIcon(SeriesAttributes.class.getResource(name));
/*     */   }
/*     */ 
/*     */   void buildManipulators()
/*     */   {
/* 104 */     JButton removeButton = new JButton(I_CLOSE);
/* 105 */     removeButton.setPressedIcon(I_CLOSE_PRESSED);
/* 106 */     removeButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
/* 107 */     removeButton.setBorderPainted(false);
/* 108 */     removeButton.setContentAreaFilled(false);
/* 109 */     removeButton.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/* 113 */         if (JOptionPane.showOptionDialog(null, "Remove the Series " + SeriesAttributes.this.getSeriesName() + "?", "Confirm", 0, 3, null, new Object[] { "Remove", "Cancel" }, null) == 0)
/*     */         {
/* 119 */           SeriesAttributes.this.getGenerator().removeSeries(SeriesAttributes.this.getSeriesIndex());
/*     */         }
/*     */       }
/*     */     });
/* 122 */     removeButton.setToolTipText("Remove this series");
/*     */ 
/* 124 */     JButton upButton = new JButton(I_UP);
/* 125 */     upButton.setPressedIcon(I_UP_PRESSED);
/* 126 */     upButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
/* 127 */     upButton.setBorderPainted(false);
/* 128 */     upButton.setContentAreaFilled(false);
/* 129 */     upButton.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/* 133 */         SeriesAttributes.this.getGenerator().moveSeries(SeriesAttributes.this.getSeriesIndex(), true);
/*     */       }
/*     */     });
/* 136 */     upButton.setToolTipText("Draw this series higher in the series order");
/*     */ 
/* 138 */     JButton downButton = new JButton(I_DOWN);
/* 139 */     downButton.setPressedIcon(I_DOWN_PRESSED);
/* 140 */     downButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
/* 141 */     downButton.setBorderPainted(false);
/* 142 */     downButton.setContentAreaFilled(false);
/* 143 */     downButton.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/* 147 */         SeriesAttributes.this.getGenerator().moveSeries(SeriesAttributes.this.getSeriesIndex(), false);
/*     */       }
/*     */     });
/* 150 */     downButton.setToolTipText("Draw this series lower in the series order");
/*     */ 
/* 152 */     this.manipulators.add(removeButton);
/* 153 */     this.manipulators.add(upButton);
/* 154 */     this.manipulators.add(downButton);
/*     */   }
/*     */ 
/*     */   public void setPlotVisible(boolean val)
/*     */   {
/* 160 */     this.plotVisible = val;
/* 161 */     getRenderer().setSeriesVisible(this.seriesIndex, Boolean.valueOf(val));
/*     */   }
/*     */ 
/*     */   public boolean isPlotVisible()
/*     */   {
/* 166 */     return this.plotVisible;
/*     */   }
/*     */ 
/*     */   public SeriesAttributes(ChartGenerator generator, String name, int index, SeriesChangeListener stoppable)
/*     */   {
/* 174 */     super(name);
/* 175 */     setStoppable(stoppable);
/* 176 */     this.generator = generator;
/* 177 */     this.seriesIndex = index;
/* 178 */     final JCheckBox check = new JCheckBox();
/* 179 */     check.setSelected(true);
/* 180 */     check.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/* 184 */         SeriesAttributes.this.setPlotVisible(check.isSelected());
/*     */       }
/*     */     });
/* 188 */     this.manipulators = new Box(0);
/* 189 */     buildManipulators();
/* 190 */     JLabel spacer = new JLabel("");
/* 191 */     spacer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
/* 192 */     Box b = new Box(0);
/* 193 */     b.add(check);
/* 194 */     b.add(spacer);
/* 195 */     b.add(this.manipulators);
/* 196 */     b.add(Box.createGlue());
/* 197 */     addLabelled("Show", b);
/*     */ 
/* 199 */     PropertyField nameF = new PropertyField(name)
/*     */     {
/*     */       public String newValue(String newValue)
/*     */       {
/* 203 */         SeriesAttributes.this.setSeriesName(newValue);
/* 204 */         SeriesAttributes.this.rebuildGraphicsDefinitions();
/* 205 */         return newValue;
/*     */       }
/*     */     };
/* 208 */     addLabelled("Series", nameF);
/*     */ 
/* 210 */     buildAttributes();
/* 211 */     rebuildGraphicsDefinitions();
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.SeriesAttributes
 * JD-Core Version:    0.6.2
 */