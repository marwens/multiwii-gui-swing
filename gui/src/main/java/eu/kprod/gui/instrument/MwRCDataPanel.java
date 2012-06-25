/**
 * @author treym (Trey Marc) Jun 22 2012
 *
 */
package eu.kprod.gui.instrument;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.net.URL;

import eu.kprod.gui.Ress;
import eu.kprod.gui.comp.StyleColor;
import eu.kprod.msp.MSP;

public class MwRCDataPanel extends MwInstrumentJPanel {

    private GeneralPath bar;

    double[] RCdata = new double[8];

    {
        if (imageRCdataeBg == null) {

            URL url = this.getClass().getResource(Ress.imgRCData);

            try {
                imageRCdataeBg = Toolkit.getDefaultToolkit().getImage(url);

            } catch (Exception e) {
                System.out.println("resources not found!!!");
            }
        }
    }
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static Image imageRCdataeBg;

    public MwRCDataPanel(Color c) {
        super(new Dimension(200, 150));

        setBackground(c);

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(g2d);

        drawBarValue(g2d);

    }

    private void drawBarValue(Graphics2D g2d) {
        final int startx = 41;
        int starty = 16;

        // bar w/h
        final int xx = 118;
        final int yy = 7;

        g2d.setStroke(new BasicStroke(1));
        g2d.setPaint(StyleColor.greenBar);
        for (int i = 0; i < RCdata.length; i++) {

            int barvalue = new Double((RCdata[i]/2000)*xx).intValue();
            bar = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
            bar.moveTo(startx, starty);
            bar.lineTo(startx + barvalue, starty);
            bar.lineTo(startx + barvalue, starty + yy);
            bar.lineTo(startx, starty + yy);
            bar.closePath();

            g2d.fill(bar);
            starty+=yy+8;

        }

    }

    private void drawBackground(Graphics2D g2d) {

        // int w = 200;

        BufferedImage bi = new BufferedImage(maxRadiusX, maxRadiusY,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.drawImage(imageRCdataeBg, 0, 0, null);

        // float[] scales = { 1.0f ,1.0f,1.0f,0.8f};
        // float[] offsets = new float[4];
        // RescaleOp rop = new RescaleOp(scales, offsets, null);

        g2d.drawImage(bi, null, 0, 0);

    }

    @Override
    public void readNewValue(String name, Double value) {

        if (MSP.IDthrottle.equals(name)) {
            RCdata[0] = value;
        } else if (MSP.IDpitch.equals(name)) {
            RCdata[1] = value;
        } else if (MSP.IDroll.equals(name)) {
            RCdata[2] = value;
        } else if (MSP.IDyaw.equals(name)) {
            RCdata[3] = value;
        } else if (MSP.IDaux1.equals(name)) {
            RCdata[4] = value;
        } else if (MSP.IDaux2.equals(name)) {
            RCdata[5] = value;
        } else if (MSP.IDaux3.equals(name)) {
            RCdata[6] = value;
        } else if (MSP.IDaux4.equals(name)) {
            RCdata[7] = value;
        }
        repaint();
    }

    @Override
    void resetAllValuesImpl() {

    }

}