import io.featureflow.client.FeatureflowClient;
import io.featureflow.client.FeatureflowUser;
import io.featureflow.client.model.Feature;

import java.io.IOException;

/**
 * This is the featureflow 5 Minute test example. Please see [http://docs.featureflow.io/v1.0/docs/featureflow-five-minute-java] and follow the steps.
 */
public class HelloWorld {

    public static void main(String... args) throws IOException {
        //Create an instance of the featureflow client - this is typically a singleton (for example a spring bean)
        FeatureflowClient client = FeatureflowClient.builder("{{YOUR_SERVER_ENVIRONMENT_API_KEY_HERE}}")
                .withFeature(new Feature(MyFeatures.EXAMPLE_FEATURE.getValue()))   //we can register features up front. that lowers technical debt by allowing configuration in one place (hint enums + IDE help find all usages!).
                //An update callback may be added to identify when a feature configuration has been updated.
                .withUpdateCallback(control -> System.out.println("Received a feature control update event: " + control.getKey() + " variant: " + control.evaluate(new FeatureflowUser())))
                .build();

        //evaluate the variant. by convention the default is off unless you set a failover when registering the feature above.
        if (client.evaluate(MyFeatures.EXAMPLE_FEATURE.getValue()).isOn()) {
            System.out.println("The " + MyFeatures.EXAMPLE_FEATURE + " variant is on!");
        } else {
            System.out.println("The " + MyFeatures.EXAMPLE_FEATURE + " variant is not on! ( the value is '" +  client.evaluate(MyFeatures.EXAMPLE_FEATURE.getValue()).value() + "')");
        }
        client.close();

        //Now have a look at HelloWorldWithContext.java for a more advanced scenario
    }
}
