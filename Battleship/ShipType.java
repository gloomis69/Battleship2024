import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public enum ShipType {
    CRUISER (2, "2_cruiser60x30.jpg"),
    DESTROYER (3, "3_destroyer90x30.jpg"),
    SUBMARINE (3, "3_submarine90x30.jpg"),
    BATTLESHIP (4, "4_battleship120x30.jpg"),
    CARRIER (5, "5_carrier150x30.jpg");

    private final int size;
    private final String img;
    private final String v_img;

    ShipType(int size, String img){
        this.size = size;
        this.img = img;
        v_img = img.substring(0, img.length()-4)+"_V.jpg";
    }

    public int length(){
        return size;
    }

    public ImageIcon img(int segment, boolean isHorizontal){
        String imageName = img;
        if(!isHorizontal) imageName = v_img;
        try{
            BufferedImage originalImg = ImageIO.read(new File("shipIcons/"+imageName));
            if(isHorizontal) return new ImageIcon(originalImg.getSubimage(segment*30, 0, 30, 30));
            return new ImageIcon(originalImg.getSubimage(0, segment*30, 30, 30));
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    
}


