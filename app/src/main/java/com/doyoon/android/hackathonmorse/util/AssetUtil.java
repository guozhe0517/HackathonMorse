package com.doyoon.android.hackathonmorse.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by DOYOON on 7/9/2017.
 */

public class AssetUtil {

    public static String getContent (Context context, String filename) throws IOException {

        InputStream inputStream = context.getAssets().open(filename);

        //todo need to replace... more efficiency after study about stream

        int data;
        char oneWord;
        String content = "";
        Reader reader = new InputStreamReader(inputStream);
        while ((data = reader.read()) != -1) {
            oneWord = (char) data ;
            content = content + oneWord;
        }

        inputStream.close();

        return content;
    }

}
