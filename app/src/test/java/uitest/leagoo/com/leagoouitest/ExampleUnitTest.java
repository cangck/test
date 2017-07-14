package uitest.leagoo.com.leagoouitest;

import android.os.SystemClock;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @BeforeClass
    public static void testBeforeClass() throws Exception {
        System.out.println("testBeforeClass " + System.currentTimeMillis());
    }

    @AfterClass
    public static void testAfterClass() throws Exception {
        System.out.println("testAfterClass " + System.currentTimeMillis());
    }

    @Before
    public void testBefore() throws Exception {
        System.out.println("testBefore " + System.currentTimeMillis());
    }

    @Test
    public void testCase() throws Exception {
        System.out.println("testCase " + System.currentTimeMillis());


    }

    @After
    public void testAfter() throws Exception {
        System.out.println("testAfter " + System.currentTimeMillis());
    }
}