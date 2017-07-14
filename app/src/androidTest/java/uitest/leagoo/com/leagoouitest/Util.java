package uitest.leagoo.com.leagoouitest;

import android.os.Build;
import android.os.SystemClock;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

/**
 * Created by ljj on 2017/5/27.
 * 文件描述：封装功能方法的类文件
 * 版本号：V1.0
 */



class CameraUtil {

    //    565-591-526-506-D4001-D5001-L5701定义不同的版本号
    private static final String VERSION_565 = "565";
    private static final String VERSION_591 = "591";
    private static final String VERSION_506 = "506";
    private static final String VERSION_D4001 = "D4001";
    private static final String VERSION_D5001 = "D5001";
    private static final String VERSION_L5701 = "L5701";
    private static final String VERSION_T5 = "T5";

    public String getCameraPackageName() {
        String currentversion = "";
        String release = Build.MODEL;
        switch (release) {
            case VERSION_565:
                break;
            case VERSION_591:
                break;
            case VERSION_506:
                break;
            case VERSION_D4001:
                break;
            case VERSION_D5001:
                break;
            case VERSION_L5701:
                break;
            case VERSION_T5:
                currentversion = "com.freeme.camera";
                break;
        }
        return currentversion;
    }

    /**
     * 通过swipe实现长按方法,
     *
     * @param ud       UiDevices 传入UiDevice设备
     * @param uiObject UiObject2 要长按的对象
     *                 滑动步长 1 step =5ms，不同机型时间不一样，有些1 step =15ms，不等，方法中默认步长是500
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    public void longClick(UiDevice ud, UiObject2 uiObject) throws UiObjectNotFoundException, InterruptedException {
        ud.swipe(uiObject.getVisibleBounds().centerX(), uiObject.getVisibleBounds().centerY(),
                uiObject.getVisibleBounds().centerX(), uiObject.getVisibleBounds().centerY(), 500);
        //一次操作后需要休眠要不然会报android.support.test.uiautomator.StaleObjectException
        Thread.sleep(3000);
    }

    /**
     * 点击拍照按钮方法
     *
     * @param shutterButton UiObject2 拍照按钮
     * @throws InterruptedException
     */
    public void clickShutterButton(UiObject2 shutterButton) throws InterruptedException {
        if (shutterButton.isEnabled()) {
            shutterButton.click();
            Thread.sleep(5000);
        }
    }

    /**
     * 切换闪光灯状态方法
     *
     * @param flashButton UiObject2 闪光灯按钮
     * @param status      String 类型 要切换的状态，只能是：打开、关闭、自动
     * @return 切换成功返回true，否在返回false
     * @throws InterruptedException
     */
    public boolean switchFlashStatus(UiObject2 flashButton, String status) throws InterruptedException {
        if (flashButton.getContentDescription().contains(status)) {
            return true;
        } else {
            do {
                flashButton.click();
                Thread.sleep(1500);
            } while (!flashButton.getContentDescription().contains(status));
            if (flashButton.getContentDescription().contains(status)) {
                return true;
            } else return false;
        }
    }

    /**
     * 前后摄像头切换方法
     *
     * @param cameraToggleButton UiObject2 摄像头切换按钮
     * @param switchStr          String 指定要切换到前置还是后置，值只能是：前置、后置
     * @throws InterruptedException
     */
    public void switchCamera(UiObject2 cameraToggleButton, String switchStr) throws InterruptedException {
        if (cameraToggleButton.getContentDescription().contains(switchStr)) {
            return;
        } else {
            while (true) {
                cameraToggleButton.click();
                Thread.sleep(2000);
                if (cameraToggleButton.getContentDescription().contains(switchStr)) return;
            }
        }
    }

    /**
     * 关闭闪光拍照、自动闪光拍照、打开闪光拍照循环,如此循环shutterTimes次方法
     *
     * @param cameraToggleButton UiObject2 类型，前后摄像头切换按钮
     * @param flashButton        UiObject2类型，切换闪光灯按钮
     * @param shutterButton      UiObject2类型，拍照按钮
     * @param shutterTimes       int 类型，要循环拍照的次数
     * @throws InterruptedException
     */
    public void flashSwitchLoopCyclePhoto(UiObject2 cameraToggleButton, UiObject2 flashButton, UiObject2 shutterButton, int shutterTimes) throws InterruptedException {
        //关闭闪光拍照、自动闪光拍照、打开闪光拍照循环 shutterTimes 次
        switchCamera(cameraToggleButton, "后置");
        for (int i = 0; i < shutterTimes; i++) {
            clickShutterButton(shutterButton);
/*            shutterButton.click();
            shutterButton.wait(Until.clickable(true),5000);
            Log.d(TAG,"click "+i+" times");*/
            switchFlashStatus(flashButton, "自动");
            clickShutterButton(shutterButton);

            switchFlashStatus(flashButton, "打开");
            clickShutterButton(shutterButton);

            switchFlashStatus(flashButton, "关闭");
        }
    }

