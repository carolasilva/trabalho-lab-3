package arquivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import models.LinhaDoIndex;

public class ArquivoIndice {
	private File file;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;
	String nomeArquivo;
	
	List<LinhaDoIndex> indice = new ArrayList<LinhaDoIndex>();
	
	public ArquivoIndice(String nomeArquivo) {
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

	public List<LinhaDoIndex> getIndice() {
		return indice;
	}

	public void addIndice(LinhaDoIndex linha) {
		this.indice.add(linha);
	}
	
	public void salvarIndiceCompleto() {
		try {
			for(LinhaDoIndex linha : indice){
	        	this.outputFile.writeObject(linha);      //escreve (sequencialmente) no formatio binÃ¡rio
	        }
	        
		} catch (IOException e) {
			System.out.println("Problema com o arquivo de índice: " + e.getMessage());
		}
	}
	
//	private void lerArquivoIndiceCompleto() {
//		LinhaDoIndex linha = null;
//		
//		try (FileInputStream fis = new FileInputStream(file); ObjectInputStream inputFile = new ObjectInputStream(fis)) {
//			while (fis.available() > 0) {
//				linha = (LinhaDoIndex) inputFile.readObject();
//				indice.add(linha);
//				
//			}
//		} catch (Exception e) {
//			System.out.println("ERRO ao gravar pessoa no disco!");
//			e.printStackTrace();
//		}
//	}
	
	public int procurarPorMatricula (Long matricula) {
		LinhaDoIndex linha = null;

		try (FileInputStream fis = new FileInputStream(file); ObjectInputStream inputFile = new ObjectInputStream(fis)) {
			while (fis.available() > 0) {
				linha = (LinhaDoIndex) inputFile.readObject();

				if (matricula == linha.getMatricula()) {
					return (int) linha.getPosicao();
				}
			}
		} catch (Exception e) {
			System.out.println("ERRO ao ler a matricula '" + matricula + "' do disco!");
			e.printStackTrace();
		}
		return -1;
	}

}
