package exercicio.pratico02;

import java.io.*;

public class ImportGraph <V,E,VP,EP> {
	
	// Leitura de Arquivo Texto retornando um StringReader que � entrada para a importa��o de grafos
	
	public static StringReader readFile (String filename) {
        StringBuilder contentBuilder = new StringBuilder();
	    try (BufferedReader br = new BufferedReader(new FileReader(filename)))
	    {
	        String sCurrentLine;
	        while ((sCurrentLine = br.readLine()) != null)
	        {
	            contentBuilder.append(sCurrentLine).append("\n");
	        }
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    StringReader readergml = new StringReader(contentBuilder.toString());
	    return readergml;
	}
	
}
