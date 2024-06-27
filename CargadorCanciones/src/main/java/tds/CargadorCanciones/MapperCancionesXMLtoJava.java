package tds.CargadorCanciones;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import tds.CargadorCanciones.Canciones;

public class MapperCancionesXMLtoJava {

	public static Canciones cargarCanciones(String fichero) throws URISyntaxException {
        JAXBContext jc;
        Canciones canciones = null;

        try {
            System.out.println("Creating JAXBContext");
            jc = JAXBContext.newInstance(Canciones.class);
            System.out.println("Creating Unmarshaller");
            Unmarshaller u = jc.createUnmarshaller();

            // Handling local file paths
            File file = new File(fichero);
            System.out.println("Checking file path: " + file.getAbsolutePath());
            System.out.println("File exists: " + file.exists());

            if (!file.exists()) {
                System.out.println("Trying to load file from classpath");
                URL resourceUrl = MapperCancionesXMLtoJava.class.getClassLoader().getResource(fichero);
                if (resourceUrl == null) {
                    throw new IllegalArgumentException("File not found: " + fichero);
                }
                System.out.println("Resource URL: " + resourceUrl);
                Path path = Paths.get(resourceUrl.toURI());
                System.out.println("Decoded path: " + path.toString());
                file = path.toFile();
            }

            System.out.println("Unmarshalling file: " + file.getAbsolutePath());
            canciones = (Canciones) u.unmarshal(file);

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return canciones;
    }
}
