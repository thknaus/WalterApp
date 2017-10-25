package hamburg.walter.data;

/**
 * Created by Thomas on 24.10.2017.
 */

public class Game {

    public static Game gameSession = new Game();

    private String gameName = "";
    private String gameSessionId = "";


    public Game(){}

    public static Game getInstance(){
        return gameSession;
    }

    public void setGameName(String name){
        gameName = name;
    }
    public void setGameSessionId(String id){
        gameSessionId = id;
    }
    public String getGameSessionId(){
        return gameSessionId;
    }

}
