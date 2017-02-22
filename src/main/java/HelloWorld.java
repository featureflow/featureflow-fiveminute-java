import io.featureflow.client.*;
import org.joda.time.DateTime;

import java.io.IOException;

public class HelloWorld {

    public static void main(String... args) throws IOException {
        //This is the simplest possible invocation of the featureflow client:
        //FeatureFlowClient client = new FeatureFlowClientImpl("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1ODNjZjlkZWI0N2VlZDAwMDYwYThiMzkiLCJhdXRoIjoiUk9MRV9FTlZJUk9OTUVOVCJ9.tSbZlWyVQcJyR8ORhqiTJYrRF9DWV-fjg-x6Uq0DgN8mDIKNZdKJo33VryoyXfzeHEArMAzErcHsTSOGr_q0Gg");

        //This is a context, in a web facing application you will generally create this when your users request (eg login, rest call etc.) comes in.
        // The context gets passed into the evaluate method.
        FeatureFlowContext context = FeatureFlowContext
                .keyedContext("flo@example.com")
                .withValue("age", 32)
                .withValue("signup_date", new DateTime(2017, 1, 1, 12, 0, 0, 0))
                .withValue("user_role", "admin")
                .withValue("user_tier", "gold")
                .build();

        //You can create the featureFlowClient using the builder. The builder helps you instatiate the client with the correct configuration.
        //Here we pass in our api key (from the featureflow environment view settings panel)
        //We provide a callback function to illustrate how featureflow can reacte as features are toggled (generally you would not evaluate on the control directly however)
        FeatureFlowClient client = new FeatureFlowClient.Builder("{{YOUR_SERVER_ENVIRONMENT_API_KEY_HERE}}")
                .withCallback(control -> System.out.println("Received a control update event: " + control.getKey() + " variant: " + control.evaluate(context)))
                .build();

        //This is en example of evaluating a specific variant.
        //The first parameter is the variant key
        //The second parameter is the context created above
        //The third parameter is the failover variant. This is the variant that is returned if all else fails.
        String variant = client.evaluate("example-feature", context, Variant.off);

        if (Variant.on.equals(variant)) {
            System.out.println("The variant is on!");
        } else {
            System.out.println("The variant is not on!");
        }

        client.close();
    }
}
