package com.todc.ddengine.util;


import java.awt.Color;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Colors {

    public static Color fromHex(String hexstring) {
        return Color.decode(hexstring);
    }

}
