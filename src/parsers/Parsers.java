package parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.jdom2.Attribute;
  
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


/*
 * <p>Parser para leer XML y mantenerlos en memoria dinamica</p>
 * @author Adrian Ibarra Fragoso, 311261871.
 */
public class Parsers {

    
    /* Imprime un mensaje de cómo usar el programa. */
    private static void uso() {
        System.out.println("Uso: java -jar Prasers.jar " +
                           "[ --jdom | --stax ]");
        System.exit(0);
    }
    /**
     * Metodo recursivo para iterar los elementos del Jdom.
     *
     * @param currentElement Elemento current
     * @param cont Contador para saber la profundidad de la iteracion
     */
    
    private static void recursionJDOM(Element currentElement, int cont)
    {
       
        List<Element> currentChildren = currentElement.getChildren();
        for (Element innerChild : currentChildren) {
            recursionJDOM(innerChild, cont+1);
            
            String  quick = "";
            String quick2 = "";
            for(int c =0; c<cont; c++)
            {
                quick+=" ***** ";
                quick2+="       ";
            }
            
            System.out.println(quick + innerChild.getName()+ "   "+ innerChild.getAttribute("nombre").getValue()+ " Tamaño: "+innerChild.getAttribute("tam").getValue());
            
                   
            //Aqui vamos a poner un if, preguntar si el archivo es de tipo texto.
            //De ser asi, imprimimos sus letras.
            //Para buscar, tenemos que entender os attrubotos de los elementos.
            
            Attribute atr = innerChild.getAttribute("tipo");
            String valor = "";
            if(atr!=null)
                valor =  atr.getValue();
            
            switch (valor) {
                case ".txt":
                    System.out.println(quick2 +"20 Characteres: "+innerChild.getTextNormalize());

                    break;
                case ".png":
                    System.out.println(quick2 + "Imagen");
                    break;
                case ".pdf":
                    System.out.println(quick2 + "Pdf");
                    break;
            }
        }
        
    }
    /**
     * Metedo que va iniciar el proceso de Jdom
     * @throws org.jdom2.JDOMException
     * @throws java.io.IOException
     */
    private static void useJDOM() throws JDOMException,IOException {

        
        
        SAXBuilder jdomBuilder = new SAXBuilder();
        
        File inputFile = new File("cache.xml");
        Document jdomDocument = jdomBuilder.build(inputFile);
        

        Element firstElement = jdomDocument.getRootElement();
        recursionJDOM(firstElement, 0);

        
    }
    
    public static void useStAX() throws XMLStreamException, FileNotFoundException{
        
//        System.out.println("Me pones 12 :) ?");
        XMLInputFactory entrada = XMLInputFactory.newInstance();
        
        
        XMLStreamReader lector;
        

        FileInputStream fs = new FileInputStream(new File("cache.xml"));
        lector = entrada.createXMLStreamReader(fs);
        
        System.out.println(lector.toString() +" asi es");
        String trasmostrador; 
        while(lector.hasNext()) {
            switch(lector.next()) {
                case XMLStreamConstants.START_ELEMENT :
                    
                        trasmostrador = lector.getLocalName();
                         System.out.println(trasmostrador);
                    break;
                    
                case XMLStreamConstants.CHARACTERS :
                    System.out.println(lector.getText().trim());
                    break;
                        
                    
         
            }
        

        }
    }
    /**
     * Metodo que te permite decidir los argumentos y la version del parser
     * @param args basic arguemnts from java 
     * @throws java.io.FileNotFoundException In case of no such file
     */
    public static void main(String[] args) throws FileNotFoundException {
        //Vamos a revisar si la entrada de los argumentos es valida.
        if (args.length < 1 || args.length > 1)
            uso();
        
        String arg = args[0];

        switch (arg) {
            case "--jdom":
                try{
                    useJDOM();
                    
                }
                catch(JDOMException | IOException e){
                }   
                break;
            case "--stax":
                try{
//                    
                    
                    useStAX();
                }
                catch(XMLStreamException e){
                              System.out.println(e);
                    
                    
                }   
               
                break;
            default:
                uso();
                System.exit(0);
        }
    }
}


