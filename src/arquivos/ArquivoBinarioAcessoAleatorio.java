package arquivos;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import models.Aluno;
import models.LinhaDoIndex;

public class ArquivoBinarioAcessoAleatorio {
	RandomAccessFile arquivoAcessoAleatorio;
	File arquivo;
	ArquivoIndice arquivoIndice;
	private Long posicao = 0L;
	
	public ArquivoBinarioAcessoAleatorio(String nomeArquivo) {
		arquivo = new File(nomeArquivo);
		arquivoIndice = new ArquivoIndice("indice.dat");
	}
	
	public void adicionarAlunoNoArquivo(Aluno aluno) {
		try {
			this.arquivoAcessoAleatorio = new RandomAccessFile(arquivo, "rw");
			this.arquivoAcessoAleatorio.seek(this.arquivo.length());
			aluno.saveData(this.arquivoAcessoAleatorio);
			arquivoIndice.addIndice(new LinhaDoIndex(aluno.getMatricula(), this.posicao));
			this.posicao++;
			this.arquivoAcessoAleatorio.close();
			
		} catch (IOException e) {
			System.out.println("Erro com o arquivo: " + e.getMessage());
		}
		
	}
	
	public Aluno procurarAlunoPorPosicaoNoArquivo (int posicao) {
		try {
			this.arquivoAcessoAleatorio = new RandomAccessFile(arquivo, "r");
			this.arquivoAcessoAleatorio.seek(posicao * Aluno.DATASIZE);
			Aluno aluno = null;
			
	        try{
	        	aluno = Aluno.readData(this.arquivoAcessoAleatorio);  
	        }
	        catch(EOFException e){  //para quando terminar os dados do arquivo
	        	aluno = null;
	        }
	        
	        this.arquivoAcessoAleatorio.close(); 
	        return aluno;
		} catch (IOException e) {
			System.out.println("Erro com o arquivo: " + e.getMessage());
		}
		return null;
	}

	public ArquivoIndice getArquivoIndice() {
		return arquivoIndice;
	}
	
	public Aluno procurarPorMatricula(Long matricula) {
		int posicao = this.arquivoIndice.procurarPorMatricula(matricula);
		if (posicao != -1L) {
			return procurarAlunoPorPosicaoNoArquivo(posicao);			
		}
		else
			return null;
	}
	
	public boolean verificaSeArquivoJaFoiCriado() {
		return arquivo.exists();
	}
	
}
