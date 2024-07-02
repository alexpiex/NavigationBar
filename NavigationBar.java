package it.alexpiex.mie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe per la gestione della barra di navigazione.
 * L'istanza della classe deve essere fatto solo nella home page della web application
 * dopo di che l'oggetto creato si mette in sessione.
 * 
 * @example 
 * esempio di utilizzo:
 * 
 *  NavigationBar navbar = new NavigationBar(true);
 *  navbar.addItem("label_0", "link1.html", "ecco il tuo link");
 *  navbar.addItem("label_1", "link2.html", "ecco il tuo link");
 *  navbar.addItem("label_2", "link3.html", "ecco il tuo link");
 *  navbar.addItem("label_3", "link4.html", "ecco il tuo link");
 * 
 *  int index = navbar.FindByLabel("label_1")
 *		
 *  // questo lo facciamo per non aggiungere di nuovo l'item al refresh della pagina.
 *  if(index >= 0) 
 *      navbar.removeItem(index); // rimuove elemento.
 * 
 *  navbar.removeFrom(2);
 *  navbar.removeTopN(1);
 *  navbar.ShowBar();
 *  
 *  navbar.replaceItem(new String[]{"label_x", "linkX.html", "ecco il tuo link"}, 3);
 *  navbar.ShowBar();
 * 
 * @package Package
 * @author  Alssandro Pietrucci 
 * @version Release 2.3
 */


class ItemLink implements Serializable {
    
    public String Display;
    public String Link;
    public String Label;
    
    public ItemLink(String label, String link, String display) {
        Label = label;
        Link = link;
        Display = display;
        
    }
    
    @Override
    public String toString() {

        return "[Label=" + Label + " | Link=" + Link + " | Display = "+Display+" ]";

    }
    
}

/**
 *
 * @author Alessandro Pietrucci.
 */
public class NavigationBar implements Serializable{
    
   /**
    * Array che contiene gli elementi della barra.
    *
    * @var [ARRAY]
    */
    private List<ItemLink> items;
    
   /**
    * Contatore, numero di elementi presenti nella barra.
    *
    * @var [INT]
    */
    private int numElements = 0;

   /**
    * Indica se l'esecuzione della classe comporta il debug: true|false.
    *
    * @var [BOOL]
    */
    private boolean debug;
    
    private final String FORMATSTR = "<a href='%s' class='navbar' title='%s'>%s</a>&nbsp;&gt;&nbsp;";
    
    public NavigationBar(boolean debug){
        
        this.debug = debug;
        items = new ArrayList<ItemLink>();
    }
    
   /**
    * Restituisce il numero di elementi.
    * 
    * @return [INT] numero di elementi
    */
    public int getNumElements(){
        
        if (this.debug )
	    System.out.println(String.format("numElements %s", numElements));
        
        return numElements;
        
    }
    
   /**
    * Aggiunge un elemento.
    * Spiegazione parametri barra di navigazione: "spiegazione link", "link effettivo", "nome sulla navbar".
    *
    * @param [STRING] $Display spiegazione link compare come tooltip.
    * @param [STRING] $Link link effettivo collegato al $Title.
    * @param [STRING] $Title nome visualizzato sulla barra di navigazione.
    */
    public void addItem (String label, String link, String display){
        
        items.add(new ItemLink(label, link, display));
        numElements++;
        if (this.debug )
	    System.out.println(String.format("addItem %s: ", items.get(items.size()-1).toString()));
 
    }
    
   /**
    * Rimuove l'item individuato dall'indice.
    *
    * @param [INT]  $index indice numerico associato ad ogni singolo item.
    * @return	void
    */
    public void removeItem(int index) throws Exception {
        
        if(index < numElements){
        
            items.remove(index);
            //numElements--;
            numElements = items.size();
        } else {
            throw new Exception("Superato il limite massimo di elementi");
                    
        }
            
    }    
    
