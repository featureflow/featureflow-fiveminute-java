import io.featureflow.client.FeatureflowClient;
import io.featureflow.client.FeatureflowContext;
import io.featureflow.client.model.Feature;

import java.io.IOException;

/**
 * This is the featureflow 5 Minute test example. Please see [http://docs.featureflow.io/v1.0/docs/featureflow-five-minute-java] and follow the steps.
 */
public class HelloWorld {

    public static void main(String... args) throws IOException {
        //This is the simplest possible invocation of the featureflow client:
        FeatureflowClient client = FeatureflowClient.builder("{{YOUR_SERVER_ENVIRONMENT_API_KEY_HERE}}")
                .withFeature(new Feature(MyFeatures.EXAMPLE_FEATURE.getValue()))   //we register features up front. that lowers technical debt by allowing configuration in one place (hint enums + IDE help find all usages!).
                .withUpdateCallback(control -> System.out.println("Received a control update event: " + control.getKey() + " variant: " + control.evaluate(FeatureflowContext.context().build())))
                .build();

        //evaluate the variant here. by convention the default is off unless you set a failover when registering the feature above.
        if (client.evaluate(MyFeatures.EXAMPLE_FEATURE.getValue()).isOn()) {
            System.out.println("The variant is on!");
        } else {
            System.out.println("The variant is not on!");
        }
        client.close();

        //Now have a look at HelloWorldWithContext.java for a more advanced scenario
    }
}
