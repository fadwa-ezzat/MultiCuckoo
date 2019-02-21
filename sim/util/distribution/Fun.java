/*     */ package sim.util.distribution;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ class Fun
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   protected Fun()
/*     */   {
/*  28 */     throw new RuntimeException("Non instantiable");
/*     */   }
/*     */ 
/*     */   private static double _fkt_value(double lambda, double z1, double z2, double x_value)
/*     */   {
/*  33 */     double y_value = Math.cos(z1 * x_value) / Math.pow(x_value * x_value + z2 * z2, lambda + 0.5D);
/*  34 */     return y_value;
/*     */   }
/*     */   public static double bessel2_fkt(double lambda, double beta) {
/*  37 */     double pi = 3.141592653589793D;
/*     */ 
/*  39 */     double epsilon = 0.01D;
/*     */ 
/*  42 */     double prod = 0.0D;
/*     */ 
/*  45 */     double[] b0 = { -1.5787132D, -0.6130827D, 0.1735823D, 1.4793411D, 2.6667307D, 4.9086836D, 8.1355339D };
/*     */ 
/*  48 */     double[] b05 = { -1.9694802D, -0.7642538D, 0.0826017D, 1.4276355D, 2.6303682D, 4.8857787D, 8.120796800000001D };
/*     */ 
/*  51 */     double[] b1 = { -2.9807345D, -1.1969943D, -0.1843161D, 1.2739241D, 2.5218256D, 4.8172216D, 8.0765633D };
/*     */ 
/*  54 */     double[] b2 = { -5.9889676D, -2.7145389D, -1.1781269D, 0.6782201D, 2.0954009D, 4.5452152D, 7.9003173D };
/*     */ 
/*  57 */     double[] b3 = { -9.680344D, -4.8211925D, -2.6533185D, -0.2583337D, 1.4091915D, 4.0993448D, 7.608831D };
/*     */ 
/*  60 */     double[] b5 = { -18.156715200000001D, -10.0939408D, -6.5819139D, -2.9371545D, -0.6289005D, 2.7270412D, 6.6936799D };
/*     */ 
/*  63 */     double[] b8 = { -32.4910195D, -19.606594300000001D, -14.034729799999999D, -8.3839439D, -4.967973D, -0.3567823D, 4.5589697D };
/*     */ 
/*  67 */     if (lambda == 0.0D) {
/*  68 */       if (beta == 0.1D) return b0[0];
/*  69 */       if (beta == 0.5D) return b0[1];
/*  70 */       if (beta == 1.0D) return b0[2];
/*  71 */       if (beta == 2.0D) return b0[3];
/*  72 */       if (beta == 3.0D) return b0[4];
/*  73 */       if (beta == 5.0D) return b0[5];
/*  74 */       if (beta == 8.0D) return b0[6];
/*     */     }
/*     */ 
/*  77 */     if (lambda == 0.5D) {
/*  78 */       if (beta == 0.1D) return b05[0];
/*  79 */       if (beta == 0.5D) return b05[1];
/*  80 */       if (beta == 1.0D) return b05[2];
/*  81 */       if (beta == 2.0D) return b05[3];
/*  82 */       if (beta == 3.0D) return b05[4];
/*  83 */       if (beta == 5.0D) return b05[5];
/*  84 */       if (beta == 8.0D) return b05[6];
/*     */     }
/*     */ 
/*  87 */     if (lambda == 1.0D) {
/*  88 */       if (beta == 0.1D) return b1[0];
/*  89 */       if (beta == 0.5D) return b1[1];
/*  90 */       if (beta == 1.0D) return b1[2];
/*  91 */       if (beta == 2.0D) return b1[3];
/*  92 */       if (beta == 3.0D) return b1[4];
/*  93 */       if (beta == 5.0D) return b1[5];
/*  94 */       if (beta == 8.0D) return b1[6];
/*     */     }
/*     */ 
/*  97 */     if (lambda == 2.0D) {
/*  98 */       if (beta == 0.1D) return b2[0];
/*  99 */       if (beta == 0.5D) return b2[1];
/* 100 */       if (beta == 1.0D) return b2[2];
/* 101 */       if (beta == 2.0D) return b2[3];
/* 102 */       if (beta == 3.0D) return b2[4];
/* 103 */       if (beta == 5.0D) return b2[5];
/* 104 */       if (beta == 8.0D) return b2[6];
/*     */     }
/*     */ 
/* 107 */     if (lambda == 3.0D) {
/* 108 */       if (beta == 0.1D) return b3[0];
/* 109 */       if (beta == 0.5D) return b3[1];
/* 110 */       if (beta == 1.0D) return b3[2];
/* 111 */       if (beta == 2.0D) return b3[3];
/* 112 */       if (beta == 3.0D) return b3[4];
/* 113 */       if (beta == 5.0D) return b3[5];
/* 114 */       if (beta == 8.0D) return b3[6];
/*     */     }
/*     */ 
/* 117 */     if (lambda == 5.0D) {
/* 118 */       if (beta == 0.1D) return b5[0];
/* 119 */       if (beta == 0.5D) return b5[1];
/* 120 */       if (beta == 1.0D) return b5[2];
/* 121 */       if (beta == 2.0D) return b5[3];
/* 122 */       if (beta == 3.0D) return b5[4];
/* 123 */       if (beta == 5.0D) return b5[5];
/* 124 */       if (beta == 8.0D) return b5[6];
/*     */     }
/*     */ 
/* 127 */     if (lambda == 8.0D) {
/* 128 */       if (beta == 0.1D) return b8[0];
/* 129 */       if (beta == 0.5D) return b8[1];
/* 130 */       if (beta == 1.0D) return b8[2];
/* 131 */       if (beta == 2.0D) return b8[3];
/* 132 */       if (beta == 3.0D) return b8[4];
/* 133 */       if (beta == 5.0D) return b8[5];
/* 134 */       if (beta == 8.0D) return b8[6];
/*     */ 
/*     */     }
/*     */ 
/* 138 */     if (beta - 5.0D * lambda - 8.0D >= 0.0D) {
/* 139 */       double my = 4.0D * lambda * lambda;
/* 140 */       double c = -0.9189385D + 0.5D * Math.log(beta) + beta;
/* 141 */       double sum = 1.0D;
/* 142 */       double value = 1.0D;
/* 143 */       double diff = 8.0D;
/* 144 */       int i = 1;
/*     */ 
/* 146 */       while ((factorial(i) * Math.pow(8.0D * beta, i) <= 9.999999999999999E+249D) && 
/* 147 */         (i <= 10)) {
/* 148 */         if (i == 1) { prod = my - 1.0D;
/*     */         } else {
/* 150 */           value += diff;
/* 151 */           prod *= (my - value);
/* 152 */           diff *= 2.0D;
/*     */         }
/* 154 */         sum += prod / (factorial(i) * Math.pow(8.0D * beta, i));
/* 155 */         i++;
/*     */       }
/* 157 */       double erg = c - Math.log(sum);
/* 158 */       return erg;
/*     */     }
/*     */ 
/* 161 */     if ((lambda > 0.0D) && (beta - 0.04D * lambda <= 0.0D)) {
/* 162 */       if (lambda < 11.5D) {
/* 163 */         double erg = -Math.log(gamma(lambda)) - lambda * Math.log(2.0D) + lambda * Math.log(beta);
/* 164 */         return erg;
/*     */       }
/*     */ 
/* 167 */       double erg = -(lambda + 1.0D) * Math.log(2.0D) - (lambda - 0.5D) * Math.log(lambda) + lambda + lambda * Math.log(beta) - 0.5D * Math.log(1.570796326794897D);
/* 168 */       return erg;
/*     */     }
/*     */ 
/* 175 */     double x = 0.0D;
/*     */ 
/* 177 */     if (beta < 1.57D) {
/* 178 */       double fx = fkt2_value(lambda, beta, x) * 0.01D;
/* 179 */       double y = 0.0D;
/*     */       while (true) {
/* 181 */         y += 0.1D;
/* 182 */         if (fkt2_value(lambda, beta, y) < fx) break;
/*     */       }
/* 184 */       double step = y * 0.001D;
/* 185 */       double x1 = step;
/* 186 */       double sum = 0.5D * (10.0D * step + fkt2_value(lambda, beta, x1)) * step;
/* 187 */       double first_value = sum;
/*     */       while (true) {
/* 189 */         x = x1;
/* 190 */         x1 += step;
/* 191 */         double new_value = 0.5D * (fkt2_value(lambda, beta, x) + fkt2_value(lambda, beta, x1)) * step;
/* 192 */         sum += new_value;
/* 193 */         if (new_value / first_value < epsilon) break;
/*     */       }
/* 195 */       double erg = -Math.log(2.0D * sum);
/* 196 */       return erg;
/*     */     }
/*     */ 
/* 199 */     double z2 = 1.57D;
/* 200 */     double z1 = beta / 1.57D;
/* 201 */     double sum = 0.0D;
/* 202 */     double period = 3.141592653589793D / z1;
/* 203 */     double step = 0.1D * period;
/* 204 */     double border = 100.0D / ((lambda + 0.1D) * (lambda + 0.1D));
/* 205 */     int nr_per = (int)Math.ceil(border / period) + 20;
/* 206 */     double x1 = step;
/* 207 */     for (int i = 1; i <= nr_per; i++) {
/* 208 */       for (int j = 1; j <= 10; j++) {
/* 209 */         double new_value = 0.5D * (_fkt_value(lambda, z1, z2, x) + _fkt_value(lambda, z1, z2, x1)) * step;
/* 210 */         sum += new_value;
/* 211 */         x = x1;
/* 212 */         x1 += step;
/*     */       }
/*     */     }
/* 215 */     for (int j = 1; j <= 5; j++) {
/* 216 */       double new_value = 0.5D * (_fkt_value(lambda, z1, z2, x) + _fkt_value(lambda, z1, z2, x1)) * step;
/* 217 */       sum += new_value;
/* 218 */       x = x1;
/* 219 */       x1 += step;
/*     */     }
/* 221 */     double first_sum = sum;
/* 222 */     for (j = 1; j <= 10; j++) {
/* 223 */       double new_value = 0.5D * (_fkt_value(lambda, z1, z2, x) + _fkt_value(lambda, z1, z2, x1)) * step;
/* 224 */       sum += new_value;
/* 225 */       x = x1;
/* 226 */       x1 += step;
/*     */     }
/* 228 */     double second_sum = sum;
/* 229 */     sum = 0.5D * (first_sum + second_sum);
/* 230 */     double erg = gamma(lambda + 0.5D) * Math.pow(2.0D * z2, lambda) / (Math.sqrt(3.141592653589793D) * Math.pow(z1, lambda)) * sum;
/* 231 */     erg = -Math.log(2.0D * erg);
/* 232 */     return erg;
/*     */   }
/*     */ 
/*     */   public static double bessi0(double x)
/*     */   {
/*     */     double ax;
/*     */     double ans;
/*     */     double ans;
/* 242 */     if ((ax = Math.abs(x)) < 3.75D) {
/* 243 */       double y = x / 3.75D;
/* 244 */       y *= y;
/* 245 */       ans = 1.0D + y * (3.5156229D + y * (3.0899424D + y * (1.2067492D + y * (0.2659732D + y * (0.0360768D + y * 0.0045813D)))));
/*     */     }
/*     */     else
/*     */     {
/* 249 */       double y = 3.75D / ax;
/* 250 */       ans = Math.exp(ax) / Math.sqrt(ax) * (0.39894228D + y * (0.01328592D + y * (0.00225319D + y * (-0.00157565D + y * (0.00916281D + y * (-0.02057706D + y * (0.02635537D + y * (-0.01647633D + y * 0.00392377D))))))));
/*     */     }
/*     */ 
/* 255 */     return ans;
/*     */   }
/*     */ 
/*     */   public static double bessi1(double x)
/*     */   {
/*     */     double ax;
/*     */     double ans;
/*     */     double ans;
/* 264 */     if ((ax = Math.abs(x)) < 3.75D) {
/* 265 */       double y = x / 3.75D;
/* 266 */       y *= y;
/* 267 */       ans = ax * (0.5D + y * (0.87890594D + y * (0.51498869D + y * (0.15084934D + y * (0.02658733D + y * (0.00301532D + y * 0.00032411D))))));
/*     */     }
/*     */     else
/*     */     {
/* 271 */       double y = 3.75D / ax;
/* 272 */       ans = 0.02282967D + y * (-0.02895312D + y * (0.01787654D - y * 0.00420059D));
/*     */ 
/* 274 */       ans = 0.39894228D + y * (-0.03988024D + y * (-0.00362018D + y * (0.00163801D + y * (-0.01031555D + y * ans))));
/*     */ 
/* 276 */       ans *= Math.exp(ax) / Math.sqrt(ax);
/*     */     }
/* 278 */     return x < 0.0D ? -ans : ans;
/*     */   }
/*     */ 
/*     */   public static long factorial(int n)
/*     */   {
/* 284 */     return Arithmetic.longFactorial(n);
/*     */   }
/*     */ 
/*     */   private static double fkt2_value(double lambda, double beta, double x_value)
/*     */   {
/* 298 */     double y_value = cosh(lambda * x_value) * Math.exp(-beta * cosh(x_value));
/* 299 */     return y_value;
/*     */   }
/*     */   private static double cosh(double x) {
/* 302 */     return (Math.exp(x) + Math.exp(-x)) / 2.0D;
/*     */   }
/*     */ 
/*     */   public static double gamma(double x)
/*     */   {
/* 310 */     x = logGamma(x);
/*     */ 
/* 312 */     return Math.exp(x);
/*     */   }
/*     */ 
/*     */   public static double logGamma(double x)
/*     */   {
/* 318 */     double c0 = 0.9189385332046728D; double c1 = 0.08333333333333333D;
/* 319 */     double c2 = -0.002777777777777778D; double c3 = 0.0007936507936507937D;
/* 320 */     double c4 = -0.0005952380952380953D; double c5 = 0.0008417508417508417D;
/* 321 */     double c6 = -0.001917526917526917D;
/*     */ 
/* 324 */     if (x <= 0.0D) return -999.0D;
/*     */ 
/* 326 */     for (double z = 1.0D; x < 11.0D; x += 1.0D) z *= x;
/*     */ 
/* 328 */     double r = 1.0D / (x * x);
/* 329 */     double g = 0.08333333333333333D + r * (-0.002777777777777778D + r * (0.0007936507936507937D + r * (-0.0005952380952380953D + r * (0.0008417508417508417D + r + -0.001917526917526917D))));
/* 330 */     g = (x - 0.5D) * Math.log(x) - x + 0.9189385332046728D + g / x;
/* 331 */     if (z == 1.0D) return g;
/* 332 */     return g - Math.log(z);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.Fun
 * JD-Core Version:    0.6.2
 */