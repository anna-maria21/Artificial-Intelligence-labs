public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Studying and testing without hidden layer:");
        Task.readingStudy();
        Task.weightsGenerate(0, 0, 0);
        Task.withoutHiddenLayerStudy();
        Task.readingTesting();
        Task.withoutHiddenLayerTesting();
        System.out.println("Studying and testing with one hidden layer:");
        Task.weightsGenerate(1, 40, 0);
        Task.withOneHiddenLayerStudy(40);
        Task.withOneHiddenLayerTesting(40);
        System.out.println("Studying and testing with two hidden layers:");
        Task.weightsGenerate(2, 30, 10);
        Task.withTwoHiddenLayersStudy(30, 10);
        Task.withTwoHiddenLayersTesting(30, 10);
    }
}
