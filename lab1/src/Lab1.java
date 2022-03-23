public class Lab1 {
    static double E=0;
    static double prevE=0;
    public static void main(String[] args) {
        int x = 1;
        int y = 0;
        System.out.println("Моделювання логічної функції І\n\tВхідні дані: x=" +
                + x + " y=" + y + "\n\tРезультат:" + Task.funcAND(x,y));
        System.out.println("Моделювання логічної функції НІ\n\tВхідні дані: x=" +
                + x + "\n\tРезультат:" + Task.funcNOT(x));
        System.out.println("Моделювання логічної функції АБО\n\tВхідні дані: x=" +
                + x + " y=" + y + "\n\tРезультат:" + Task.funcOR(x,y));
        System.out.println("Моделювання логічної функції Виключне АБО\n\tВхідні дані: x=" +
                + x + " y=" + y + "\n\tРезультат:" + Task.funcXOR(x,y));

        double[] xMas = {2.57, 4.35, 1.27, 5.46, 1.30, 4.92, 1.31, 4.14, 1.97, 5.67, 0.92, 4.76, 1.72, 4.44, 1.49};
        System.out.println("Прогнозування часових рядів за допомогою нейрону з сигмоїдальною функцією активації\nНавчання:");
        int count =0;
        /*do {
            prevE = E;
            E = 0;
            System.out.println("Епоха: " + count);
            for (int i = 0; i < xMas.length - 5; i++) {
                Task.networkTraining(xMas[i], xMas[i + 1], xMas[i + 2], xMas[i + 3], false);
            }
            count++;
        } while((count < 100000) && (Math.abs(E-prevE)>0.00000001));
        System.out.println("Кількість епох: " + count);
        System.out.println("\nТестування:");
        for (int i=10; i<xMas.length-3; i++) {
            Task.networkTraining(xMas[i], xMas[i+1], xMas[i+2], xMas[i+3], true);
        }*/
        System.out.println(Task.func(-0.3));
        System.out.println(Task.funcDerivative(-0.142));
        System.out.println(Task.funcDerivative(0.062));
        System.out.println(Task.funcDerivative(-2.068));
    }
}
