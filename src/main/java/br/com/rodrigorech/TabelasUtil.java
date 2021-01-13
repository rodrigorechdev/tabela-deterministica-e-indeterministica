package br.com.rodrigorech;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TabelasUtil {
    
    static String[] naoTerminais;
    static String[] terminais;

    /**
     * Encontra os não terminais de uma gramática recebendo ela no formato de "conteúdo separado" feito pelo método separaConteudo
     * @param conteudoSeparado
     * @return
     */
    public String[] encontraNaoTerminais(String[][] conteudoSeparado) {
        String conteudoJunto = "";

        int quantidadeLinhas = conteudoSeparado.length;

        for(int i = 0; i<quantidadeLinhas; i++) {//transforma String[][] em String
            for(String conteudoLinha : conteudoSeparado[i]) {
                conteudoJunto+= conteudoLinha;
            }
        }        
        
        String[] terminais = this.encontraTerminais(conteudoSeparado);
        conteudoJunto = this.removeCaracteresDeUmaString(terminais, conteudoJunto);
        conteudoJunto = this.removeCaracteresDuplicados(conteudoJunto);
        conteudoJunto = conteudoJunto.replaceAll("ε","");

        String[] naoTerminais = conteudoJunto.split("");
        TabelasUtil.naoTerminais = naoTerminais;
        return naoTerminais;
    }

    /**
     * Recebe uma string, procura caracteres repetidos e deleta as repetições, retornando uma String
     * onde cada caractere aparece no máximo uma vez.
     * @param valor
     * @return
     */
    public String removeCaracteresDuplicados(String valor){
        StringBuilder sb = new StringBuilder();
        valor.chars().distinct().forEach(c -> sb.append((char) c));//remove caracteres duplicados
        return sb.toString();
    }

    /**
     * Recebe caracteres e remove-os da string recebida por paramêtros
     * @param caracteres
     * @param string
     * @return
     */
    public String removeCaracteresDeUmaString(String[] caracteres, String string){
        for(String caractere : caracteres) {//remove os terminais da String
            string = string.replaceAll(caractere,"");
        }
        return string;
    }

        /**
     * Encontra os terminais de uma gramática recebendo ela no formato de "conteúdo separado" feito pelo método separaConteudo
     * @param conteudoSeparado
     * @return
     */
    public String[] encontraTerminais(String[][] conteudoSeparado) {
        String[] terminais = new String[conteudoSeparado.length];
        
        for(int i = 0; i<conteudoSeparado.length; i++) {
            terminais[i] = conteudoSeparado[i][0];
        }

        TabelasUtil.terminais = terminais;
        return terminais;
    }

    private String encontraNaoTerminal(String valor){
        int indexFimDoNaoTerminal = valor.lastIndexOf(">");
        return valor.substring(0, indexFimDoNaoTerminal + 1);
    }

    // public String[] imprimeTabela(HashMap<String, List<String>> tabela, List<String> estadosFinais){
    //     String[] terminais = TabelasUtil.terminais;
    //     String[] naoTerminais = TabelasUtil.naoTerminais;
        
    //     Set<String> tabelaKeySet = tabela.keySet();
    // }
}
