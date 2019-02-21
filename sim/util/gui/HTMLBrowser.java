/*     */ package sim.util.gui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.util.Stack;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JEditorPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.event.HyperlinkEvent;
/*     */ import javax.swing.event.HyperlinkEvent.EventType;
/*     */ import javax.swing.event.HyperlinkListener;
/*     */ import javax.swing.text.Caret;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.EditorKit;
/*     */ 
/*     */ public class HTMLBrowser extends JPanel
/*     */ {
/*  23 */   Stack stack = new Stack();
/*     */   JEditorPane infoPane;
/*     */   JScrollPane scroll;
/*     */ 
/*     */   public void setText(Object HTMLTextOrURL)
/*     */   {
/*  29 */     if (HTMLTextOrURL == null) HTMLTextOrURL = "<html><body bgcolor='white'></body></html>";
/*  30 */     this.stack = new Stack();
/*     */ 
/*  35 */     this.infoPane.setContentType("text/html");
/*  36 */     Document d = this.infoPane.getEditorKit().createDefaultDocument();
/*     */ 
/*  41 */     this.infoPane.setDocument(d);
/*  42 */     if ((HTMLTextOrURL instanceof String)) {
/*  43 */       this.infoPane.setText((String)HTMLTextOrURL);
/*  44 */     } else if ((HTMLTextOrURL instanceof URL))
/*     */     {
/*     */       try {
/*  47 */         this.infoPane.setPage((URL)HTMLTextOrURL);
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/*  51 */         e.printStackTrace();
/*  52 */         this.infoPane = new JEditorPane();
/*     */       }
/*     */     }
/*     */     else {
/*  56 */       new RuntimeException("Info object was neither a string nor a URL").printStackTrace();
/*  57 */       this.infoPane = new JEditorPane();
/*     */     }
/*     */ 
/*  61 */     this.infoPane.getCaret().setDot(0);
/*     */   }
/*     */ 
/*     */   public HTMLBrowser(final Object HTMLTextOrURL)
/*     */   {
/*  69 */     this.infoPane = new JEditorPane();
/*  70 */     setText(HTMLTextOrURL);
/*     */ 
/*  72 */     this.infoPane.setEditable(false);
/*  73 */     this.scroll = new JScrollPane(this.infoPane);
/*  74 */     setLayout(new BorderLayout());
/*  75 */     add(this.scroll, "Center");
/*     */ 
/*  77 */     this.infoPane.getCaret().setDot(0);
/*     */ 
/*  80 */     JButton backButton = new JButton("Back");
/*  81 */     final Box backButtonBox = new Box(0);
/*  82 */     backButtonBox.add(backButton);
/*  83 */     backButtonBox.add(Box.createGlue());
/*     */ 
/*  86 */     this.infoPane.addHyperlinkListener(new HyperlinkListener()
/*     */     {
/*     */       public void hyperlinkUpdate(HyperlinkEvent he)
/*     */       {
/*  90 */         HyperlinkEvent.EventType type = he.getEventType();
/*  91 */         if (type == HyperlinkEvent.EventType.ENTERED)
/*     */         {
/*  93 */           HTMLBrowser.this.infoPane.setCursor(Cursor.getPredefinedCursor(12));
/*     */         }
/*  95 */         else if (type == HyperlinkEvent.EventType.EXITED)
/*     */         {
/*  97 */           HTMLBrowser.this.infoPane.setCursor(Cursor.getDefaultCursor());
/*     */         }
/*     */         else
/*     */         {
/* 101 */           URL url = he.getURL();
/*     */           try
/*     */           {
/* 104 */             HTMLBrowser.this.infoPane.getEditorKit().createDefaultDocument();
/* 105 */             HTMLBrowser.this.infoPane.setPage(url);
/* 106 */             if (HTMLBrowser.this.stack.isEmpty())
/*     */             {
/* 109 */               HTMLBrowser.this.add(backButtonBox, "South");
/* 110 */               HTMLBrowser.this.revalidate();
/*     */             }
/* 112 */             HTMLBrowser.this.stack.push(url);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 116 */             e.printStackTrace();
/* 117 */             Toolkit.getDefaultToolkit().beep();
/*     */           }
/*     */         }
/*     */       }
/*     */     });
/* 124 */     backButton.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent ae)
/*     */       {
/*     */         try
/*     */         {
/* 130 */           HTMLBrowser.this.stack.pop();
/* 131 */           if (HTMLBrowser.this.stack.isEmpty())
/*     */           {
/* 134 */             HTMLBrowser.this.remove(backButtonBox);
/* 135 */             HTMLBrowser.this.revalidate();
/* 136 */             HTMLBrowser.this.setText(HTMLTextOrURL);
/*     */           } else {
/* 138 */             HTMLBrowser.this.infoPane.setPage((URL)HTMLBrowser.this.stack.peek());
/*     */           }
/*     */         }
/*     */         catch (Exception e) {
/* 142 */           System.err.println("WARNING: This should never happen." + e);
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.HTMLBrowser
 * JD-Core Version:    0.6.2
 */