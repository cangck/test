package uitest.leagoo.com.leagoouitest;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.internal.runner.listener.InstrumentationResultPrinter;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.Switch;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Created by cangck on 17/7/12.
 */

@RunWith(AndroidJUnit4.class) //使用注解指定版本
@SdkSuppress(minSdkVersion = 18) //指定最新支持的sdk版本号4.3
public class UITest {


    private static final long LAUNCH_TIMEOUT = 5000;
    private static final String PACKAGE_NAME = "uitest.leagoo.com.leagoouitest";

    @Test
    public void testRegistry() throws Exception {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        UiDevice instance = UiDevice.getInstance(instrumentation);
//        instance.pressHome();
//        String launcherPackageName = instance.getLauncherPackageName();
//        Log.i("Aige", "======== currentPackageName" + launcherPackageName);
//        assertThat(launcherPackageName, notNullValue());
//        instance.wait(Until.hasObject(By.pkg(launcherPackageName).depth(0)),
//                LAUNCH_TIMEOUT);
//        Context context = InstrumentationRegistry.getContext();
//        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME);
//        launchIntentForPackage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        context.startActivity(launchIntentForPackage);
//
//        //等待过程，避免命令执行完成后，界面还没有反应过来
//        instance.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), LAUNCH_TIMEOUT);

        //获取当前的打开的app名称
//        String currentPackageName = instance.getCurrentPackageName();
//        Log.i("Aige", "======== currentPackageName" + currentPackageName);

        UiObject object = instance.findObject(new UiSelector().text("Ok").className("android.widget.Button"));
        if (object.exists() && object.isEnabled()) {
            object.click();
        }


    }

    @Test
    public void testClick() throws Exception {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        PackageManager packageManager = targetContext.getPackageManager();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        List<String> apps = new ArrayList<>();

        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo info : resolveInfos) {
            String packageName = info.activityInfo.packageName;

            if (!TextUtils.isEmpty(packageName) && !"com.android.settings".equals(packageName)) {
                Log.i("Aige", "========= " + packageName);
                apps.add("com.android.camera");
                apps.add("com.android.contacts");
                apps.add("com.android.dialer");
                apps.add("com.android.gallery");
            }
        }

        Context context = InstrumentationRegistry.getContext();
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        uiDevice.pressHome();

//        for (String pkg : apps) {
        Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(apps.get(0));
        if (launchIntentForPackage != null) {
//                launchIntentForPackage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            uiDevice.wait(Until.hasObject(By.pkg(apps.get(0)).depth(0)), 5000);
            uiDevice.pressHome();
        }
//        }

    }

    @Test
    public void getPackage() throws Exception {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        PackageManager packageManager = targetContext.getPackageManager();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo info : resolveInfos) {
            Log.i("Aige", "========= " + info.activityInfo.packageName + "/ " + info.activityInfo.name);
        }

    }


    @Test
    public void testApps() throws UiObjectNotFoundException {
        // TODO 重要注意： 在运行该测试代码的时候 需要先把手机语言环境设置为英文。

        // 模拟 HOME 键点击事件
        getUiDevice().pressHome();

        // 现在打开了主屏应用，模拟点击所有应用按钮操作来启动所有应用界面。
        // 如果你使用了uiautomatorviewer来查看主屏，则可以发现“所有应用”按钮的
        // content-description 属性为“Apps”。可以使用该属性来找到该按钮。
        UiObject allAppsButton = new UiObject(new UiSelector().description("Apps"));

        // 模拟点击所有应用按钮，并等待所有应用界面起来
        allAppsButton.clickAndWaitForNewWindow();

        // 在所有应用界面，时钟应用位于Apps tab界面中。下面模拟用户点击Apps tab操作。
        // 找到 Apps tab 按钮
        UiObject appsTab = new UiObject(new UiSelector().text("Apps"));

        // 模拟点击 Apps tab.
        appsTab.click();

        // 然后在 Apps tab界面，模拟用户滑动到时钟应用的操作。
        // 由于Apps界面是可以滚动的，所有用
        // UiScrollable 对象.
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));

        // 设置滚动模式为水平滚动(默认为垂直滚动)
        appViews.setAsHorizontalList();

        if (allAppsButton.exists() && allAppsButton.isEnabled()) {
            // allAppsButton在当前界面已经不可见了 所以这里不会执行
            allAppsButton.click();
        }
        // 查找时钟应用并点击
        UiObject settingsApp = appViews.getChildByText(
                new UiSelector().className(android.widget.TextView.class.getName()), "Clock");
        settingsApp.clickAndWaitForNewWindow();

        // 验证当前显示 的应用包名为时钟

        UiObject settingsValidation = new UiObject(new UiSelector().packageName("com.google.android.deskclock"));
        // 如果不存在则出错提示
        assertTrue("Unable to detect Clock", settingsValidation.exists());

        // 模拟点击时间tab
        UiObject clock = new UiObject(new UiSelector().description("Clock"));
        clock.clickAndWaitForNewWindow();
        // 模拟点击下方的闹钟图标
        UiObject alarms = new UiObject(new UiSelector().description("Alarms"));
        alarms.clickAndWaitForNewWindow();

        UiScrollable list = new UiScrollable(new UiSelector().className(ListView.class.getName()));
        if (list.getChildCount() > 0) {
            UiObject listIndex0 = list.getChild(new UiSelector().index(0));
            UiObject switchBtn = listIndex0.getChild(new UiSelector().className(Switch.class.getName()));

            boolean isChecked = switchBtn.isChecked();

            switchBtn.click();
        }
        // 模拟点击返回键
        getUiDevice().pressBack();

        UiObject timer = new UiObject(new UiSelector().description("Timer"));
        timer.clickAndWaitForNewWindow();

    }

    public UiDevice getUiDevice() {
        return UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }


    @Test
    public void testDump() throws Exception {
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        String externalStorageState = Environment.getExternalStorageState();
//        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
//            uiDevice.dumpWindowHierarchy(new File("window"));
//        }

        Log.i("Aige", "======= " + uiDevice.getDisplayHeight() + uiDevice.getProductName());
    }
}


