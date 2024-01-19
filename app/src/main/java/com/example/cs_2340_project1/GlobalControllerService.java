package com.example.cs_2340_project1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class GlobalControllerService extends Service {
    public GlobalControllerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}