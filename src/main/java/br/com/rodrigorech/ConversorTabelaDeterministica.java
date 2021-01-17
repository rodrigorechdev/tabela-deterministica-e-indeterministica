package br.com.rodrigorech;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConversorTabelaDeterministica {
    List<String> estadosTabelaDetermistica = new ArrayList<String>();

    /**
     * Recebe um objeto de Tabelas com uma tabela não dinâmica já montada, 
     * com base nisso faz a tabela não dinâmica
     * @param tabelas
     */
    public Tabelas criaTabelaDeterministica(Tabelas tabelas) {
        HashMap<String, List<String>> tabelaDeterministica = this.criaCopiaDaTabelaNaoDeterministica(tabelas.getTabelaNaoDeterministica());
        List<String> estadosFinais = new ArrayList<String>();
        estadosFinais.addAll(tabelas.getTabelaNaoDeterministicaEstadosFinais());
        List<String> estadosTabelaDetermistica = new ArrayList<String>();
        estadosTabelaDetermistica.addAll(tabelas.getEstadosTabelaNaoDetermistica());

        boolean existeIndeterminismo = true;
        while(existeIndeterminismo) {
            String chaveIndeterminismo = this.encontraIndeterminismo(tabelaDeterministica);
            if(chaveIndeterminismo == null) {
                existeIndeterminismo = false;
            }
            else {
                this.removeIndeterminismo(tabelaDeterministica, chaveIndeterminismo, estadosFinais, estadosTabelaDetermistica);
            }
        }          

        tabelas.setTabelaDeterministica(tabelaDeterministica);
        tabelas.setTabelaDeterministicaEstadosFinais(estadosFinais);
        tabelas.setEstadosTabelaDetermistica(estadosTabelaDetermistica);
        return tabelas;
    }
       

    /**
     * Cria cópia da tabela não determinística de forma que fique igual a tabela determinística
     * porém com outro endereço, para que futuras alterações no hash da tabela não determinística
     * não afete a tabela determinística 
     * @param tabelaNaoDeterministica
     * @return
     */
    private HashMap<String, List<String>> criaCopiaDaTabelaNaoDeterministica(HashMap<String, List<String>> tabelaNaoDeterministica) {
        HashMap<String, List<String>> tabelaCopiada = new HashMap<>();
        Set<String> chaves = tabelaNaoDeterministica.keySet();

        for(String chave : chaves) {
            List<String> listaTemporaria = tabelaNaoDeterministica.get(chave);
            List<String> novaLista = new ArrayList<String>();
            novaLista.addAll(listaTemporaria);
            tabelaCopiada.put(chave, novaLista);
        }

        return tabelaCopiada;
    }


    /**
     * Encontra Indeterminismo de tabela, retorna null caso não haja mais indeterminismos na tabela
     * @param tabelaDeterministica
     * @return
     */
    private String encontraIndeterminismo(HashMap<String, List<String>> tabelaDeterministica) {
        Set<String> chaves = tabelaDeterministica.keySet();

        for(String chave : chaves) {
            if(tabelaDeterministica.get(chave).size() > 1) {//Indeterminismo encontrado
                return chave;
            }
        }

        return null;//Só retorna null quando não há mais indeterminismo
    }

    private void removeIndeterminismo(HashMap<String, List<String>> tabelaDeterministica, String chaveDeterminismo, 
    List<String> estadosFinais, List<String> estadosTabelaDetermistica){

        List<String> producoes = tabelaDeterministica.get(chaveDeterminismo);
        String naoTerminal0 = producoes.get(0);
        String naoTerminal1 = producoes.get(1);

        String nomeDoNovoEstado1 = this.criaNomeDeEstado(naoTerminal0, naoTerminal1);
        String nomeDoNovoEstado2 = this.criaNomeDeEstado(naoTerminal1, naoTerminal0);
        String nomeDoNovoEstado;

        if(this.encontraSeEstadoJaExiste(tabelaDeterministica, nomeDoNovoEstado1)) {
            nomeDoNovoEstado = nomeDoNovoEstado1;
        }
        else {
            if(this.encontraSeEstadoJaExiste(tabelaDeterministica, nomeDoNovoEstado2)) {
                nomeDoNovoEstado = nomeDoNovoEstado2;
            }
            else {
                nomeDoNovoEstado = this.criaNovoEstadoDeterministico(tabelaDeterministica, naoTerminal0, naoTerminal1);
                this.ajustaEstadosFinais(naoTerminal0, nomeDoNovoEstado, estadosFinais);
                this.ajustaEstadosFinais(naoTerminal1, nomeDoNovoEstado, estadosFinais);
                estadosTabelaDetermistica.add(nomeDoNovoEstado);
            }
        }

        tabelaDeterministica.get(chaveDeterminismo).remove(naoTerminal0);
        tabelaDeterministica.get(chaveDeterminismo).remove(naoTerminal1);
        tabelaDeterministica.get(chaveDeterminismo).add(nomeDoNovoEstado);
    }

    // /**
    //  * Faz todas as produções de uma tabela que apontavam para o estado antigo apontar para o estado novo
    //  * @return
    //  */
    // private void reapontaEstadosEmTabela(HashMap<String, List<String>> tabelaDeterministica, String estadoAntigo, String estadoNovo) {
    //     Set<String> chaves = tabelaDeterministica.keySet();

    //     for(String chave : chaves) {
    //         List<String> lista = tabelaDeterministica.get(chave);
    //         for(String producao : lista){
    //             if(producao.equals(estadoAntigo)) {
    //                 lista.remove(estadoAntigo);
    //                 lista.add(estadoNovo);
    //             }
    //         }
    //     }
    // }

    private void ajustaEstadosFinais(String estadoAntigo, String estadoNovo, List<String> estadosFinais) {
        if(!estadoAntigo.equals("S")) {
            if(estadosFinais.contains(estadoAntigo) && !estadosFinais.contains(estadoNovo)) {
                estadosFinais.add(estadoNovo);
            }

        }
    }

    /**
     * Encontra chaves de uma tabela que contenham a string valor.
     * @param valor
     * @param tabela
     */
    public List<String> encontraChaves(HashMap<String, List<String>> tabela, String valor){
        Set<String> chaves = tabela.keySet();
        List<String> chavesQueContemValor = new ArrayList<String>();
        for(String chave : chaves){
            if(chave.contains(valor)){
                if(!chave.contains("><")){//se contém "><" quer dizer que é um estado que já é a junção de dois estados. ex: <A><B>
                    chavesQueContemValor.add(chave);
                }
            }
        }

        return chavesQueContemValor;
    }

    /**
     * Retorna as produções de uma tabela recebendo a chave da produção e a tabela
     * @param tabela
     * @param chavesProducoesEstado
     * @return
     */
    public List<String> encontraProducoes(HashMap<String, List<String>> tabela, String chaveProducaoEstado){
        List<String> producoesEstado = new ArrayList<String>();
        List<String> producao = tabela.get(chaveProducaoEstado);
        producoesEstado.addAll(producao);
        
        return producoesEstado;
    }

    /**
     * Cria estado novo, juntando dois nao terminais para resolver um indeterminismo destes dois nao terminais.
     * @param tabela
     * @param naoTerminal0
     * @param naoTerminal1
     * @return 
     */
    public String criaNovoEstadoDeterministico(HashMap<String, List<String>> tabela, String naoTerminal0, String naoTerminal1){
        String nomeNovoEstado;

        if(this.encontraSeEstadoJaExiste(tabela, this.criaNomeDeEstado(naoTerminal1, naoTerminal0))){
            nomeNovoEstado = this.criaNomeDeEstado(naoTerminal1, naoTerminal0);
        }
        else{
            nomeNovoEstado = this.criaNomeDeEstado(naoTerminal0, naoTerminal1);
        }

        String[] naoTerminais = {naoTerminal0, naoTerminal1};
        for(String naoTerminal : naoTerminais){
            List<String> chavesProducoesEstado0 = this.encontraChaves(tabela, naoTerminal);//procura chaves que contenham o não terminal
            for(String chaveProducaoEstado0 : chavesProducoesEstado0){
                List<String> producoesEstado0 = tabela.get(chaveProducaoEstado0);//pega producao do estado
                String[] chaveDividida = chaveProducaoEstado0.split(">");
                String terminal = chaveDividida[chaveDividida.length-1];
                if(tabela.containsKey(nomeNovoEstado + terminal)){
                    for(String producaoEstado0 : producoesEstado0){
                        if(!tabela.get(nomeNovoEstado + terminal).contains(producaoEstado0)){
                            tabela.get(nomeNovoEstado + terminal).add(producaoEstado0);
                        }
                    }
                }
                else{
                    List<String> novaProducao = new ArrayList<String>();
                    novaProducao.addAll(producoesEstado0);
                    tabela.put(nomeNovoEstado + terminal, novaProducao);
                }
            }
        }

        return nomeNovoEstado;
    }

    
    private String encontraNaoTerminal(String valor){
        int indexFimDoNaoTerminal = valor.lastIndexOf(">");
        return valor.substring(0, indexFimDoNaoTerminal + 1);
    }
    
    /**
     * O estado deve ser enviado com um não terminal e terminal, ex: <A>b ou <B>d 
     * @param tabela
     * @param nomeEstado
     * @return
     */
    private boolean encontraSeEstadoJaExiste(HashMap<String, List<String>> tabela, String nomeEstado) {
        Set<String> tabelaChaves = tabela.keySet();
        for(String chave : tabelaChaves){
            String naoTerminalDaTabela = this.encontraNaoTerminal(chave);
            if(naoTerminalDaTabela.equals(nomeEstado)){
                return true;
            }
        }

        return false;
    }

    /**
     * Cria nome de estado baseado em dois não terminais. 
     * Ex: recebendo naoTerminal0 = <A> e naoTerminal1 = <B>, retorna <AB>
     * @return
     */
    private String criaNomeDeEstado(String naoTerminal0, String naoTerminal1){
        String nomeDoNovoEstado = naoTerminal0 + naoTerminal1;
        nomeDoNovoEstado = nomeDoNovoEstado.replaceAll(">", "");
        nomeDoNovoEstado = nomeDoNovoEstado.replaceAll("<", "");
        nomeDoNovoEstado = "<" + nomeDoNovoEstado + ">";
        return nomeDoNovoEstado;
    }
}
