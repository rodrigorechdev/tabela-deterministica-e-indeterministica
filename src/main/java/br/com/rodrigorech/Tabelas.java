package br.com.rodrigorech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Tabelas {

    private String[] terminais;

    /**chave da tabelaNaoDeterministica é o terminal + naoTerminal */
    private HashMap<String, List<String>> tabelaNaoDeterministica = new HashMap<String, List<String>>();
    private List<String> tabelaNaoDeterministicaEstadosFinais = new ArrayList<String>();//estados que contém ε 
    private List<String> estadosTabelaNaoDetermistica = new ArrayList<String>();

    /**chave da tabelaDeterministica é o terminal + naoTerminal */
    private HashMap<String, List<String>> tabelaDeterministica = new HashMap<String, List<String>>();
    private List<String> tabelaDeterministicaEstadosFinais = new ArrayList<String>();//estados que contém ε 
    private List<String> estadosTabelaDetermistica = new ArrayList<String>();

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

    public void setEstadosTabelaNaoDetermistica(List<String> valor){
        this.estadosTabelaNaoDetermistica = valor;
    }

    public void setEstadosTabelaDetermistica(List<String> valor){
        this.estadosTabelaDetermistica = valor;
    }

    public List<String> getEstadosTabelaDetermistica(){
        return this.estadosTabelaDetermistica;
    }

    public List<String> getEstadosTabelaNaoDetermistica(){
        return this.estadosTabelaNaoDetermistica;
    }

    public String[] getTerminais(){
        return this.terminais;
    }

    public void setTerminais(String[] value){
        this.terminais = value;
    }

}
