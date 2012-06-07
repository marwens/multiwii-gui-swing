package eu.kprod.gui;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartPanel;

import eu.kprod.ds.DSLoadableException;
import eu.kprod.ds.MwDataSource;
import eu.kprod.ds.MwDataSourceImpl;
import eu.kprod.ds.MwSensorClass;
import eu.kprod.utils.LogLoader;


public class LogViewerFrame extends JFrame {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger
            .getLogger(LogViewerFrame.class);

    private final void frameSetDefaultPosition(){
        // TODO , get last frame position
        setPreferredSize(new java.awt.Dimension(500, 270));
        setSize(new java.awt.Dimension(500, 270));
        setVisible(true);
        pack();
    }

    
    public LogViewerFrame(String name,MwDataSource ds, Class<? extends MwSensorClass> sclass) {
        // TODO Auto-generated constructor stub
        super(name);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


        MwChartPanel chartTrendPanel = MwChartFactory.createChart(ds.getDS(sclass));
        ds.addListener(sclass, chartTrendPanel);
        getContentPane().add(chartTrendPanel);
        frameSetDefaultPosition();
    }
    
    public LogViewerFrame(String name, MwDataSource mwDataSource) {
        // TODO Auto-generated constructor stub
        super(name);
        // when loading a file, we want to dipose the frame after usage
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        MwDataSource ds;
        try {
            ds = new LogLoader().getDataSourceContent(name);
        } catch (DSLoadableException e) {
            LOGGER.error("Can not open log file : " + name);
            e.printStackTrace();
            //TODO get datasource impl
            ds = new MwDataSourceImpl();
        }
        ChartPanel chartTrendPanel = MwChartFactory.createChart(ds.getDS(MwSensorClass.class));
        chartTrendPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        getContentPane().add(chartTrendPanel);
        frameSetDefaultPosition();
    }

}