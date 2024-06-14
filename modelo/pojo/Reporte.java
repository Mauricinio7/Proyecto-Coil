package coilvic.modelo.pojo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import coilvic.utilidades.Utils;
import javafx.scene.control.Alert.AlertType;
import coilvic.modelo.dao.ColaboracionDAO;
import coilvic.modelo.dao.ProfesorUvDAO;
import coilvic.utilidades.Constantes;

public class Reporte {
    
    private ArrayList<Colaboracion> colaboraciones = new ArrayList<>();
    private ArrayList<ProfesorUv> profesoresUv = new ArrayList<>();
    private Document documento;
    private FileOutputStream fileOutputStream;
    private Font fuenteTitulo = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private Font fuenteNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

    public Reporte(String ruta, String periodo, String tipo) {
        if (tipo.equals("Profesores")) {
            generarPdfProfesores(ruta, periodo);
        } else if (tipo.equals("Colaboraciones")) {
            generarPdfColaboraciones(ruta, periodo);
        }
    }

    private void crearDocumento(String ruta, String nombreArchivo) throws FileNotFoundException, DocumentException {
        documento = new Document(PageSize.A4, 36, 36, 36, 36);
        fileOutputStream = new FileOutputStream(ruta + nombreArchivo);
        PdfWriter.getInstance(documento, fileOutputStream);
    }

    private void abrirDocumento() {
        documento.open();
    }

    private void agregarTitulo(String texto) throws DocumentException {
        PdfPTable tabla = new PdfPTable(1);
        PdfPCell celda = new PdfPCell(new com.itextpdf.text.Paragraph(texto, fuenteTitulo));
        celda.setColspan(5);
        celda.setBorderColor(BaseColor.WHITE);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        documento.add(tabla);
    }

    private void agregarParrafo(String texto) throws DocumentException {
        Paragraph parrafo = new Paragraph();
        parrafo.add(new Phrase(texto, fuenteNormal));
        documento.add(parrafo);
    }

    private void agregarSaltoLinea() throws DocumentException {
        Paragraph saltoLinea = new Paragraph();
        saltoLinea.add(new Phrase(Chunk.NEWLINE));
        documento.add(saltoLinea);
    }

    private void agregarTablaColaboraciones(String periodo) throws DocumentException {
        PdfPTable tabla = new PdfPTable(5);
        tabla.addCell("Nombre");
        tabla.addCell("Idioma");
        tabla.addCell("No. Estudiantes Externos");
        tabla.addCell("Profesor UV");
        tabla.addCell("No Personal");
        obtenerColaboraciones(periodo);
        for (Colaboracion colaboracion : colaboraciones) {
            ProfesorUv profesorUv = obtenerProfesorUv(colaboracion.getIdProfesorUV());
            tabla.addCell(colaboracion.getNombre());
            tabla.addCell(colaboracion.getIdioma());
            tabla.addCell(String.valueOf(colaboracion.getNoEstudiantesExternos()));
            tabla.addCell(profesorUv.getNombre());
            tabla.addCell(profesorUv.getNoPersonal());
        }
        documento.add(tabla);
        documento.close();
    }

    private void obtenerColaboraciones(String periodo) {
        HashMap<String, Object> resultado = ColaboracionDAO.obtenerColaboracionesConcluidasPorPeriodo(periodo);
        if (!(boolean) resultado.get(Constantes.KEY_ERROR)) {
            colaboraciones = (ArrayList<Colaboracion>) resultado.get("colaboraciones");
        } else {
            Utils.mostrarAlertaSimple("", ""+resultado.get(Constantes.KEY_MENSAJE), AlertType.ERROR);
        }
    }    

    private ProfesorUv obtenerProfesorUv(Integer idProfesorUv) {
        ProfesorUv profesorUv = null;
        HashMap<String, Object> respuesta = ProfesorUvDAO.obtenerProfesorUvPorId(idProfesorUv);
        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            profesorUv = new ProfesorUv();
            profesorUv = (ProfesorUv) respuesta.get("Profesor");
        }
        return profesorUv;
    }

    private void agregarTablaProfesores(String periodo) throws DocumentException {
        PdfPTable tabla = new PdfPTable(4);
        tabla.addCell("Nombre");
        tabla.addCell("Correo");
        tabla.addCell("No Personal");
        tabla.addCell("Región");
        obtenerProfesores(periodo);
        for (ProfesorUv profesorUv : profesoresUv) {
            tabla.addCell(profesorUv.getNombre());
            tabla.addCell(profesorUv.getCorreo());
            tabla.addCell(profesorUv.getNoPersonal());
            tabla.addCell(profesorUv.getNombreRegion());
        }
        documento.add(tabla);
        documento.close();
    }

    private void obtenerProfesores(String periodo) {
        HashMap<String, Object> resultado = ProfesorUvDAO.obtenerProfesoresPeriodoConcluido(periodo);
        if (!(boolean) resultado.get(Constantes.KEY_ERROR)) {
            profesoresUv = (ArrayList<ProfesorUv>) resultado.get("profesoresuv");
        } else {
            Utils.mostrarAlertaSimple("", ""+resultado.get(Constantes.KEY_MENSAJE), AlertType.ERROR);
        }
    }

    private void generarPdfColaboraciones(String ruta, String periodo) {
        try {
            crearDocumento(ruta, "/ReporteColaboraciones.pdf");
            abrirDocumento();
            agregarTitulo("Reporte de Colaboraciones");
            agregarSaltoLinea();
            agregarSaltoLinea();
            agregarParrafo("Periodo: " + periodo);
            agregarSaltoLinea();
            agregarSaltoLinea();
            agregarTablaColaboraciones(periodo);
        } catch (DocumentException | FileNotFoundException ex) {
            Utils.mostrarAlertaSimple("", "No se han podido cargar los datos", AlertType.ERROR);
        }
    }

    private void generarPdfProfesores(String ruta, String periodo) {
        try {
            crearDocumento(ruta, "/ReporteProfesores.pdf");
            abrirDocumento();
            agregarTitulo("Reporte de Profesores");
            agregarSaltoLinea();
            agregarSaltoLinea();
            agregarParrafo("Periodo: " + periodo);
            agregarSaltoLinea();
            agregarSaltoLinea();
            agregarTablaProfesores(periodo);
        } catch (DocumentException | FileNotFoundException ex) {
            Utils.mostrarAlertaSimple("", "No se han podido cargar los datos", AlertType.ERROR);
        }
    }
}
