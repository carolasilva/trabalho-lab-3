package models;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class LinhaDoIndex implements Serializable{

	private static final long serialVersionUID = 1L;
	public static final Long DATASIZE = 16L;
	private long matricula;
	private long posicao;

	public LinhaDoIndex(long matricula, long posicao) {
		this.matricula = matricula;
		this.posicao = posicao;
	}
	public LinhaDoIndex(Long matricula) {
		this.matricula = matricula;
	}
	public long getMatricula() {
		return matricula;
	}
	public void setMatricula(long matricula) {
		this.matricula = matricula;
	}
	public long getPosicao() {
		return posicao;
	}
	public void setPosicao(long posicao) {
		this.posicao = posicao;
	}
	public void saveData(RandomAccessFile arquivoAcessoAleatorio) {
		try {
			arquivoAcessoAleatorio.writeLong(this.matricula);
			arquivoAcessoAleatorio.writeLong(this.posicao);
		} catch (IOException e) {
			System.out.println("Erro com o arquivo: " + e.getMessage());
		}
		
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (matricula ^ (matricula >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinhaDoIndex other = (LinhaDoIndex) obj;
		if (matricula != other.matricula)
			return false;
		return true;
	}
	
	
}
