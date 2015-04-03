package com.li.tritonia.wildlife;

/**
 * Created by Tritonia on 2015-04-02.
 */
public class Hunt {

    int huntId;
    String huntName;
    String huntDesc;

    public Hunt(){
        huntId = 1;
        huntDesc = "";
        huntName = "";
    }

    public Hunt(int huntId, String huntName, String huntDesc) {
        this.huntId = huntId;
        this.huntName = huntName;
        this.huntDesc = huntDesc;
    }

    public int getHuntId() {
        return huntId;
    }

    public void setHuntId(int huntId) {
        this.huntId = huntId;
    }

    public String getHuntName() {
        return huntName;
    }

    public void setHuntName(String huntName) {
        this.huntName = huntName;
    }

    public String getHuntDesc() {
        return huntDesc;
    }

    public void setHuntDesc(String huntDesc) {
        this.huntDesc = huntDesc;
    }
}
