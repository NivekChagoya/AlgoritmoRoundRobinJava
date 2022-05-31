
import javax.swing.*;
import java.util.*;
import java.lang.Exception;

public class Validacion
{

    public static String leerString(String c){
        return JOptionPane.showInputDialog(c);
    }

    public static void Mensaje(String c){
        JOptionPane.showMessageDialog(null,c);
    }

    public static int leerEntero(String c){
        int entero=0;
        boolean correcto=false;
        while(!correcto){
            try{
                String cad2=leerString(c);
                if(cad2!=null){
                    entero=Integer.parseInt(cad2);
                    if(entero<0){
                        throw new NumberFormatException();
                    }
                }
                correcto=true;
            }catch(NumberFormatException Excepcion){
                Mensaje("VALOR INGRESADO ES INVALIDO");
            }
        }
        return entero;
    }

}

