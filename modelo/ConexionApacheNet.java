package coilvic.modelo;

import coilvic.utilidades.Constantes;
import java.io.IOException;
import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.time.TimeTCPClient;


public class ConexionApacheNet {
    public static LocalDateTime obtenerFechaHoraServidorNTP(String servidorNTP) {
        try {
            NTPUDPClient clienteNTP = new NTPUDPClient();
            clienteNTP.setDefaultTimeout(Constantes.TIEMPO_ESPERA_SERVIDOR); 
            clienteNTP.open();
            InetAddress direccionServidor = InetAddress.getByName(servidorNTP);
            TimeInfo infoTiempo = clienteNTP.getTime(direccionServidor);
            infoTiempo.computeDetails();
            long tiempoNTP = infoTiempo.getMessage().getTransmitTimeStamp().getTime();
            System.out.println("Fecha obtenida del ntp"); 
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(tiempoNTP), ZoneId.systemDefault());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LocalDateTime fecha = LocalDateTime.now();
        return fecha;
    }
}
