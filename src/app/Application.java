package app;

import java.io.File;
import java.util.Scanner;

import arquivos.ArquivoBinarioAcessoAleatorio;
import arquivos.ArquivoTexto;
import arquivos.OrdenacaoExternaArquivo;
import models.Aluno;

public class Application {
	public static void main(String[] args) {
		File arquivos = new File("alunos.dat");
		ArquivoBinarioAcessoAleatorio arquivoBinario;
		if (!arquivos.exists()) {
			ArquivoTexto arquivo = new ArquivoTexto("LabComp3_Exerc1_dadosTeste.txt");
			arquivoBinario = arquivo.transformaArquivoTextoEmBinario("alunos.dat");		
		} else {
			arquivoBinario = new ArquivoBinarioAcessoAleatorio("alunos.dat");
		}
		
		int opcao = 2;
		Scanner leitor = new Scanner(System.in);
		Aluno aluno;
		
        do{
            System.out.println("=== ARQUIVOS E ACESSO ALEAT�RIO ===");
            System.out.println("1 - Procurar aluno por posi��o");
            System.out.println("2 - Procurar aluno por matr�cula");
            System.out.println("3 - Listar alunos em ordem alfab�tica");
            System.out.println("4 - Sair");
            
            opcao = leitor.nextInt();
            leitor.nextLine();
            switch(opcao){
                case 1: System.out.print("Posi��o do registro: ");
		                int pos = leitor.nextInt();
		                aluno = arquivoBinario.procurarAlunoPorPosicaoNoArquivo(pos);
		                if(aluno != null) System.out.println(aluno);
		                System.out.println();
		                break;
                case 2: System.out.println("Matr�cula: ");
                		Long matricula = leitor.nextLong();
                		aluno = arquivoBinario.procurarPorMatricula(matricula);
                		if(aluno != null) System.out.println(aluno);
		                System.out.println();
                case 3: OrdenacaoExternaArquivo.ordenarArquivoPorOrdemAlfab�tica(arquivoBinario);
            }
        }while(opcao != 4);
	
        leitor.close();
	}
}	
