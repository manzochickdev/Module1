package com.example.tuananh.module1.Model;

import java.util.ArrayList;

public class Relationship {
    private static String[] relationship = new String[]{"Parent","Child","Sibling","Spouse"};

    public static String[] getRelationship() {
        return relationship;
    }



    public static int convertRelationship(String s){
        if(s.equals(relationship[0])){
            return 2;
        }
        else if (s.equals(relationship[1])){
            return 0;
        }
        else if (s.equals(relationship[2])){
            return 1;
        }
        else return -1;
    }

    public static String convertIntRelationship(int i){
        switch (i){
            case 0 :
                return relationship[1];
            case 1 :
                return relationship[2];
            case 2:
                return relationship[0];

            default : return null;
        }
    }
}
