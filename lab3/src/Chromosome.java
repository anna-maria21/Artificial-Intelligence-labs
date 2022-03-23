public class Chromosome implements Comparable<Chromosome>{
    static int genotypeLength = 64;
    StringBuilder genotype;
    double phenotype;
    double fitness;

    public Chromosome(StringBuilder genotype, double phenotype, double fitness) {
        this.genotype = genotype;
        this.phenotype = phenotype;
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "genotype='" + genotype + '\''  +
                ", phenotype=" + phenotype +
                ", fitness=" + fitness +
                '}';
    }

    public StringBuilder getGenotype() {
        return genotype;
    }

    public void setGenotype(StringBuilder genotype) {
        this.genotype = genotype;
    }

    public double getPhenotype() {
        return phenotype;
    }

    public void setPhenotype(double phenotype) {
        this.phenotype = phenotype;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Chromosome o) {
        return (int) (o.phenotype -  this.phenotype);
    }
}
