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
            NTPUDPClient clienteNTP = new NTPUDPClient();
            clienteNTP.setDefaultTimeout(60000); 
            clienteNTP.open();
            InetAddress direccionServidor = InetAddress.getByName(servidorNTP);
            TimeInfo infoTiempo = clienteNTP.getTime(direccionServidor);
            infoTiempo.computeDetails();
            long tiempoNTP = infoTiempo.getMessage().getTransmitTimeStamp().getTime(); 
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(tiempoNTP), ZoneId.systemDefault());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
