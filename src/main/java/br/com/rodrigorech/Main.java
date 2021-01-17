package br.com.rodrigorech;

/**
 * Código responsável por ler o arquivo entrada.txt que contém uma gramática e simula 
 * um automato finito criando a tabela deterministica, não deterministica, remove
 * os estados inalcançaveis e mortos. O arquivo entrada.txt deve conter na primeira linha
 * o número total de linhas da gramática. Além disso a gramática deve começar com o estado "S"
 * e os terminais são definidos por estarem dentro de '<' e '>'.
 * Exemplo de entrada.txt:
 *  2
    <S> ::= a<A> | e<A> | i<A> | o<A> | u<A> 
    <A> ::= a<A> | e<A> | i<A> | o<A> | u<A> | ε

 @author Rodrigo Rech
 */
public class Main 
{
    public static void main( String[] args )
    {
        String[] conteudo = new LeitorDeArquivo().lerArquivo();
        Tabelas tabelas = new ConversorTabelaNaoDeterministica().criaTabelaNaoDeterministica(conteudo);
        tabelas = new ConversorTabelaDeterministica().criaTabelaDeterministica(tabelas);

        System.out.println("TABELA NÃO DETERMINISTICA:");
        new TabelasUtil().imprimeTabela(tabelas.getTabelaNaoDeterministica(), tabelas.getTabelaNaoDeterministicaEstadosFinais(),
        tabelas.getTerminais(), tabelas.getEstadosTabelaNaoDetermistica());

        System.out.println("TABELA DETERMINISTICA:");
        new TabelasUtil().imprimeTabela(tabelas.getTabelaDeterministica(), tabelas.getTabelaDeterministicaEstadosFinais(),
        tabelas.getTerminais(), tabelas.getEstadosTabelaDetermistica());


    }
}
