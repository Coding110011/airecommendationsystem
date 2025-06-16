import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
// Use Euclidean instead of Pearson
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.io.File;
import java.util.List;

public class RecommenderApp {
    public static void main(String[] args) {
        try {
            // Load the dataset (CSV without header)
            DataModel model = new FileDataModel(new File("dataset.csv"));

            // Use Euclidean Distance Similarity
            UserSimilarity similarity = new EuclideanDistanceSimilarity(model);

            // Use 5 nearest neighbors
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, model);

            // Create recommender
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Recommend 5 items for user 1
            List<RecommendedItem> recommendations = recommender.recommend(1, 5);

            // Print recommendations
            if (recommendations.isEmpty()) {
                System.out.println("No recommendations found for user 1.");
            } else {
                System.out.println("Recommendations for user 1:");
                for (RecommendedItem recommendation : recommendations) {
                    System.out.println("Item ID: " + recommendation.getItemID() +
                            " | Estimated Preference: " + recommendation.getValue());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
