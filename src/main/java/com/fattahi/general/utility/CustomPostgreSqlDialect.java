package com.fattahi.general.utility;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL81Dialect;
import org.hibernate.dialect.function.NoArgSQLFunction;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomPostgreSqlDialect extends PostgreSQL81Dialect {
   public CustomPostgreSqlDialect() {
      super();
      registerFunction("sysdate", new NoArgSQLFunction("current_date", StandardBasicTypes.DATE, false));
      registerFunction("nvl", new StandardSQLFunction("coalesce"));
      registerColumnType(Types.CHAR, "varchar($l)");
      registerColumnType(Types.TINYINT, "smallint");
   }
}
