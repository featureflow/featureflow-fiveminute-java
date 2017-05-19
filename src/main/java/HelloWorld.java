import io.featureflow.client.FeatureflowClient;
import io.featureflow.client.FeatureflowContext;
import io.featureflow.client.model.Feature;

import java.io.IOException;

public class HelloWorld {

    public static void main(String... args) throws IOException {
        //This is the simplest possible invocation of the featureflow client:
        FeatureflowClient client = FeatureflowClient.builder("{{YOUR_SERVER_ENVIRONMENT_API_KEY_HERE}}")
                .withFeature(new Feature(MyFeatures.EXAMPLE_FEATURE.getValue()))
                .withUpdateCallback(control -> System.out.println("Received a control update event: " + control.getKey() + " variant: " + control.evaluate(FeatureflowContext.context().build())))
                .build();
        //we call evaluate here to get the variant, which will be the evaluated variant value as per your rules - or 'off' in the case of a failover situation
        //to change the failover see the HelloWorldWithContext example
        if (client.evaluate(MyFeatures.EXAMPLE_FEATURE.getValue()).isOn()) {
            System.out.println("The variant is on!");
        } else {
            System.out.println("The variant is not on!");
        }
        client.close();
        //Now have a look at HelloWorldWithContext.java for a more advanced scenario
    }
}
