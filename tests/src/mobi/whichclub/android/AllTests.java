package mobi.whichclub.android;

import android.test.suitebuilder.TestSuiteBuilder;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author camrdale
 *
 */
public class AllTests extends TestSuite {
    
    /**
     * Run all the tests.
     * @return the tests to run
     */
    public static Test suite() {
        return new TestSuiteBuilder(AllTests.class)
                .includeAllPackagesUnderHere().build();
    }
}
