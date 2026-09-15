import javax.swing.*;
import java.awt.*;


public class Tile {

    // This object takes a coordinate and draws the grassTile image at that coordinate

    public Image tileImage;
    public int x;
    public int y;

    public Tile(int x, int y){

        this.x=x;
        this.y=y;
        tileImage = new ImageIcon(getClass().getResource("/grassTile.png")).getImage();
    }

    public void draw(Graphics g){
        g.drawImage(tileImage,x,y,null);
    }

}

