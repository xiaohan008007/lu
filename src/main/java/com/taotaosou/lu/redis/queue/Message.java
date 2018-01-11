package com.taotaosou.lu.redis.queue;

import java.io.Serializable;

/**
 * Created by Kinglf on 2016/10/17.
 */
public class Message implements Serializable {

	private static final long serialVersionUID = -389326121047047723L;
	private int id;
	private String content;

	public Message(int id, String content) {
		this.id = id;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}