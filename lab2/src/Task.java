import java.io.FileReader;
import java.util.Scanner;

public class Task {
    static double [][] masXStudy;
    static double [][] masXTesting;
    static double [][] neededResultsStudy;
    static double [][] neededResultsTesting;
    static double[] w;
    static double[] wIntermediate;
    static double[] wFinal;
    static double eta = 0.5;

    public static void readingStudy() throws Exception {
        FileReader fileReader = new FileReader("input.txt");
        Scanner scanner = new Scanner(fileReader);
        masXStudy = new double[5][36];
        neededResultsStudy = new double[5][3];

        for (int i=0; i<5; i++) {
            for (int j=0; j<36; j++) {
                masXStudy[i][j] = scanner.nextInt();
            }
            for (int k=0; k<3; k++) {
                neededResultsStudy[i][k] = scanner.nextInt();
            }
        }
    }

    public static void readingTesting() throws Exception {
        FileReader fileReader = new FileReader("testInput.txt");
        Scanner scanner = new Scanner(fileReader);
        masXTesting = new double[5][36];
        neededResultsTesting = new double[5][3];

        for (int i=0; i<5; i++) {
            for (int j=0; j<36; j++) {
                masXTesting[i][j] = scanner.nextInt();
            }
            for (int k=0; k<3; k++) {
                neededResultsTesting[i][k] = scanner.nextInt();
            }
        }
    }
    public static void weightsGenerate(int layers, int neuronsInHiddenLayer1, int neuronsInHiddenLayer2) {
        if (layers == 0) {
            w = new double[masXStudy[0].length * neededResultsStudy[0].length];
        } if (layers == 1) {
            w = new double[masXStudy[0].length*neuronsInHiddenLayer1];
            wFinal = new double[neuronsInHiddenLayer1*neededResultsStudy[0].length];
            for (int i=0; i<wFinal.length; i++) {
                wFinal[i] = Math.random() - 0.5;
            }
        } if (layers == 2) {
            w = new double[masXStudy[0].length*neuronsInHiddenLayer1];
            wIntermediate = new double[neuronsInHiddenLayer1*neuronsInHiddenLayer2];
            wFinal = new double[neuronsInHiddenLayer2*neededResultsStudy[0].length];
            for (int i=0; i<wIntermediate.length; i++) {
                wIntermediate[i] = Math.random() - 0.5;
            }
            for (int i=0; i<wFinal.length; i++) {
                wFinal[i] = Math.random() - 0.5;
            }
        }
        for (int i=0; i<w.length; i++) {
            w[i] = Math.random() - 0.5;
        }
    }
    public static void withoutHiddenLayerStudy() {
        int iteration =0;
        do {
            for (int q = 0; q < 5; q++) {
                double[] sum = takeSum1(w, masXStudy[q], 3);
                double[] res = new double[3];
                for (int i = 0; i < 3; i++) {
                    res[i] = func(sum[i]);
                    //System.out.println(neededResults[q][i] + "    " + res[i] + " ");
                }
                //System.out.println();
                double[] e = new double[3];
                for (int i = 0; i < e.length; i++) {
                    e[i] = res[i] - neededResultsStudy[q][i];
                }

                double[] delta = new double[3];
                for (int i = 0; i < 3; i++) {
                    delta[i] = e[i] * funcDerivative(sum[i]);
                }
                for (int i = 0; i < 3; i++) {
                    for (int j=0; j<36; j++) {
                        w[j+36*i] = w[j+36*i] - eta * masXStudy[q][j] * delta[i];
                    }
                }
            }
            iteration++;
        } while (iteration<200);
    }
    public static void withoutHiddenLayerTesting() {
        System.out.println("Testing...");
            for (int q = 0; q < 5; q++) {
                double[] sum = takeSum1(w, masXTesting[q], 3);
                double[] res = new double[3];
                double error=0;
                for (int i = 0; i < 3; i++) {
                    res[i] = func(sum[i]);
                    System.out.printf("%.0f   %.3f %c %d\n", neededResultsTesting[q][i], res[i], (char)187, (res[i]>0.5) ? 1 : 0 );
                    error = error + Math.abs(neededResultsTesting[q][i]-res[i]);
                }
                System.out.println("Summary error: " + error + "\n");
            }

    }

