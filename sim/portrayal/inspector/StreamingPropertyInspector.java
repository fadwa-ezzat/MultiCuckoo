/*     */ package sim.portrayal.inspector;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Container;
/*     */ import java.awt.FileDialog;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.util.Properties;
/*     */ import sim.util.gui.NumberTextField;
/*     */ import sim.util.gui.Utilities;
/*     */ 
/*     */ public class StreamingPropertyInspector extends PropertyInspector
/*     */ {
/*     */   PrintWriter out;
/*  22 */   boolean shouldCloseOnStop = true;
/*     */   int streamingTo;
/*  24 */   int interval = 1;
/*     */   static final int CUSTOM = -1;
/*     */   static final int FILE = 0;
/*     */   static final int WINDOW = 1;
/*     */   static final int STDOUT = 2;
/*     */   JTextArea area;
/*     */   JScrollPane pane;
/*     */   JFrame frame;
/*  32 */   double lastTime = -1.0D;
/*     */ 
/*  34 */   public static String name() { return "Stream"; } 
/*  35 */   public static Class[] types() { return null; }
/*     */ 
/*     */ 
/*     */   public StreamingPropertyInspector(Properties properties, int index, Frame parent, GUIState simulation, PrintWriter stream, String streamName)
/*     */   {
/*  42 */     super(properties, index, parent, simulation);
/*  43 */     this.out = stream;
/*  44 */     setLayout(new BorderLayout());
/*  45 */     add(new JLabel("Streaming to..."), "North");
/*  46 */     add(new JLabel(streamName), "Center");
/*  47 */     this.streamingTo = -1;
/*  48 */     setValidInspector(true);
/*     */   }
/*     */ 
/*     */   public StreamingPropertyInspector(final Properties properties, final int index, Frame parent, final GUIState simulation)
/*     */   {
/*  53 */     super(properties, index, parent, simulation);
/*     */ 
/*  55 */     Object[] possibilities = { "A file (overwriting)", "A file (appending)", "A window", "Standard Out" };
/*     */ 
/*  59 */     String s = (String)JOptionPane.showInputDialog(parent, "Stream the property to:", "Stream", -1, null, possibilities, possibilities[0]);
/*     */ 
/*  68 */     if (s != null)
/*     */     {
/*  71 */       NumberTextField skipField = new NumberTextField("Skip: ", 1.0D, false)
/*     */       {
/*     */         public double newValue(double newValue)
/*     */         {
/*  75 */           int val = (int)newValue;
/*  76 */           if (val < 1) val = (int)this.currentValue;
/*  77 */           StreamingPropertyInspector.this.interval = val;
/*  78 */           return val;
/*     */         }
/*     */       };
/*  81 */       skipField.setToolTipText("Specify the number of steps between stream fetches");
/*  82 */       skipField.setBorder(BorderFactory.createEmptyBorder(2, 2, 0, 2));
/*     */ 
/*  84 */       if ((s.equals(possibilities[0])) || (s.equals(possibilities[1])))
/*     */       {
/*  86 */         this.streamingTo = 0;
/*     */ 
/*  88 */         FileDialog fd = new FileDialog(parent, "Stream the Property " + (s.equals(possibilities[1]) ? "(appending) " : "") + "\"" + properties.getName(index) + "\" to File...", 1);
/*     */ 
/*  91 */         fd.setFile(properties.getName(index) + ".out");
/*  92 */         fd.setVisible(true);
/*  93 */         if (fd.getFile() != null)
/*     */           try {
/*  95 */             File file = new File(fd.getDirectory(), Utilities.ensureFileEndsWith(fd.getFile(), ".out"));
/*     */ 
/*  99 */             this.out = new PrintWriter(new BufferedWriter(new FileWriter(file.getCanonicalPath(), s.equals(possibilities[1]))));
/* 100 */             setLayout(new BorderLayout());
/* 101 */             Box b = new Box(1);
/* 102 */             b.add(skipField);
/* 103 */             b.add(new JLabel("Streaming to" + (s.equals(possibilities[1]) ? " (appending)" : "") + "..."));
/*     */ 
/* 106 */             b.add(new JLabel(file.getPath()));
/* 107 */             b.add(new JLabel("Format: \"timestamp: value\""));
/* 108 */             b.add(Box.createGlue());
/* 109 */             add(b, "North");
/* 110 */             setValidInspector(true);
/*     */           }
/*     */           catch (IOException e)
/*     */           {
/* 114 */             e.printStackTrace();
/*     */           }
/*     */       }
/* 117 */       else if (s.equals(possibilities[2]))
/*     */       {
/* 119 */         this.streamingTo = 1;
/* 120 */         this.area = new JTextArea();
/* 121 */         this.pane = new JScrollPane(this.area);
/* 122 */         setLayout(new BorderLayout());
/* 123 */         add(this.pane, "Center");
/* 124 */         add(skipField, "North");
/* 125 */         Box box = new Box(0);
/* 126 */         JButton saveButton = new JButton("Save Contents");
/* 127 */         box.add(saveButton);
/* 128 */         saveButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 132 */             synchronized (simulation.state.schedule)
/*     */             {
/* 134 */               FileDialog fd = new FileDialog(StreamingPropertyInspector.this.frame, "Save the (Present) Contents to File...", 1);
/* 135 */               fd.setFile(properties.getName(index) + ".out");
/* 136 */               fd.setVisible(true);
/* 137 */               if (fd.getFile() != null)
/*     */                 try {
/* 139 */                   File file = new File(fd.getDirectory(), Utilities.ensureFileEndsWith(fd.getFile(), ".out"));
/*     */ 
/* 143 */                   PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter(file)));
/* 144 */                   p.println(StreamingPropertyInspector.this.area.getText());
/* 145 */                   p.close();
/*     */                 }
/*     */                 catch (IOException ex)
/*     */                 {
/* 149 */                   ex.printStackTrace();
/*     */                 }
/*     */             }
/*     */           }
/*     */         });
/* 154 */         box.add(new JLabel("Format: \"timestamp: value\""));
/* 155 */         box.add(Box.createGlue());
/* 156 */         add(box, "South");
/* 157 */         setValidInspector(true);
/*     */       }
/*     */       else
/*     */       {
/* 161 */         this.streamingTo = 2;
/* 162 */         this.shouldCloseOnStop = false;
/* 163 */         setLayout(new BorderLayout());
/* 164 */         Box b = new Box(1);
/* 165 */         b.add(skipField);
/* 166 */         b.add(new JLabel("Streaming to Standard Out"), "Center");
/* 167 */         b.add(new JLabel("Format: \"timestamp/object/property: value\""));
/* 168 */         b.add(Box.createGlue());
/* 169 */         add(b, "North");
/* 170 */         setValidInspector(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateInspector()
/*     */   {
/* 177 */     double time = this.simulation.state.schedule.getTime();
/* 178 */     if ((time >= 0.0D) && (time < (1.0D / 0.0D)) && (this.lastTime <= time - this.interval))
/*     */     {
/* 181 */       this.lastTime = time;
/* 182 */       switch (this.streamingTo) {
/*     */       case -1:
/*     */       case 0:
/* 185 */         if (this.out != null) this.out.println(time + ": " + this.properties.getValue(this.index)); break;
/*     */       case 1:
/* 188 */         this.area.append(time + ": " + this.properties.getValue(this.index) + "\n");
/* 189 */         break;
/*     */       case 2:
/* 191 */         System.out.println(this.properties.getObject() + "/" + this.properties.getName(this.index) + "/" + time + ": " + this.properties.getValue(this.index));
/*     */ 
/* 193 */         break;
/*     */       default:
/* 195 */         throw new RuntimeException("default case should never occur");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Stoppable reviseStopper(Stoppable stopper)
/*     */   {
/* 203 */     final Stoppable newStopper = super.reviseStopper(stopper);
/* 204 */     return new Stoppable()
/*     */     {
/*     */       public void stop()
/*     */       {
/* 208 */         if (newStopper != null) newStopper.stop();
/* 209 */         if (StreamingPropertyInspector.this.out != null)
/*     */         {
/* 211 */           if (StreamingPropertyInspector.this.streamingTo == 2) StreamingPropertyInspector.this.out.flush();
/* 212 */           else if ((StreamingPropertyInspector.this.streamingTo == 0) || (StreamingPropertyInspector.this.streamingTo == -1)) StreamingPropertyInspector.this.out.close();
/*     */         }
/* 214 */         StreamingPropertyInspector.this.out = null;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public JFrame createFrame(Stoppable stopper)
/*     */   {
/* 221 */     this.frame = super.createFrame(stopper);
/* 222 */     this.frame.getContentPane().setLayout(new BorderLayout());
/* 223 */     this.frame.getContentPane().removeAll();
/* 224 */     this.frame.getContentPane().add(this, "Center");
/* 225 */     if (this.pane != null) this.frame.setSize(400, 300); else
/* 226 */       this.frame.pack();
/* 227 */     return this.frame;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.inspector.StreamingPropertyInspector
 * JD-Core Version:    0.6.2
 */