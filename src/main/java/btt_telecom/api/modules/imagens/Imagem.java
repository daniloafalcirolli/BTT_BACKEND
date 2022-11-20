package btt_telecom.api.modules.imagens;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import btt_telecom.api.modules.provedores.model.ImagemProvedor;

@Entity
@Table(name = "imagens")
public class Imagem {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private Long id;
	
	private String fileName;
	
	private String fileType;
	
	@Lob
	private String content;
	
	@OneToOne
	private ImagemProvedor imagem_provedor;
	
	public Imagem() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public ImagemProvedor getImagem_provedor() {
		return imagem_provedor;
	}


	public void setImagem_provedor(ImagemProvedor imagem_provedor) {
		this.imagem_provedor = imagem_provedor;
	}
}
