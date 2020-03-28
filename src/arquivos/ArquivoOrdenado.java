package arquivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import models.Aluno;

public class ArquivoOrdenado {
	private File file;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;
	String nomeArquivo;
	
	public ArquivoOrdenado(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
		file = new File(nomeArquivo);
		boolean append = file.exists();
		try {
			fos = new FileOutputStream(file, append);
			outputFile = new AppendableObjectOutputStream(fos, append);
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
	
	public void add(Aluno aluno) {
		try {
			outputFile.writeObject(aluno);
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o aluno '" + aluno + "' no disco!");
			e.printStackTrace();
		}
	}
	
	public Aluno buscaPorPosicao(int posicao) {
		try (FileInputStream fis = new FileInputStream(file); ObjectInputStream inputFile = new ObjectInputStream(fis)) {
			inputFile.skipNBytes(0);
			return (Aluno) inputFile.readObject();
		} catch (Exception e) {
			return null;
		}
	}
	
	public void imprimeTodos() {
		Aluno aluno = null;
		try (FileInputStream fis = new FileInputStream(file); ObjectInputStream inputFile = new ObjectInputStream(fis)) {
			while (fis.available() > 0) {
				aluno = (Aluno) inputFile.readObject();
				System.out.println(aluno);
				
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar pessoa no disco!");
			e.printStackTrace();
		}
	}

	public Aluno buscaProximo(Aluno alunoAntigo1) {
		Aluno aluno = null;

		try (FileInputStream fis = new FileInputStream(file); ObjectInputStream inputFile = new ObjectInputStream(fis)) {
			while (fis.available() > 0) {
				aluno = (Aluno) inputFile.readObject();

				if (aluno.equals(alunoAntigo1)) {
					aluno = (Aluno) inputFile.readObject();
					return aluno;
				}
			}
		} catch (Exception e) {
			return null;
		}
		return aluno;
	}
}
