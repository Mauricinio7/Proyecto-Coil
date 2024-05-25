package coilvic.modelo.pojo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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

public class ReporteColaboraciones {
    
    private Document documento;
    private FileOutputStream fileOutputStream;
    private Font fuenteTitulo = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private Font fuenteNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

    public void crearDocumento() throws FileNotFoundException, DocumentException {
        documento = new Document(PageSize.A4, 36, 36, 36, 36);
        String ruta = System.getProperty("user.home");
        fileOutputStream = new FileOutputStream(ruta + "/Desktop/ReporteColaboraciones.pdf");
        PdfWriter.getInstance(documento, fileOutputStream);
    }

    public void abrirDocumento() {
        documento.open();
    }

    public void agregarTitulo(String texto) throws DocumentException {
        PdfPTable tabla = new PdfPTable(1);
        PdfPCell celda = new PdfPCell(new com.itextpdf.text.Paragraph(texto, fuenteTitulo));
        celda.setColspan(5);
        celda.setBorderColor(BaseColor.WHITE);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
        documento.add(tabla);
    }

    public void agregarParrafo(String texto) throws DocumentException {
        Paragraph parrafo = new Paragraph();
        parrafo.add(new Phrase(texto, fuenteNormal));
        documento.add(parrafo);
    }

    public void agregarSaltoLinea() throws DocumentException {
        Paragraph saltoLinea = new Paragraph();
        saltoLinea.add(new Phrase(Chunk.NEWLINE));
        documento.add(saltoLinea);
    }

    public void agregarTablaColaboraciones() {
        PdfPTable tabla = new PdfPTable(5);
        tabla.addCell("Nombre");
        tabla.addCell("Idioma");
        tabla.addCell("No. Estudiantes Externos");
        tabla.addCell("Profesor UV");
        tabla.addCell("No Personal");
        
    }
}
