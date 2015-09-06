/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaventas;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;
/**
 *
 * @author Equipo
 */
public class Limitador extends PlainDocument {
    //Declarando que se va a manipular (Text Field)
    private JTextField editor;
    private int num;
    //Atributos constructores
    public Limitador(JTextField editor, int num) {
        this.editor = editor;
        this.num = num;
    }
    //Metodo que comprueba que no se sobrepasen los limites por definir (cant. caracteres)
  public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException{
      if((editor.getText().length()+arg1.length()) > this.num){
          return;
      }super.insertString(arg0, arg1, arg2);
  }
}
