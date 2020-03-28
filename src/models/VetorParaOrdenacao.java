package models;

public class VetorParaOrdenacao {
	private Aluno aluno;
	private boolean menor;
	
	public VetorParaOrdenacao() {
		this.aluno = null;
		this.menor = false;
	}
	
	public String getNome() {
		return aluno.getNome();
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public boolean isMenor() {
		return menor;
	}
	public void setMenor(boolean menor) {
		this.menor = menor;
	}

	public Aluno getAluno() {
		return aluno;
	}
	
	
}
