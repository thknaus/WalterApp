package lib;

import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import hamburg.walter.data.IP;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Achle on 04.11.2017.
 */

public class IOSocket {

    private Socket socket = null;

    public IOSocket(String string) throws URISyntaxException {
        this.socket = IO.socket(new IP().URL);
        this.socket.on(string, updateFromServer);
    }

    private Emitter.Listener updateFromServer = new Emitter.Listener() {
        @Override
        public void call(final Object[] args) {
            JSONObject data = (JSONObject) args[0];
        }
    };
}
