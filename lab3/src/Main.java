import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Task interval = new Task(1, 10);
        ArrayList<Chromosome> result = interval.creationOfChromosomes();
        System.out.println("Genetic Algorithm Results:");
        System.out.println("Maximum:\t\tX: " + result.get(0).getPhenotype() + "\t\tY: " + result.get(0).getFitness());
        System.out.println("Minimum:\t\tX: " + result.get(result.size()-1).getPhenotype() + "\t\tY: " + result.get(result.size()-1).getFitness());

    }
}
