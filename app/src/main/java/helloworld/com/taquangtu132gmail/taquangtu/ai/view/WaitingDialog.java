package helloworld.com.taquangtu132gmail.taquangtu.ai.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;

import helloworld.com.taquangtu132gmail.taquangtu.ai.R;

public class WaitingDialog extends Dialog {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_waiting);
        this.setCancelable(false);
    }
    public WaitingDialog(@NonNull Context context) {
        super(context);
    }

}
