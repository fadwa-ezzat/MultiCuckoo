/*     */ package sim.util.gui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.FocusAdapter;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import sim.util.Interval;
/*     */ 
/*     */ public class PropertyField extends JComponent
/*     */ {
/*  33 */   JComboBox list = new JComboBox();
/*  34 */   JTextField valField = new JTextField();
/*  35 */   JCheckBox checkField = new JCheckBox();
/*  36 */   JButton viewButton = new JButton("View");
/*  37 */   JLabel viewLabel = new JLabel();
/*  38 */   JLabel optionalLabel = new JLabel();
/*     */   static final int SLIDER_MAX = 1000;
/*     */   static final int SLIDER_WIDTH = 80;
/*  41 */   JSlider slider = new JSlider(0, 1000) {
/*     */     public Dimension getMaximumSize() {
/*  43 */       return new Dimension(80, super.getMaximumSize().height); } 
/*  44 */     public Dimension getPreferredSize() { return getMaximumSize(); }
/*     */ 
/*  41 */   };
/*     */ 
/*  47 */   DecimalFormat sliderFormatter = new DecimalFormat();
/*     */   Border valFieldBorder;
/*     */   Border emptyBorder;
/*     */   String currentValue;
/*     */   boolean isReadWrite;
/*     */   Object domain;
/*     */   int displayState;
/*     */   public static final int SHOW_CHECKBOX = 0;
/*     */   public static final int SHOW_TEXTFIELD = 1;
/*     */   public static final int SHOW_VIEWBUTTON = 2;
/*     */   public static final int SHOW_SLIDER = 3;
/*     */   public static final int SHOW_LIST = 4;
/*     */   Color defaultColor;
/*  65 */   Color editedColor = new Color(225, 225, 255);
/*     */ 
/*  82 */   boolean edited = false;
/*     */ 
/*  96 */   KeyListener listener = new KeyListener() {
/*     */     public void keyReleased(KeyEvent keyEvent) {
/*     */     }
/*     */     public void keyTyped(KeyEvent keyEvent) {
/*     */     }
/*     */     public void keyPressed(KeyEvent keyEvent) {
/* 102 */       if (keyEvent.getKeyCode() == 10)
/*     */       {
/* 104 */         PropertyField.this.submit();
/*     */       }
/* 106 */       else if (keyEvent.getKeyCode() == 27)
/*     */       {
/* 108 */         PropertyField.this.update();
/*     */       }
/*     */       else
/*     */       {
/* 112 */         PropertyField.this.setEdited(true);
/*     */       }
/*     */     }
/*  96 */   };
/*     */ 
/* 117 */   ActionListener checkListener = new ActionListener()
/*     */   {
/*     */     public void actionPerformed(ActionEvent e)
/*     */     {
/* 121 */       PropertyField.this.setValue(PropertyField.this.newValue("" + PropertyField.this.checkField.isSelected()));
/*     */     }
/* 117 */   };
/*     */ 
/* 125 */   ActionListener viewButtonListener = new ActionListener()
/*     */   {
/*     */     public void actionPerformed(ActionEvent e)
/*     */     {
/* 129 */       PropertyField.this.viewProperty();
/*     */     }
/* 125 */   };
/*     */ 
/* 133 */   FocusAdapter focusAdapter = new FocusAdapter()
/*     */   {
/*     */     public void focusLost(FocusEvent e)
/*     */     {
/* 137 */       PropertyField.this.submit();
/*     */     }
/* 133 */   };
/*     */ 
/* 142 */   boolean sliding = false;
/*     */ 
/* 158 */   boolean ignoreEvent = false;
/* 159 */   ChangeListener sliderListener = new ChangeListener()
/*     */   {
/*     */     public void stateChanged(ChangeEvent e)
/*     */     {
/* 163 */       if ((!PropertyField.this.ignoreEvent) && (PropertyField.this.domain != null) && ((PropertyField.this.domain instanceof Interval)))
/*     */       {
/* 165 */         double d = 0.0D;
/* 166 */         Interval domain = (Interval)PropertyField.this.domain;
/* 167 */         int i = PropertyField.this.slider.getValue();
/*     */         String str;
/*     */         String str;
/* 169 */         if (domain.isDouble())
/*     */         {
/* 171 */           double min = domain.getMin().doubleValue();
/* 172 */           double max = domain.getMax().doubleValue();
/* 173 */           d = i / 1000.0D * (max - min) + min;
/* 174 */           PropertyField.this.sliderFormatter.setMinimumFractionDigits(PropertyField.this.calcDecimalPlacesForInterval(min, max, 80));
/* 175 */           str = PropertyField.this.sliderFormatter.format(d);
/*     */         }
/*     */         else {
/* 178 */           str = Integer.toString(i);
/*     */         }
/* 180 */         PropertyField.this.sliding = true;
/* 181 */         PropertyField.this.setValue(PropertyField.this.newValue(str));
/* 182 */         PropertyField.this.sliding = false;
/*     */       }
/* 184 */       PropertyField.this.ignoreEvent = false;
/*     */     }
/* 159 */   };
/*     */ 
/* 188 */   ActionListener listListener = new ActionListener()
/*     */   {
/*     */     public void actionPerformed(ActionEvent e)
/*     */     {
/* 192 */       if (!PropertyField.this.settingList)
/* 193 */         PropertyField.this.setValue(PropertyField.this.newValue("" + PropertyField.this.list.getSelectedIndex()));
/*     */     }
/* 188 */   };
/*     */ 
/* 197 */   boolean settingList = false;
/*     */ 
/*     */   public JTextField getField()
/*     */   {
/*  49 */     return this.valField;
/*     */   }
/*     */ 
/*     */   public void setEditedColor(Color c)
/*     */   {
/*  67 */     this.editedColor = c; } 
/*  68 */   public Color getEditedColor() { return this.editedColor; }
/*     */ 
/*     */ 
/*     */   public void submit()
/*     */   {
/*  73 */     if (this.edited) setValue(newValue(this.valField.getText()));
/*     */   }
/*     */ 
/*     */   public void update()
/*     */   {
/*  79 */     setValue(getValue());
/*     */   }
/*     */ 
/*     */   void setEdited(boolean edited)
/*     */   {
/*  85 */     this.edited = edited;
/*  86 */     if (edited)
/*     */     {
/*  88 */       this.valField.setBackground(this.editedColor);
/*     */     }
/*     */     else
/*     */     {
/*  92 */       this.valField.setBackground(this.isReadWrite ? this.defaultColor : this.checkField.getBackground());
/*     */     }
/*     */   }
/*     */ 
/*     */   int calcDecimalPlacesForInterval(double low, double high, int ticks)
/*     */   {
/* 154 */     double epsilon = (high - low) / ticks;
/* 155 */     return (int)Math.ceil(Math.log10(1.0D / epsilon));
/*     */   }
/*     */ 
/*     */   public void setValue(String val)
/*     */   {
/* 202 */     switch (this.displayState)
/*     */     {
/*     */     case 3:
/* 205 */       setEdited(false);
/* 206 */       if (!this.sliding) slide(val);
/* 207 */       this.valField.setText(val);
/* 208 */       break;
/*     */     case 1:
/* 210 */       setEdited(false);
/* 211 */       this.valField.setText(val);
/* 212 */       break;
/*     */     case 0:
/* 214 */       if ((val != null) && (val.equals("true")))
/* 215 */         this.checkField.setSelected(true);
/*     */       else
/* 217 */         this.checkField.setSelected(false);
/* 218 */       break;
/*     */     case 2:
/* 220 */       this.viewLabel.setText(val);
/* 221 */       break;
/*     */     case 4:
/* 223 */       this.settingList = true;
/*     */       try { this.list.setSelectedIndex(Integer.parseInt(val)); } catch (Exception e) {
/* 225 */         this.settingList = false; throw new RuntimeException("" + e);
/* 226 */       }this.settingList = false;
/* 227 */       break;
/*     */     default:
/* 229 */       throw new RuntimeException("default case should never occur");
/*     */     }
/* 231 */     this.currentValue = val;
/*     */   }
/*     */ 
/*     */   void slide(String val)
/*     */   {
/*     */     try
/*     */     {
/* 238 */       if ((this.domain instanceof Interval))
/*     */       {
/* 240 */         Interval domain = (Interval)this.domain;
/* 241 */         double d = Double.parseDouble(val);
/* 242 */         double min = domain.getMin().doubleValue();
/* 243 */         double max = domain.getMax().doubleValue();
/* 244 */         int i = (int)((d - min) / (max - min) * 1000.0D);
/* 245 */         if (!domain.isDouble())
/* 246 */           i = (int)d;
/* 247 */         this.slider.setValue(i);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getValue() {
/* 256 */     return this.currentValue;
/*     */   }
/*     */ 
/*     */   public PropertyField()
/*     */   {
/* 262 */     this(null, "", true);
/*     */   }
/*     */ 
/*     */   public PropertyField(String initialValue)
/*     */   {
/* 268 */     this(null, initialValue, true);
/*     */   }
/*     */ 
/*     */   public PropertyField(String initialValue, boolean isReadWrite)
/*     */   {
/* 274 */     this(null, initialValue, isReadWrite);
/*     */   }
/*     */ 
/*     */   public PropertyField(String label, String initialValue)
/*     */   {
/* 280 */     this(label, initialValue, true);
/*     */   }
/*     */ 
/*     */   public PropertyField(String label, String initialValue, boolean isReadWrite)
/*     */   {
/* 286 */     this(label, initialValue, isReadWrite, null, 1);
/*     */   }
/*     */ 
/*     */   public PropertyField(String label, String initialValue, boolean isReadWrite, Object domain, int show)
/*     */   {
/* 308 */     setLayout(new BorderLayout());
/* 309 */     add(this.optionalLabel, "West");
/*     */ 
/* 311 */     this.valFieldBorder = this.valField.getBorder();
/* 312 */     Insets i = this.valFieldBorder.getBorderInsets(this.valField);
/* 313 */     this.emptyBorder = new EmptyBorder(i.top, i.left, i.bottom, i.right);
/*     */ 
/* 315 */     this.defaultColor = this.valField.getBackground();
/* 316 */     this.valField.addKeyListener(this.listener);
/* 317 */     this.valField.addFocusListener(this.focusAdapter);
/* 318 */     this.checkField.addActionListener(this.checkListener);
/* 319 */     this.viewButton.addActionListener(this.viewButtonListener);
/* 320 */     this.slider.addChangeListener(this.sliderListener);
/* 321 */     this.list.addActionListener(this.listListener);
/*     */ 
/* 324 */     this.viewButton.putClientProperty("Quaqua.Button.style", "square");
/*     */ 
/* 326 */     if ((domain != null) && ((domain instanceof Interval)))
/*     */     {
/* 328 */       Interval interval = (Interval)domain;
/* 329 */       if (!interval.isDouble())
/*     */       {
/* 336 */         this.slider.setMinimum(interval.getMin().intValue());
/* 337 */         this.slider.setMaximum(interval.getMax().intValue());
/*     */       }
/*     */     }
/*     */ 
/* 341 */     this.sliderFormatter.setGroupingUsed(false);
/*     */ 
/* 344 */     this.ignoreEvent = true;
/* 345 */     setValues(label, initialValue, isReadWrite, domain, show);
/*     */   }
/*     */ 
/*     */   void setValues(String label, String initialValue, boolean isReadWrite, Object domain, int show)
/*     */   {
/* 368 */     this.domain = domain;
/* 369 */     removeAll();
/* 370 */     add(this.optionalLabel, "West");
/*     */ 
/* 373 */     if ((show == 3) && (!isReadWrite)) show = 1;
/* 374 */     if ((domain != null) && (domain.getClass().isArray()))
/*     */     {
/* 376 */       domain = Arrays.asList((Object[])domain);
/*     */     }
/*     */ 
/* 379 */     this.displayState = show;
/* 380 */     switch (this.displayState)
/*     */     {
/*     */     case 3:
/* 383 */       JPanel p = new JPanel();
/* 384 */       p.setLayout(new BorderLayout());
/* 385 */       p.add(this.valField, "Center");
/* 386 */       if ((isReadWrite) && (domain != null) && ((domain instanceof Interval)))
/* 387 */         p.add(this.slider, "West");
/* 388 */       add(p, "Center");
/* 389 */       break;
/*     */     case 1:
/* 391 */       add(this.valField, "Center");
/* 392 */       break;
/*     */     case 0:
/* 394 */       add(this.checkField, "Center");
/* 395 */       break;
/*     */     case 2:
/* 397 */       add(this.viewLabel, "Center");
/* 398 */       add(this.viewButton, "West");
/* 399 */       break;
/*     */     case 4:
/* 401 */       if ((domain != null) && ((domain instanceof List)))
/*     */       {
/* 403 */         this.settingList = true;
/* 404 */         this.list.setEditable(false);
/* 405 */         this.list.setModel(new DefaultComboBoxModel(new Vector((List)domain)));
/* 406 */         add(this.list, "Center");
/* 407 */         this.list.setEnabled(isReadWrite);
/* 408 */         this.settingList = false; } break;
/*     */     default:
/* 412 */       throw new RuntimeException("default case should never occur");
/*     */     }
/* 414 */     revalidate();
/* 415 */     repaint();
/*     */ 
/* 417 */     this.currentValue = initialValue;
/* 418 */     this.optionalLabel.setText(label);
/*     */ 
/* 420 */     this.checkField.setEnabled(isReadWrite);
/* 421 */     this.valField.setEditable(isReadWrite);
/* 422 */     this.valField.setBorder(isReadWrite ? this.valFieldBorder : this.emptyBorder);
/*     */ 
/* 424 */     this.isReadWrite = isReadWrite;
/* 425 */     setValue(this.currentValue);
/*     */   }
/*     */ 
/*     */   public String newValue(String newValue)
/*     */   {
/* 433 */     return newValue;
/*     */   }
/*     */ 
/*     */   public void viewProperty()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setToolTipText(String text)
/*     */   {
/* 444 */     super.setToolTipText(text);
/* 445 */     this.valField.setToolTipText(text);
/* 446 */     this.checkField.setToolTipText(text);
/* 447 */     this.optionalLabel.setToolTipText(text);
/* 448 */     this.viewButton.setToolTipText(text);
/* 449 */     this.viewLabel.setToolTipText(text);
/* 450 */     this.slider.setToolTipText(text);
/* 451 */     this.list.setToolTipText(text);
/*     */   }
/*     */ 
/*     */   public Dimension getMinimumSize()
/*     */   {
/* 456 */     Dimension s = super.getMinimumSize();
/* 457 */     s.height = this.valField.getMinimumSize().height;
/* 458 */     return s;
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredSize() {
/* 462 */     Dimension s = super.getPreferredSize();
/* 463 */     s.height = this.valField.getPreferredSize().height;
/* 464 */     return s;
/*     */   }
/*     */ 
/*     */   public void setEnabled(boolean b)
/*     */   {
/* 469 */     super.setEnabled(b);
/* 470 */     this.valField.setEnabled(b);
/* 471 */     this.checkField.setEnabled(b);
/* 472 */     this.optionalLabel.setEnabled(b);
/* 473 */     this.viewButton.setEnabled(b);
/* 474 */     this.viewLabel.setEnabled(b);
/* 475 */     this.slider.setEnabled(b);
/* 476 */     this.list.setEnabled(b);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.PropertyField
 * JD-Core Version:    0.6.2
 */