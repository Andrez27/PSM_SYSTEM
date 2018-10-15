package py.edu.facitec.psmsystem.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_cliente")
public class Cliente {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(strategy = "increment", name = "increment")
	@Column(name="cli_id")
	private int id;

	@Column(name="cli_nombre", nullable=false, length=50)
	private String nombre;

	@Column(name="cli_documento", nullable=false, unique=true, length=20)
	private String documento;

	@Column(name="cli_telefono", nullable=false, length=20)
	private String telefono;

	@Column(name="cli_domicilio", nullable=false, length=100)
	private String domicilio;

	@Column(name="cli_email", length=50)
	private String email;


	@OneToMany(mappedBy="cliente")
	private List<Empeno>empenos;


	public void setEmpenhos(List<Empeno> empenos) {
		this.empenos = empenos;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String direccion) {
		this.domicilio = direccion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Empeno> getEmpenos() {
		return empenos;
	}


}
