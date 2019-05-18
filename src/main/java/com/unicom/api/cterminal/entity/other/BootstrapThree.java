package com.unicom.api.cterminal.entity.other;

import java.util.List;
import java.util.Map;

/**
 * 前台bootstrap需要的树
 * @author fuce 
 * @date: 2018年9月8日 下午10:47:07
 */
public class BootstrapThree {
	private String menu_name;//文字
	private String icon;//图标
	private String data;//数据
	private Integer menu_id;//id
	private String url;//url
	private boolean isChilren;
	private Map<String,Object> state;//选中参数
	private List<BootstrapThree> nodes;//子元素

	public boolean isChilren() {
		return isChilren;
	}

	public void setChilren(boolean chilren) {
		isChilren = chilren;
	}

	public String getText() {
		return menu_name;
	}
	public void setText(String text) {
		this.menu_name = text;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public List<BootstrapThree> getNodes() {
		return nodes;
	}
	public void setNodes(List<BootstrapThree> nodes) {
		this.nodes = nodes;
	}
	public BootstrapThree() {
		super();
	}
	public Integer getId() {
		return menu_id;
	}
	public void setId(Integer id) {
		this.menu_id = id;
	}
	public Map<String, Object> getState() {
		return state;
	}
	public void setState(Map<String, Object> state) {
		this.state = state;
	}

	/**
	 * 这个初始化new为可以设置默认选中
	 * @param text
	 * @param icon
	 * @param data
	 * @param id
	 * @param nodes
	 * @param state  传入{"checked":true} 
	 */
	public BootstrapThree(String menu_name, String icon, String data, Integer menu_id,
                          List<BootstrapThree> nodes, String url, Map<String, Object> state) {
		super();
		this.menu_name = menu_name;
		this.icon = icon;
		this.data = data;
		this.menu_id = menu_id;
		this.url=url;
		this.state = state;
		this.nodes = nodes;
	}
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public BootstrapThree(String menu_name, String icon, String data, Integer menu_id, String url,
                          List<BootstrapThree> nodes) {
		super();
		this.menu_name = menu_name;
		this.icon = icon;
		this.data = data;
		this.menu_id = menu_id;
		this.url=url;
		this.nodes = nodes;
	}
	
}
