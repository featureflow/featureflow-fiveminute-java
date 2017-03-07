import io.featureflow.client.*;
import org.joda.time.DateTime;

import java.io.IOException;

public class HelloWorld {

    public static void main(String... args) throws IOException {
        //This is the simplest possible invocation of the featureflow client:
        FeatureFlowClient client = new FeatureFlowClientImpl("{{YOUR_SERVER_ENVIRONMENT_API_KEY_HERE}}" +
                "");
        String failoverVariant = Variant.off;
        String variant = client.evaluate("example-feature", failoverVariant).value();

        if (Variant.on.equals(variant)) {
            System.out.println("The variant is on!");
        } else {
            System.out.println("The variant is not on!");
        }
        client.close();
        //Now have a look at HelloWorldWithContext.java for a more advanced scenario
    }
}
