package demo.multibhashi.com.demoapp.utilities;

import android.view.View;

/**
 * Created by sumanhaque on 10/18/2017.
 */

public interface ClickListener {

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
