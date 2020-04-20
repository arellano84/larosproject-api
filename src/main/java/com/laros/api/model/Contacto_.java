package com.laros.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Contacto.class)
public abstract class Contacto_ {

	public static volatile SingularAttribute<Contacto, Long> codigo;
	public static volatile SingularAttribute<Contacto, Persona> persona;
	public static volatile SingularAttribute<Contacto, String> telefono;
	public static volatile SingularAttribute<Contacto, String> nombre;
	public static volatile SingularAttribute<Contacto, String> email;

}

