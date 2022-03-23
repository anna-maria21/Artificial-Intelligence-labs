public class Task {
    public static int funcAND(int x1, int x2) {
        int w1 = 1, w2 = 1;
        int s = x1*w1 + x2*w2;
        if (s<1.5)
            return 0;
        else return 1;
    }
    public static int funcOR(int x1, int x2) {
        int w1 = 1, w2 = 1;
        int s = x1*w1 + x2*w2;
        if (s<0.5)
            return 0;
        else return 1;
    }
    public static int funcNOT(int x) {
        double w=-1.5;
        double s = x*w;
        if (s<-1)
            return 0;
        else return 1;
    }
    public static int funcXOR(int x1, int x2) {
        int w11=1, w22=1, w31=1, w32=1, w12=-1, w21=-1;
        int y1=0,y2=0;
        double s1 = x1*w11+x2*w12;
        double s2 = x1*w21+x2*w22;
        if (s1>=0.5)
            y1 = 1;
        if (s2>=0.5)
            y2 = 1;
        double s = y1*w31+y2*w32;
        if (s<0.5)
            return 0;
        else return 1;
    }

    static double[] w = {0.1, -0.2, 0.3, -0.2, 0.3, -0.5, -0.3, 0.8, -0.8, -0.5, 0.1, 0.6};
    static double[] wFinal = {-0.5, 0.1, -0.1, 0.2};
    static double[] s = new double[5];
    static double[] f = new double[5];
    public static void networkTraining(double x1, double x2, double x3, double y, boolean flag) {
        int k = 0;
        for (int i = 0; i < w.length - 2; i += 3) {
            s[k] = w[i] * x1 + w[i + 1] * x2 + w[i + 2] * x3;
            f[k] = func(s[k]);
            k++;
        }
        s[k]=0;
        for (int i = 0; i < k; i++) {
            s[k] = s[k] + f[i] * wFinal[i];
        }
        f[k] = func(s[k]);
        System.out.println("Rezult: " + y + " " + f[k] + " error: " + Math.abs(y - f[k]));
        if (!flag) {
                myBackPropagation(y, f[k], new double[]{x1, x2, x3});
        }
    }
    public static void myBackPropagation(double y, double z, double[] x) {
        double e = z-y;
        Lab1.E = Lab1.E + e*e;
        double[] delta = new double[4];
        double finalDelta = e*funcDerivative(s[s.length-1]);
        double eta = 0.001;

        for (int i=0; i<delta.length; i++) {
            delta[i]=finalDelta*wFinal[i]*funcDerivative(s[i]);
        }
        for (int i=0; i< wFinal.length; i++) {
            wFinal[i] = wFinal[i]-eta*finalDelta*f[i];
        }
        for (int i=0; i< w.length; i++) {
            w[i] = w[i]-eta*delta[i/3]*x[i%3];
        }
    }
    public static double func(double x) {
        return 10.0/(1+Math.exp(-x));
    }
    public static double funcDerivative(double x) {
        return 10.0*Math.exp(-x)/Math.pow(1+Math.exp(-x), 2);
    }
}
