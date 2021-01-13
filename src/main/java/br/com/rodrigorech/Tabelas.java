package br.com.rodrigorech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Tabelas {

    /**chave da tabelaNaoDeterministica é o terminal + naoTerminal */
    private HashMap<String, List<String>> tabelaNaoDeterministica = new HashMap<String, List<String>>();
    private List<String> tabelaNaoDeterministicaEstadosFinais = new ArrayList<String>();//estados que contém ε 

    /**chave da tabelaDeterministica é o terminal + naoTerminal */
    private HashMap<String, List<String>> tabelaDeterministica = new HashMap<String, List<String>>();
    private List<String> tabelaDeterministicaEstadosFinais = new ArrayList<String>();//estados que contém ε 

    public HashMap<String, List<String>> getTabelaNaoDeterministica(){
        return this.tabelaNaoDeterministica;
    }

    public List<String> getTabelaNaoDeterministicaEstadosFinais(){
        return this.tabelaNaoDeterministicaEstadosFinais;
    }
    public HashMap<String, List<String>> getTabelaDeterministica(){
        return this.tabelaDeterministica;
    }

    public List<String> getTabelaDeterministicaEstadosFinais(){
        return this.tabelaDeterministicaEstadosFinais;
    }

    public void setTabelaDeterministicaEstadosFinais(List<String> valor){
        this.tabelaDeterministicaEstadosFinais = valor;
    }

    public void setTabelaDeterministica(HashMap<String, List<String>> valor){
        this.tabelaDeterministica = valor;
    }
 
}
