package br.com.rodrigorech;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Responsável por ler e formatar os arquivos de entrada.
 */
public class LeitorDeArquivo {
    
    public String[] lerArquivo() {
        final File file = new File("entrada.txt");
        BufferedReader bufferedReader;
        if (file.exists()) {
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                Integer primeiraLinha = Integer.valueOf(bufferedReader.readLine()) - 1;                
                String[] conteudoDoArquivo = new String[primeiraLinha];
                for(int i = 0; i < primeiraLinha; i++){
                    conteudoDoArquivo[i] = bufferedReader.readLine();
                }
                bufferedReader.close();

                return this.removeEspacos(conteudoDoArquivo);

            } catch (Exception e) {
                System.out.println("O arquivo entrada.txt foi encontrado" + 
                " mas houve uma falha ao ler o conteúdo dentro dele");
                e.printStackTrace();
                return null;
            }
        }
        else {
            System.out.println("Arquivo entrada.txt não encontrado");
            return null;
        }
    }

    /**
     * Remove todos os caracteres " "
     * @param conteudo
     * @return
     */
    private String[] removeEspacos(String[] conteudo) {
        int i = 0;
        for(String conteudoUnidade : conteudo) {
            conteudo[i] = conteudoUnidade.replaceAll(" ","");
            i++;
        }

        return conteudo;
    }

    

}
