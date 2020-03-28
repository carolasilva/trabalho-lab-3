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
		vetor = preencheVetorCompleto(vetor, arquivo);
		String nomeArquivo = "arquivo";
		int quantidadeDeArquivos = 1;
		ArquivoBinarioAcessoAleatorio arquivoTemporario;
		
		do {

			arquivoTemporario = new ArquivoBinarioAcessoAleatorio(nomeArquivo + quantidadeDeArquivos + ".dat");
			while (aindaExisteMaior(vetor)) {
				vetor = preencheVetorCompleto(vetor, arquivo);
				imprimeVetor(vetor);
				vetor = colocaMenorEncontradoNoArquivoAberto(arquivoTemporario, vetor);
			}
	
			arquivoTemporario.adicionarAlunoNoArquivo(new Aluno(";", -1L, -1));
			ultimoColocadoNoArquivo = null;
			if (quantidadeDeArquivos == quantidadeTotalDeArquivos) {
				quantidadeDeArquivos = 1;
				
			} else
				quantidadeDeArquivos++;
			vetor = retiraAsteriscosDoVetor(vetor);
			
		} while (arquivo.arquivo.length() > posicaoNoArquivoOriginal * Aluno.DATASIZE);
		
		for (int j=1; j<5; j++) {
			arquivoTemporario = new ArquivoBinarioAcessoAleatorio(nomeArquivo + j + ".dat");
			System.out.println("---------------" + nomeArquivo + j + "------------------");
			arquivoTemporario.imprimirTodos();
			System.out.println("--------------------------------------------------------");
		}
		
		arquivoTemporario = new ArquivoBinarioAcessoAleatorio("arquivo1.dat");
		System.out.println("ALUNO 3 DO ARQUIVO 1: " + arquivoTemporario.procurarAlunoPorPosicaoNoArquivo(10));
		
		//intercalaArquivos("arquivo1.dat", "arquivo2.dat", "arquivo5.dat");
		
	}

	private static void imprimeVetor(VetorParaOrdenacao[] vetor) {
		System.out.println("-------------------------------------");
		for(int i=0; i<TAMANHO_VETOR; i++) {
			if (vetor[i].getAluno() != null) {
				boolean temAsterisco = vetor[i].isMenor();
				String asterisco = "";
				if (temAsterisco)
					asterisco = "*";
				System.out.println(vetor[i].getNome() + ": " + asterisco);
				
			} else
				System.out.println("nulo");
		}
		
	}

	private static void intercalaArquivos(String nomeArquivo1, String nomeArquivo2, String nomeArquivoSaida) {
		ArquivoOrdenado arquivo1 = new ArquivoOrdenado(nomeArquivo1);
		ArquivoOrdenado arquivo2 = new ArquivoOrdenado(nomeArquivo2);
		ArquivoOrdenado arquivoSaida = new ArquivoOrdenado(nomeArquivoSaida);
		
		Aluno aluno1 = arquivo1.buscaPorPosicao(0);
		Aluno aluno2 = arquivo2.buscaPorPosicao(0);
		
		Aluno alunoAntigo1 = aluno1;
		Aluno alunoAntigo2 = aluno2;
		
		do {
			do {
				
				if (aluno1.getNome().compareTo(aluno2.getNome()) < 0 && (aluno1.getMatricula() != -1)) {
					alunoAntigo1 = aluno1;
					arquivoSaida.add(aluno1);
					aluno1 = arquivo1.buscaProximo(alunoAntigo1);
				} else if (aluno2.getMatricula() != -1){
					alunoAntigo2 = aluno2;
					arquivoSaida.add(aluno2);
					aluno2 = arquivo2.buscaProximo(alunoAntigo2);
				}
				
				
			} while (aluno1.getMatricula() != -1 && aluno2.getMatricula() != -1);

			arquivoSaida.add(new Aluno(";", -1L, -1.0));
			
		} while (aluno1 != null && aluno2 != null);
		
		System.out.println("ARQUIVO SAIDA");
		arquivoSaida.imprimeTodos();
	}

	private static VetorParaOrdenacao[] retiraAsteriscosDoVetor(VetorParaOrdenacao[] vetor) {
		for (int i=0; i<TAMANHO_VETOR; i++) {
			vetor[i].setMenor(false);
		}
		return vetor;
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

	private static VetorParaOrdenacao[] colocaMenorEncontradoNoArquivoAberto(ArquivoBinarioAcessoAleatorio arquivo1, VetorParaOrdenacao[] vetor) {
		VetorParaOrdenacao menor = null;
		
		String ultimo = "";
		if (ultimoColocadoNoArquivo != null)
			ultimo = ultimoColocadoNoArquivo.getNome();
		
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
			arquivo1.adicionarAlunoNoArquivo(menor.getAluno());
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
				if (aluno == null)
					nomes[i].setMenor(true);
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
