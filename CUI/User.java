package CUI;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private int elo; //

    public User(String name, int elo) {
        this.name = name;
        this.elo = elo;
    }

    public String getName() {
        return name;
    }

    public int getElo() {
        return elo;
    }

}

