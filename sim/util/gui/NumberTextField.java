/*     */ package sim.util.gui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.FocusAdapter;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JTextField;
/*     */ 
/*     */ public class NumberTextField extends JComponent
/*     */ {
/*  32 */   JTextField valField = new JTextField();
/*     */   JButton downButton;
/*     */   JButton upButton;
/*     */   JButton bellyButton;
/*     */   JLabel fieldLabel;
/*     */   double initialValue;
/*     */   double multiply;
/*     */   double add;
/*     */   protected double currentValue;
/*     */   Color defaultColor;
/*  43 */   Color editedColor = new Color(225, 225, 255);
/*     */ 
/*  48 */   public static final ImageIcon I_DOWN = iconFor("LeftArrow.png");
/*  49 */   public static final ImageIcon I_DOWN_PRESSED = iconFor("LeftArrowPressed.png");
/*  50 */   public static final ImageIcon I_BELLY = iconFor("BellyButton.png");
/*  51 */   public static final ImageIcon I_BELLY_PRESSED = iconFor("BellyButtonPressed.png");
/*  52 */   public static final ImageIcon I_UP = iconFor("RightArrow.png");
/*  53 */   public static final ImageIcon I_UP_PRESSED = iconFor("RightArrowPressed.png");
/*     */ 
/*  60 */   boolean edited = false;
/*     */ 
/*  96 */   KeyListener listener = new KeyListener() {
/*     */     public void keyReleased(KeyEvent keyEvent) {
/*     */     }
/*     */     public void keyTyped(KeyEvent keyEvent) {
/*     */     }
/* 101 */     public void keyPressed(KeyEvent keyEvent) { if (keyEvent.getKeyCode() == 10)
/*     */       {
/* 103 */         NumberTextField.this.submit();
/*     */       }
/* 105 */       else if (keyEvent.getKeyCode() == 27)
/*     */       {
/* 107 */         NumberTextField.this.update();
/*     */       }
/*     */       else
/*     */       {
/* 111 */         NumberTextField.this.setEdited(true);
/*     */       }
/*     */     }
/*  96 */   };
/*     */ 
/* 116 */   FocusAdapter focusAdapter = new FocusAdapter()
/*     */   {
/*     */     public void focusLost(FocusEvent e)
/*     */     {
/* 120 */       NumberTextField.this.submit();
/*     */     }
/* 116 */   };
/*     */ 
/*     */   public void setEditedColor(Color c)
/*     */   {
/*  45 */     this.editedColor = c; } 
/*  46 */   public Color getEditedColor() { return this.editedColor; }
/*     */ 
/*     */ 
/*     */   static ImageIcon iconFor(String name)
/*     */   {
/*  57 */     return new ImageIcon(NumberTextField.class.getResource(name));
/*     */   }
/*     */ 
/*     */   void setEdited(boolean edited)
/*     */   {
/*  63 */     if (this.edited != edited)
/*     */     {
/*  65 */       this.edited = edited;
/*  66 */       if (edited)
/*     */       {
/*  68 */         this.valField.setBackground(this.editedColor);
/*     */       }
/*     */       else
/*     */       {
/*  72 */         this.valField.setBackground(this.defaultColor);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void submit()
/*     */   {
/*  79 */     if (this.edited)
/*     */     {
/*     */       double val;
/*     */       try
/*     */       {
/*  84 */         val = Double.parseDouble(this.valField.getText());
/*     */       } catch (NumberFormatException e) {
/*  86 */         val = this.initialValue;
/*  87 */       }setValue(newValue(val));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void update()
/*     */   {
/*  93 */     setValue(getValue());
/*     */   }
/*     */ 
/*     */   public void setValue(double val)
/*     */   {
/* 127 */     if ((int)val == val)
/* 128 */       this.valField.setText("" + (int)val);
/* 129 */     else this.valField.setText("" + val);
/* 130 */     this.currentValue = val;
/* 131 */     setEdited(false);
/*     */   }
/*     */ 
/*     */   public double getValue()
/*     */   {
/* 138 */     return this.currentValue;
/*     */   }
/*     */   public JTextField getField() {
/* 141 */     return this.valField;
/*     */   }
/*     */ 
/*     */   public void setInitialValue(double initialValue) {
/* 145 */     this.initialValue = initialValue;
/* 146 */     setValue(initialValue);
/*     */   }
/*     */   public double getInitialValue() {
/* 149 */     return this.initialValue;
/*     */   }
/*     */ 
/*     */   public NumberTextField(double initialValue)
/*     */   {
/* 154 */     this(null, initialValue, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   public NumberTextField(String label, double initialValue)
/*     */   {
/* 160 */     this(label, initialValue, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   public NumberTextField(double initialValue, boolean doubleEachTime)
/*     */   {
/* 169 */     this(null, initialValue, doubleEachTime);
/*     */   }
/*     */ 
/*     */   public NumberTextField(double initialValue, double multiply, double add)
/*     */   {
/* 185 */     this(null, initialValue, multiply, add);
/*     */   }
/*     */ 
/*     */   public NumberTextField(String label, double initialValue, boolean doubleEachTime)
/*     */   {
/* 196 */     if (doubleEachTime)
/* 197 */       setValues(label, initialValue, 2.0D, 0.0D);
/*     */     else
/* 199 */       setValues(label, initialValue, 1.0D, 1.0D);
/*     */   }
/*     */ 
/*     */   public NumberTextField(String label, double initialValue, double multiply, double add)
/*     */   {
/* 214 */     setValues(label, initialValue, multiply, add);
/*     */   }
/*     */ 
/*     */   void setValues(String label, double initialValue, double multiply, double add)
/*     */   {
/* 219 */     this.defaultColor = this.valField.getBackground();
/*     */ 
/* 221 */     this.initialValue = initialValue;
/* 222 */     this.multiply = multiply;
/* 223 */     this.add = add;
/*     */ 
/* 225 */     this.currentValue = initialValue;
/*     */ 
/* 227 */     setLayout(new BorderLayout());
/*     */ 
/* 229 */     if ((label != null) && (label.length() != 0)) {
/* 230 */       add(this.fieldLabel = new JLabel(label), "West");
/*     */     }
/* 232 */     this.valField.addKeyListener(this.listener);
/* 233 */     this.valField.addFocusListener(this.focusAdapter);
/* 234 */     setValue(initialValue);
/* 235 */     add(this.valField, "Center");
/*     */ 
/* 237 */     if (multiply != 0.0D)
/*     */     {
/* 239 */       Box box = new Box(0);
/*     */ 
/* 242 */       this.downButton = new JButton(I_DOWN);
/* 243 */       this.downButton.setPressedIcon(I_DOWN_PRESSED);
/* 244 */       this.downButton.addActionListener(new ActionListener()
/*     */       {
/*     */         public void actionPerformed(ActionEvent e)
/*     */         {
/* 248 */           NumberTextField.this.setValue(NumberTextField.this.newValue((NumberTextField.this.getValue() - NumberTextField.this.add) / NumberTextField.this.multiply));
/*     */         }
/*     */       });
/* 250 */       this.downButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
/* 251 */       this.downButton.setBorderPainted(false);
/* 252 */       this.downButton.setContentAreaFilled(false);
/* 253 */       box.add(this.downButton);
/* 254 */       this.bellyButton = new JButton(I_BELLY);
/* 255 */       this.bellyButton.setPressedIcon(I_BELLY_PRESSED);
/* 256 */       this.bellyButton.addActionListener(new ActionListener()
/*     */       {
/*     */         public void actionPerformed(ActionEvent e)
/*     */         {
/* 260 */           NumberTextField.this.setValue(NumberTextField.this.newValue(NumberTextField.this.initialValue));
/*     */         }
/*     */       });
/* 262 */       this.bellyButton.setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0));
/* 263 */       this.bellyButton.setBorderPainted(false);
/* 264 */       this.bellyButton.setContentAreaFilled(false);
/* 265 */       box.add(this.bellyButton);
/* 266 */       this.upButton = new JButton(I_UP);
/* 267 */       this.upButton.setPressedIcon(I_UP_PRESSED);
/* 268 */       this.upButton.addActionListener(new ActionListener()
/*     */       {
/*     */         public void actionPerformed(ActionEvent e)
/*     */         {
/* 272 */           NumberTextField.this.setValue(NumberTextField.this.newValue(NumberTextField.this.getValue() * NumberTextField.this.multiply + NumberTextField.this.add));
/*     */         }
/*     */       });
/* 274 */       this.upButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
/* 275 */       this.upButton.setBorderPainted(false);
/* 276 */       this.upButton.setContentAreaFilled(false);
/* 277 */       box.add(this.upButton);
/* 278 */       add(box, "East");
/*     */     }
/*     */   }
/*     */ 
/*     */   public double newValue(double newValue)
/*     */   {
/* 287 */     return newValue;
/*     */   }
/*     */ 
/*     */   public void setToolTipText(String text)
/*     */   {
/* 292 */     super.setToolTipText(text);
/* 293 */     if (this.downButton != null) this.downButton.setToolTipText(text);
/* 294 */     if (this.upButton != null) this.upButton.setToolTipText(text);
/* 295 */     if (this.bellyButton != null) this.bellyButton.setToolTipText(text);
/* 296 */     if (this.valField != null) this.valField.setToolTipText(text);
/* 297 */     if (this.fieldLabel != null) this.fieldLabel.setToolTipText(text);
/*     */   }
/*     */ 
/*     */   public void setEnabled(boolean b)
/*     */   {
/* 302 */     if (this.downButton != null) this.downButton.setEnabled(b);
/* 303 */     if (this.upButton != null) this.upButton.setEnabled(b);
/* 304 */     if (this.bellyButton != null) this.bellyButton.setEnabled(b);
/* 305 */     if (this.valField != null) this.valField.setEnabled(b);
/* 306 */     if (this.fieldLabel != null) this.fieldLabel.setEnabled(b);
/*     */   }
/*     */ 
/*     */   public void setText(String val)
/*     */   {
/* 312 */     this.valField.setText(val);
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/* 317 */     return this.valField.getText();
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.NumberTextField
 * JD-Core Version:    0.6.2
 */