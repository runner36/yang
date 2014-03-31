package com.winchannel.base.model;

/**
 * BaseMessResId generated by MyEclipse Persistence Tools
 */

public class BaseMessResId implements java.io.Serializable {

	// Fields

	private String messLanguage;

	private String messBundle;

	private String messKey;

	// Constructors

	/** default constructor */
	public BaseMessResId() {
	}

	/** full constructor */
	public BaseMessResId(String messLanguage, String messBundle, String messKey) {
		this.messLanguage = messLanguage;
		this.messBundle = messBundle;
		this.messKey = messKey;
	}

	// Property accessors

	public String getMessLanguage() {
		return this.messLanguage;
	}

	public void setMessLanguage(String messLanguage) {
		this.messLanguage = messLanguage;
	}

	public String getMessBundle() {
		return this.messBundle;
	}

	public void setMessBundle(String messBundle) {
		this.messBundle = messBundle;
	}

	public String getMessKey() {
		return this.messKey;
	}

	public void setMessKey(String messKey) {
		this.messKey = messKey;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BaseMessResId))
			return false;
		BaseMessResId castOther = (BaseMessResId) other;

		return ((this.getMessLanguage() == castOther.getMessLanguage()) || (this.getMessLanguage() != null
				&& castOther.getMessLanguage() != null && this.getMessLanguage().equals(castOther.getMessLanguage())))
				&& ((this.getMessBundle() == castOther.getMessBundle()) || (this.getMessBundle() != null
						&& castOther.getMessBundle() != null && this.getMessBundle().equals(castOther.getMessBundle())))
				&& ((this.getMessKey() == castOther.getMessKey()) || (this.getMessKey() != null && castOther.getMessKey() != null && this
						.getMessKey().equals(castOther.getMessKey())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getMessLanguage() == null ? 0 : this.getMessLanguage().hashCode());
		result = 37 * result + (getMessBundle() == null ? 0 : this.getMessBundle().hashCode());
		result = 37 * result + (getMessKey() == null ? 0 : this.getMessKey().hashCode());
		return result;
	}

}