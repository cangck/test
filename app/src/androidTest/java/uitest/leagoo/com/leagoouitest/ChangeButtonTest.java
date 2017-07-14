package uitest.leagoo.com.leagoouitest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertThat;

/**
 * Created by cangck on 17/7/13.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class ChangeButtonTest {
    private static final String PACKAGE_NAME = "uitest.leagoo.com.leagoouitest";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice uiDevice;
    private Context mContext;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";

    @Before
    public void launcherApp() throws Exception {
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mContext = InstrumentationRegistry.getContext();
// Initialize UiDevice instance
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        uiDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, CoreMatchers.notNullValue());
        uiDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PACKAGE_NAME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        uiDevice.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testChangeTest() throws Exception {
        uiDevice.findObject(By.res(PACKAGE_NAME, "ok")).click();
//        assertThat(ok.getText(), is(equalTo(STRING_TO_BE_TYPED)));
        uiDevice.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), 15000);
    }


    @Test
    public void testEidtTest() throws Exception {
        UiObject2 edit = uiDevice.findObject(By.res(PACKAGE_NAME, "edit"));

        for (int i = 0; i < 150; i++) {
            edit.setText("test " + i);
            SystemClock.sleep(500);
        }
        uiDevice.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), LAUNCH_TIMEOUT);
    }

    /**
     * $date 创建时间： 17/7/13 上午11:54
     * $author 作者： Aige
     * 描述： 测试集合UI集合的使用
     *
     * @param
     * @return
     */
    @Test
    public void TestGetAllIcon() throws Exception {

        UiCollection videos = new UiCollection(new UiSelector()
                .className("android.widget.LinearLayout"));
        uiDevice.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), 5000);
        int childCount = videos.getChildCount(new UiSelector().className("android.widget.Button"));
        UiObject childByDescription = videos.getChildByDescription(new UiSelector().description("ok"), "ok");
        childByDescription.click();
        Log.i("Aige", "======= " + childCount);

    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
