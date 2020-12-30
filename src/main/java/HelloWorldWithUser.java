import io.featureflow.client.FeatureflowClient;
import io.featureflow.client.FeatureflowUser;
import io.featureflow.client.model.Feature;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Arrays;

/**
 * This is the featureflow 5 Minute test example. Please see [http://docs.featureflow.io/v1.0/docs/featureflow-five-minute-java] and follow the steps.
 */
public class HelloWorldWithUser {

    public static void main(String... args) throws IOException {
        //Create a context object. The context tells featureflow about the current user and any other information you may wish to target

        FeatureflowClient client = new FeatureflowClient.Builder("{{YOUR_SERVER_ENVIRONMENT_API_KEY_HERE}}")
                .withFeature(new Feature(MyFeatures.EXAMPLE_FEATURE.getValue()))
                .withFeature(new Feature(MyFeatures.NEW_FEATURE.getValue()))
                .build();

        FeatureflowClient.Evaluate exampleEval = client.evaluate(MyFeatures.EXAMPLE_FEATURE.getValue(), getUser());
        System.out.println("The variant for " + MyFeatures.EXAMPLE_FEATURE.getValue() + " is " + exampleEval.value());

        FeatureflowClient.Evaluate newFeatureEval = client.evaluate(MyFeatures.NEW_FEATURE.getValue(), getUser());
        System.out.println("The variant for " + MyFeatures.NEW_FEATURE.getValue() + " is " + newFeatureEval.value());
        
        client.close();
    }

    private static FeatureflowUser getUser(){
        FeatureflowUser user = new FeatureflowUser("flo@example.com")
                .withAttribute("age", 32)
                .withAttribute("signup_date", new DateTime(2017, 1, 1, 12, 0, 0, 0))
                .withStringAttributes("user_role", Arrays.asList("admin", "pvt_tester"))
                .withAttribute("tier", "gold");
        return user;
    }
}
