import javax.swing.JOptionPane;
import java.text.DecimalFormat;
import java.util.*;
public class Round_Robin
{

    /** METODO PRINCIPAL EN EL CUAL SE CALCULA EL TIEMPO DE ESPERA PROMEDIO **/
    public static void main(String []args){

        //DECLARACION DE VARIABLES
        int entrada,tiempo,ejecucion,q,nuevaRafaga;//VARIABLES
        String tiempos="0";//TIEMPOS DE EJECUCION
        double tmes=0;//TIEMPO DE ESPERA PROMEDIO
        String formato,Salida;
        DecimalFormat decimal=new DecimalFormat("0.00");//FORMATO DE IMPRESION A RESULTADO

        //ARRAYLIST EMPLEADOS
        ArrayList llegadas=new ArrayList();
        ArrayList rafagas=new ArrayList();
        ArrayList procesos=new ArrayList();
        ArrayList copiallegadas=new ArrayList();
        ArrayList copiarafagas=new ArrayList();

        //VALORES PEDIDOS AL USUARIO
        int N=Validacion.leerEntero("DIGITE LA CANTIDAD DE PROCESOS");  
        int Quantum=Validacion.leerEntero("DIGITE EL VALOR DEL QUANTUM");
        int qtdProcesos=N;
        //ARREGLOS EMPLEADOS
        int tiemposFinales[]=tiemposFinales=new int [N];
        int tiemposEjecucion[]=tiemposEjecucion=new int [N];
        int[] arr=new int[N];

        //RELLENO DE ARREGLO DE RAFAGAS DE LOS PROCESOS
        for(int i=0;i<N;i++){
            llegadas.add(0);
            entrada=Validacion.leerEntero("DIGITE LA RAFAGA DE CPU DEL PROCESO "+(i+1));
            rafagas.add(entrada);
            arr[i]=entrada;
        }

        //CLONACION DE ARRAYLIST DE LLEGADAS Y DE RAFAGAS
        copiallegadas=(ArrayList)llegadas.clone();
        copiarafagas=(ArrayList)rafagas.clone();
        tiempo=(int)llegadas.get(0);

        while(qtdProcesos>0){
            for(int i=0;i<N;i++){
                if((int)llegadas.get(i) != -1 && (int)llegadas.get(i) <= tiempo){
                    procesos.add(i);
                    llegadas.set(i, -1);

                }
            }

            if(procesos.isEmpty()){
                tiempo++;
            }else{
                ejecucion=(int)procesos.remove(0);
                q=Quantum;
                while(q>0 && (int)rafagas.get(ejecucion)>0){
                    tiempo++;
                    q--;
                    nuevaRafaga=(int)rafagas.get(ejecucion)-1;
                    rafagas.set(ejecucion,nuevaRafaga);
                }
                if((int)rafagas.get(ejecucion)>0){
                    //verificacion si algun proceso entro durante o tiempo de ejecucion
                    for(int i=0;i<N;i++){
                        if((int)llegadas.get(i) != -1 && (int)llegadas.get(i) <= tiempo){
                            procesos.add(i);
                            llegadas.set(i,-1);
                        }
                    }

                    procesos.add(ejecucion);
                }else{
                    tiemposFinales[ejecucion]=tiempo;
                    qtdProcesos--;
                }
            }
        }

        //CALCULO DEL TIEMPO DE ESPERA PROMEDIO
        for(int i=0;i<N;i++){
            tiemposEjecucion[i]=tiemposFinales[i]-(int)copiallegadas.get(i);
            tiempos+=tiemposEjecucion[i] + " "; 
            tmes += tiemposEjecucion[i]-(int)copiarafagas.get(i);
        }

        tmes=tmes/N;
        System.out.println(" PRUEBA DE EJECUCION  ");

        formato=decimal.format(tmes);
        Salida="TIEMPO DE ESPERA PROMEDIO= "+ formato + "s";
        Salida=Salida.replace(",",",");
        System.out.println(Salida);
        //INVOCACION DEL METODO QUE IMPRIME EL DIAGRAMA DE GANTT
        gantt(Quantum,arr,N);
    }

    /** METODO QUE IMPRIME EL DIAGRAMA DE GANTT **/
    private static void gantt(int quantum, int[] rafagas, int cantidad_procesos){
        String orden="";
        int n=0;
        String numeros="0 ";
        while(tiempos(rafagas)>0){
            for(int i=0; i<cantidad_procesos; i++){
                if(rafagas[i]>0){orden=orden +"P"+(i+1)+" ";
                    if(rafagas[i]>=quantum){ n+=quantum; numeros=numeros + n +" "; rafagas[i]-=quantum;}
                    else {n+=rafagas[i]; numeros=numeros + n +" ";rafagas[i]=0;} }

            }
        }
        System.out.println();
        System.out.println("  DIAGRAMA DE GANTT \n"+orden);
        System.out.println(numeros);
    }

    /** METODO QUE SUMA LAS RAFAGAS DE LOS PROCESOS **/
    private static int tiempos(int[] rafagas){
        int n=0;
        for(int i=0; i<rafagas.length; i++){ if(rafagas[i]<0)rafagas[i]*=-1; n+=rafagas[i];}
        return n;
    }

}
