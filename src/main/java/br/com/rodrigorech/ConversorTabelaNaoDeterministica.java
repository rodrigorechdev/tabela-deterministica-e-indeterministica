package br.com.rodrigorech;

import java.util.ArrayList;
import java.util.List;

public class ConversorTabelaNaoDeterministica {
    
    /**
     * Recebe conteúdo escrito no arquivo entrada.txt e converte para uma tabela de linguagem não dinâmica. 
     */
    public Tabelas criaTabelaNaoDinamica(String[] conteudo) {
        String[][] conteudoSeparado = separaConteudo(conteudo);
        return this.ocupaTabelaNaoDinamica(conteudoSeparado);
    }

    /**
     * Separa o conteúdo recebido por paramêtro, cada elemento do conteúdo será dívidido, essa divisão 
     * ocorre se existe o elemento '::=' ou '|' no código. Sendo assim ele separa cada produção de uma linha 
     * e o primeiro elemento do array resultante será o estado, que vem antes do '::='
     * @param conteudo
     * @return
     */
    private String[][] separaConteudo(String[] conteudo) {
        String[][] conteudoSeparado = new String[conteudo.length][];
        int i = 0;
        for(String conteudoPorLinha : conteudo){
            if(conteudoPorLinha.contains("::=")) {
                conteudoSeparado[i] = conteudoPorLinha.split("[:][:][=]|\\|");
                i++;
            }
            else{
                //não tem ::=, então é uma palavra (token)
            }
        }

        return conteudoSeparado;
    }

    private Tabelas ocupaTabelaNaoDinamica(String[][] conteudoSeparado) {
        Tabelas tabelas = new Tabelas();
        
        String[] terminais = new TabelasUtil().encontraTerminais(conteudoSeparado);
        String[] naoTerminais = new TabelasUtil().encontraNaoTerminais(conteudoSeparado);

        for(String[] producoesPorLinha : conteudoSeparado) {
            int i = 0;
            String estado = null;
            for(String producao : producoesPorLinha) {
                if(i == 0) {
                    estado = producao;//o primeiro elemento de uma linha será sempre o estado
                }
                else {
                    //encontra nao terminal
                    if(producao.equals("ε")){
                        tabelas.getTabelaNaoDeterministicaEstadosFinais().add(estado);
                    }
                    else{
                        String naoTerminalDaProducao = new TabelasUtil().removeCaracteresDeUmaString(terminais, producao);
                        String terminalDaProducao = new TabelasUtil().removeCaracteresDeUmaString(naoTerminais, producao);
                        String chaveDoHash = estado + naoTerminalDaProducao;
                        if(!tabelas.getTabelaNaoDeterministica().containsKey(chaveDoHash)) {
                            List<String> listaVazia = new ArrayList<String>();
                            tabelas.getTabelaNaoDeterministica().put(chaveDoHash, listaVazia);
                        }
                        tabelas.getTabelaNaoDeterministica().get(chaveDoHash).add(terminalDaProducao);
                    }

                }
                i++;
            }        
        }

        return tabelas;
    }

    
}
