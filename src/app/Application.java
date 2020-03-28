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
		                break;
                case 3: OrdenacaoExternaArquivo.ordenarArquivoPorOrdemAlfab�tica(arquivoBinario);
                		arquivoBinario = new ArquivoBinarioAcessoAleatorio("arquivo3.dat", false);
                		int posicao = 0, i=0;
                		
                		String resposta = "";
                		while (! resposta.toUpperCase().equals("S") && ! resposta.toUpperCase().equals("N")) {
                			System.out.println("Voc� deseja come�ar a listagem de uma posi��o espec�fica do arquivo? (S - N)");
                			resposta = leitor.nextLine();
                		}
                		if (resposta.toUpperCase().equals("S")) {
                			System.out.println("Digite a posi��o que voc� deseja come�ar a listar: ");
                			posicao = leitor.nextInt();
                			leitor.nextLine();
                		}
                		
                		aluno = arquivoBinario.procurarAlunoPorPosicaoNoArquivo(posicao);
                		while (aluno.getMatricula() != -1) {
                			i = 0;
                			while (aluno.getMatricula() != -1 && i < 20) {
                				System.out.println(aluno);
                				posicao++;
                				aluno = arquivoBinario.procurarAlunoPorPosicaoNoArquivo(posicao);
                				i++;
                			}
                			System.out.println("Digite <enter> para ir para a pr�xima p�gina");
                			leitor.nextLine();
                		} 
            }			
            	
        }while(opcao != 4);
	
        leitor.close();
	}
}	
