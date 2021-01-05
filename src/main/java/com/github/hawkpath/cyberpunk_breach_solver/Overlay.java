package com.github.hawkpath.cyberpunk_breach_solver;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.WinDef.HWND;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Overlay extends JFrame {

  public Overlay() {
    super("Cyberpunk Breach Solver");

    DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getDefaultScreenDevice().getDisplayMode();
    int refreshRate = display.getRefreshRate();
    int width = display.getWidth();
    int height = display.getHeight();

    setSize(new Dimension(width, height));
    setAlwaysOnTop(true);
    setUndecorated(true);
    setBackground(new Color(0,0,0,0));

    add(new OverlayComponent());
  }

  @Override
  public void setVisible(boolean b) {
    super.setVisible(b);
    setTransparent(this);
  }

  /**
   * Source: https://stackoverflow.com/a/28772306
   */
  private static void setTransparent(Component w) {
    WinDef.HWND hwnd = getHWnd(w);
    int wl = User32.INSTANCE.GetWindowLong(hwnd, WinUser.GWL_EXSTYLE);
    wl = wl | WinUser.WS_EX_LAYERED | WinUser.WS_EX_TRANSPARENT;
    User32.INSTANCE.SetWindowLong(hwnd, WinUser.GWL_EXSTYLE, wl);
  }

  /**
   * Get the window handle from the OS
   */
  private static HWND getHWnd(Component w) {
    HWND hwnd = new HWND();
    hwnd.setPointer(Native.getComponentPointer(w));
    return hwnd;
  }

}

class OverlayComponent extends JComponent {

  static final Color lineColor = new Color(0xff0000);
  private GridNode[] solution = null;

  public void setSolution(GridNode[] solution) {
    this.solution = solution;
  }

  @Override
  public void paintComponent(Graphics g0) {
    super.paintComponent(g0);
    // if (solution == null)
    //   return;

    Graphics2D g = (Graphics2D) g0.create();
    g.setRenderingHint(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON
    );
    g.setColor(lineColor);
    g.setStroke(new BasicStroke(
        10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND
    ));

    g.draw(new Line2D.Float(500, 500, 500, 1000));
    g.draw(new Line2D.Float(500, 1000, 1000, 500));
    g.dispose();
  }

}