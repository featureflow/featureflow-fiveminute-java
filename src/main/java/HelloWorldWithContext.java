import io.featureflow.client.FeatureFlowClient;
import io.featureflow.client.FeatureFlowContext;
import io.featureflow.client.Variant;
import org.joda.time.DateTime;

import java.io.IOException;

public class HelloWorldWithContext {

    public static void main(String... args) throws IOException {
        /*
            This is an example of evaluating a feature by passing in some context information
            Continue down the comments below for an explanation.
         */

        /*
            This is a context, in a web facing application you will generally create this when your users' request (eg login, rest call etc.) comes in.
            If you do not have a logged in user or any context that's ok, we will assume the user is 'anonymous'
            We have a builder just to aid generating the context, you will then pass the context into the evaluate method.

            Some quick concepts: A 'context' is evaluated against a features 'rules' to determine which 'variant' of the feature to use for the given context.
            In the 'HelloWorld' the variants are simple 'On' and 'Off' - in more complex scenarios they can be anything - eg: 'red','blue','1.1','1.2'
            This allows a huge amount of control, for example:
                'Set the 'Google Login Button' feature to show the '1.2-NewVersion' variant if the 'context' contains a 'user_role' of 'pvt_tester'
                'Set the 'Stock Chart' feature to show the 'Enhanced Stock Chart' variant if the 'context' contains a 'tier' of 'gold'
            Noting that these same rules can be applied using both the backend APIs and the Frontend Javascript API you can toggle conveniently at the front-end while backing up securely with server rules.


            As these  context values are evaluated they appear in featureflow so that you can select them in 'rules' to target your features.
         */
        FeatureFlowContext context = FeatureFlowContext
                .keyedContext("flo@example.com")
                .withValue("age", 32)
                .withValue("signup_date", new DateTime(2017, 1, 1, 12, 0, 0, 0))
                .withValue("user_role", "admin")
                .withValue("tier", "gold")
                .build();


        /*
            We create the featureFlowClient using the builder. The builder helps you instantiate the client with the correct configuration.
            The client should be a singleton - if your using spring you could create it in your @Configuration and @Inject it, either way you only need one.
            Here we pass in our 'Server Api Key' - you can get the key in the environments page under the 'Api Keys' link.
            We provide a callback function to illustrate how featureflow can react in real-time as features are toggled (generally you would not evaluate on the control directly however)
         */
        FeatureFlowClient client = new FeatureFlowClient.Builder("{{YOUR_SERVER_ENVIRONMENT_API_KEY_HERE}}")
                .withCallback(control -> System.out.println("Received a control update event: " + control.getKey() + " variant: " + control.evaluate(context)))
                .build();


        /*
            'client.evaluate' is en example of evaluating a specific feature variant.
            The first parameter is the variant key
            The second parameter is the context created above
            The third parameter is the failover variant. This is the variant that is returned if all else fails.
         */
        String failoverVariant = Variant.off;
        String variant = client.evaluate("example-feature", context, failoverVariant).value();

        if (Variant.on.equals(variant)) {
            System.out.println("The variant is on!");
        } else {
            System.out.println("The variant is not on!");
        }

        /**
         * TRY IT! Try editing the 'example-feature' in the environment that matches the key you have set above.
         * Edit the feature and add a new variant called 'New Version 1.2'
         * Now edit the control for the environment you are connected to above - Add a new Rule which says when 'tier' is equal to 'gold' then 'New Version 1.2'
         * You should immediately see an update event which will evaluate to the new version (because your user is in the 'gold' tier in your context above.
         * Of course for more information and a visual guide please consult the docs http://docs.featureflow.io
         */

        client.close();
    }
}
