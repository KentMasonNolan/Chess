package GUI;

public class Piece {
    private String color;
    private String type;

    public Piece(String color, String type) {
        this.color = color;
        this.type = type;
    }

    // Add methods for movement and capturing logic


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
