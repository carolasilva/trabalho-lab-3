package models;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Aluno {
	private String nome;
	private long matricula;
	private double nota;
	private static final int NAMESIZE = 20; 
    public static final int DATASIZE = 56; //40 bytes (String 20 caracteres) + 8 bytes (long) + 8 bytes double
	
	public Aluno(String nome, long matricula, double nota) {
		this.nome = nome;
		this.matricula = matricula;
		this.nota = nota;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public long getMatricula() {
		return matricula;
	}
	
	public void setMatricula(long matricula) {
		this.matricula = matricula;
	}
	
	public double getNota() {
		return nota;
	}
	
	public void setNota(double nota) {
		this.nota = nota;
	}

	@Override
	public String toString() {
		return "Aluno [nome=" + nome + ", matricula=" + matricula + ", nota=" + nota + "]";
	}
		
	 public void saveData(RandomAccessFile arq) throws IOException{
		StringBuilder finalName = new StringBuilder(this.nome);
		finalName.setLength(NAMESIZE);
		arq.writeLong(this.matricula);
		arq.writeChars(finalName.toString());   
		arq.writeDouble(this.getNota());
	 }

	 public static Aluno readData(RandomAccessFile arq) throws IOException{
		Long matricula = arq.readLong();
		
        char name[] = new char[NAMESIZE];
        for (int i=0; i<NAMESIZE; i++) {
          name[i] = arq.readChar();
        }
        String nome = new String(name);
        nome = nome.trim();
        
        double nota = arq.readDouble();
        Aluno aluno = new Aluno(nome, matricula, nota);
        return aluno;
    }
}
