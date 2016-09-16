package de.cokuss.chrisbee.telefonieren;


        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.net.Network;
        import android.net.NetworkInfo;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.telephony.PhoneStateListener;
        import android.telephony.TelephonyManager;
        import android.view.View;
        import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String LOG = MainActivity.class.getSimpleName();
    private TelephonyManager mgr;
    private PhoneStateListener psl;
    private TextView tv, textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        textView = (TextView) findViewById(R.id.textView);
        mgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        psl = new PhoneStateListener(){

            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                // super.onCallStateChanged(state, incomingNumber);
                tv.append("Status " + state + " --- " + "Eingehende Nummer: " + incomingNumber + " \n");
            }


            @Override
            public void onMessageWaitingIndicatorChanged(boolean mwi) {
                // super.onMessageWaitingIndicatorChanged(mwi);
                tv.setText("is Changed " + mwi);
            }
        };
        tv.setText("Neustart: \n");
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] networkInfos = connMgr.getAllNetworks();
        NetworkInfo info = null;
        View v = new View(this).getRootView();
        String name = v.toString();
        for (Network n : networkInfos){
            info = connMgr.getNetworkInfo(n);
            textView.append(info.getSubtypeName());
            textView.append("\nVerfügbar: " + info.isAvailable());
            textView.append("\nVerbunden: " + info.isConnected());
            textView.append("\nAchtung Roaming: " + info.isRoaming());

        }
        mgr.listen(psl, PhoneStateListener.LISTEN_CALL_STATE);
//        Log.d(LOG, mgr.getDeviceId());
//        Log.d(LOG, Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
//
//        try {
//            Log.d(LOG, "SKIP_FIRST_USE_HINTS: " + Settings.Secure.getInt(getContentResolver(), "0"));
//            //Settings.Secure.SKIP_FIRST_USE_HINTS));
//        } catch (Settings.SettingNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mgr.listen(psl, PhoneStateListener.LISTEN_NONE);
    }
}
