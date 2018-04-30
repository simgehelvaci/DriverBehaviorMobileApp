package com.openxc.openxcstarter;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;

/**
 * Created by simgehelvaci on 30.04.2018.
 */

public class FileReadWriteHelper {




    public void writeFileOnInternalStorage(Context mcoContext, String sFileName, String sBody){
        File file = new File(mcoContext.getFilesDir(),"mydir");
        if(!file.exists()){
            file.mkdir();
        }

        try{
            File gpxfile = new File(file, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();

        }
    }



}
