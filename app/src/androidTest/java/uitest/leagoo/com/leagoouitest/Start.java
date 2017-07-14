package uitest.leagoo.com.leagoouitest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Environment;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by liujunjie on 2017/5/4.
 * 此类为描述测试用例封装成方法
 */

/**
 * 测试需求
 * 1.开机，第一次点击相机100次，相机反应灵敏，不会失效或者反应迟钝
 * 2.进入相机，自动连拍100次，相机反应灵敏，不会失效或者反应迟钝，相机功能正常
 * 3.进入相机，手动连拍100次，相机反应灵敏，不会失效或者反应迟钝，相机功能正常
 * 4.进入相机，开闪光灯手动连拍100次，相机反应灵敏，不会失效或者反应迟钝，相机功能正常，闪光灯功能正常
 * 5.进入相机，闪光灯自动状态手动连拍100次，相机反应灵敏，不会失效或者反应迟钝，相机功能正常，闪光灯功能正常
 * 6.进入相机，开闪光灯录像20次每次5分钟，相机反应灵敏，不会失效或者反应迟钝，相机功能正常，闪光灯功能正常
 * 7.进入相机，闪光灯自动状态录像20次每次5分钟，相机反应灵敏，不会失效或者反应迟钝，相机功能正常，闪光灯功能正常
 * 8.进入相机，闪光灯关闭状态录像20次每次5分钟，相机反应灵敏，不会失效或者反应迟钝，相机功能正常，闪光灯功能正常
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Start {
    //点击次数
    private int CLICK_TIME = 5;
    private int PIC_TIME = 50000; //拍照时间

    enum VersionModel {
        T5
    }

    public static final String TAG = "myuitest";
    public UiDevice mDevice;
    private static final int LAUNCH_TIMEOUT = 5000;
    String dir = String.format("%s/%s", Environment.getExternalStorageDirectory().getPath(), "test-screenshots");
    public Watcher myWatcher = new Watcher();

    @Before
    public void setUp() throws RemoteException, IOException {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.wakeUp();
        //touch TP unLock
        mDevice.swipe(200, 560, 200, 200, 6);
        // Start from the home screen
        mDevice.pressHome();

        mDevice.registerWatcher("catDialog", myWatcher);
//        mDevice.executeShellCommand("monkey -s 1 -p com.freeme.calculator -v -v -v 10000");
        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
        openLauncher();
    }


    @Test
    public void checkPreconditions() {
        assertThat(mDevice, notNullValue());
    }

    @Test
    public void cameraTest() throws InterruptedException, RemoteException, UiObjectNotFoundException {
        /*mDevice.findObject(By.desc("应用")).click();
        mDevice.wait(Until.hasObject(By.desc("计算器")), LAUNCH_TIMEOUT);
        mDevice.findObject(By.desc("计算器")).click();*/
        CameraUtil myCameraUtil = new CameraUtil();
        String cameraPackageName = myCameraUtil.getCameraPackageName();

        int shutterTimes = 2;
        openApp(cameraPackageName);
        // Wait for the app to appear
        Thread.sleep(3000);
        mDevice.wait(Until.hasObject(By.pkg(cameraPackageName).depth(0)), LAUNCH_TIMEOUT);
        UiObject2 shutterButton = mDevice.wait(Until.findObject(By.res(cameraPackageName, "shutter_button")), 1000);//拍照按钮,F518机型
        UiObject2 flashButton = mDevice.findObject(By.descContains("闪光"));//闪光灯切换按钮,F518机型
        UiObject2 cameraToggleButton = mDevice.findObject(By.res(cameraPackageName, "camera_toggle_button"));//前后摄像头切换按钮,F518机型
        UiObject2 thumbnail = mDevice.findObject(By.res(cameraPackageName, "thumbnail"));//照片预览小图,F518机型
        UiObject2 photoImage = mDevice.findObject(By.descContains("拍摄"));//打开预览后的照片对象,F518机型
        UiObject2 modeSwitch = mDevice.findObject(By.res(cameraPackageName, "mode_switcher"));//拍照录像切换按钮,F518机型
//        com.freeme.camera:id/flash_toggle_button
        /*myCameraUtil.flashSwitchLoopCyclePhoto(cameraToggleButton,flashButton,shutterButton,shutterTimes);
        myCameraUtil.rearFrontSwitchPhotos(cameraToggleButton,flashButton,shutterButton,shutterTimes);
        myCameraUtil.rearSeriesPhotos(cameraToggleButton,mDevice,shutterButton,shutterTimes);
        myCameraUtil.rearPhtots(cameraToggleButton,flashButton,shutterButton,"关闭",shutterTimes);
        myCameraUtil.frontPhotos(cameraToggleButton,shutterButton,shutterTimes);*/


//        1.开机，第一次点击相机100次，相机反应灵敏，不会失效或者反应迟钝	　
//        2.进入相机，自动连拍100次，相机反应灵敏，不会失效或者反应迟钝，相机功能正常	　
//        myCameraUtil.clickOntime(shutterButton, CLICK_TIME);
//        3.进入相机，手动连拍100次，相机反应灵敏，不会失效或者反应迟钝，相机功能正常	　
//        4.进入相机，开闪光灯手动连拍100次，相机反应灵敏，不会失效或者反应迟钝，相机功能正常，闪光灯功能正常
        UiObject2 LoopCycle = mDevice.findObject(By.res(cameraPackageName, "flash_toggle_button"));
        if (LoopCycle != null && LoopCycle.isEnabled()) {
            LoopCycle.click();
        }
        myCameraUtil.clickOntime(shutterButton, CLICK_TIME);
        if (LoopCycle.isEnabled()) {
            LoopCycle.click();
        }