   /**
    * Rimuove l'ultimo elemento.
    *
    * @return [ARRAY] Valore dell'ultimo elemento.
    */ 
    public void removeLastItem() {
        
        if(numElements > 0){
            if (this.debug )
                System.out.println(String.format("removeLastItem %s: ", items.get(items.size()-1).toString()));
            
            items.remove(items.size()-1);
            //numElements--;
            numElements = items.size();

        }
    }
    
   /**
    * Sostituisce un elemento della navigation bar.
    *
    * @param [STRING] $Label indice alfanumerico univoco.
    * @param [STRING] $Link link effettivo collegato al $Title.
    * @param [STRING] $Display nome visualizzato sulla barra di navigazione.
    * @param [INT]    $index indice numerico associato all item da sostituire.
    * @return	void
    */
    public void replaceItem(String[] item, int index ) throws Exception {

        if(index < numElements){
        
            ItemLink itemLink = new ItemLink(item[0], item[1], item[2]);

            items.set(index, itemLink);
            //numElements = items.size();
        } else {
            throw new Exception("Superato il limite massimo di elementi");
                    
        }
        
    }

   /**
    * Rimuove dall'indice (compreso) in poi fino alla fine dell'array.
    *
    * @param [INT]  $index indice numerico associato ad ogni singolo item.
    * @return	void
    */
    public void removeFrom(int index) throws Exception {
        
        if(index < numElements){
            items = items.subList(0, index);
            
            /*
            while (index < numElements) {
                    removeItem(index);
            }
            */
            numElements = items.size();
        } else {
            throw new Exception("Superato il limite massimo di elementi");
                    
        }
    }
    
   /**
    * Rimuove n elementi dalla coda.
    *
    * @param [INT] $numbers numero di item da rimuovere.
    * @return	void.
    */
    public void removeTopN(int numbers) throws Exception{
        if(numbers < numElements){
            for(int i=0; i< numbers ; i++){
                if (debug ){
                    System.out.println(String.format("remove element number: ",i));
                }
                removeLastItem();
            }

            numElements = items.size();
        } else {
            throw new Exception("Superato il limite massimo di elementi");
                    
        }
    }
    
    
   /**
    * Rimuove tutti gli elementi.
    *
    * @return void
    */
    public void removeAll(){
       
        if(items.size()>0){
            items.clear();
            numElements = 0;
        }
        
    }
    
   /**
    * Visualizza l'item associato all'index.
    *
    * @param  [INT]    $index indice numerico associato ad ogni singolo item.
    * @param  [STRING] $formatStr modalita della stringa formattata.
    * @return [STRING] stringa formattata dell'item.
    */
    public String showItem(int index){
            
        if (index >= numElements)
                System.out.println("<b>Warning NavigationBar.showItem() indice immesso superiore o uguale al numero massimo di elementi presenti");

        ItemLink item = (ItemLink)items.get(index);
        
        return String.format(FORMATSTR, item.Link, item.Display, item.Display);
    }
    
    
   /**
    * Visualizza tutta la barra di navigazione.
    *
    * @param  [STRING] $formatStr stringa formattata della barra di navigazione.
    * @return [STRING] stringa formattata in HTML, barra di navigazione.
    */
    public String showBar(){
            
        String output ="";

        //Iterator itr = items.iterator();
        //while(itr.hasNext()){
         for(int i=0; i< items.size() ; i++){
            //System.out.println(itr.next());
            //ItemLink item = (ItemLink)itr.next();
            output += showItem(i);
        }

        if (this.debug )
	    System.out.println(String.format("ShowBar %s", output)); 
         
        return output;

    }
    
   /**
    * Ricerca elemento per Label .
    *
    * @param  [STRING] label indica l'item da ricercare.
    * @return [INT]    Restituisce il numero dell'indice altrimenti un valore negativo.
    */
    public int findByLabel(String label){
        
       boolean found = false;
       int index = -1;
        
       if(numElements > 0){
            for(int i=0; i< items.size() ; i++){
                
                ItemLink item = (ItemLink)items.get(i);
                if(label.equals(item.Label)){
                   found = true;
                   index = i;
                   break;
                }
            }
           
        }

        if (this.debug )
	    System.out.println(String.format("FindByLabel %s, index: %s", found, index));
       
        return index;
    }


}