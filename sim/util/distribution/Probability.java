/*      */ package sim.util.distribution;
/*      */ 
/*      */ public class Probability extends Constants
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*   32 */   protected static final double[] P0 = { -59.963350101410789D, 98.001075418599967D, -56.676285746907027D, 13.931260938727968D, -1.239165838673813D };
/*      */ 
/*   39 */   protected static final double[] Q0 = { 1.954488583381418D, 4.676279128988815D, 86.360242139089053D, -225.46268785411937D, 200.26021238006066D, -82.037225616833339D, 15.90562251262117D, -1.1833162112133D };
/*      */ 
/*   55 */   protected static final double[] P1 = { 4.055448923059625D, 31.525109459989388D, 57.162819224642128D, 44.080507389320083D, 14.684956192885803D, 2.186633068507903D, -0.1402560791713545D, -0.03504246268278482D, -0.0008574567851546855D };
/*      */ 
/*   66 */   protected static final double[] Q1 = { 15.779988325646675D, 45.390763512887922D, 41.317203825467203D, 15.04253856929075D, 2.504649462083094D, -0.1421829228547878D, -0.03808064076915783D, -0.0009332594808954574D };
/*      */ 
/*   81 */   protected static final double[] P2 = { 3.23774891776946D, 6.915228890689842D, 3.938810252924744D, 1.333034608158076D, 0.2014853895491791D, 0.012371663481782D, 0.0003015815535082354D, 2.658069746867376E-006D, 6.239745391849833E-009D };
/*      */ 
/*   92 */   protected static final double[] Q2 = { 6.02427039364742D, 3.679835638561609D, 1.377020994890813D, 0.2162369935944966D, 0.01342040060885432D, 0.0003280144646821277D, 2.892478647453807E-006D, 6.790194080099813E-009D };
/*      */ 
/*      */   public static double beta(double a, double b, double x)
/*      */   {
/*  129 */     return incompleteBeta(a, b, x);
/*      */   }
/*      */ 
/*      */   public static double betaComplemented(double a, double b, double x)
/*      */   {
/*  139 */     return incompleteBeta(b, a, x);
/*      */   }
/*      */ 
/*      */   public static double binomial(int k, int n, double p)
/*      */   {
/*  162 */     if ((p < 0.0D) || (p > 1.0D)) throw new IllegalArgumentException();
/*  163 */     if ((k < 0) || (n < k)) throw new IllegalArgumentException();
/*      */ 
/*  165 */     if (k == n) return 1.0D;
/*  166 */     if (k == 0) return Math.pow(1.0D - p, n - k);
/*      */ 
/*  168 */     return incompleteBeta(n - k, k + 1, 1.0D - p);
/*      */   }
/*      */ 
/*      */   public static double binomialComplemented(int k, int n, double p)
/*      */   {
/*  191 */     if ((p < 0.0D) || (p > 1.0D)) throw new IllegalArgumentException();
/*  192 */     if ((k < 0) || (n < k)) throw new IllegalArgumentException();
/*      */ 
/*  194 */     if (k == n) return 0.0D;
/*  195 */     if (k == 0) return 1.0D - Math.pow(1.0D - p, n - k);
/*      */ 
/*  197 */     return incompleteBeta(k + 1, n - k, p);
/*      */   }
/*      */ 
/*      */   public static double chiSquare(double v, double x)
/*      */     throws ArithmeticException
/*      */   {
/*  225 */     if ((x < 0.0D) || (v < 1.0D)) return 0.0D;
/*  226 */     return incompleteGamma(v / 2.0D, x / 2.0D);
/*      */   }
/*      */ 
/*      */   public static double chiSquareComplemented(double v, double x)
/*      */     throws ArithmeticException
/*      */   {
/*  254 */     if ((x < 0.0D) || (v < 1.0D)) return 0.0D;
/*  255 */     return incompleteGammaComplement(v / 2.0D, x / 2.0D);
/*      */   }
/*      */ 
/*      */   public static double errorFunction(double x)
/*      */     throws ArithmeticException
/*      */   {
/*  280 */     double[] T = { 9.604973739870516D, 90.026019720384269D, 2232.0053459468431D, 7003.3251411280507D, 55592.301301039493D };
/*      */ 
/*  287 */     double[] U = { 33.561714164750313D, 521.35794978015269D, 4594.3238297098014D, 22629.000061389095D, 49267.394260863592D };
/*      */ 
/*  296 */     if (Math.abs(x) > 1.0D) return 1.0D - errorFunctionComplemented(x);
/*  297 */     double z = x * x;
/*  298 */     double y = x * Polynomial.polevl(z, T, 4) / Polynomial.p1evl(z, U, 5);
/*  299 */     return y;
/*      */   }
/*      */ 
/*      */   public static double errorFunctionComplemented(double a)
/*      */     throws ArithmeticException
/*      */   {
/*  326 */     double[] P = { 2.461969814735305E-010D, 0.5641895648310689D, 7.463210564422699D, 48.637197098568137D, 196.5208329560771D, 526.44519499547732D, 934.52852717195765D, 1027.5518868951572D, 557.53533536939938D };
/*      */ 
/*  337 */     double[] Q = { 13.228195115474499D, 86.707214088598974D, 354.93777888781989D, 975.70850174320549D, 1823.9091668790973D, 2246.3376081871097D, 1656.6630919416134D, 557.53534081772773D };
/*      */ 
/*  349 */     double[] R = { 0.5641895835477551D, 1.275366707599781D, 5.019050422511805D, 6.160210979930536D, 7.40974269950449D, 2.978866653721002D };
/*      */ 
/*  357 */     double[] S = { 2.260528632201173D, 9.396035249380015D, 12.048953980809666D, 17.081445074756591D, 9.608968090632859D, 3.369076451000815D };
/*      */     double x;
/*      */     double x;
/*  367 */     if (a < 0.0D) x = -a; else {
/*  368 */       x = a;
/*      */     }
/*  370 */     if (x < 1.0D) return 1.0D - errorFunction(a);
/*      */ 
/*  372 */     double z = -a * a;
/*      */ 
/*  374 */     if (z < -709.78271289338397D) {
/*  375 */       if (a < 0.0D) return 2.0D;
/*  376 */       return 0.0D;
/*      */     }
/*      */ 
/*  379 */     z = Math.exp(z);
/*      */     double q;
/*      */     double p;
/*      */     double q;
/*  381 */     if (x < 8.0D) {
/*  382 */       double p = Polynomial.polevl(x, P, 8);
/*  383 */       q = Polynomial.p1evl(x, Q, 8);
/*      */     } else {
/*  385 */       p = Polynomial.polevl(x, R, 5);
/*  386 */       q = Polynomial.p1evl(x, S, 6);
/*      */     }
/*      */ 
/*  389 */     double y = z * p / q;
/*      */ 
/*  391 */     if (a < 0.0D) y = 2.0D - y;
/*      */ 
/*  393 */     if (y == 0.0D) {
/*  394 */       if (a < 0.0D) return 2.0D;
/*  395 */       return 0.0D;
/*      */     }
/*      */ 
/*  398 */     return y;
/*      */   }
/*      */ 
/*      */   public static double gamma(double a, double b, double x)
/*      */   {
/*  422 */     if (x < 0.0D) return 0.0D;
/*  423 */     return incompleteGamma(b, a * x);
/*      */   }
/*      */ 
/*      */   public static double gammaComplemented(double a, double b, double x)
/*      */   {
/*  447 */     if (x < 0.0D) return 0.0D;
/*  448 */     return incompleteGammaComplement(b, a * x);
/*      */   }
/*      */ 
/*      */   public static double negativeBinomial(int k, int n, double p)
/*      */   {
/*  473 */     if ((p < 0.0D) || (p > 1.0D)) throw new IllegalArgumentException();
/*  474 */     if (k < 0) return 0.0D;
/*      */ 
/*  476 */     return incompleteBeta(n, k + 1, p);
/*      */   }
/*      */ 
/*      */   public static double negativeBinomialComplemented(int k, int n, double p)
/*      */   {
/*  499 */     if ((p < 0.0D) || (p > 1.0D)) throw new IllegalArgumentException();
/*  500 */     if (k < 0) return 0.0D;
/*      */ 
/*  502 */     return incompleteBeta(k + 1, n, 1.0D - p);
/*      */   }
/*      */ 
/*      */   public static double normal(double a)
/*      */     throws ArithmeticException
/*      */   {
/*  525 */     double x = a * 0.7071067811865476D;
/*  526 */     double z = Math.abs(x);
/*      */     double y;
/*      */     double y;
/*  528 */     if (z < 0.7071067811865476D) { y = 0.5D + 0.5D * errorFunction(x);
/*      */     } else {
/*  530 */       y = 0.5D * errorFunctionComplemented(z);
/*  531 */       if (x > 0.0D) y = 1.0D - y;
/*      */     }
/*      */ 
/*  534 */     return y;
/*      */   }
/*      */ 
/*      */   public static double normal(double mean, double variance, double x)
/*      */     throws ArithmeticException
/*      */   {
/*  557 */     if (x > 0.0D) {
/*  558 */       return 0.5D + 0.5D * errorFunction((x - mean) / Math.sqrt(2.0D * variance));
/*      */     }
/*  560 */     return 0.5D - 0.5D * errorFunction(-(x - mean) / Math.sqrt(2.0D * variance));
/*      */   }
/*      */ 
/*      */   public static double normalInverse(double y0)
/*      */     throws ArithmeticException
/*      */   {
/*  580 */     double s2pi = Math.sqrt(6.283185307179586D);
/*      */ 
/*  582 */     if (y0 <= 0.0D) throw new IllegalArgumentException();
/*  583 */     if (y0 >= 1.0D) throw new IllegalArgumentException();
/*  584 */     int code = 1;
/*  585 */     double y = y0;
/*  586 */     if (y > 0.864664716763387D) {
/*  587 */       y = 1.0D - y;
/*  588 */       code = 0;
/*      */     }
/*      */ 
/*  591 */     if (y > 0.135335283236613D) {
/*  592 */       y -= 0.5D;
/*  593 */       double y2 = y * y;
/*  594 */       double x = y + y * (y2 * Polynomial.polevl(y2, P0, 4) / Polynomial.p1evl(y2, Q0, 8));
/*  595 */       x *= s2pi;
/*  596 */       return x;
/*      */     }
/*      */ 
/*  599 */     double x = Math.sqrt(-2.0D * Math.log(y));
/*  600 */     double x0 = x - Math.log(x) / x;
/*      */ 
/*  602 */     double z = 1.0D / x;
/*      */     double x1;
/*      */     double x1;
/*  603 */     if (x < 8.0D)
/*  604 */       x1 = z * Polynomial.polevl(z, P1, 8) / Polynomial.p1evl(z, Q1, 8);
/*      */     else
/*  606 */       x1 = z * Polynomial.polevl(z, P2, 8) / Polynomial.p1evl(z, Q2, 8);
/*  607 */     x = x0 - x1;
/*  608 */     if (code != 0)
/*  609 */       x = -x;
/*  610 */     return x;
/*      */   }
/*      */ 
/*      */   public static double poisson(int k, double mean)
/*      */     throws ArithmeticException
/*      */   {
/*  632 */     if (mean < 0.0D) throw new IllegalArgumentException();
/*  633 */     if (k < 0) return 0.0D;
/*  634 */     return incompleteGammaComplement(k + 1, mean);
/*      */   }
/*      */ 
/*      */   public static double poissonComplemented(int k, double mean)
/*      */     throws ArithmeticException
/*      */   {
/*  656 */     if (mean < 0.0D) throw new IllegalArgumentException();
/*  657 */     if (k < -1) return 0.0D;
/*  658 */     return incompleteGamma(k + 1, mean);
/*      */   }
/*      */ 
/*      */   public static double studentT(double k, double t)
/*      */     throws ArithmeticException
/*      */   {
/*  689 */     if (k <= 0.0D) throw new IllegalArgumentException();
/*  690 */     if (t == 0.0D) return 0.5D;
/*      */ 
/*  692 */     double cdf = 0.5D * incompleteBeta(0.5D * k, 0.5D, k / (k + t * t));
/*      */ 
/*  694 */     if (t >= 0.0D) cdf = 1.0D - cdf;
/*      */ 
/*  696 */     return cdf;
/*      */   }
/*      */ 
/*      */   public static double studentTInverse(double alpha, int size)
/*      */   {
/*  712 */     double cumProb = 1.0D - alpha / 2.0D;
/*      */ 
/*  717 */     cumProb = 1.0D - alpha / 2.0D;
/*  718 */     double x1 = normalInverse(cumProb);
/*      */ 
/*  721 */     if (size > 200) {
/*  722 */       return x1;
/*      */     }
/*      */ 
/*  726 */     double f1 = studentT(size, x1) - cumProb;
/*  727 */     double x2 = x1; double f2 = f1;
/*      */     do {
/*  729 */       if (f1 > 0.0D)
/*  730 */         x2 /= 2.0D;
/*      */       else {
/*  732 */         x2 += x1;
/*      */       }
/*  734 */       f2 = studentT(size, x2) - cumProb;
/*  735 */     }while (f1 * f2 > 0.0D);
/*      */     do
/*      */     {
/*  741 */       double s12 = (f2 - f1) / (x2 - x1);
/*  742 */       double x3 = x2 - f2 / s12;
/*      */ 
/*  745 */       double f3 = studentT(size, x3) - cumProb;
/*  746 */       if (Math.abs(f3) < 1.0E-008D)
/*      */       {
/*  748 */         return x3;
/*      */       }
/*      */ 
/*  751 */       if (f3 * f2 < 0.0D) {
/*  752 */         x1 = x2; f1 = f2;
/*  753 */         x2 = x3; f2 = f3;
/*      */       } else {
/*  755 */         double g = f2 / (f2 + f3);
/*  756 */         f1 = g * f1;
/*  757 */         x2 = x3; f2 = f3;
/*      */       }
/*      */     }
/*  759 */     while (Math.abs(x2 - x1) > 0.001D);
/*      */ 
/*  761 */     if (Math.abs(f2) <= Math.abs(f1)) {
/*  762 */       return x2;
/*      */     }
/*  764 */     return x1;
/*      */   }
/*      */ 
/*      */   public static double beta(double a, double b)
/*      */     throws ArithmeticException
/*      */   {
/*  792 */     double y = a + b;
/*  793 */     y = gamma(y);
/*  794 */     if (y == 0.0D) return 1.0D;
/*      */ 
/*  796 */     if (a > b) {
/*  797 */       y = gamma(a) / y;
/*  798 */       y *= gamma(b);
/*      */     }
/*      */     else {
/*  801 */       y = gamma(b) / y;
/*  802 */       y *= gamma(a);
/*      */     }
/*      */ 
/*  805 */     return y;
/*      */   }
/*      */ 
/*      */   public static double gamma(double x)
/*      */     throws ArithmeticException
/*      */   {
/*  812 */     double[] P = { 0.0001601195224767519D, 0.001191351470065864D, 0.01042137975617616D, 0.04763678004571372D, 0.207448227648436D, 0.4942148268014971D, 1.0D };
/*      */ 
/*  821 */     double[] Q = { -2.315818733241201E-005D, 0.0005396055804933034D, -0.004456419138517973D, 0.01181397852220604D, 0.03582363986054987D, -0.2345917957182434D, 0.0714304917030273D, 1.0D };
/*      */ 
/*  837 */     double q = Math.abs(x);
/*      */ 
/*  839 */     if (q > 33.0D) {
/*  840 */       if (x < 0.0D) {
/*  841 */         double p = Math.floor(q);
/*  842 */         if (p == q) throw new ArithmeticException("gamma: overflow");
/*      */ 
/*  844 */         double z = q - p;
/*  845 */         if (z > 0.5D) {
/*  846 */           p += 1.0D;
/*  847 */           z = q - p;
/*      */         }
/*  849 */         z = q * Math.sin(3.141592653589793D * z);
/*  850 */         if (z == 0.0D) throw new ArithmeticException("gamma: overflow");
/*  851 */         z = Math.abs(z);
/*  852 */         z = 3.141592653589793D / (z * stirlingFormula(q));
/*      */ 
/*  854 */         return -z;
/*      */       }
/*  856 */       return stirlingFormula(x);
/*      */     }
/*      */ 
/*  860 */     double z = 1.0D;
/*  861 */     while (x >= 3.0D) {
/*  862 */       x -= 1.0D;
/*  863 */       z *= x;
/*      */     }
/*      */ 
/*  866 */     while (x < 0.0D) {
/*  867 */       if (x == 0.0D) {
/*  868 */         throw new ArithmeticException("gamma: singular");
/*      */       }
/*  870 */       if (x > -1.E-009D) {
/*  871 */         return z / ((1.0D + 0.5772156649015329D * x) * x);
/*      */       }
/*  873 */       z /= x;
/*  874 */       x += 1.0D;
/*      */     }
/*      */ 
/*  877 */     while (x < 2.0D) {
/*  878 */       if (x == 0.0D) {
/*  879 */         throw new ArithmeticException("gamma: singular");
/*      */       }
/*  881 */       if (x < 1.E-009D) {
/*  882 */         return z / ((1.0D + 0.5772156649015329D * x) * x);
/*      */       }
/*  884 */       z /= x;
/*  885 */       x += 1.0D;
/*      */     }
/*      */ 
/*  888 */     if ((x == 2.0D) || (x == 3.0D)) return z;
/*      */ 
/*  890 */     x -= 2.0D;
/*  891 */     double p = Polynomial.polevl(x, P, 6);
/*  892 */     q = Polynomial.polevl(x, Q, 7);
/*  893 */     return z * p / q;
/*      */   }
/*      */ 
/*      */   public static double incompleteBeta(double aa, double bb, double xx)
/*      */     throws ArithmeticException
/*      */   {
/*  907 */     if ((aa <= 0.0D) || (bb <= 0.0D)) throw new ArithmeticException("ibeta: Domain error!");
/*      */ 
/*  910 */     if ((xx <= 0.0D) || (xx >= 1.0D)) {
/*  911 */       if (xx == 0.0D) return 0.0D;
/*  912 */       if (xx == 1.0D) return 1.0D;
/*  913 */       throw new ArithmeticException("ibeta: Domain error!");
/*      */     }
/*      */ 
/*  916 */     boolean flag = false;
/*  917 */     if ((bb * xx <= 1.0D) && (xx <= 0.95D)) {
/*  918 */       double t = powerSeries(aa, bb, xx);
/*  919 */       return t;
/*      */     }
/*      */ 
/*  922 */     double w = 1.0D - xx;
/*      */     double x;
/*      */     double a;
/*      */     double b;
/*      */     double xc;
/*      */     double x;
/*  925 */     if (xx > aa / (aa + bb)) {
/*  926 */       flag = true;
/*  927 */       double a = bb;
/*  928 */       double b = aa;
/*  929 */       double xc = xx;
/*  930 */       x = w;
/*      */     } else {
/*  932 */       a = aa;
/*  933 */       b = bb;
/*  934 */       xc = w;
/*  935 */       x = xx;
/*      */     }
/*      */ 
/*  938 */     if ((flag) && (b * x <= 1.0D) && (x <= 0.95D)) {
/*  939 */       double t = powerSeries(a, b, x);
/*  940 */       if (t <= 1.110223024625157E-016D) t = 0.9999999999999999D; else
/*  941 */         t = 1.0D - t;
/*  942 */       return t;
/*      */     }
/*      */ 
/*  946 */     double y = x * (a + b - 2.0D) - (a - 1.0D);
/*  947 */     if (y < 0.0D)
/*  948 */       w = incompleteBetaFraction1(a, b, x);
/*      */     else {
/*  950 */       w = incompleteBetaFraction2(a, b, x) / xc;
/*      */     }
/*      */ 
/*  956 */     y = a * Math.log(x);
/*  957 */     double t = b * Math.log(xc);
/*  958 */     if ((a + b < 171.62437695630271D) && (Math.abs(y) < 709.78271289338397D) && (Math.abs(t) < 709.78271289338397D)) {
/*  959 */       t = Math.pow(xc, b);
/*  960 */       t *= Math.pow(x, a);
/*  961 */       t /= a;
/*  962 */       t *= w;
/*  963 */       t *= gamma(a + b) / (gamma(a) * gamma(b));
/*  964 */       if (flag) {
/*  965 */         if (t <= 1.110223024625157E-016D) t = 0.9999999999999999D; else
/*  966 */           t = 1.0D - t;
/*      */       }
/*  968 */       return t;
/*      */     }
/*      */ 
/*  971 */     y += t + logGamma(a + b) - logGamma(a) - logGamma(b);
/*  972 */     y += Math.log(w / a);
/*  973 */     if (y < -745.13321910194122D)
/*  974 */       t = 0.0D;
/*      */     else {
/*  976 */       t = Math.exp(y);
/*      */     }
/*  978 */     if (flag) {
/*  979 */       if (t <= 1.110223024625157E-016D) t = 0.9999999999999999D; else
/*  980 */         t = 1.0D - t;
/*      */     }
/*  982 */     return t;
/*      */   }
/*      */ 
/*      */   static double incompleteBetaFraction1(double a, double b, double x)
/*      */     throws ArithmeticException
/*      */   {
/*  993 */     double k1 = a;
/*  994 */     double k2 = a + b;
/*  995 */     double k3 = a;
/*  996 */     double k4 = a + 1.0D;
/*  997 */     double k5 = 1.0D;
/*  998 */     double k6 = b - 1.0D;
/*  999 */     double k7 = k4;
/* 1000 */     double k8 = a + 2.0D;
/*      */ 
/* 1002 */     double pkm2 = 0.0D;
/* 1003 */     double qkm2 = 1.0D;
/* 1004 */     double pkm1 = 1.0D;
/* 1005 */     double qkm1 = 1.0D;
/* 1006 */     double ans = 1.0D;
/* 1007 */     double r = 1.0D;
/* 1008 */     int n = 0;
/* 1009 */     double thresh = 3.33066907387547E-016D;
/*      */     do {
/* 1011 */       double xk = -(x * k1 * k2) / (k3 * k4);
/* 1012 */       double pk = pkm1 + pkm2 * xk;
/* 1013 */       double qk = qkm1 + qkm2 * xk;
/* 1014 */       pkm2 = pkm1;
/* 1015 */       pkm1 = pk;
/* 1016 */       qkm2 = qkm1;
/* 1017 */       qkm1 = qk;
/*      */ 
/* 1019 */       xk = x * k5 * k6 / (k7 * k8);
/* 1020 */       pk = pkm1 + pkm2 * xk;
/* 1021 */       qk = qkm1 + qkm2 * xk;
/* 1022 */       pkm2 = pkm1;
/* 1023 */       pkm1 = pk;
/* 1024 */       qkm2 = qkm1;
/* 1025 */       qkm1 = qk;
/*      */ 
/* 1027 */       if (qk != 0.0D) r = pk / qk;
/*      */       double t;
/* 1028 */       if (r != 0.0D) {
/* 1029 */         double t = Math.abs((ans - r) / r);
/* 1030 */         ans = r;
/*      */       } else {
/* 1032 */         t = 1.0D;
/*      */       }
/* 1034 */       if (t < thresh) return ans;
/*      */ 
/* 1036 */       k1 += 1.0D;
/* 1037 */       k2 += 1.0D;
/* 1038 */       k3 += 2.0D;
/* 1039 */       k4 += 2.0D;
/* 1040 */       k5 += 1.0D;
/* 1041 */       k6 -= 1.0D;
/* 1042 */       k7 += 2.0D;
/* 1043 */       k8 += 2.0D;
/*      */ 
/* 1045 */       if (Math.abs(qk) + Math.abs(pk) > 4503599627370496.0D) {
/* 1046 */         pkm2 *= 2.220446049250313E-016D;
/* 1047 */         pkm1 *= 2.220446049250313E-016D;
/* 1048 */         qkm2 *= 2.220446049250313E-016D;
/* 1049 */         qkm1 *= 2.220446049250313E-016D;
/*      */       }
/* 1051 */       if ((Math.abs(qk) < 2.220446049250313E-016D) || (Math.abs(pk) < 2.220446049250313E-016D)) {
/* 1052 */         pkm2 *= 4503599627370496.0D;
/* 1053 */         pkm1 *= 4503599627370496.0D;
/* 1054 */         qkm2 *= 4503599627370496.0D;
/* 1055 */         qkm1 *= 4503599627370496.0D;
/*      */       }
/* 1057 */       n++; } while (n < 300);
/*      */ 
/* 1059 */     return ans;
/*      */   }
/*      */ 
/*      */   static double incompleteBetaFraction2(double a, double b, double x)
/*      */     throws ArithmeticException
/*      */   {
/* 1070 */     double k1 = a;
/* 1071 */     double k2 = b - 1.0D;
/* 1072 */     double k3 = a;
/* 1073 */     double k4 = a + 1.0D;
/* 1074 */     double k5 = 1.0D;
/* 1075 */     double k6 = a + b;
/* 1076 */     double k7 = a + 1.0D;
/* 1077 */     double k8 = a + 2.0D;
/*      */ 
/* 1079 */     double pkm2 = 0.0D;
/* 1080 */     double qkm2 = 1.0D;
/* 1081 */     double pkm1 = 1.0D;
/* 1082 */     double qkm1 = 1.0D;
/* 1083 */     double z = x / (1.0D - x);
/* 1084 */     double ans = 1.0D;
/* 1085 */     double r = 1.0D;
/* 1086 */     int n = 0;
/* 1087 */     double thresh = 3.33066907387547E-016D;
/*      */     do {
/* 1089 */       double xk = -(z * k1 * k2) / (k3 * k4);
/* 1090 */       double pk = pkm1 + pkm2 * xk;
/* 1091 */       double qk = qkm1 + qkm2 * xk;
/* 1092 */       pkm2 = pkm1;
/* 1093 */       pkm1 = pk;
/* 1094 */       qkm2 = qkm1;
/* 1095 */       qkm1 = qk;
/*      */ 
/* 1097 */       xk = z * k5 * k6 / (k7 * k8);
/* 1098 */       pk = pkm1 + pkm2 * xk;
/* 1099 */       qk = qkm1 + qkm2 * xk;
/* 1100 */       pkm2 = pkm1;
/* 1101 */       pkm1 = pk;
/* 1102 */       qkm2 = qkm1;
/* 1103 */       qkm1 = qk;
/*      */ 
/* 1105 */       if (qk != 0.0D) r = pk / qk;
/*      */       double t;
/* 1106 */       if (r != 0.0D) {
/* 1107 */         double t = Math.abs((ans - r) / r);
/* 1108 */         ans = r;
/*      */       } else {
/* 1110 */         t = 1.0D;
/*      */       }
/* 1112 */       if (t < thresh) return ans;
/*      */ 
/* 1114 */       k1 += 1.0D;
/* 1115 */       k2 -= 1.0D;
/* 1116 */       k3 += 2.0D;
/* 1117 */       k4 += 2.0D;
/* 1118 */       k5 += 1.0D;
/* 1119 */       k6 += 1.0D;
/* 1120 */       k7 += 2.0D;
/* 1121 */       k8 += 2.0D;
/*      */ 
/* 1123 */       if (Math.abs(qk) + Math.abs(pk) > 4503599627370496.0D) {
/* 1124 */         pkm2 *= 2.220446049250313E-016D;
/* 1125 */         pkm1 *= 2.220446049250313E-016D;
/* 1126 */         qkm2 *= 2.220446049250313E-016D;
/* 1127 */         qkm1 *= 2.220446049250313E-016D;
/*      */       }
/* 1129 */       if ((Math.abs(qk) < 2.220446049250313E-016D) || (Math.abs(pk) < 2.220446049250313E-016D)) {
/* 1130 */         pkm2 *= 4503599627370496.0D;
/* 1131 */         pkm1 *= 4503599627370496.0D;
/* 1132 */         qkm2 *= 4503599627370496.0D;
/* 1133 */         qkm1 *= 4503599627370496.0D;
/*      */       }
/* 1135 */       n++; } while (n < 300);
/*      */ 
/* 1137 */     return ans;
/*      */   }
/*      */ 
/*      */   public static double incompleteGamma(double a, double x)
/*      */     throws ArithmeticException
/*      */   {
/* 1150 */     if ((x <= 0.0D) || (a <= 0.0D)) return 0.0D;
/*      */ 
/* 1152 */     if ((x > 1.0D) && (x > a)) return 1.0D - incompleteGammaComplement(a, x);
/*      */ 
/* 1155 */     double ax = a * Math.log(x) - x - logGamma(a);
/* 1156 */     if (ax < -709.78271289338397D) return 0.0D;
/*      */ 
/* 1158 */     ax = Math.exp(ax);
/*      */ 
/* 1161 */     double r = a;
/* 1162 */     double c = 1.0D;
/* 1163 */     double ans = 1.0D;
/*      */     do
/*      */     {
/* 1166 */       r += 1.0D;
/* 1167 */       c *= x / r;
/* 1168 */       ans += c;
/*      */     }
/* 1170 */     while (c / ans > 1.110223024625157E-016D);
/*      */ 
/* 1172 */     return ans * ax / a;
/*      */   }
/*      */ 
/*      */   public static double incompleteGammaComplement(double a, double x)
/*      */     throws ArithmeticException
/*      */   {
/* 1184 */     if ((x <= 0.0D) || (a <= 0.0D)) return 1.0D;
/*      */ 
/* 1186 */     if ((x < 1.0D) || (x < a)) return 1.0D - incompleteGamma(a, x);
/*      */ 
/* 1188 */     double ax = a * Math.log(x) - x - logGamma(a);
/* 1189 */     if (ax < -709.78271289338397D) return 0.0D; 
/*      */ ax = Math.exp(ax);
/*      */ 
/* 1194 */     double y = 1.0D - a;
/* 1195 */     double z = x + y + 1.0D;
/* 1196 */     double c = 0.0D;
/* 1197 */     double pkm2 = 1.0D;
/* 1198 */     double qkm2 = x;
/* 1199 */     double pkm1 = x + 1.0D;
/* 1200 */     double qkm1 = z * x;
/* 1201 */     double ans = pkm1 / qkm1;
/*      */     double t;
/*      */     do { c += 1.0D;
/* 1205 */       y += 1.0D;
/* 1206 */       z += 2.0D;
/* 1207 */       double yc = y * c;
/* 1208 */       double pk = pkm1 * z - pkm2 * yc;
/* 1209 */       double qk = qkm1 * z - qkm2 * yc;
/* 1210 */       if (qk != 0.0D) {
/* 1211 */         double r = pk / qk;
/* 1212 */         double t = Math.abs((ans - r) / r);
/* 1213 */         ans = r;
/*      */       } else {
/* 1215 */         t = 1.0D;
/*      */       }
/* 1217 */       pkm2 = pkm1;
/* 1218 */       pkm1 = pk;
/* 1219 */       qkm2 = qkm1;
/* 1220 */       qkm1 = qk;
/* 1221 */       if (Math.abs(pk) > 4503599627370496.0D) {
/* 1222 */         pkm2 *= 2.220446049250313E-016D;
/* 1223 */         pkm1 *= 2.220446049250313E-016D;
/* 1224 */         qkm2 *= 2.220446049250313E-016D;
/* 1225 */         qkm1 *= 2.220446049250313E-016D;
/*      */       } }
/* 1227 */     while (t > 1.110223024625157E-016D);
/*      */ 
/* 1229 */     return ans * ax;
/*      */   }
/*      */ 
/*      */   public static double logGamma(double x)
/*      */     throws ArithmeticException
/*      */   {
/* 1237 */     double[] A = { 0.0008116141674705085D, -0.0005950619042843014D, 0.0007936503404577169D, -0.002777777777300997D, 0.0833333333333332D };
/*      */ 
/* 1244 */     double[] B = { -1378.2515256912086D, -38801.631513463784D, -331612.99273887119D, -1162370.9749276231D, -1721737.0082083966D, -853555.66424576542D };
/*      */ 
/* 1252 */     double[] C = { -351.81570143652345D, -17064.210665188115D, -220528.59055385445D, -1139334.4436798252D, -2532523.0717758294D, -2018891.4143353277D };
/*      */ 
/* 1262 */     if (x < -34.0D) {
/* 1263 */       double q = -x;
/* 1264 */       double w = logGamma(q);
/* 1265 */       double p = Math.floor(q);
/* 1266 */       if (p == q) throw new ArithmeticException("lgam: Overflow");
/* 1267 */       double z = q - p;
/* 1268 */       if (z > 0.5D) {
/* 1269 */         p += 1.0D;
/* 1270 */         z = p - q;
/*      */       }
/* 1272 */       z = q * Math.sin(3.141592653589793D * z);
/* 1273 */       if (z == 0.0D) throw new ArithmeticException("lgamma: Overflow");
/*      */ 
/* 1275 */       z = 1.1447298858494D - Math.log(z) - w;
/* 1276 */       return z;
/*      */     }
/*      */ 
/* 1279 */     if (x < 13.0D) {
/* 1280 */       double z = 1.0D;
/* 1281 */       while (x >= 3.0D) {
/* 1282 */         x -= 1.0D;
/* 1283 */         z *= x;
/*      */       }
/* 1285 */       while (x < 2.0D) {
/* 1286 */         if (x == 0.0D) throw new ArithmeticException("lgamma: Overflow");
/*      */ 
/* 1288 */         z /= x;
/* 1289 */         x += 1.0D;
/*      */       }
/* 1291 */       if (z < 0.0D) z = -z;
/* 1292 */       if (x == 2.0D) return Math.log(z);
/* 1293 */       x -= 2.0D;
/* 1294 */       double p = x * Polynomial.polevl(x, B, 5) / Polynomial.p1evl(x, C, 6);
/* 1295 */       return Math.log(z) + p;
/*      */     }
/*      */ 
/* 1298 */     if (x > 2.556348E+305D) throw new ArithmeticException("lgamma: Overflow");
/*      */ 
/* 1301 */     double q = (x - 0.5D) * Math.log(x) - x + 0.9189385332046728D;
/*      */ 
/* 1303 */     if (x > 100000000.0D) return q;
/*      */ 
/* 1305 */     double p = 1.0D / (x * x);
/* 1306 */     if (x >= 1000.0D) {
/* 1307 */       q += ((0.0007936507936507937D * p - 0.002777777777777778D) * p + 0.08333333333333333D) / x;
/*      */     }
/*      */     else
/*      */     {
/* 1311 */       q += Polynomial.polevl(p, A, 4) / x;
/* 1312 */     }return q;
/*      */   }
/*      */ 
/*      */   static double powerSeries(double a, double b, double x)
/*      */     throws ArithmeticException
/*      */   {
/* 1321 */     double ai = 1.0D / a;
/* 1322 */     double u = (1.0D - b) * x;
/* 1323 */     double v = u / (a + 1.0D);
/* 1324 */     double t1 = v;
/* 1325 */     double t = u;
/* 1326 */     double n = 2.0D;
/* 1327 */     double s = 0.0D;
/* 1328 */     double z = 1.110223024625157E-016D * ai;
/* 1329 */     while (Math.abs(v) > z) {
/* 1330 */       u = (n - b) * x / n;
/* 1331 */       t *= u;
/* 1332 */       v = t / (a + n);
/* 1333 */       s += v;
/* 1334 */       n += 1.0D;
/*      */     }
/* 1336 */     s += t1;
/* 1337 */     s += ai;
/*      */ 
/* 1339 */     u = a * Math.log(x);
/* 1340 */     if ((a + b < 171.62437695630271D) && (Math.abs(u) < 709.78271289338397D)) {
/* 1341 */       t = gamma(a + b) / (gamma(a) * gamma(b));
/* 1342 */       s = s * t * Math.pow(x, a);
/*      */     } else {
/* 1344 */       t = logGamma(a + b) - logGamma(a) - logGamma(b) + u + Math.log(s);
/* 1345 */       if (t < -745.13321910194122D) s = 0.0D; else
/* 1346 */         s = Math.exp(t);
/*      */     }
/* 1348 */     return s;
/*      */   }
/*      */ 
/*      */   static double stirlingFormula(double x)
/*      */     throws ArithmeticException
/*      */   {
/* 1355 */     double[] STIR = { 0.0007873113957930937D, -0.0002295499616133781D, -0.002681326178057812D, 0.003472222216054587D, 0.0833333333333482D };
/*      */ 
/* 1362 */     double MAXSTIR = 143.01607999999999D;
/*      */ 
/* 1364 */     double w = 1.0D / x;
/* 1365 */     double y = Math.exp(x);
/*      */ 
/* 1367 */     w = 1.0D + w * Polynomial.polevl(w, STIR, 4);
/*      */ 
/* 1369 */     if (x > MAXSTIR)
/*      */     {
/* 1371 */       double v = Math.pow(x, 0.5D * x - 0.25D);
/* 1372 */       y = v * (v / y);
/*      */     } else {
/* 1374 */       y = Math.pow(x, x - 0.5D) / y;
/*      */     }
/* 1376 */     y = 2.506628274631001D * y * w;
/* 1377 */     return y;
/*      */   }
/*      */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.Probability
 * JD-Core Version:    0.6.2
 */