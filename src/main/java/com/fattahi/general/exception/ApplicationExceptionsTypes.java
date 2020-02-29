package com.fattahi.general.exception;

public enum ApplicationExceptionsTypes {

	/** اخطار */
	ALERT((byte) 1),

	/** تاییدیه */
	CONFIRM((byte) 6),

	/** خطا */
	ERROR((byte) 2),

	/** اطلاع */
	INFO((byte) 3),

	/** قفل کردن فرم */
	LOCK((byte) 7),

	/** سوال */
	QUESTION((byte) 4),

	/** هشدار */
	WARNING((byte) 5),

	/** عدم دسترسی */
	UNAUTHORIZED((byte)41);

	public static ApplicationExceptionsTypes getApplicationExceptionsTypes(Byte id) {
      for (ApplicationExceptionsTypes obj : ApplicationExceptionsTypes.values()) {
         if (obj.getValue().equals(id)) {
            return obj;
         }
      }
      return null;
   }

	private byte value;

	private ApplicationExceptionsTypes(Byte value) {
		this.value = value;
	}

	public Byte getValue() {
		return value;
	}

}
