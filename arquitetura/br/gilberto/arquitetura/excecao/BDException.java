package br.gilberto.arquitetura.excecao;

public class BDException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public BDException(Exception e) {
		super(e);
	}

	public BDException(String msg) {
		super(msg);		
	}

	public BDException() {
		super("Erro de Daos");		
	}

}
