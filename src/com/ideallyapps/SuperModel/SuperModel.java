package com.ideallyapps.SuperModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import android.text.TextUtils;

/**
 * SuperModel is an abstract class used for easy CRUD and model validation.
 * <p>
 * Usage:
 * <p>
 * <code>
 * Validation validation = new Validation();<br />
 * HashMap<String, Object> attrs = new HashMap<String, Object>();<br />
 * attrs.put(SuperModel.VALUE, exercise);<br />
 * attrs.put(SuperModel.MSG, "Exercise is required.");<br /> 
 * validation = validatesPresenceOf(attrs);<br />
 * return validation;
 * </code>
 * 
 * @author davidwparker
 * @version 0.1
 */
public abstract class SuperModel {

	protected static final String VALUE = "VALUE";
	protected static final String MSG = "MSG";
	protected static final String MSG_MIN = "MSG_MIN";
	protected static final String MSG_MAX = "MSG_MAX";
	protected static final String FORMAT = "FORMAT";
	protected static final String MIN = "MIN";
	protected static final String MAX = "MAX";
	protected boolean createMode = true;

	/*******************************
	 * CRUD
	 *******************************/
	public boolean save() {
		if (createMode)
			return this.create();
		else
			return this.update();
	}

	protected abstract boolean create();

	protected abstract boolean update();

	protected abstract boolean delete();

	/*******************************
	 * VALIDATIONS
	 *******************************/
	protected abstract Validation validate();

	/**
	 * Encapsulates the pattern of testing a date's format
	 * <p>
	 * Requires attributes: VALUE, FORMAT, MSG
	 * </p>
	 * 
	 * @param attrs
	 * @return Validation
	 */
	protected Validation validatesDateFormatOf(final HashMap<String, Object> attrs) {
		final Validation validation = new Validation();
		final String format = (String) attrs.get(FORMAT);
		try {
			(new SimpleDateFormat(format)).parse((String) attrs.get(VALUE));
		} catch (ParseException e) {
			validation.setHasErrors(true);
			validation.setMsgString(String.format((String) attrs.get(MSG), format));
		}
		return validation;
	}

	/**
	 * Encapsulates the pattern of testing whether a value is numerical. Optionally allows testing
	 * of min and max values for the numerical value
	 * <p>
	 * Requires attributes: VALUE, MSG
	 * </p>
	 * <p>
	 * Optional attributes: ALLOW_BLANK; MIN and MSG_MIN; MAX and MSG_MAX
	 * </p>
	 * 
	 * @param attrs
	 * @return Validation
	 */
	protected Validation validatesNumericalityOf(final HashMap<String, Object> attrs) {
		final Validation validation = new Validation();
		final String value = (String) attrs.get(VALUE);
		final Integer min = (Integer) attrs.get(MIN);
		final Integer max = (Integer) attrs.get(MAX);
		if (!TextUtils.isDigitsOnly(value)) {
			validation.setHasErrors(true);
			validation.setMsgString((String) attrs.get(MSG));
			return validation;
		}
		if (min != null
				&& !TextUtils.isEmpty(value) && Integer.valueOf(value) < Integer.valueOf(min)) {
			validation.setHasErrors(true);
			validation.setMsgString((String) attrs.get(MSG_MIN));
			return validation;
		}
		if (max != null
				&& !TextUtils.isEmpty(value) && Integer.valueOf(value) > Integer.valueOf(max)) {
			validation.setHasErrors(true);
			validation.setMsgString((String) attrs.get(MSG_MAX));
			return validation;
		}
		return validation;
	}

	/**
	 * Encapsulates the pattern of testing that a value is not empty (it is present)
	 * <p>
	 * Required attributes: VALUE, MSG
	 * </p>
	 * 
	 * @param attrs
	 * @return Validation
	 */
	protected Validation validatesPresenceOf(final HashMap<String, Object> attrs) {
		final Validation validation = new Validation();
		final String value = (String) attrs.get(VALUE);
		if (TextUtils.isEmpty(value)) {
			validation.setHasErrors(true);
			validation.setMsgString((String) attrs.get(MSG));
		}
		return validation;
	}

	public boolean getCreateMode() {
		return createMode;
	}

	public void setCreateMode(boolean mode) {
		this.createMode = mode;
	}

}
