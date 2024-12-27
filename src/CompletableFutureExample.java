import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {

    public static void main(String[] args) {
        CompletableFuture<Integer> task1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("Завдання 1 завершено");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return (int) (Math.random() * 10);
        });


        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Завдання 2 завершено");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return (int) (Math.random() * 15);
        });


        CompletableFuture<Integer> task3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("Завдання 3 завершено");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return (int) (Math.random() * 30);
        });


        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(task1, task2, task3);

        allOfFuture.join();

        System.out.println("Результат task1: " + task1.join());
        System.out.println("Результат task2: " + task2.join());
        System.out.println("Результат task3: " + task3.join());



    }
}