import java.util.*;

public class Task {
    double a;
    double b;
    int sizeOfPopulation;

    public Task(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public ArrayList<Chromosome> creationOfChromosomes() {
        ArrayList<Chromosome> population = new ArrayList();

        for(int i = 100; i < (b - a + 1)*100; i++) {
            StringBuilder bin = new StringBuilder(Long.toBinaryString(Double.doubleToLongBits((double) (i+1)/100)));

            while(bin.length() < Chromosome.genotypeLength){
                bin.insert(0, '0');
            }
            population.add(new Chromosome(bin, (double)(i+1)/100, myFunc((i+1)/100)));
        }
//        printing(population);
        sizeOfPopulation = population.size();
        for (int i = 0; i < 10; i++) {
            selection(population);
        }
        printing(population);
        return population;
    }
    public ArrayList<Chromosome> selection(ArrayList<Chromosome> population) {
        population.sort((one, two) -> Double.compare(two.getFitness(), one.getFitness()));
        for (int i=0; i<population.size()-1; i+=2) {
            Chromosome[] res = recombination(population.get(i), population.get(i+1), i);
            Chromosome first = res[0];
            Chromosome second = res[1];
            population.set(i, first);
            population.set(i+1, second);
        }
        return population;
    }
    public Chromosome[] recombination(Chromosome chr1, Chromosome chr2, int position) {
        int pointOfIntersection = (int) (Math.random()*(chr1.genotype.length()-1) + 1);
        StringBuilder temp1 = new StringBuilder();
        StringBuilder temp2 = new StringBuilder();
        for (int i = 0; i<pointOfIntersection; i++) {
            temp1.append(chr1.genotype.charAt(i));
            temp2.append(chr2.genotype.charAt(i));
        }
        for (int i = pointOfIntersection; i<chr1.genotype.length(); i++) {
            temp1.append(chr2.genotype.charAt(i));
            temp2.append(chr1.genotype.charAt(i));
        }
        temp1 = mutation(temp1);
        temp2 = mutation(temp2);
        double phenotype1 = Double.longBitsToDouble(Long.parseLong(String.valueOf(temp1), 2));
        double phenotype2 = Double.longBitsToDouble(Long.parseLong(String.valueOf(temp2), 2));
        double fitness1 = myFunc(phenotype1);
        double fitness2 = myFunc(phenotype2);
        if (a <= phenotype1 && phenotype1 <= b && a <= phenotype2 && phenotype2 <= b) {
            chr1 = new Chromosome(temp1, phenotype1, fitness1);
            chr2 = new Chromosome(temp2, phenotype2, fitness2);
        } else if ((a > phenotype1 || phenotype1 > b) && a <= phenotype2 && phenotype2 <= b) {
            chr2 = new Chromosome(temp2, phenotype2, fitness2);
        } else if (a > phenotype1 && phenotype1 > b && (a <= phenotype2 || phenotype2 <= b)) {
            if (position < sizeOfPopulation/2) {
                chr1 = new Chromosome(temp1, phenotype1, fitness1);
            } else {
                chr2 = new Chromosome(temp1, phenotype1, fitness1);
            }

        }
        Chromosome[] result = new Chromosome[2];
        result[0] = chr1;
        result[1] = chr2;
        return result;
    }

    public StringBuilder mutation(StringBuilder chr) {
        if (Math.random() < 0.1) {
            int pointOfMutation = (int) (Math.random()*(chr.length()-1) + 1);
            char position = chr.charAt(pointOfMutation);
            chr.setCharAt(pointOfMutation, change(position));
        }
        return chr;
    }

    public char change(char i) {
        if (i == '0') {
            return '1';
        } else {
            return '0';
        }
    }
    public void printing(ArrayList<Chromosome> listToPrint) {
        for (int i = 0; i < listToPrint.size(); i++) {
            System.out.println(listToPrint.get(i));
        }
        System.out.println();
    }
    public double myFunc(double x) {
        return 5*Math.sin(x)*Math.pow(Math.cos(x*x+1/x), 2);
    }
}