//        5.进入相机，闪光灯自动状态手动连拍100次，相机反应灵敏，不会失效或者反应迟钝，相机功能正常，闪光灯功能正常
//        6.进入相机，开闪光灯录像20次每次5分钟，相机反应灵敏，不会失效或者反应迟钝，相机功能正常，闪光灯功能正常
//        7.进入相机，闪光灯自动状态录像20次每次5分钟，相机反应灵敏，不会失效或者反应迟钝，相机功能正常，闪光灯功能正常	　
//        com.freeme.camera:id/torch_toggle_button
        TestT5Case testT5Case = new TestT5Case();
        testT5Case.testAutoFlashAudio(mDevice, cameraPackageName, shutterButton, LoopCycle, PIC_TIME);
//        8.进入相机，闪光灯关闭状态录像20次每次5分钟，相机反应灵敏，不会失效或者反应迟钝，相机功能正常，闪光灯功能正常
        testT5Case.testNoAutoFlashAudio(mDevice, cameraPackageName, shutterButton, LoopCycle, PIC_TIME);

//        myCameraUtil.tackPhotoAndPreview(cameraToggleButton, shutterButton, thumbnail, "后置", mDevice, photoImage, shutterTimes);
//        myCameraUtil.loopVideoAtTenSeconds(cameraToggleButton, shutterButton, modeSwitch, "前置", shutterTimes);
//        mDevice.pressHome();
    }

    @Test
    public void TestT5() throws Exception {
        TestT5Case testT5Case = new TestT5Case();
        CameraUtil myCameraUtil = new CameraUtil();
        String cameraPackageName = myCameraUtil.getCameraPackageName();

        openApp(cameraPackageName);
        // Wait for the app to appear
        Thread.sleep(3000);
        mDevice.wait(Until.hasObject(By.pkg(cameraPackageName).depth(0)), LAUNCH_TIMEOUT);
        UiObject2 shutterButton = mDevice.wait(Until.findObject(By.res(cameraPackageName, "shutter_button")), 1000);//拍照按钮

//        1.开机，第一次点击相机100次，相机反应灵敏，不会失效或者反应迟钝	　
//        2.进入相机，自动连拍100次，相机反应灵敏，不会失效或者反应迟钝，相机功能正常	　
        myCameraUtil.clickOntime(shutterButton, CLICK_TIME);
//        3.进入相机，手动连拍100次，相机反应灵敏，不会失效或者反应迟钝，相机功能正常	　
//        4.进入相机，开闪光灯手动连拍100次，相机反应灵敏，不会失效或者反应迟钝，相机功能正常，闪光灯功能正常
        //        查找自动闪光灯
        testT5Case.testautoflashPic(cameraPackageName, mDevice, myCameraUtil, CLICK_TIME, shutterButton);
//        5.进入相机，闪光灯自动状态手动连拍100次，相机反应灵敏，不会失效或者反应迟钝，相机功能正常，闪光灯功能正常
//        6.进入相机，开闪光灯录像20次每次5分钟，相机反应灵敏，不会失效或者反应迟钝，相机功能正常，闪光灯功能正常
//        7.进入相机，闪光灯自动状态录像20次每次5分钟，相机反应灵敏，不会失效或者反应迟钝，相机功能正常，闪光灯功能正常	　
//        com.freeme.camera:id/torch_toggle_button
        UiObject2 LoopCycle = mDevice.findObject(By.res(cameraPackageName, "flash_toggle_button"));
        testT5Case.testNoAutoFlashAudio(mDevice, cameraPackageName, shutterButton, LoopCycle, PIC_TIME);
//        8.进入相机，闪光灯关闭状态录像20次每次5分钟，相机反应灵敏，不会失效或者反应迟钝，相机功能正常，闪光灯功能正常
        testT5Case.testAutoFlashAudio(mDevice, cameraPackageName, shutterButton, LoopCycle, PIC_TIME);
    }

    /**
     * 打开APP的方法
     *
     * @param packagename 要打开的应用包名
     */
    private void openApp(String packagename) {
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(packagename);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }


    /**
     * $date 创建时间： 17/7/13 下午4:28
     * $author 作者： Aige
     * 描述： 打开launcher
     *
     * @param
     * @return
     */
    private void openLauncher() {
        Context context = InstrumentationRegistry.getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.leagoo.launcher3");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }

    @Test
    public void getDeviceInfo() {
        String release = Build.MODEL;
        Log.i("Aige", "===== " + release);
    }
}

class Watcher implements UiWatcher {
    /**
     * Custom handler that is automatically called when the testing framework is unable to
     * find a match using the {@link UiSelector}
     * <p>
     * When the framework is in the process of matching a {@link UiSelector} and it
     * is unable to match any widget based on the specified criteria in the selector,
     * the framework will perform retries for a predetermined time, waiting for the display
     * to update and show the desired widget. While the framework is in this state, it will call
     * registered watchers' checkForCondition(). This gives the registered watchers a chance
     * to take a look at the display and see if there is a recognized condition that can be
     * handled and in doing so allowing the current test to continue.
     * <p>
     * An example usage would be to look for dialogs popped due to other background
     * processes requesting user attention and have nothing to do with the application
     * currently under test.
     *
     * @return true to indicate a matched condition or false for nothing was matched
     * @since API Level 16
     */
    @Override
    public boolean checkForCondition() {
        System.out.println("监听器检查函数开始运行-挂电话");
        if (false) {

            return true;
        }
        System.out.println("监听器条件判断失败--挂电话");
        return false;
    }
}

