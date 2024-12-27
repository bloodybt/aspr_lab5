import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class ProductReview {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Random random = new Random();

        CompletableFuture<List<Integer>> platformAReviews = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                List<Integer> reviewsA = generateRandomReviews(random);
                System.out.println("Відгуки з платформи A: " + reviewsA);
                return reviewsA;
            } catch (InterruptedException e) {
                return List.of();
            }
        });

        CompletableFuture<List<Integer>> platformBReviews = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1500);
                List<Integer> reviewsB = generateRandomReviews(random);
                System.out.println("Відгуки з платформи B: " + reviewsB);
                return reviewsB;
            } catch (InterruptedException e) {
                return List.of();
            }
        });

        CompletableFuture<List<Integer>> platformCReviews = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                List<Integer> reviewsC = generateRandomReviews(random);
                System.out.println("Відгуки з платформи C: " + reviewsC);
                return reviewsC;
            } catch (InterruptedException e) {
                return List.of();
            }
        });

        CompletableFuture<Integer> combinedReviewsFuture = platformAReviews
                .thenCombine(platformBReviews, (reviewsA, reviewsB) -> {
                    int sumA = reviewsA.stream().mapToInt(Integer::intValue).sum();
                    int sumB = reviewsB.stream().mapToInt(Integer::intValue).sum();
                    int avgA = sumA / reviewsA.size();
                    int avgB = sumB / reviewsB.size();
                    System.out.println("Середній рейтинг на платформі A: " + avgA);
                    System.out.println("Середній рейтинг на платформі B: " + avgB);
                    return (avgA + avgB) / 2;
                })
                .thenCombine(platformCReviews, (combinedAvgAB, reviewsC) -> {
                    int sumC = reviewsC.stream().mapToInt(Integer::intValue).sum();
                    int avgC = sumC / reviewsC.size();
                    System.out.println("Середній рейтинг на платформі C: " + avgC);
                    int totalSum = combinedAvgAB * (platformAReviews.join().size() + platformBReviews.join().size()) + sumC;
                    int totalSize = platformAReviews.join().size() + platformBReviews.join().size() + reviewsC.size();
                    return totalSum / totalSize;
                });

        System.out.println("Загальний середній рейтинг: " + combinedReviewsFuture.get());
    }

    private static List<Integer> generateRandomReviews(Random random) {
        return Arrays.asList(
                random.nextInt(11),
                random.nextInt(11),
                random.nextInt(11),
                random.nextInt(11),
                random.nextInt(11)
        );
    }
}
