import io.featureflow.client.FeatureFlowClient;
import io.featureflow.client.FeatureFlowClientImpl;
import io.featureflow.client.Variant;

import java.io.IOException;

public class HelloWorld {

    public static void main(String... args) throws IOException {
        //This is the simplest possible invocation of the featureflow client:
        FeatureFlowClient client = FeatureFlowClient.builder("{{YOUR_SERVER_ENVIRONMENT_API_KEY_HERE}}").build();
        String failoverVariant = Variant.off;
        String variant = client.evaluate("example-feature").value();

        if (Variant.on.equals(variant)) {
            System.out.println("The variant is on!");
        } else {
            System.out.println("The variant is not on!");
        }
        client.close();
        //Now have a look at HelloWorldWithContext.java for a more advanced scenario
    }
}
