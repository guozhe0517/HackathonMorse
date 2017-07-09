package com.doyoon.android.hackathonmorse.domain.firebase;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DOYOON on 7/8/2017.
 */

public class FirebaseHelper {

    public static final String TAG = FirebaseHelper.class.getSimpleName();

    // Parsing.. when loading start
    //public static HashMap<String, Class> modelPropertyMap;
    public static HashMap<String, HashMap<String, String>> dbStructureMap;

    private static String nodeStack2Path(Stack<String> nodeStack) {

        String[] nodes = new String[nodeStack.size()];
        nodeStack.toArray(nodes);

        String path = "";
        for(int i = 0; i < nodes.length; i++) {
            path += nodes[i] + "/";
        }
        return path;
    }

    private static void buildParentNodeStack(Stack<String> parentNodeStack, String currentNodeName, int preDepth, int currentDepth) {
        if(preDepth < currentDepth){
            parentNodeStack.add(currentNodeName);
        } else if (preDepth > currentDepth) {
            for(int i=0; i < preDepth - currentDepth; i++) {
                parentNodeStack.pop();
            }
        } else if (preDepth == currentDepth) {
            parentNodeStack.pop();
            parentNodeStack.add(currentNodeName);
        }

    }

    private static void putAllAttiributeToDbStructureMap(String nodeName, XmlPullParser parser, String dbpath){
        /* preapare hash map */
        HashMap<String, String> attributeMap;
        if(dbStructureMap.containsKey(nodeName)){
            attributeMap = dbStructureMap.get(nodeName);
            if(attributeMap == null){
                attributeMap = new HashMap<>();
                dbStructureMap.put(nodeName, attributeMap);
            }
        } else {
            attributeMap = new HashMap<>();
            dbStructureMap.put(nodeName, attributeMap);
        }

        /* insert attribute and value */
        for(int i = 0; i < parser.getAttributeCount(); i++) {
            String attrName = parser.getAttributeName(i);
            String attrValue = parser.getAttributeValue(null, attrName);
            attributeMap.put(attrName, attrValue);
        }
        // todo // FIXME: 7/9/2017 hird wiring
        attributeMap.put("modelDir", dbpath);
    }

    public static void loadDbStructure(Context context){

        int preDepth = 0;
        Stack<String> parentNodeStack = new Stack<>();
        dbStructureMap = new HashMap<>();
        try {
            InputStream inputStream = context.getAssets().open("database_structure.xml");
            // InputStream inputStream = context.getAssets().open("temperature.xml");
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();
            myParser.setInput(inputStream, null);

            int event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT)  {

                switch (event){
                    case XmlPullParser.START_TAG:

                        /* Current TAG info */
                        String currentTagName = myParser.getName();
                        int currentDepth = myParser.getDepth();
                        String currentType = myParser.getAttributeValue(null, "type");

                         /* Analyze attribute */
                        // todo // FIXME: 7/9/2017 hard wiring
                        if ( "value".equals(currentType)) {
                            putAllAttiributeToDbStructureMap(currentTagName, myParser, nodeStack2Path(parentNodeStack));
                        } else if("reference-key".equals(currentType)){
                            buildParentNodeStack(parentNodeStack, currentTagName, preDepth, currentDepth);
                            preDepth = currentDepth;
                        } else if("primary-key".equals(currentType)){
                            buildParentNodeStack(parentNodeStack, "{" + currentTagName + "}", preDepth, currentDepth);
                            preDepth = currentDepth;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = myParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // todo 고민... 용어 통일 node, tag.. ref...
    public static class Dao {
        /* Return DB full Path */
        public static <T extends FirebaseModel> String insert(T t, String modelDir) {
            if (dbStructureMap == null) {
                throw new RuntimeException("DB Structure Map is null, Db structure is not executed yet.");
            }

            if(modelDir == null) {

            }

            /* Get Info(isBundle, isAutoGenerateMdoelKey, model attribute map) */
            String modelName = t.getClass().getSimpleName().toLowerCase();
            HashMap<String, String> modelAttributeMap = dbStructureMap.get(modelName);
            boolean isBundle = Boolean.parseBoolean(modelAttributeMap.get("isBundle")); // // FIXME: 7/9/2017 hird wiring
            boolean isAutoGenerateModelKey = Boolean.parseBoolean(modelAttributeMap.get("isAutoGenerateModelKey"));
            // String modelDir = modelAttributeMap.get("modelDir");

            /* Define Model Key */
            String modelKey = "";
            if(!isBundle){
                modelKey = modelName;
            } else {
                if(isAutoGenerateModelKey){
                    modelKey = FirebaseDatabase.getInstance().getReference(modelDir).push().getKey();
                } else {
                    if (t.getModelKey() == null) {
                        throw new NullPointerException( "[" + t.getClass().getSimpleName() + "] Model key is null, If isAutoGenerateKey attribute is false, you have to define your own model key.");
                    }
                    modelKey = t.getModelKey();
                }
            }

            /* Build db path... */
            String modelPath = modelDir + modelKey;
            FirebaseDatabase.getInstance().getReference(modelPath).setValue(t);
            printInsertLog(modelName, isBundle, isAutoGenerateModelKey, modelDir, modelPath);

            return modelPath;
        }
    }

    public static List<String> findParams(String modelDir){
        String pattern = "(\\{\\w+\\})";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(modelDir);
        List<String> paramList = new ArrayList<>();
        while(m.find()){
            paramList.add(m.group(1));
        }
        return paramList;
    }

    private static void printInsertLog(String modelName, boolean isBundle, boolean isAutoGenerateModelKey, String modelDir, String modelPath){
        Log.e(TAG, "                             ");
        Log.e(TAG, "model name = [" + modelName + "], is bundle = [" + isBundle + "], isAutoGenerateKey = [" + isAutoGenerateModelKey + "]");
        Log.e(TAG, "model directory = " + modelDir);
        Log.e(TAG, "model path = " + modelPath);
        Log.e(TAG, "                              ");
    }

}
