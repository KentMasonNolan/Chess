package GUI;

public class Piece {
    private String colour;
    private String type;

    public Piece(String colour, String type) {
        this.colour = colour;
        this.type = type;
    }

    // Add methods for movement and capturing logic


    public String getcolour() {
        return colour;
    }

    public void setcolour(String colour) {
        this.colour = colour;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
