package com.fattahi.general.utility;

import java.sql.Types;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomOracle10gDialect extends Oracle10gDialect {

   public CustomOracle10gDialect() {
      super();
      registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
      registerFunction("LISTAGG", new StandardSQLFunction("LISTAGG", StandardBasicTypes.STRING));
      registerKeyword("within");
   }

   @Override
   protected void registerFunctions() {
      super.registerFunctions();
      registerFunction("test", new StandardSQLFunction("test", StandardBasicTypes.BOOLEAN));
   }

}