package Network;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Created by Nick van Endhoven on 25-9-2017.
 */
public class Datapacket {

    /*
        playerid: [playerid]
        type: [datatype: connect, message]
        content: [content]
     */

    // TODO: 25-9-2017 Make JSON
    String playerid;
    String type; // TODO: 25-9-2017 Make enum
    String content;

    public Datapacket(String input){
        String[] data = input.split(Pattern.quote("|1|"));

        this.playerid = data[0];
        this.type = data[1];
        this.content = data[2];
    }

    public String getContent() {
        return content;
    }

    public String getPlayerid() {
        return playerid;
    }

    public String getType() {
        return type;
    }
}
