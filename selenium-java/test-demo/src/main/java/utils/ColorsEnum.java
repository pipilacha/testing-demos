package utils;

import org.openqa.selenium.support.Color;

public enum ColorsEnum {

    ERROR_RED(new Color(226, 35, 26, 0));

    private final Color color;

    ColorsEnum(Color color) {
        this.color = color;
    }

    public String getColorAsRGB(){
        return color.asRgb();
    }
}
