package arquivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import models.Aluno;

public class ArquivoTexto {
	File arquivoTexto;

	public ArquivoTexto(String nomeArquivo) {
		this.arquivoTexto = new File(nomeArquivo);
	}
	
	
	public ArquivoBinarioAcessoAleatorio transformaArquivoTextoEmBinario(String arquivoBinario) {
		Scanner leitor;
		String[] dividido;
		Aluno aluno;
		ArquivoBinarioAcessoAleatorio arquivoBinarioAcessoAleatorio = new ArquivoBinarioAcessoAleatorio(arquivoBinario);
		
		try {
			leitor = new Scanner(arquivoTexto);
			while(leitor.hasNextLine()){
	            String frase = leitor.nextLine();
	            dividido = frase.split(";");
	            aluno = new Aluno(dividido[1], Long.parseLong(dividido[0]), Double.parseDouble(dividido[2].replace(',', '.')));
	            arquivoBinarioAcessoAleatorio.adicionarAlunoNoArquivo(aluno);
	        }
			arquivoBinarioAcessoAleatorio.getArquivoIndice().salvarIndiceCompleto();
	        leitor.close();
	        System.out.println("Arquivo binário criado com sucesso!");
	        return arquivoBinarioAcessoAleatorio;
		} catch (FileNotFoundException e) {
			System.out.println("Algo deu errado na leitura do arquivo...");
		}
		return arquivoBinarioAcessoAleatorio;
		
	}
	
}
