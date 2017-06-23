/**
 * Created by oliver.oldfieldhodge on 20/3/17.
 */
public enum MyFeatures {
    EXAMPLE_FEATURE("example-feature"),
    NEW_FEATURE("new-feature");

    private final String value;

    MyFeatures(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
