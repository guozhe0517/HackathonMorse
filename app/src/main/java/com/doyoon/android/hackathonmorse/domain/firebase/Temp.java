package com.doyoon.android.hackathonmorse.domain.firebase;

/**
 * Created by DOYOON on 7/8/2017.
 */

public class Temp {

    // todo move to xml or build config... (it is database structure)
    public static class DbStructure {


        public static class StructureModel {
            public String modelPath = "";
            public boolean isBundle;                // todo make static... in child class
            public boolean isAutoGenerateModelKey;
            /*
            public String getPath(String... keys){    // user id.. chat id...
                String path = null;
                int need_node_name_num = 3; // get froem db structure..
                for (int i = 0 ; i < need_node_name_num ; i++ ){
                    this.path = null; // add nodeName.... 순서대로....
                }
                return path;
            }
            */
        }

        public static class User extends StructureModel {
            /*
            public String MODEL_PATH = "users";
            public final boolean IS_BUNDLE = true;                   // todo make static...
            public final boolean AUTO_GENERATE_MODEL_KEY = false;
            */
            public String modelPath = "users";
            public boolean isBundle = true;                // todo make static... in child class
            public boolean isAutoGenerateModelKey;
        }

        public static class UserProfile extends StructureModel {
            public static String MODEL_PATH = "users/{User_key}/userprofile";
            public final boolean IS_BUNDLE = false;
        }

        public static class ChatKey {
            public String MODEL_PATH = "users/{User_key}/chatkeys";
            public final boolean IS_BUNDLE = true;
            public final boolean AUTO_GENERATE_MODEL_KEY = true;
        }
        public static class FriendKey{
            public static String MODEL_PATH = "users/{User_key}/friendkey";
            public final static boolean IS_BUNDLE = true;
            public final static boolean AUTO_GENERATE_MODEL_KEY = true;
        }

        public static class adsfasd{
            public static String MODEL_PATH = "users/{User_key}/friendkey/{asdf}/friendKey/{asdfqewr}";
        }
    }
}
