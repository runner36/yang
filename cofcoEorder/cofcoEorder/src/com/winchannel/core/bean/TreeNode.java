package com.winchannel.core.bean;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {
	
	private TreeNode<T> parent;
	private T entity;
	private List<TreeNode<T>> children;
	
	public TreeNode(T entity) {
		this.entity = entity;
	}

	public void addChild(TreeNode<T> child) {
		if (children == null) {
			children = new ArrayList<TreeNode<T>>();
		}
		children.add(child);
		child.setParent(this);
	}
	
	public void generateEntityList(List<T> list) {
		if (entity != null) {
			list.add(entity);
		}
		if (children != null) {
			for (TreeNode<T> node : children) {
				node.generateEntityList(list);
			}
		}
		
	}
	
	public void generateLeafEntityList(List<T> list) {
		if (entity != null && children == null) {
			list.add(entity);
		}
		if (children != null) {
			for (TreeNode<T> node : children) {
				node.generateLeafEntityList(list);
			}
		}
		
	}
	

	public List<TreeNode<T>> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode<T>> children) {
		this.children = children;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public void setParent(TreeNode<T> parent) {
		this.parent = parent;
	}
	
	
	

}
