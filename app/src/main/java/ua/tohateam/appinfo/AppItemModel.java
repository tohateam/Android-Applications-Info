package ua.tohateam.appinfo;

import android.content.pm.*;

public class AppItemModel
{
	ApplicationInfo applicationInfo;
	String label;
	Long date;
	Long size = -1L;

	public void setSize(Long size) {
		this.size = size;
	}

	public Long getSize() {
		return size;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Long getDate() {
		return date;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}


	public void setApplicationInfo(ApplicationInfo applicationInfo) {
		this.applicationInfo = applicationInfo;
	}

	public ApplicationInfo getApplicationInfo() {
		return applicationInfo;
	}
}
