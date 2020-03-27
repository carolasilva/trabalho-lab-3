package arquivos;

import models.Aluno;
import models.VetorParaOrdenacao;

public class OrdenacaoExternaArquivo {
	private final static int TAMANHO_VETOR = 4;
	private static int posicaoNoArquivoOriginal = 0;
	private static Aluno ultimoColocadoNoArquivo = null;
	
	public static void ordenarArquivoPorOrdemAlfabética(ArquivoBinarioAcessoAleatorio arquivo) {
		VetorParaOrdenacao[] vetor = inicializaVetor();
		ArquivoOrdenado arquivo1 = new ArquivoOrdenado("arquivo1.dat");
		int i = 0;

		
		while (aindaExisteMaior(vetor)) {
			vetor = preencheVetorCompleto(vetor, arquivo);
			imprimeVetor(vetor);
			vetor = colocaMenorEncontradoNoArquivoAberto(arquivo1, vetor);
		}
		
		arquivo1.imprimeTodos();
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
			if (vetor[i].getAluno().equals(menorNoVetor.getAluno())) {
				vetor[i].setAluno(null);
				break;
			}
		}
		return vetor;
	}

	private static VetorParaOrdenacao[] colocaMenorEncontradoNoArquivoAberto(ArquivoOrdenado arquivo1, VetorParaOrdenacao[] vetor) {
		VetorParaOrdenacao menor = null;
		
		String ultimo = "";
		if (ultimoColocadoNoArquivo != null)
			ultimo = ultimoColocadoNoArquivo.getNome();
		if (ultimo.equals("Rosa E E FranÃ§a"))
			System.out.println("COLE");
		
		for (int i=0; i<TAMANHO_VETOR; i++) {
			if (ultimo != "" && vetor[i].getNome().compareTo(ultimo) < 0) {
				vetor[i].setMenor(true);
			}  
			if (menor == null && !vetor[i].isMenor()) {
				menor = vetor[i];
			}
			else if (menor != null && vetor[i].getNome().compareTo(menor.getNome()) < 0) {
				menor = vetor[i];
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
