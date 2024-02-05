package com.example.cs_2340_project1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;

public class GlobalControllerService extends Service {
    public static ArrayList<ClassesModel> classModels = new ArrayList<>();
    public static ArrayList<AssignmentModel> assignmentModels = new ArrayList<>();

    public GlobalControllerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}