package com.smtown.sigran0.horseraceapp.tools;

/**
 * Created by jungsungwoo on 11/6/17.
 */

public class ScreenConfig {

    private int m_screen_width;
    private int m_screen_height;
    private int m_virtual_width;
    private int m_virtual_height;

    public ScreenConfig(int screenWidth, int screenHeight) {
        m_screen_width = screenWidth;
        m_screen_height = screenHeight;
    }

    public void setSize(int width, int height) {
        m_virtual_width = width;
        m_virtual_height = height;
    }

    public int getX(int x) {
        return (int) (x * m_screen_width / m_virtual_width);
    }

    public int getY(int y) {
        return (int) (y * m_screen_height / m_virtual_height);
    }
}
