package uitest.leagoo.com.leagoouitest;

import android.os.SystemClock;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;

/**
 * Created by cangck on 17/7/14.
 */

public class TestT5Case {

    /**
     * $date 创建时间： 17/7/14 下午4:16
     * $author 作者： Aige
     * 描述： 进入相机，闪光灯自动状态录像20次每次5分钟，相机反应灵敏，不会失效或者反应迟钝，相机功能正常，闪光灯功能正常
     *
     * @param
     * @return
     */
    public void testAutoFlashAudio(UiDevice mDevice, String cameraPackageName, UiObject2 shutterButton, UiObject2 loopCycle, int time) {
        String[] ids = {
                "mode_switcher",
                "flash_toggle_button",
                "torch_toggle_button"
        };
        //        查找自动闪光灯
        boolean stop = true;
        int i = 0;
        int length = ids.length;
        while (stop) {
//        1.闪光灯已打开
            UiObject2 mode_switcher = mDevice.findObject(By.res(cameraPackageName, ids[i % (length - 1)]));
            if (mode_switcher != null) {
                mode_switcher.click();
            }
            SystemClock.sleep(2000);
            //        3.com.freeme.camera:id/flash_toggle_button
            UiObject2 autoflash = mDevice.findObject(By.res(cameraPackageName,"flash_toggle_button"));
            SystemClock.sleep(2000);
            if (autoflash != null) {
//                autoflash.click();
                stop = false;
            }
        }
        UiObject2 mode_switcher = mDevice.findObject(By.res(cameraPackageName, "mode_switcher"));
        mode_switcher.click();
        SystemClock.sleep(2000);
        shutterButton.click();//开始录像
        SystemClock.sleep(time);
        shutterButton.click();//结束录像
        SystemClock.sleep(2000);
        mode_switcher.click();//切换到拍照状态
        SystemClock.sleep(2000);
        loopCycle.click();
        SystemClock.sleep(2000);

    }

    /**
     * $date 创建时间： 17/7/14 下午4:23
     * $author 作者： Aige
     * 描述： 关闭闪光点测试录像功能
     *
     * @param
     * @return
     */
    public void testNoAutoFlashAudio(UiDevice mDevice, String cameraPackageName, UiObject2 shutterButton, UiObject2 loopCycle, int pic_time) {
        UiObject2 mode_switcher = mDevice.findObject(By.res(cameraPackageName, "mode_switcher"));
        mode_switcher.click();
        SystemClock.sleep(2000);
        shutterButton.click();//开始录像
        SystemClock.sleep(pic_time);
        shutterButton.click();//结束录像
        SystemClock.sleep(2000);
        mode_switcher.click();//切换到拍照状态
        SystemClock.sleep(2000);
        loopCycle.click();
    }

    public void testautoflashPic(String cameraPackageName, UiDevice mDevice, CameraUtil myCameraUtil, int CLICK_TIME, UiObject2 shutterButton) {
        boolean stop = true;
        while (stop) {
//        1.闪光灯已打开
            UiObject2 mode_switcher = mDevice.findObject(By.res(cameraPackageName, "mode_switcher"));
            if (mode_switcher != null) {
                mode_switcher.click();
            }
            SystemClock.sleep(2000);
//       2. com.freeme.camera:id/flash_toggle_button
            UiObject2 flash_toggle_button = mDevice.findObject(By.res(cameraPackageName, "flash_toggle_button"));
            SystemClock.sleep(2000);
            if (flash_toggle_button != null) {
                flash_toggle_button.click();
                stop = false;
            }
//        手电筒已关闭
            UiObject2 torch_toggle_button = mDevice.findObject(By.res(cameraPackageName, "torch_toggle_button"));
            SystemClock.sleep(2000);
            if (torch_toggle_button != null) {
                torch_toggle_button.click();
            }

            //        3.com.freeme.camera:id/flash_toggle_button
            UiObject2 autoflash = mDevice.findObject(By.descContains("自动闪光"));
            SystemClock.sleep(2000);
            if (autoflash != null) {
//                autoflash.click();
                stop = false;
            }
        }
        UiObject2 LoopCycle = mDevice.findObject(By.res(cameraPackageName, "flash_toggle_button"));
        if (LoopCycle != null && LoopCycle.isEnabled()) {
            LoopCycle.click();
        }
        myCameraUtil.clickOntime(shutterButton, CLICK_TIME);
        if (LoopCycle.isEnabled()) {
            LoopCycle.click();
        }
    }
}
