package br.com.rodrigorech;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TabelasUtil {
    

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

        return terminais;
    }

    private String encontraNaoTerminal(String valor) {
        int indexFimDoNaoTerminal = valor.lastIndexOf(">");
        return valor.substring(0, indexFimDoNaoTerminal + 1);
    }

    public void imprimeTabela(HashMap<String, List<String>> tabela, List<String> estadosFinais, 
    String[] terminais, List<String> naoTerminais) {

        int linhasTabelaFormatada = naoTerminais.size() + 1;
        int colunasTabelaFormatada = terminais.length + 1;
        String[][] tabelaFormatada = new String[linhasTabelaFormatada][colunasTabelaFormatada]; 
        tabelaFormatada[0][0] = "estados";
        int coluna = 1;
        for(String terminal : terminais){
            tabelaFormatada[0][coluna] = terminal;
            coluna++;
        }

        Set<String> tabelaKeySet = tabela.keySet();

        for(int linha = 1; linha < linhasTabelaFormatada; linha++) {
            String estadoAtual = naoTerminais.get(linha-1);
            tabelaFormatada[linha][0] = estadoAtual;

            coluna = 1;
            for(String terminal : terminais) {
                for(String chave : tabelaKeySet) {
                    if(chave.contains(estadoAtual) && chave.contains(terminal)) {
                        List<String> producoes = tabela.get(chave);
                        String producoesConcatenadas = "";
                        for(String producao : producoes){
                            if(producoesConcatenadas.length() == 0) {
                                producoesConcatenadas = producao;
                            }
                            else {
                                producoesConcatenadas+= ", " + producao;
                            }
                        }
                        tabelaFormatada[linha][coluna] = producoesConcatenadas;
                        break;
                    }
                }
                if(tabelaFormatada[linha][coluna] == null) {
                    String estado = tabelaFormatada[linha][0];
                    if(estadosFinais.contains(estado)) {
                        tabelaFormatada[linha][coluna] = "ε";
                    }
                    else {
                        tabelaFormatada[linha][coluna] = "<X>";
                    }
                }
                coluna++;
            }

        }

        //imprimir:
        for(int linha = 0; linha < linhasTabelaFormatada; linha++){
            for(coluna = 0; coluna < colunasTabelaFormatada; coluna++){
                if(coluna == 0){
                    System.out.print("|");
                }
                if(coluna == 0 && estadosFinais.contains(tabelaFormatada[linha][coluna])) {
                    System.out.printf("%15s |", tabelaFormatada[linha][coluna] + "*");
                }
                else {
                    System.out.printf("%15s |",tabelaFormatada[linha][coluna]);
                }
            }
            System.out.println("");
        }

        //printa estado de erro <X>
        for(coluna = 0; coluna < colunasTabelaFormatada; coluna++){
            if(coluna == 0){
                System.out.printf("|%15s |", "<X>*");
            }
            else{
                System.out.printf("%15s |", "ε");
            }
        }
        System.out.println("");
        System.out.println("");

    }
}
