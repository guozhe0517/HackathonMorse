package com.doyoon.android.hackathonmorse.domain.firebase;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Stack;

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
        attributeMap.put("dbpath", dbpath);
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

                        /* Build Node Path */
                        buildParentNodeStack(parentNodeStack, currentTagName, preDepth, currentDepth);
                        preDepth = currentDepth;

                        /* Analyze attribute */
                        // todo // FIXME: 7/9/2017 hard wiring
                        String currentType = myParser.getAttributeValue(null, "type");
                        if("value".equals(currentType)){
                            putAllAttiributeToDbStructureMap(currentTagName, myParser, nodeStack2Path(parentNodeStack));
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
        public static <T extends FirebaseModel> void insert(T t) {
            if (dbStructureMap == null) {
                throw new RuntimeException("DB Structure Map is null, Db structure is not executed yet.");
            }

            // // FIXME: 7/9/2017 hird wiring
            /* get model attribute map */
            String modelName = t.getClass().getSimpleName().toLowerCase();
            HashMap<String, String> modelAttributeMap = dbStructureMap.get(modelName);
            boolean isBundle = Boolean.parseBoolean(modelAttributeMap.get("isBundle"));
            boolean isAutoGenerateModelKey = Boolean.parseBoolean(modelAttributeMap.get("isAutoGenerateModelKey"));
            String dbPath = modelAttributeMap.get("dbpath");

            Log.e(TAG, "                             ");
            Log.e(TAG, "model name = [" + modelName + "], is bundle = [" + isBundle + "] + isAutoGenerateKey = [" + isAutoGenerateModelKey + "]");
            Log.e(TAG, "dbpath = " + dbPath);
            Log.e(TAG, "                              ");
            /*
            // get simplename 으로
            String index = t.getClass().getSimpleName();
            //String index = t.getKey();
            DatabaseReference thisRootRef = FirebaseDatabase.getInstance().getReference(dbPath).child(index);    // users

            if(!isBundle){
                thisRootRef.setValue(t);
            } else {
                String key = "";
                if(isAutoGenerateModelKey){
                    key = thisRootRef.push().getKey();
                } else {
                    key = t.getValueKey();
                }
                thisRootRef.child(key).setValue(t);
            }
            */
        }
    }

}