    /**
     * 后置摄像头连续拍照shutterTimes次
     *
     * @param cameraToggleButton UiObject2 类型，前后摄像头切换按钮
     * @param flashButton        UiObject2类型，切换闪光灯按钮
     * @param shutterButton      UiObject2类型，拍照按钮
     * @param flashStatus        String 设置闪光灯是否打开，值只能是：打开、关闭
     * @param shutterTimes       int 类型，要循环拍照的次数
     * @throws InterruptedException
     */
    public void rearPhtots(UiObject2 cameraToggleButton, UiObject2 flashButton, UiObject2 shutterButton, String flashStatus, int shutterTimes) throws InterruptedException {
        //后置成像 shuuterTimes次
        switchCamera(cameraToggleButton, "后置");
        if (flashStatus.equals("打开")) {
            switchFlashStatus(flashButton, "打开");
        } else switchFlashStatus(flashButton, "关闭");
        for (int i = 0; i < shutterTimes; i++) {
            clickShutterButton(shutterButton);
        }
    }

    /**
     * * 拍照，点击打开预览，放大，缩小，滑动，循环shutterTimes次方法
     *
     * @param cameraToggleButton UiObject2 类型，前后摄像头切换按钮
     * @param shutterButton      UiObject2类型，拍照按钮
     * @param thumbnail          UiObject2类型，预览小图
     * @param cameraSwitchStr    String 说明前置还是后置摄像头，值只能是：前置、后置
     * @param uiDevice           UiDevice 传入设备
     * @param photoImage         UiObject2类型 打开预览后的照片对象
     * @param shutterTimes       int 类型，要循环拍照的次数
     * @throws InterruptedException
     */
    public void tackPhotoAndPreview(UiObject2 cameraToggleButton, UiObject2 shutterButton, UiObject2 thumbnail, String cameraSwitchStr, UiDevice uiDevice
            , UiObject2 photoImage, int shutterTimes) throws InterruptedException {
        //拍照，点击打开预览，放大，缩小，滑动，循环shutterTimes次
        if (cameraSwitchStr.equals("前置")) {
            switchCamera(cameraToggleButton, "前置");
        } else {
            switchCamera(cameraToggleButton, "后置");
        }


        for (int i = 0; i < shutterTimes; i++) {
            clickShutterButton(shutterButton);
            thumbnail.click();
            Thread.sleep(1000);
//            photoImage.pinchOpen(1.0f,300);//传进来会报空针，先在方法里面获取
            uiDevice.findObject(By.descContains("拍摄")).pinchOpen(1.0f, 300);
            Thread.sleep(300);
            uiDevice.findObject(By.descContains("拍摄")).pinchClose(1.0f, 300);
            uiDevice.swipe(uiDevice.getDisplayWidth() / 5 * 4, uiDevice.getDisplayHeight() / 2, uiDevice.getDisplayWidth() / 5, uiDevice.getDisplayHeight() / 2, 6);
            Thread.sleep(1000);
            uiDevice.swipe(uiDevice.getDisplayWidth() / 5, uiDevice.getDisplayHeight() / 2, uiDevice.getDisplayWidth() / 5 * 4, uiDevice.getDisplayHeight() / 2, 6);
            uiDevice.pressBack();
            Thread.sleep(500);
        }

    }

