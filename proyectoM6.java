/*
 * proyectoM6.java
 * 
 * Copyright 2018 ERIC <ERIC@DESKTOP-5T1N085>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * Este programa transforma datos de un fichero csv aen un fichero xml.
 * 
 * Fet per Eric Visier Sánchez
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;



public class proyectoM6 {

	public static void iniciarArchivoXml(BufferedWriter xml, PrintWriter logpw, Date date) {
		try {
			xml.write("<doc>\n");
		} catch (Exception e) {
			logpw.println(date.toString() + " Error al iniciar el fichero xml");
		}
	}

	public static void rellenarCampos(BufferedReader csv, BufferedWriter xml, PrintWriter logpw, Date date) {
		try {
			String linea;
			String linea2;
			int a = 1;

			while ((linea = csv.readLine()) != null) {
				xml.write("\t<elem nr=\"" + a + "\">\n");

				String[] descDato = linea.split(",");

				BufferedReader txt = new BufferedReader ( new FileReader    ("C:\\Users\\ERIC\\Desktop\\proyecto.txt"));

				for(int indice = 0; indice < descDato.length; indice++) {

					linea2 = txt.readLine();
					if(linea2 == null) {
						linea2 = "altre";
					}
					xml.write("		<" + linea2 + ">");
					xml.write(descDato[indice].trim());
					xml.write("</" + linea2 + ">\n");
				}

				xml.write("	</elem>\n");
				txt.close();
				a = a +1;
			}

		} catch (Exception e) {
			logpw.println(date.toString() + " Error al leer el fichero txt y rellenar los campos del fichero xml.");
		}
	}



	public static void main(String[] args) throws IOException {
		int contador = 0;
		File[] archivos;
		FileWriter log = null;
		PrintWriter logpw = null;
		Date date = new Date();

		try{
			log = new FileWriter("C:\\Users\\ERIC\\Desktop\\error.log",true);
			logpw = new PrintWriter(log);

			logpw.println(date.toString() + " Log iniciado.");

		} catch(IOException e){
			System.out.print("Error al abrir el archivo log.");
		}


		File csv = new File("C:\\Users\\ERIC\\Desktop\\NuevaCarpeta4");
		archivos = csv.listFiles();
		BufferedReader ficherocsv;

		for(File archivo : archivos) {
			if(archivo.getName().endsWith(".csv")) {
				contador++;
				ficherocsv = new BufferedReader ( new FileReader    (archivo));


				File fichero = new File ("C:\\Users\\ERIC\\Desktop\\NuevaCarpeta4\\proyecto"+contador+".xml");  // declaración fichero

				FileWriter fw = new FileWriter(fichero);
				BufferedWriter bw = new BufferedWriter(fw);

				iniciarArchivoXml(bw, logpw, date);
				rellenarCampos(ficherocsv, bw, logpw, date);
				logpw.println(date.toString() + " Log cerrado.");


				if (bw != null)
					bw.close();
				log.close();

				if (fw != null)
					fw.close();
			}
		}

	}
}
