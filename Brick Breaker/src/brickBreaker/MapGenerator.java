package brickBreaker;

import java.awt.*;

/**
 * Created by Pawel on 16.06.2017.
 */
public class MapGenerator {
    public int map[][];
    public int brickWidth;
    public int brickHeight;
    public MapGenerator(int row,int col){
        map=new int[row][col];
        for (int i=0;i<map.length;i++){
            for (int j=0;j<map[0].length;j++){
                map[i][j]=1;
            }
        }
        brickWidth=600/col;
        brickHeight=150/row;

    }

    public void draw(Graphics2D g){
        for (int i=0;i<map.length;i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    //to tworzy klocki
                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 50, i * brickHeight + 50, brickWidth, brickHeight);

                    // to tworzy przestrzen miedzy klockami
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.DARK_GRAY);
                    g.drawRect(j * brickWidth + 50, i * brickHeight + 50, brickWidth, brickHeight);

                }
            }
        }
    }

    public void setBrickValue(int value,int row,int col){
        map[row][col]=value;

    }
}