    public static void withOneHiddenLayerStudy(int hLayer) {
        int iteration = 0;
        do {
            for (int q = 0; q < 5; q++) {
                double[] sum = takeSum1(w, masXStudy[q], hLayer);
                double[] res = new double[hLayer];
                for (int i = 0; i < res.length; i++) {
                    res[i] = func(sum[i]);
                }
                double[] sum2 = takeSum1(wFinal, res, 3);
                double[] res2 = new double[3];
                for (int i=0; i<res2.length; i++) {
                    res2[i] = func(sum2[i]);
                    //System.out.println(neededResultsStudy[q][i] + "    " + res2[i] + " ");
                }
                //System.out.println();
                double[] e = new double[3];
                for (int i = 0; i < e.length; i++) {
                    e[i] = res2[i] - neededResultsStudy[q][i];
                }
                double[] delta = new double[3];
                for (int i = 0; i < 3; i++) {
                    delta[i] = e[i] * funcDerivative(sum2[i]);
                }
                double[] summary = new double[hLayer];
                for (int i = 0; i < summary.length; i++) {
                    for (int j=0; j<3; j++) {
                        summary[i] = summary[i]+wFinal[hLayer*j+i]*delta[j];
                    }
                }
                double[] deltaHiddenLayer = new double[hLayer];
                for (int j=0; j<3; j++) {
                    for (int i=0; i<deltaHiddenLayer.length; i++) {
                        deltaHiddenLayer[i] = summary[i]*funcDerivative(sum[i]);
                    }
                }
                for (int i=0; i<wFinal.length; i++) {
                    wFinal[i] = wFinal[i] - eta*delta[i/hLayer]*res[i%hLayer];
                }
                for (int i=0; i<w.length; i++) {
                    w[i] = w[i] - eta * deltaHiddenLayer[i/36] * masXStudy[q][i%36];
                }
            }
            iteration++;
        } while (iteration<200);
    }
    public static void withOneHiddenLayerTesting(int hLayer) {
        System.out.println("Testing...");
        for (int q = 0; q < 5; q++) {
            double[] sum = takeSum1(w, masXTesting[q], hLayer);
            double[] res = new double[hLayer];
            for (int i = 0; i < res.length; i++) {
                res[i] = func(sum[i]);
            }
            double[] sum2 = takeSum1(wFinal, res, 3);
            double[] res2 = new double[3];
            double error=0;
            for (int i=0; i<res2.length; i++) {
                res2[i] = func(sum2[i]);
                System.out.printf("%.0f   %.3f %c %d\n", neededResultsTesting[q][i], res2[i], (char)187, (res2[i]>0.5) ? 1 : 0 );
                error = error + Math.abs(neededResultsTesting[q][i]-res2[i]);
            }
            System.out.println("Summary error: " + error + "\n");
        }
    }

