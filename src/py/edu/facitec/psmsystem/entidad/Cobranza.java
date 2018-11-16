package py.edu.facitec.psmsystem.entidad;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="tb_cobranza")
public class Cobranza {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(strategy="increment",name="increment")
	@Column(name="cob_id")
	private int id;
	@Column(name="cob_fecha_cobro", nullable=false)
	private Date fechaCobro;
	@Column(name="cob_valor_cobro", nullable=false)
	private double valorCobro;
	@Column(name = "cob_estado", nullable = false)
	private boolean estado;

	@OneToMany(mappedBy="cobranza",fetch=FetchType.EAGER)
	private List<DeudaCliente> deudaClientes;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFechaCobro() {
		return fechaCobro;
	}
	public void setFechaCobro(Date fechaCobro) {
		this.fechaCobro = fechaCobro;
	}
	public double getValorCobro() {
		return valorCobro;
	}
	public void setValorCobro(double valorCobro) {
		this.valorCobro = valorCobro;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public List<DeudaCliente> getDeudaClientes() {
		return deudaClientes;
	}
	public void setDeudaClientes(List<DeudaCliente> deudaClientes) {
		this.deudaClientes = deudaClientes;
	}
}
