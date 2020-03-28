package arquivos;

import models.Aluno;
import models.VetorParaOrdenacao;

public class OrdenacaoExternaArquivo {
	private final static int TAMANHO_VETOR = 4;
	private static int posicaoNoArquivoOriginal = 0;
	private static Aluno ultimoColocadoNoArquivo = null;
	private static int quantidadeTotalDeArquivos = 4;
	
	public static void ordenarArquivoPorOrdemAlfabética(ArquivoBinarioAcessoAleatorio arquivo) {
		posicaoNoArquivoOriginal = 0;
		VetorParaOrdenacao[] vetor = inicializaVetor();
		vetor = preencheVetorCompleto(vetor, arquivo);
		String nomeArquivo = "arquivo";
		int quantidadeDeArquivos = 1;
		ArquivoBinarioAcessoAleatorio arquivoTemporario;
		
		do {

			arquivoTemporario = new ArquivoBinarioAcessoAleatorio(nomeArquivo + quantidadeDeArquivos + ".dat", false);
			while (aindaExisteMaior(vetor)) {
				vetor = preencheVetorCompleto(vetor, arquivo);
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
		
		
		intercalaArquivos("arquivo1.dat", "arquivo2.dat", "arquivo5.dat");
		intercalaArquivos("arquivo3.dat", "arquivo4.dat", "arquivo6.dat");
		intercalaArquivos("arquivo5.dat", "arquivo6.dat", "arquivo3.dat");
		
		
		boolean ePossivelRedividirMais = true;
		while(ePossivelRedividirMais) {
			ePossivelRedividirMais = redivisaoArquivos("arquivo3.dat");
		}
	}

	private static boolean redivisaoArquivos(String nomeArquivo) {
		ArquivoBinarioAcessoAleatorio arquivo1 = new ArquivoBinarioAcessoAleatorio(nomeArquivo);
		ArquivoBinarioAcessoAleatorio arquivoTemporario;
		String nomesArquivosTemporario = "arqTemp";
		int posicao = 1;
		int quantidadeDeArquivos = 1, arquivosNecessarios = 0;
		Aluno aluno = arquivo1.procurarAlunoPorPosicaoNoArquivo(0);
		
		while (aluno != null) {
			arquivoTemporario = new ArquivoBinarioAcessoAleatorio(nomesArquivosTemporario + quantidadeDeArquivos + ".dat", false);
			arquivosNecessarios++;
			while (aluno.getMatricula() != -1) {
				arquivoTemporario.adicionarAlunoNoArquivo(aluno);
				posicao++;
				aluno = arquivo1.procurarAlunoPorPosicaoNoArquivo(posicao);				
			}
			
			arquivoTemporario.adicionarAlunoNoArquivo(new Aluno(";", -1L, -1));
			posicao++;
			aluno = arquivo1.procurarAlunoPorPosicaoNoArquivo(posicao);	
			if (quantidadeDeArquivos == quantidadeTotalDeArquivos) {
				quantidadeDeArquivos = 1;
				
			} else
				quantidadeDeArquivos++;
		}
		intercalaArquivos("arqTemp1.dat", "arqTemp2.dat", "arquivo1.dat");
		intercalaArquivos("arqTemp3.dat", "arqTemp4.dat", "arquivo2.dat");
		intercalaArquivos("arquivo1.dat", "arquivo2.dat", "arquivo3.dat");			
		if (arquivosNecessarios > 1) {
			return true;
		}
		
		return false;
		
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
		ArquivoBinarioAcessoAleatorio arquivo1 = new ArquivoBinarioAcessoAleatorio(nomeArquivo1);
		ArquivoBinarioAcessoAleatorio arquivo2 = new ArquivoBinarioAcessoAleatorio(nomeArquivo2);
		ArquivoBinarioAcessoAleatorio arquivoSaida = new ArquivoBinarioAcessoAleatorio(nomeArquivoSaida, true);
		
		Aluno aluno1 = arquivo1.procurarAlunoPorPosicaoNoArquivo(0);
		Aluno aluno2 = arquivo2.procurarAlunoPorPosicaoNoArquivo(0);
		
		int posicaoArquivo1 = 0;
		int posicaoArquivo2 = 0;
		int i=0;
		while (aluno1 != null || aluno2 != null) {
			do {
				
				if (aluno2 == null || (aluno1.getNome().compareTo(aluno2.getNome()) < 0 && (aluno1.getMatricula() != -1)) || aluno2.getMatricula() == -1) {
					arquivoSaida.adicionarAlunoNoArquivo(aluno1);
					posicaoArquivo1++;
					aluno1 = arquivo1.procurarAlunoPorPosicaoNoArquivo(posicaoArquivo1);
				} else if (aluno2.getMatricula() != -1){
					arquivoSaida.adicionarAlunoNoArquivo(aluno2);
					posicaoArquivo2++;
					aluno2 = arquivo2.procurarAlunoPorPosicaoNoArquivo(posicaoArquivo2);
				}
				
				
			} while ((aluno1 != null && aluno1.getMatricula() != -1) || (aluno2 != null && aluno2.getMatricula() != -1));

			
			posicaoArquivo1++;
			posicaoArquivo2++;
			aluno1 = arquivo1.procurarAlunoPorPosicaoNoArquivo(posicaoArquivo1);
			aluno2 = arquivo2.procurarAlunoPorPosicaoNoArquivo(posicaoArquivo2);
			arquivoSaida.adicionarAlunoNoArquivo(new Aluno(";", -1L, -1.0));
		} 
		arquivo1.apagarArquivo();
		arquivo2.apagarArquivo();
	}

	private static boolean temSoUmBloco(String nome) {
		ArquivoBinarioAcessoAleatorio arquivo = new ArquivoBinarioAcessoAleatorio(nome);
		if (arquivo.getQuantidadeBlocos() == 1)
			return true;
		return false;
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
