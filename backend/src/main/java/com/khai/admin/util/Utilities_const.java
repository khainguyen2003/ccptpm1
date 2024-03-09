package com.khai.admin.util;

public enum Utilities_const {
	UPLOAD_PATH("src/main/webapp/images"),
	USERMAIL_NAME("quyet3582@gmail.com"),
	USERMAIL_PASS("ahnagqoldkmumbxc"),
	MAIL_HOST("smtp.gmail.com"),
	MAIL_PORT("587"),
	ALERT_SUCCESS("success"),
	ALERT_ERROR("error"),
	ALERT_WARNING("warn"),
	SEPERATOR(";");
	
	
	public final String label;

    private Utilities_const(String label) {
        this.label = label;
    }
}
