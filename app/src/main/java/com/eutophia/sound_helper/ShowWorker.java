package com.eutophia.sound_helper;

import static android.app.PendingIntent.getActivity;

import static com.eutophia.sound_helper.MainActivity.context;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ShowWorker extends Worker {
    Context mContext = context;
    public ShowWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }
    @Override
    public Result doWork() {

        // Do the work here--in this case, upload the images.
        Intent intent = new Intent(getApplicationContext(), TransmitActivity.class);
        context.startActivity(intent);
        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }
}
