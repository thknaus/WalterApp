package hamburg.walter.data;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private JSONObject jsonObject = null;
    private String email = "";
    private String _id = "";
    private String name = "";

    private static final User user = new User();

    public User(){}

    public static User getInstance(){
        return user;
    }

    public String getEmail(){
        return email;
    }
    public String getID(){
        return _id;
    }
    public void setUserData(JSONObject obj){
        try{
            email = obj.getString("email");
            _id = obj.getString("id");
        }catch(JSONException e){

        }
    }
    public String getName(){
        return name;
    }
    public void setName(String n){
        name = n;
    }

}