    /**
     * 录像10秒钟，循环shutterTimes次
     *
     * @param cameraToggleButton UiObject2 类型，前后摄像头切换按钮
     * @param shutterButton      UiObject2类型，拍照按钮
     * @param modeSwitch         UiObject2类型，拍照、摄像模式切换按钮
     * @param cameraSwitchStr    String 说明前置还是后置摄像头，值只能是：前置、后置
     * @param shutterTimes       int 类型，要循环拍照的次数
     * @throws InterruptedException
     */
    public void loopVideoAtTenSeconds(UiObject2 cameraToggleButton, UiObject2 shutterButton, UiObject2 modeSwitch, String cameraSwitchStr, int shutterTimes) throws InterruptedException {
        //录像10秒钟，循环shutterTimes次
        if (cameraSwitchStr.equals("前置")) switchCamera(cameraToggleButton, "前置");
        else switchCamera(cameraToggleButton, "后置");
        modeSwitch.click();
        for (int i = 0; i < shutterTimes; i++) {
            Thread.sleep(1000);
            shutterButton.click();
            Thread.sleep(10001);
            shutterButton.click();
            Thread.sleep(2000);
        }
        modeSwitch.click();
    }

    /**
     * 前置摄像头连续拍照shutterTimes次
     *
     * @param cameraToggleButton UiObject2 类型，前后摄像头切换按钮
     * @param shutterButton      UiObject2类型，拍照按钮
     * @param shutterTimes       int 类型，要循环拍照的次数
     * @throws InterruptedException
     */
    public void frontPhotos(UiObject2 cameraToggleButton, UiObject2 shutterButton, int shutterTimes) throws InterruptedException {
        //前置成像 shuuterTimes次
        switchCamera(cameraToggleButton, "前置");
        for (int i = 0; i < shutterTimes; i++) {
            clickShutterButton(shutterButton);
        }
    }

    /**
     * 前置摄像头连拍方法，连拍shutterTimes次
     *
     * @param cameraToggleButton UiObject2 前后摄像头切换按钮
     * @param mDevice            UiDevice 传入设备
     * @param uiObject2          UiObject2 要长按的对象
     * @param shutterTimes       int 循环次数
     * @throws InterruptedException
     * @throws UiObjectNotFoundException
     */
    public void frontSeriesPhotos(UiObject2 cameraToggleButton, UiDevice mDevice, UiObject2 uiObject2, int shutterTimes) throws InterruptedException, UiObjectNotFoundException {
        //前置摄像头连拍
        switchCamera(cameraToggleButton, "前置");
        for (int i = 0; i < shutterTimes; i++) {
            longClick(mDevice, uiObject2);
        }
    }

    /**
     * 后置摄像头连拍方法，连拍shutterTimes次
     *
     * @param cameraToggleButton UiObject2 前后摄像头切换按钮
     * @param mDevice            UiDevice 传入设备
     * @param uiObject2          UiObject2 要长按的对象
     * @param shutterTimes       int 循环次数
     * @throws InterruptedException
     * @throws UiObjectNotFoundException
     */
    public void rearSeriesPhotos(UiObject2 cameraToggleButton, UiDevice mDevice, UiObject2 uiObject2, int shutterTimes) throws InterruptedException, UiObjectNotFoundException {
        //后置摄像头连拍
        switchCamera(cameraToggleButton, "后置");
        for (int i = 0; i < shutterTimes; i++) {
            longClick(mDevice, uiObject2);
        }
    }

    /**
     * 前后摄像头切换拍照，循环shutterTimes 次
     *
     * @param cameraToggleButton UiObject2 前后摄像头切换按钮
     * @param flashButton        UiObject2 闪光灯切换按钮
     * @param shutterButton      UiObject2 拍照按钮
     * @param shutterTimes       int 拍照次数
     * @throws InterruptedException
     */
    public void rearFrontSwitchPhotos(UiObject2 cameraToggleButton, UiObject2 flashButton, UiObject2 shutterButton, int shutterTimes) throws InterruptedException {
        //前后摄像头切换拍照，循环shutterTimes 次
        switchFlashStatus(flashButton, "关闭");
        for (int i = 0; i < shutterTimes; i++) {
            switchCamera(cameraToggleButton, "前置");
            clickShutterButton(shutterButton);
            switchCamera(cameraToggleButton, "后置");
            clickShutterButton(shutterButton);
        }
    }

    /**
     * $date 创建时间： 17/7/13 下午5:01
     * $author 作者： Aige
     * 描述： 点击次数
     *
     * @param
     * @return
     */
    public void clickOntime(UiObject2 shutterButton, int CLICK_TIME) {
        for (int i = 0; i < CLICK_TIME; i++) {
            if (shutterButton.isEnabled()) {
                shutterButton.click();
                SystemClock.sleep(1500);
            }
        }

    }
}
