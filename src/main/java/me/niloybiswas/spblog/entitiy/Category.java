package me.niloybiswas.spblog.entitiy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "categories")
@DynamicInsert
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private BigInteger id;
	
	@Column(name = "title", nullable = false)
	private String categoryTitle;
	
	@Column(name = "description", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'John Snow'")
	private String categoryDescription;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();

	
}
