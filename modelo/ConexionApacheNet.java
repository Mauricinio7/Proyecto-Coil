package coilvic.modelo;

import java.io.IOException;
import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.time.TimeTCPClient;

import coilvic.utilidades.Constantes;

public class ConexionApacheNet {
    public static LocalDateTime obtenerFechaHoraServidorNTP(String servidorNTP) {
        try {
            TimeTCPClient client = new TimeTCPClient();
            client.setDefaultTimeout(Constantes.TIEMPO_ESPERA_SERVIDOR);
            client.connect(servidorNTP);
            long tiempoMillis = client.getDate().getTime();
            client.disconnect();
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(tiempoMillis), ZoneId.systemDefault());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