    public static void withTwoHiddenLayersStudy(int layer1, int layer2) {
        int iteration = 0;
        do {
            for (int q = 0; q < 5; q++) {
                double[] sum = takeSum1(w, masXStudy[q], layer1);
                double[] res = new double[layer1];
                for (int i = 0; i < res.length; i++) {
                    res[i] = func(sum[i]);
                }
                double[] sum2 = takeSum1(wIntermediate, res, layer2);
                double[] res2 = new double[layer2];
                for (int i=0; i<res2.length; i++) {
                    res2[i] = func(sum2[i]);
                }
                double[] sum3 = takeSum1(wFinal, res2, 3);
                double[] res3 = new double[3];
                for (int i=0; i<res3.length; i++) {
                    res3[i] = func(sum3[i]);
                    //System.out.println(neededResultsStudy[q][i] + "    " + res3[i] + " ");
                }
                //System.out.println();
                double[] e = new double[3];
                for (int i = 0; i < e.length; i++) {
                    e[i] = res3[i] - neededResultsStudy[q][i];
                }
                double[] delta = new double[3];
                for (int i = 0; i < 3; i++) {
                    delta[i] = e[i] * funcDerivative(sum3[i]);
                }
                double[] summary = new double[layer2];
                for (int i = 0; i < summary.length; i++) {
                    for (int j=0; j<3; j++) {
                        summary[i] = summary[i]+wFinal[layer2*j+i]*delta[j];
                    }
                }
                double[] deltaHiddenLayer2 = new double[layer2];
                for (int i=0; i<deltaHiddenLayer2.length; i++) {
                    deltaHiddenLayer2[i] = summary[i]*funcDerivative(sum2[i]);
                }
                double[] summary2 = new double[layer1];
                for (int i = 0; i < summary2.length; i++) {
                    for (int j=0; j<deltaHiddenLayer2.length; j++) {
                        summary2[i] = summary2[i]+wIntermediate[layer1*j+i]*deltaHiddenLayer2[j];
                    }
                }
                double[] deltaHiddenLayer1 = new double[layer1];
                for (int i=0; i<deltaHiddenLayer1.length; i++) {
                    deltaHiddenLayer1[i] = summary2[i]*funcDerivative(sum[i]);
                }
                for (int i=0; i<wFinal.length; i++) {
                    wFinal[i] = wFinal[i] - eta*delta[i/layer2]*res2[i%layer2];
                }
                for (int i=0; i<wIntermediate.length; i++) {
                    wIntermediate[i] = wIntermediate[i] - eta * deltaHiddenLayer2[i/layer1] * res[i%layer1];
                }
                for (int i=0; i<w.length; i++) {
                    w[i] = w[i] - eta * deltaHiddenLayer1[i/36] * masXStudy[q][i%36];
                }
            }
            iteration++;
        } while (iteration<200);
    }
    public static void withTwoHiddenLayersTesting(int layer1, int layer2) {
        System.out.println("Testing...");
        for (int q = 0; q < 5; q++) {
            double[] sum = takeSum1(w, masXStudy[q], layer1);
            double[] res = new double[layer1];
            for (int i = 0; i < res.length; i++) {
                res[i] = func(sum[i]);
            }
            double[] sum2 = takeSum1(wIntermediate, res, layer2);
            double[] res2 = new double[layer2];
            for (int i=0; i<res2.length; i++) {
                res2[i] = func(sum2[i]);
            }
            double[] sum3 = takeSum1(wFinal, res2, 3);
            double[] res3 = new double[3];
            double error=0;
            for (int i=0; i<res3.length; i++) {
                res3[i] = func(sum3[i]);
                System.out.printf("%.0f   %.3f %c %d\n", neededResultsTesting[q][i], res3[i], (char)187, (res3[i]>0.5) ? 1 : 0 );
                error = error + Math.abs(neededResultsTesting[q][i]-res3[i]);
            }
            System.out.println("Summary error: " + error + "\n");
        }
    }

    public static double[] takeSum1(double[] w, double[] x, int count) {
        double[] sum  = new double[count];
            for (int i=0; i<count; i++) {
                for (int j=0; j<x.length; j++) {
                    sum[i] = sum[i] +x[j]*w[j+x.length*i];
                }
            }
        return sum;
    }

    /*Сигмоїдальна*/
    public static double func(double x) {
        return (double) 1/(1+Math.exp(-x));
    }
    public static double funcDerivative(double x) {
        return Math.exp(-x)/Math.pow(1+Math.exp(-x), 2);
    }
    /*Softsign*/
    /*public static double func(double x) {
        return 0.5*x/(1+Math.abs(x)) + 0.5;
    }
    public static double funcDerivative(double x) {
        return 0.5/Math.pow(1+Math.abs(x), 2);
    }*/
    /*Синусоїда*/
    /*public static double func(double x) {
        return 0.5*Math.sin(x) + 0.5;
    }
    public static double funcDerivative(double x) {
        return 0.5*Math.cos(x);
    }*/
    /*Inverse square root unit (Обратный квадратный корень)*/
    /*public static double func(double x) {
        return x/Math.sqrt(1+4*x*x)+0.5;
    }
    public static double funcDerivative(double x) {
        return Math.pow(1.0/Math.sqrt(1+4*x*x), 3);
    }*/

}
