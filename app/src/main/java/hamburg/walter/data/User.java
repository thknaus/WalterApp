package hamburg.walter.data;

import org.json.JSONObject;

public class User {
    private JSONObject jsonObject = null;
    public String firstname = "", lastname = "";
    public int _id = -1;

    public User(JSONObject jsonObject) {
        this.jsonObject = jsonObject;

        if (jsonObject != null) {
            _id = jsonObject.optInt("_id", -1);
            firstname = jsonObject.optString("firstname", "");
            lastname = jsonObject.optString("lastname", "");
        }
    }
}
