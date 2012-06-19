/*  This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program;
 */

package eu.kprod.ds.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;

import eu.kprod.ds.DSLoadable;
import eu.kprod.ds.DSLoadableException;
import eu.kprod.ds.MwDataSource;
import eu.kprod.ds.MwDataSourceImpl;




/**
 * load a DataSource from a formated Log file.
 * @author treym
 *
 */
public class LogLoader implements DSLoadable {

    public LogLoader() {
    }

    /**
     * charge un fichier ligne par ligne
     *
     * @param filePath
     *            le chemin du ficher à lire
     * @return le contenu du fichier,une liste vide pour une fichier vide, null
     *         en cas d'erreur
     * @throws DSLoadableException 
     */
    public final MwDataSource getDataSourceContent(final String filePath) throws DSLoadableException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:SS");

        MwDataSource content = new MwDataSourceImpl();

        try {
            BufferedReader buff = new BufferedReader(new FileReader(filePath));

            try {
                String line;
                while ((line = buff.readLine()) != null) {
                    try {
                        String[] content1 = parse(line);

                        // System.err.println("content1[0] = "+content1[0]);
                        // System.err.println("content1[1] = "+content1[1]);
                        String date = filePath
                                .substring(filePath.length() - 10)
                                + " "
                                + line.substring(0, line.indexOf(":") + 6);
                        // System.err.println("date = "+date);
                        content.put(sdf.parse(date), content1[0],
                                Double.valueOf(content1[1]),null);
                    } catch (Exception e) {
                         e.printStackTrace();
                         // failed to load
                    }
                }
            } finally {
                buff.close();
            }
        } catch (IOException ioe) {
            throw new DSLoadableException(ioe);
        }
        return content;
    }

    private static String[] parse(final String line) {
        // System.err.println("line = "+line);
        String[] content = new String[3];
        // TODO Auto-generated method stub
        if (line.contains("SENSOR") && line.contains(":")) {
            String s = line.substring(line.lastIndexOf('R') + 1);
            int pos = s.lastIndexOf(':');
            content[0] = s.substring(0, pos - 1);
            content[1] = s.substring(pos + 1);

            // System.err.println(" -> content[0] = "+content[0]);
            // System.err.println(" -> content[1] = "+content[1]);
            //
        }

        return content;
    }
}
