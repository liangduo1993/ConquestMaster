package GameConsole.Window;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import GameConsole.World.Country;
import GameConsole.World.World;

/**
 * Created by jingyang on 2017-10-06.
 */


public class CountryDisplay {
    private JPanel map;
    private ArrayList<CountryButton> buttons = new ArrayList<>();

    public CountryDisplay() {

    }

    public void GameDisplayInit(JPanel map) throws IOException {
        this.map = map;
        MapLoader mapLoader = new MapLoader();
        int countryNum = mapLoader.countries.size();
        for(int i = 0; i < countryNum; i++) {
            BufferedImage buttonImage = ImageIO.read(new File(mapLoader.getImageFilePath()));
            String buttonName = mapLoader.countries.get(i).getName();
            Country country = mapLoader.countries.get(i);
            CountryButton countryButton = new CountryButton(buttonImage, buttonName, country);
            int x = country.getXLoc();
            int y = country.getYLoc();
            countryButton.setDoubleBounds(x, y, 100, 100);
            map.add(countryButton.getLabel());
            map.add(countryButton.b);
            buttons.add(countryButton);
        }
    }

    public JPanel getMap() {
        return map;
    }

    public ArrayList<CountryButton> getButtons() {
        return buttons;
    }




}
