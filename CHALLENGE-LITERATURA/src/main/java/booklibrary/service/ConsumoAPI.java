package service;

import org.json.JSONObject;
import java.io.*;
import java.net.*;

public class ConsumoAPI {

    // Método para obtener los datos de la API de Gutendex
    public JSONObject obtenerDatosDesdeAPI(String query) {
        HttpURLConnection conn = null;
        try {
            // Construir la URL con la búsqueda
            URL url = new URL("https://gutendex.com/books?search=" + URLEncoder.encode(query, "UTF-8"));
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);  // Aumentar el tiempo de espera de conexión a 10 segundos
            conn.setReadTimeout(10000);     // Aumentar el tiempo de espera de lectura a 10 segundos

            // Verificar el código de respuesta HTTP
            int status = conn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                System.out.println("Error: Código de respuesta HTTP " + status);
                return null;  // Si la respuesta no es 200 OK, retornar null
            }

            // Leer la respuesta de la API
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Convertir la respuesta a JSON
            return new JSONObject(response.toString()).optJSONArray("results").getJSONObject(0);

        } catch (Exception e) {
            System.out.println("Error en la conexión o procesando la respuesta: " + e.getMessage());
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}




