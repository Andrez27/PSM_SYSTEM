package py.edu.facitec.psmsystem.entidad;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_empeno")
public class Empeno {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(strategy = "increment", name = "increment")
	@Column(name="emp_id")
	private int id;

	@Column(name = "emp_fecha_dia", nullable = false)
	private Date fechaDia;

	@Column(name = "emp_fecha_vencimiento", nullable = false)
	private Date fechaVencimiento;

	@Column(name = "emp_valor_total", nullable = false)
	private double valorTotal;

	@Column(name = "emp_estado", nullable = false)
	private int estado;

	@Column(name = "emp_obs")
	private String observacion;

	@ManyToOne
	@JoinColumn(name = "Clienteid", referencedColumnName = "cli_id")
	private Cliente cliente;

	@OneToMany(mappedBy = "empeno", fetch = FetchType.LAZY)
	private List<DeudaCliente> deudaClientes;

	@OneToOne(cascade=CascadeType.ALL)
	private Producto producto;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setDeudaClientes(List<DeudaCliente> deudaClientes) {
		this.deudaClientes = deudaClientes;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Date getFechaDia() {
		return fechaDia;
	}
	public void setFechaDia(Date fechaDia) {
		this.fechaDia = fechaDia;
	}
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public List<DeudaCliente> getDeudaClientes() {
		return deudaClientes;
	}
	public Producto getProducto() {
		return producto;
	}


}
