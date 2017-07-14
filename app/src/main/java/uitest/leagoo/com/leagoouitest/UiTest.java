package uitest.leagoo.com.leagoouitest;

import android.app.Instrumentation;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class UiTest extends AppCompatActivity {
    private static final String TAG = "UiTest-MonkeyTest";
    Button startMonkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_test);
        startMonkey = (Button) findViewById(R.id.StartMonkeyButton);
        startMonkey.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {
                /*Instrumentation mIns = getInstrumentation();
                InstrumentationRegistry.registerInstance(mIns,mIns.getBinderCounts());
                UiAutomation uia = mIns.getUiAutomation();
                uia.executeShellCommand("monkey -s 1 -p com.freeme.calculator -v -v -v 10000");*/
                try {
                    new Start().setUp();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runMonkey();
            }
        });


        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Aige", "======= ok");
            }
        });
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Aige", "======= cancel");
            }
        });
    }

    /**
     * 点击按钮对应的方法
     */
    public void runMonkey() {
        Log.i(TAG, "runMyMonkey: ");
        new UiautomatorThread().start();
    }

    public UiDevice mDevice;

    //    @RunWith(AndroidJUnit4.class)
//    @SdkSuppress(minSdkVersion = 18)
    public class Start {
        //        @Before
        public void setUp() throws RemoteException, IOException {
            // Initialize UiDevice instance
            Instrumentation mis = getInstrumentation();
//            InstrumentationRegistry.registerInstance(mis,InstrumentationRegistry.getArguments());
            InstrumentationRegistry.registerInstance(mis, mis.getAllocCounts());
            mDevice = UiDevice.getInstance(mis);
//            mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            mDevice.wakeUp();
        }
    }

    /**
     * 运行uiautomator是个费时的操作，不应该放在主线程，因此另起一个线程运行
     */
    class UiautomatorThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                mDevice.executeShellCommand("monkey -s 1 -p com.freeme.calculator -v -v -v 10000");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
