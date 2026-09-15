import javax.swing.*;
import java.awt.*;

public class Organism {

    public int x;
    public int y;
    public int speed;
    public int sensoryRadius;
    public int hunger;

    public int targetX;
    public int targetY;

    public String type;
    public Image rabbitImage;
    public Image wolfImage;
    public Image plantImage;

    public boolean actionTaken;

    public Organism(int x, int y, String type, int targetX, int targetY, int speed, int sensoryRadius,int hunger, Boolean actionTaken){

        this.x=x;
        this.y=y;
        this.type=type;
        this.targetX=targetX;
        this.targetY=targetY;
        this.speed=speed;
        this.sensoryRadius=sensoryRadius;
        this.hunger=hunger;
        this.actionTaken=actionTaken;


        rabbitImage = new ImageIcon(getClass().getResource("/rabbit.png")).getImage();
        wolfImage = new ImageIcon(getClass().getResource("/wolf.png")).getImage();
        plantImage = new ImageIcon(getClass().getResource("/plant.png")).getImage();

    }


    public void draw(Graphics g){
        if(type.equals("rabbit"))
            g.drawImage(rabbitImage,x,y,null);
        else if(type.equals("wolf"))
            g.drawImage(wolfImage,x-2,y,null);
            // x-2 because wolf image is 30*20 instead of 25*25, so x-2 to center it relative to the tile
        else if(type.equals("plant"))
            g.drawImage(plantImage,x,y,null);
    }

}
