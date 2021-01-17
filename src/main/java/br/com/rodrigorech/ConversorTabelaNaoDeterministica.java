package br.com.rodrigorech;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class ConversorTabelaNaoDeterministica {
    
    static int arrobas = 0;//representa a qtd de estados <@n>. esses estados são criados para entradas informadas com palavras em vez de gramáticas

    /**
     * Recebe conteúdo escrito no arquivo entrada.txt e converte para uma tabela de linguagem não deterministica. 
     */
    public Tabelas criaTabelaNaoDeterministica(String[] conteudo) {
        String[][] conteudoSeparado = separaConteudo(conteudo);
        return this.ocupaTabelaNaoDeterministica(conteudoSeparado);
    }

    /**
     * Separa o conteúdo recebido por parametro, cada elemento do conteúdo será dívidido, essa divisão 
     * ocorre se existe o elemento '::=' ou '|' no código. Sendo assim ele separa cada produção de uma linha 
     * e o primeiro elemento do array resultante será o estado, que vem antes do '::='
     * @param conteudo
     * @return
     */
    private String[][] separaConteudo(String[] conteudo) {
        //String[][] conteudoSeparado = new String[conteudo.length][];
        List<String[]> listaConteudoPorLinha = new ArrayList<String[]>();
        
        String[] valorEstadoInicial = {"<S>"};
        listaConteudoPorLinha.add(valorEstadoInicial);

        for(String conteudoPorLinha : conteudo) {
            if(conteudoPorLinha.contains("::=")) {
                if(conteudoPorLinha.startsWith("<S>")) {
                    String[] producoesAtuais = listaConteudoPorLinha.get(0);
                    conteudoPorLinha = conteudoPorLinha.replace("<S>::=", "");
                    String[] novasProducoes = conteudoPorLinha.split("[:][:][=]|\\|");
                    producoesAtuais = ArrayUtils.addAll(producoesAtuais, novasProducoes);
                    listaConteudoPorLinha.remove(0);
                    listaConteudoPorLinha.add(0, producoesAtuais);
                }
                else {
                    listaConteudoPorLinha.add(conteudoPorLinha.split("[:][:][=]|\\|"));
                }
            }
            else { 
                if(!this.procuraSeExisteEstado(listaConteudoPorLinha, "<@>")) {
                    String[] valorEstadoArroba = {"<@>", "ε"};
                    listaConteudoPorLinha.add(valorEstadoArroba);
                }

                String[] palavra = conteudoPorLinha.split("");
                String[] novaProducao = new String[1];
                String novoEstado = this.geraNovoNomeEstadoArroba();
                novaProducao[0] = palavra[0] + novoEstado;
                String[] producoesAtuais = listaConteudoPorLinha.get(0);
                producoesAtuais = ArrayUtils.addAll(producoesAtuais, novaProducao);//adiciona nova producao no estado <S>
                listaConteudoPorLinha.remove(0);
                listaConteudoPorLinha.add(0, producoesAtuais);

                int i = 1;
                while(true) {
                    String estadoAntigo;
                    estadoAntigo = novoEstado;
                    if(i != palavra.length - 1){
                        novoEstado = this.geraNovoNomeEstadoArroba();
                        String[] novaLinha = {estadoAntigo, palavra[i] + novoEstado};
                        listaConteudoPorLinha.add(novaLinha);
                        i++;
                    }
                    else {
                        String[] novaLinha = {estadoAntigo, palavra[i] + "<@>"};
                        listaConteudoPorLinha.add(novaLinha);
                        break;
                    }
                }

            }
        }

        String[][] conteudoSeparado = new String[listaConteudoPorLinha.size()][];
        for(int i = 0; i < listaConteudoPorLinha.size(); i++) {
            conteudoSeparado[i] = listaConteudoPorLinha.get(i);
        }

        return conteudoSeparado;
    }

    private Tabelas ocupaTabelaNaoDeterministica(String[][] conteudoSeparado) {
        Tabelas tabelas = new Tabelas();

        String[] terminais = new TabelasUtil().encontraTerminais(conteudoSeparado);
        String[] naoTerminais = new TabelasUtil().encontraNaoTerminais(conteudoSeparado);
        tabelas.setTerminais(naoTerminais); 
        for(String[] producoesPorLinha : conteudoSeparado) {
            int i = 0;
            String estado = null;
            for(String producao : producoesPorLinha) {
                if(i == 0) {
                    estado = producao;//o primeiro elemento de uma linha será sempre o estado
                    tabelas.getEstadosTabelaNaoDetermistica().add(estado);
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


    private boolean procuraSeExisteEstado(List<String[]> listaConteudoPorLinha, String estado) {
        for(String[] lista : listaConteudoPorLinha) {
            if(lista[0].equals(estado)) {
                return true;
            }
        }
        return false;
    }

    private String geraNovoNomeEstadoArroba() {
        int valor = ConversorTabelaNaoDeterministica.arrobas;
        ConversorTabelaNaoDeterministica.arrobas++;
        return "<@" + valor + ">";
    }
    
}
