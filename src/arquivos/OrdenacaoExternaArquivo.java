package arquivos;

import models.Aluno;
import models.VetorParaOrdenacao;

public class OrdenacaoExternaArquivo {
	private final static int TAMANHO_VETOR = 4;
	private static int posicaoNoArquivoOriginal = 0;
	private static Aluno ultimoColocadoNoArquivo = null;
	private static int quantidadeTotalDeArquivos = 4;
	
	public static void ordenarArquivoPorOrdemAlfabética(ArquivoBinarioAcessoAleatorio arquivo) {
		VetorParaOrdenacao[] vetor = inicializaVetor();
		String nomeArquivo = "arquivo";
		int quantidadeDeArquivos = 1;
		ArquivoOrdenado arquivoTemporario;
		int i = 0;

		do {

			arquivoTemporario = new ArquivoOrdenado(nomeArquivo + quantidadeDeArquivos + ".dat");
			while (aindaExisteMaior(vetor) && !vetorETodoNulo(vetor)) {
				vetor = preencheVetorCompleto(vetor, arquivo);
				vetor = colocaMenorEncontradoNoArquivoAberto(arquivoTemporario, vetor);
			}
	
			arquivoTemporario.add(new Aluno(";", -1L, -1.0));
			ultimoColocadoNoArquivo = null;
			if (quantidadeDeArquivos == quantidadeTotalDeArquivos) {
				quantidadeDeArquivos = 1;
				
			} else
				quantidadeDeArquivos++;
			
			
			vetor = retiraAsteriscosDoVetor(vetor);
			System.out.println();
		} while (!vetorETodoNulo(vetor));
		
		
		for (int j=1; j<5; j++) {
			arquivoTemporario = new ArquivoOrdenado(nomeArquivo + j + ".dat");
			System.out.println("---------------" + nomeArquivo + j + "------------------");
			arquivoTemporario.imprimeTodos();
			System.out.println("--------------------------------------------------------");
		}
	}

	private static boolean vetorETodoNulo(VetorParaOrdenacao[] vetor) {
		for (int i=0; i<TAMANHO_VETOR; i++)
			if (vetor[i].getAluno() != null) 
				return false;
		return true;
	}

	private static VetorParaOrdenacao[] retiraAsteriscosDoVetor(VetorParaOrdenacao[] vetor) {
		for (int i=0; i<TAMANHO_VETOR; i++) {
			vetor[i].setMenor(false);
		}
		return vetor;
	}

	private static void imprimeVetor(VetorParaOrdenacao[] vetor) {
		System.out.println("-------------------------------------");
		for (int i=0; i<TAMANHO_VETOR; i++) {
			String asterisco = "";
			if (vetor[i].isMenor()) asterisco = "*";
			System.out.println(vetor[i].getNome() + ": " + asterisco);
		}
		System.out.println("-------------------------------------");
		
	}

	private static VetorParaOrdenacao[] apagaAlunoAdicionadoNoArquivoDoVetor(VetorParaOrdenacao[] vetor,
			VetorParaOrdenacao menorNoVetor) {
		for (int i=0; i<TAMANHO_VETOR; i++) {
			if (vetor[i] != null && vetor[i].getAluno() != null) {
				if (vetor[i].getAluno().equals(menorNoVetor.getAluno())) {
					vetor[i].setAluno(null);
					break;
				}
			}
		}
		return vetor;
	}

	private static VetorParaOrdenacao[] colocaMenorEncontradoNoArquivoAberto(ArquivoOrdenado arquivo1, VetorParaOrdenacao[] vetor) {
		VetorParaOrdenacao menor = null;
		
		String ultimo = "";
		if (ultimoColocadoNoArquivo != null)
			ultimo = ultimoColocadoNoArquivo.getNome();
		if (ultimo.equals("Rosa R L Araujo"))
			System.out.println("COLE");
		
		for (int i=0; i<TAMANHO_VETOR; i++) {
			if (vetor[i] != null && vetor[i].getAluno() != null) {
				if (ultimo != "" && vetor[i].getNome().compareTo(ultimo) < 0) {
					vetor[i].setMenor(true);
				}  
				if (menor == null && !vetor[i].isMenor()) {
					menor = vetor[i];
				}
				else if (menor != null && vetor[i].getNome().compareTo(menor.getNome()) < 0 && ! vetor[i].isMenor()) {
					menor = vetor[i];
				}
			}	
		}
		
		if (menor != null) {
			arquivo1.add(menor.getAluno());
			ultimoColocadoNoArquivo = menor.getAluno();
			vetor = apagaAlunoAdicionadoNoArquivoDoVetor(vetor, menor);			
		}
		return vetor;
	}

	private static VetorParaOrdenacao[] inicializaVetor() {
		VetorParaOrdenacao[] vetor = new VetorParaOrdenacao[TAMANHO_VETOR];
		for (int i=0; i<TAMANHO_VETOR; i++) {
			vetor[i] = new VetorParaOrdenacao();
		}
		return vetor;
	}

	private static VetorParaOrdenacao[] preencheVetorCompleto(VetorParaOrdenacao[] nomes, ArquivoBinarioAcessoAleatorio arquivo) {
		for(int i=0; i < TAMANHO_VETOR; i++) {
			if (nomes[i].getAluno() == null) {
				Aluno aluno = arquivo.procurarAlunoPorPosicaoNoArquivo(posicaoNoArquivoOriginal);
				nomes[i].setAluno(aluno);
				posicaoNoArquivoOriginal++;
			}
		}
		return nomes;
	}

	private static boolean aindaExisteMaior(VetorParaOrdenacao[] nomes) {
		for (int i=0; i < TAMANHO_VETOR; i++) {
			if (! nomes[i].isMenor())
				return true;
		}
		return false;
	}
}
