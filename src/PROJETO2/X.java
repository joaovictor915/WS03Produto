package PROJETO2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class X {
	private int id;
	private String descricao;
	private float preco;
	private int qtd;
	private LocalDateTime fabricacao;	
	private LocalDate validade;
	
	public X() {
		id = -1;
		descricao = "";
		preco = 0.00F;
		qtd = 0;
		fabricacao = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		validade = LocalDate.now().plusMonths(1); // validade de 1 mes.
	}

	public X(int id, String descricao, float preco, int qtd, LocalDateTime fabricacao, LocalDate v) {
		setId(id);
		setDescricao(descricao);
		setPreco(preco);
		setqtd(qtd);
		setfabricacao(fabricacao);
		setvalidade(v);
	}		
	
	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public int getqtd() {
		return qtd;
	}
	
	public void setqtd(int qtd) {
		this.qtd = qtd;
	}
	
	public LocalDate getvalidade() {
		return validade;
	}

	public LocalDateTime getfabricacao() {
		return fabricacao;
	}

	public void setfabricacao(LocalDateTime fabricacao) {
		// Pega a Data Atual
		LocalDateTime agora = LocalDateTime.now();
		// Garante que a data de fabricação não pode ser futura
		if (agora.compareTo(fabricacao) >= 0)
			this.fabricacao = fabricacao;
	}

	public void setvalidade(LocalDate validade) {
		// a data de fabricação deve ser anterior é data de validade.
		if (getfabricacao().isBefore(validade.atStartOfDay()))
			this.validade = validade;
	}

	public boolean emValidade() {
		return LocalDateTime.now().isBefore(this.getvalidade().atTime(23, 59));
	}


	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Produto: " + descricao + "   Preço: R$" + preco + "   qtd.: " + qtd + "   Fabricação: "
				+ fabricacao  + "   Data de Validade: " + validade;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((X) obj).getID());
	}	
}